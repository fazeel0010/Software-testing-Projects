const express = require('express');
const { loadHandlers, getHandler, getRegisteredTypes } = require('./registry');

const app = express();
const PORT = 8001;

// In-memory store: entrypoint name -> { type, data, complete, bits, ... }
const store = new Map();

app.use(express.json());

// POST /setup - register a new data leak session
app.post('/setup', (req, res) => {
  const { entrypoint, type } = req.body;

  if (!entrypoint || !type) {
    return res.status(400).json({ error: 'Missing entrypoint or type' });
  }

  const handler = getHandler(type);
  if (!handler) {
    return res.status(400).json({
      error: `Unknown leak type: ${type}`,
      available: getRegisteredTypes()
    });
  }

  store.set(entrypoint, { type, data: '', complete: false });
  res.json({ status: 'ok', entrypoint, type });
});

// PUT /:entrypoint - receive leaked data
app.put('/:entrypoint', (req, res) => {
  const { entrypoint } = req.params;
  const session = store.get(entrypoint);



  if (!session) {
    return res.status(404).json({ error: 'Entrypoint not found' });
  }

  const handler = getHandler(session.type);
  if (!handler) {
    return res.status(500).json({ error: 'No handler for type: ' + session.type });
  }

  console.log(session);
  const result = handler.handlePut(req, session);
  res.status(result.status).json(result.body);
});

// GET /:entrypoint - retrieve leaked data
app.get('/:entrypoint', (req, res) => {
  const { entrypoint } = req.params;
  const session = store.get(entrypoint);

  console.log(store);
  console.log(entrypoint);
    console.log(session);
  if (!session) {
    return res.status(404).json({ error: 'Entrypoint not found' });
  }

  const handler = getHandler(session.type);
  if (!handler) {
    return res.status(500).json({ error: 'No handler for type: ' + session.type });
  }

  const result = handler.handleGet(session);
  res.status(result.status).json(result.body);
});

// Load all leak handler plugins, then start
loadHandlers();
console.log(`Registered leak types: ${getRegisteredTypes().join(', ')}`);

app.listen(PORT, () => {
  console.log(`Data leak server running on port ${PORT}`);
});
