const name = 'dedicatedheader';

/**
 * Filter for the dedicated-header leak method.
 * Removes the X-dataleak header from the request before forwarding,
 * which prevents the data from reaching the malicious server.
 */
function filterRequest(req, headers) {
  const filtered = headers.filter(
    ([key]) => key.toLowerCase() !== 'x-dataleak'
  );
  return filtered;
}

module.exports = { name, filterRequest };
