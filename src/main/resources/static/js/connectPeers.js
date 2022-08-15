// 영상이 들어갈 공간
const showings = document.getElementsByClassName('showing');
// showingsCount 0번째는 본인이 들어갈 위치. 1번째부터 상대방 영상.
var showingsCount = 1;
// 임시로 영상을 추가할때 사용됨(실험용)
const testgrid = document.getElementById('test_grid');

const myVideo = document.createElement('video')
myVideo.style.width = '200px';
myVideo.style.height = '100%';

const roo_name = document.getElementById('room_name').value;

const socket = io('https://192.168.0.5:3000', {
    transports: ['websocket']
})
const myPeer = new Peer({
    config: {'iceServers': [
        { url: 'stun:stun.xten.com' },
      ]}
}, {
    host: '/',
    port: 3001,
    secure: false
})

navigator.mediaDevices.getUserMedia({
    video: true,
    audio: true
}).then(stream => {
    addVideoStream(myVideo, stream, showings[0])

    myPeer.on('call', async call => {
        console.log('call');
        call.answer(stream)
        const video = document.createElement('video')
        video.style.width = '200px';
        // video.style.height = '100%';

        // 다른사람의 스트림(영상, 소리)를 받아옵니다.
        call.on('stream', async(receivedStream) => {
            console.log('receivedStream', showingsCount)
            addVideoStream(video, receivedStream, showings[showingsCount++])
        })
    })

    // 들어온 사람과 통신을 시도합니다.
    socket.on('user-connected', async userId => {
        console.log('userConnected', userId)
        await connectToNewUser(userId, stream)
    })
})

socket.on('user-disconnected', async userId => {
    showingsCount--
    console.log('userDisconnected', userId, showingsCount)
    if (peers[userId]) peers[userId].close()
})

// peer start.
myPeer.on('open', id => {
    console.log('join-room', roo_name, id)
    socket.emit('join-room', roo_name, id)
})

myVideo.muted = true
const peers = {}

// 이슈: 재 접속시 스트림(상대 영상)의 신호가 오지않는 경우가 발생.
// 당장 시연 자체는 가능한 수준..

async function connectToNewUser(userId, stream) {
    console.log('connectToNewUser', userId)
    const call = await myPeer.call(userId, stream)
    console.log('call..')

    const video = document.createElement('video')
    video.style.width = '100%';
    video.style.height = '100%';

    call.on('stream', async(userVideoStream) => {
        console.log('connectedUserStream', showingsCount)
        addVideoStream(video, userVideoStream, showings[showingsCount++])
    })
    call.on('close', () => {
        console.log('close')
        video.remove()
    })
    
    peers[userId] = call
}

function addVideoStream(video, stream, grid) {
    console.log('addVideoStream', showingsCount);
    video.srcObject = stream
    video.addEventListener('loadedmetadata', () => {
        video.play()
    })
    console.log('append video', video)
    grid.append(video)
}