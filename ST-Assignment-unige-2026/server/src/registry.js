const fs = require('fs');
const path = require('path');

const leakHandlers = {};

function registerHandler(name, handler) {
  leakHandlers[name] = handler;
}

function getHandler(name) {
  return leakHandlers[name] || null;
}

function getRegisteredTypes() {
  return Object.keys(leakHandlers);
}

// Auto-discover and load all handler modules from the leaks/ directory.
// Each module must export: { name, handlePut(req, session), handleGet(session) }
function loadHandlers() {
  const leaksDir = path.join(__dirname, 'leaks');
  const files = fs.readdirSync(leaksDir).filter(f => f.endsWith('.js'));
  for (const file of files) {
    const handler = require(path.join(leaksDir, file));
    if (handler.name && handler.handlePut && handler.handleGet) {
      registerHandler(handler.name, handler);
    }
  }
}

module.exports = { registerHandler, getHandler, getRegisteredTypes, loadHandlers };
