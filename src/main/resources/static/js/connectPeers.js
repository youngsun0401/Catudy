const showings = document.getElementsByClassName('showing');
var videoCount = 0;
console.log(showings);

const videoGrid = document.getElementById('video-grid')
const myVideo = document.createElement('video')
myVideo.style.width = '100%';
myVideo.style.height = '100%';

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
    console.log('join-room', roo_name, userId);
    socket.emit('join-room', roo_name, userId)
})

let myStream;
navigator.mediaDevices.getUserMedia({
    video: true,
    audio: true
}).then(stream => {
    myStream = stream;
    addVideoStream(myVideo, stream)
})

myPeer.on('call', call => {
    call.answer(myStream)
    const video = document.createElement('video')
    video.style.width = '100%';
    video.style.height = '100%';
    call.on('stream', userVideoStream => {
        addVideoStream(video, userVideoStream)
    })
})

myVideo.muted = true
const peers = {}

socket.on('user-disconnected', userId => {
    if (peers[userId]) peers[userId].close()
})

socket.on('user-connected', userId => {
    connectToNewUser(userId, myStream)
})

myPeer.on('open', id => {
    userId = id
})

function connectToNewUser(userId, stream) {
    const call = myPeer.call(userId, stream)
    const video = document.createElement('video')
    video.style.width = '100%';
    video.style.height = '100%';
    call.on('stream', userVideoStream => {
        addVideoStream(video, userVideoStream)
    })
    call.on('close', () => {
        video.remove()
    })

    peers[userId] = call
}

function addVideoStream(video, stream) {
    console.log('addVideoStream', videoCount, video, stream);
    video.srcObject = stream
    video.addEventListener('loadedmetadata', () => {
        video.play()
    })
    showings[videoCount++].append(video);
    // videoGrid.append(video)
}