const name = 'headersorder';

/**
 * Handles a PUT request for the headers-order leak method.
 *
 * Each PUT transfers a single bit. The 3rd and 4th header lines of the raw
 * HTTP request (i.e. the 2nd and 3rd headers after Host) are compared:
 *   - if header3 name < header4 name (string comparison) => bit 0
 *   - otherwise => bit 1
 *
 * Bits are accumulated into a buffer. Every 8 bits form one character of
 * the base64-encoded payload. Eight consecutive 0-bits (a null byte) signals
 * the end of transmission.
 */
function handlePut(req, session) {
  // rawHeaders is [name, value, name, value, ...] preserving order
  const rawHeaders = req.rawHeaders;

  // Build ordered list of header names (skip the request line, which isn't in rawHeaders).
  // rawHeaders pairs: index 0,1 = first header; 2,3 = second header; etc.
  const headerNames = [];
  for (let i = 0; i < rawHeaders.length; i += 2) {
    const header = rawHeaders[i].toLowerCase();

    // if (header !== 'user-agent' && header !== 'accept') {
      headerNames.push(rawHeaders[i]);
    // }
  }

  // Row 1 = request line (not in rawHeaders), Row 2 = Host (index 0).
  // Rows 3 and 4 are the 1st and 2nd headers after Host => indices 1 and 2.
  if (headerNames.length < 3) {
    return { status: 400, body: { error: 'Not enough headers to determine bit' } };
  }

  const header3 = headerNames[1];
  const header4 = headerNames[2];
  
  const header5 = headerNames[3];
  const header6 = headerNames[4];
  
  const bit = header3 < header4 ? '0' : '1';
  const bit2 = header5 < header6 ? '0' : '1';
//01100001 01100010 00000000
  console.log("RAW HEADERS:", req.rawHeaders);
  console.log("HEADER NAMES:", headerNames);
  console.log("HEADER3:", header3);
  console.log("HEADER4:", header4);
  console.log("HEADER5:", header5);
  console.log("HEADER6:", header6);

  console.log("BIT:", bit);
  console.log("BIT2:", bit2);

  if (!session.bits) {  
    session.bits = '';
  }

  session.bits += bit;
  session.bits += bit2;

  // Check for null-byte terminator (8 consecutive zeros)
  if (session.bits.length >= 8 && session.bits.slice(-8) === '00000000') {
    // Remove the trailing null byte
    const payload = session.bits.slice(0, -8);
    // Convert bit string to characters (each 8 bits = one ASCII char of the base64 string)
    let decoded = '';
    for (let i = 0; i < payload.length; i += 8) {
      const byte = payload.slice(i, i + 8);
      if (byte.length === 8) {
        decoded += String.fromCharCode(parseInt(byte, 2));
      }
    }
    session.data = decoded;
    session.complete = true;
  }

  



  return { status: 200, body: { status: 'ok', bit, bit2`        ` } };
}

/**
 * Handles a GET request: returns the leaked data if the transmission is complete.
 */
function handleGet(session) {
  if (!session.complete) {
    return { status: 404, body: { error: 'Data not available yet' } };
  }
  return { status: 200, body: { data: session.data } };
}

module.exports = { name, handlePut, handleGet };
