const name = 'dedicatedheader';

/**
 * Handles a PUT request for the dedicated-header leak method.
 * Reads the base64-encoded data from the X-dataleak header and stores it.
 */
function handlePut(req, session) {
  const value = req.headers['x-dataleak'];
  if (!value) {
    return { status: 400, body: { error: 'Missing X-dataleak header' } };
  }
  session.data = value;
  session.complete = true;
  return { status: 200, body: { status: 'ok' } };
}

/**
 * Handles a GET request: returns the leaked data if the session is complete.
 */
function handleGet(session) {
  if (!session.complete) {
    return { status: 404, body: { error: 'Data not available yet' } };
  }
  return { status: 200, body: { data: session.data } };
}

module.exports = { name, handlePut, handleGet };
