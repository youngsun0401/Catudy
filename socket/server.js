const { readFileSync } = require("fs");
const { createServer } = require("https");
const express = require("express");
const { Server } = require("socket.io");

const app = express();

// 인증키 생성 아래 링크 참조
// https://letsencrypt.org/docs/certificates-for-localhost/
// openssl req -x509 -out localhost.crt -keyout localhost.key -newkey rsa:2048 -nodes -sha256 -subj '/CN=localhost' -extensions EXT -config <( \printf "[dn]\nCN=localhost\n[req]\ndistinguished_name = dn\n[EXT]\nsubjectAltName=DNS:localhost\nkeyUsage=digitalSignature\nextendedKeyUsage=serverAuth")

const httpServer = createServer({
    key: readFileSync("localhost.key"),
    cert: readFileSync("localhost.crt")
}, app);

const io = new Server(httpServer, { /* options */ });

app.get('/', (req, res) => {
    res.send('hello world');
})

io.on('connection', socket => {
    socket.on('join-room', (roomId, userId) => {
        console.log('join-room', roomId, userId)
        socket.join(roomId, userId);
        // socket.to(roomId).broadcast.emit('user-connected', userId)
        socket.to(roomId).emit('user-connected', userId)

        socket.on('disconnect', () => {
            console.log('disconnect')
            // socket.to(roomId).broadcast.emit('user-disconnected', userId)
            socket.to(roomId).emit('user-disconnected', userId)
        })
    })
})

httpServer.listen(3000);