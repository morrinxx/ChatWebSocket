"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var WebSocket = require("ws");
// Create WebSockets server listening on port 3000
var wss = new WebSocket.Server({ port: 3000 });
wss.on('connection', function (ws) {
    // Called whenever a new client connects
    // Add handler for incoming messages
    ws.on('message', function (message) {
        console.log('received: %s', message);
        broadcast(message.toString()); // send to all
    });
    // Send text message to new client
    ws.send('Welcome!');
});
// send message to all connected ws-clients
function broadcast(data) {
    // Iterate over all clients
    wss.clients.forEach(function (client) {
        // Send if connection is open
        if (client.readyState === WebSocket.OPEN)
            client.send(data);
    });
}
