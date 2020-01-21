 import * as WebSocket from 'ws';

// Create WebSockets server listening on port 3000
const wss = new WebSocket.Server({port: 3000});

wss.on('connection', ws => {
  // Called whenever a new client connects

  // Add handler for incoming messages
  ws.on('message', message => {console.log('received: %s', message);
                               broadcast(message.toString());  // send to all
                              });

  // Send text message to new client
  ws.send('Welcome!');
});

// send message to all connected ws-clients
function broadcast(data: string) {
  // Iterate over all clients
  wss.clients.forEach(client => {
    // Send if connection is open
    if (client.readyState === WebSocket.OPEN) client.send(data);
  });
}