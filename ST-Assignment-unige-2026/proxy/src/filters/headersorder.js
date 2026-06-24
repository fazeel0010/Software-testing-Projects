const name = 'headersorder';

/**
 * Filter for the headers-order leak method.
 * Alphabetically sorts non-essential headers (everything except Host and
 * Connection) so the attacker's carefully chosen ordering is destroyed,
 * making the encoded bit meaningless.
 */
function filterRequest(req, headers) {

  console.log("Filteration:",req.rawHeaders);
  const preserved = [];
  const sortable = [];

  for (const [key, value] of headers) {
    const lower = key.toLowerCase();
    if (lower === 'host' || lower === 'connection' || lower === 'content-length') {
      preserved.push([key, value]);
    } else {
      sortable.push([key, value]);
    }
  }

  sortable.sort((a, b) => a[0].toLowerCase().localeCompare(b[0].toLowerCase()));

  return [...preserved, ...sortable];
}

module.exports = { name, filterRequest };
