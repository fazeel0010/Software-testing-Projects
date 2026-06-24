const fs = require('fs');
const path = require('path');

const filters = {};

function registerFilter(name, filter) {
  filters[name] = filter;
}

function getFilter(name) {
  return filters[name] || null;
}

function getRegisteredTypes() {
  return Object.keys(filters);
}

// Auto-discover and load all filter modules from the filters/ directory.
// Each module must export: { name, filterRequest(proxyReq, req, options) }
function loadFilters() {
  const filtersDir = path.join(__dirname, 'filters');
  const files = fs.readdirSync(filtersDir).filter(f => f.endsWith('.js'));
  for (const file of files) {
    const filter = require(path.join(filtersDir, file));
    if (filter.name && filter.filterRequest) {
      registerFilter(filter.name, filter);
    }
  }
}

module.exports = { registerFilter, getFilter, getRegisteredTypes, loadFilters };
