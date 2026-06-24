const http = require('http');
const { loadFilters, getFilter, getRegisteredTypes } = require('./registry');

const TARGET_HOST = process.env.TARGET_HOST || 'server';
const TARGET_PORT = parseInt(process.env.TARGET_PORT || '8001', 10);
const PORT = 8000;
//The proxy intercepts requests, identifies the leak type, applies the appropriate filter, and then forwards the modified 
// request to the malicious server.
// Track known entrypoints and their types (snooped from /setup POST requests)
const entrypoints = new Map();

function parseBody(req) {
  return new Promise((resolve, reject) => {
    const chunks = [];
    req.on('data', chunk => chunks.push(chunk));
    req.on('end', () => {
      try {
        resolve(JSON.parse(Buffer.concat(chunks).toString()));
      } catch {
        resolve(null);
      }
    });
    req.on('error', reject);
  });
}

/**
 * Build an ordered array of [name, value] pairs from the raw incoming headers,
 * skipping the first header (typically Host, index 0 in rawHeaders).
 */
function getRawHeaderPairs(req) {
  const pairs = [];
  for (let i = 0; i < req.rawHeaders.length; i += 2) {
    pairs.push([req.rawHeaders[i], req.rawHeaders[i + 1]]);
  }
  return pairs;
}

const server = http.createServer(async (req, res) => {
  const url = new URL(req.url, `http://${req.headers.host}`);
  const pathname = url.pathname;
console.log("\nfilters:",req);

console.log("\n\ndata:",url);
console.log("\n\ndata:",pathname);
  let bodyBuffer = null;
  let headerPairs = getRawHeaderPairs(req);

  // Snoop on /setup to learn about entrypoints
  if (req.method === 'POST' && pathname === '/setup') {
    const body = await parseBody(req);
    if (body && body.entrypoint && body.type) {
      entrypoints.set(body.entrypoint, body.type);
    }
    bodyBuffer = Buffer.from(JSON.stringify(body));
  }

  // Apply filter on PUT to known entrypoints
  if (req.method === 'PUT') {
    const entrypoint = pathname.slice(1);
    const type = entrypoints.get(entrypoint);
    if (type) {
      const filter = getFilter(type);
      if (filter) {
        headerPairs = filter.filterRequest(req, headerPairs);
        console.log(`[BLOCKED] PUT /${entrypoint} - neutralized "${type}" leak attempt from ${req.socket.remoteAddress}`);
      }
    }
  }

  // If we haven't consumed the body yet, collect it for forwarding
  if (!bodyBuffer) {
    bodyBuffer = await new Promise((resolve, reject) => {
      const chunks = [];
      req.on('data', chunk => chunks.push(chunk));
      req.on('end', () => resolve(Buffer.concat(chunks)));
      req.on('error', reject);
    });
  }

  // Build outgoing headers from the (possibly filtered) pairs
  const outgoingHeaders = {};
  const headerOrder = [];
  for (const [key, value] of headerPairs) {
    const lower = key.toLowerCase();
    if (lower === 'host') {
      outgoingHeaders[key] = `${TARGET_HOST}:${TARGET_PORT}`;
    } else if (lower === 'content-length') {
      outgoingHeaders[key] = bodyBuffer.length.toString();
    } else {
      outgoingHeaders[key] = value;
    }
    headerOrder.push(key);
  }

  // Forward the request to the target server using raw http to preserve header order
  const options = {
    hostname: TARGET_HOST,
    port: TARGET_PORT,
    path: req.url,
    method: req.method,
    headers: outgoingHeaders,
  };

  const proxyReq = http.request(options, (proxyRes) => {
    res.writeHead(proxyRes.statusCode, proxyRes.headers);
    proxyRes.pipe(res);
  });

  proxyReq.on('error', (err) => {
    console.error('Proxy error:', err.message);
    res.writeHead(502, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ error: 'Bad gateway' }));
  });

  if (bodyBuffer.length > 0) {
    proxyReq.write(bodyBuffer);
  }
  proxyReq.end();
});

loadFilters();
console.log(`Registered filter types: ${getRegisteredTypes().join(', ')}`);

server.listen(PORT, () => {
  console.log(`Proxy running on port ${PORT}, forwarding to ${TARGET_HOST}:${TARGET_PORT}`);
});
