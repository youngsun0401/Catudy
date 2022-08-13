const { readFileSync } = require("fs");
const { createServer } = require("https");
const { Server } = require("socket.io");
const express = require("express");

const app = express();

const httpServer = createServer({
  key: readFileSync("key.pem"),
  cert: readFileSync("cert.pem")
}, app);

const io = new Server(httpServer, { /* options */ });

app.get('/', (req, res) => {
    res.send('hello world');
})

io.on('connection', socket => {
    socket.on('join-room', (roomId, userId) => {
        socket.join(roomId, userId);
        // socket.to(roomId).broadcast.emit('user-connected', userId)
        socket.broadcast.to(roomId).emit('user-connected', userId)

        socket.on('disconnect', () => {
            // socket.to(roomId).broadcast.emit('user-disconnected', userId)
            socket.broadcast.to(roomId).emit('user-disconnected', userId)
        })
    })
})

httpServer.listen(3000);