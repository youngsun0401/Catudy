const videoGrid = document.getElementById('video-grid')
const myVideo = document.createElement('video')

const socket = io('https://192.168.0.36:3000', {
    transports: ['websocket']
})
const myPeer = new Peer(undefined, {
    host: '/',
    port: 3001,
    secure: false
})

var userId;
const roo_name = document.getElementById('room_name').value;
document.getElementById('room_name_btn').addEventListener('click', () => {
    // const user_name = document.getElementById('name').value;
    console.log(roo_name, userId);
})

navigator.mediaDevices.getUserMedia({
    video: true,
    audio: true
}).then(stream => {
    addVideoStream(myVideo, stream)

    myPeer.on('call', call => {
        call.answer(stream)
        const video = document.createElement('video')
        call.on('stream', userVideoStream => {
            addVideoStream(video, userVideoStream)
        })
    })

    socket.on('user-connected', userId => {
        connectToNewUser(userId, stream)
    })
})
myVideo.muted = true
const peers = {}

socket.on('user-disconnected', userId => {
    if (peers[userId]) peers[userId].close()
})

myPeer.on('open', id => {
    userId = id;
    socket.emit('join-room', roo_name, userId)
})

function connectToNewUser(userId, stream) {
    const call = myPeer.call(userId, stream)
    const video = document.createElement('video')
    call.on('stream', userVideoStream => {
        addVideoStream(video, userVideoStream)
    })
    call.on('close', () => {
        video.remove()
    })

    peers[userId] = call
}

function addVideoStream(video, stream) {
    video.srcObject = stream
    video.addEventListener('loadedmetadata', () => {
        video.play()
    })
    videoGrid.append(video)
}