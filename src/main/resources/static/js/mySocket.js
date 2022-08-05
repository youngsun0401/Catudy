// const socket = io('wss://localhost:8000', { transports : ['websocket'] });
const socket = io('wss://192.168.10.97:8000', { transports : ['websocket'] });

const myFace = document.getElementById('myFace');
// 조작버튼
const muteBtn = document.getElementById('mute');
const cameraBtn = document.getElementById('camera');
const mikes = document.getElementById('mikes');
const camerasSelect = document.getElementById('cameras');

let myStream;
let roomName;
let myPeerConnection;

const welcome = document.getElementById('welcome');
const call = document.getElementById('call');

call.hidden = true;

async function getCameras() {
    try {
        //mdn
        // Older browsers might not implement mediaDevices at all, so we set an empty object first
        if (navigator.mediaDevices === undefined) {
            navigator.mediaDevices = {};
        }

        // Some browsers partially implement mediaDevices. We can't just assign an object
        // with getUserMedia as it would overwrite existing properties.
        // Here, we will just add the getUserMedia property if it's missing.
        if (navigator.mediaDevices.getUserMedia === undefined) {
            navigator.mediaDevices.getUserMedia = function (constraints) {

                // First get ahold of the legacy getUserMedia, if present
                var devices = navigator.webkitGetUserMedia || navigator.mozGetUserMedia;

                // Some browsers just don't implement it - return a rejected promise with an error
                // to keep a consistent interface
                if (!devices) {
                    return Promise.reject(new Error('getUserMedia is not implemented in this browser'));
                }

                // Otherwise, wrap the call to the old navigator.getUserMedia with a Promise
                return new Promise(function (resolve, reject) {
                    devices.call(navigator, constraints, resolve, reject);
                });
            }
        }

        //custom
        // 모든 디바이스들의 정보를 가져옵니다.
        const cameras = await navigator.mediaDevices.enumerateDevices()
            .then(function (devices) {
                // 가져온 디바이스들 중 비디오만 가져옵니다.
                return devices.filter(device => device.kind === 'videoinput');
            });
            // .filter(device => device.kind === 'videoinput');
            
        // 사용 가능한 카메라 중 첫번째로 초기화합니다.
        currentCam = myStream.getVideoTracks()[0];

        cameras.forEach(camera => {
            const option = document.createElement('option');
            // option에 값과 텍스트를 집어넣습니다.
            option.value = camera.deviceId;
            option.innerText = camera.label;

            // 반복문을 돌면서 카메라 중 선택된 카메라와 일치하면 
            if (currentCam.label === camera.label) {
                option.selected = true;
            }
            camerasSelect.appendChild(option);

        });
    } catch (err) {
        console.log(err);
    }
}

async function getMedia(deviceId) {
    // device가 검색되지 않을때 사용됨
    const initialConstrains = {
        audio: true,
        video: { facingMode: 'user' },
    };
    const cameraConstraints = {
        audio: true,
        video: { deviceId: { exact: deviceId } },
    }

    await navigator.mediaDevices.getUserMedia
    (deviceId ? cameraConstraints : initialConstrains)
        .then(function (stream) {
            myStream = stream;
            // Older browsers may not have srcObject
            // 오래된 브라우저는 지원하지 않을 수 있습니다.
            if ("srcObject" in myFace) {
                myFace.srcObject = stream;
            } else {
                // Avoid using this in new browsers, as it is going away.
                myFace.src = window.URL.createObjectURL(stream);
            }
            myFace.onloadedmetadata = function (e) {
                myFace.play();
            };
            if (!deviceId) {
                getCameras();
            }
        })
        .catch(function (err) {
            console.log(err.name + ": " + err.message);
        });
}

async function handleWelcomeSubmit(event) {
    event.preventDefault();
    const input = welcomeForm.querySelector('input');
    await initCall();
    socket.emit('join_room', input.value);
    roomName = input.value;
    input.value = '';
}

async function initCall() {
    welcome.hidden = true;
    call.hidden = false;
    await getMedia();
    makeConnection();
}

function makeConnection() {
    myPeerConnection = new RTCPeerConnection({
        iceServers: [
            {
                urls: [
                    'stun:stun.voipbuster.com',
                    'stun:stun.voipstunt.com',
                    'stun:stun.voxgratia.org',
                    'stun:stun.xten.com',
                ],
            }
        ]
    });
    myPeerConnection.addEventListener('icecandidate', handleIce);
    myPeerConnection.addEventListener('addstream', handleAddStream);

    // track: [audio, video]
    // stream: ???
    // 같이쓰는 이유좀..
    myStream.getTracks().forEach(track => {
        myPeerConnection.addTrack(track, myStream);
    });
}

function handleIce(data) {
    // console.log('sent candidate:', data);
    socket.emit('ice', data.candidate, roomName);
}

function handleAddStream(data) {
    // const peersFace = document.getElementById('peersFace');

    const peersFace = document.createElement('video');
    peersFace.setAttribute('autoplay', true);
    peersFace.setAttribute('playsinline', true);

    const attatch = document.getElementById('peers');
    attatch.appendChild(peersFace);
    peersFace.srcObject = data.stream;
    // console.log('got event from my peer');
    // console.log('peer`s stream', data.stream);
    // console.log('my stream', myStream)
}

// events =======================================================

// 1. 혼자있을 때 이 이벤트만 실행하고.
socket.on('welcome', async () => {
    console.log('someone joined');
    // 다른 브라우저가 초대받을수 있는 초대장을 만드는것
    const offer = await myPeerConnection.createOffer();
    myPeerConnection.setLocalDescription(offer);
    // console.log('sent offer');
    socket.emit('offer', offer, roomName);
    // console.log(offer);
});

socket.on('offer', async (offer) => {
    console.log('offer:', offer);
    // console.log('received offer');
    myPeerConnection.setRemoteDescription(offer);

    const answer = await myPeerConnection.createAnswer();
    myPeerConnection.setLocalDescription(answer);
    // console.log(answer);
    socket.emit('answer', answer, roomName);
    console.log('sent the answer');
});

socket.on('answer', async (answer) => {
    console.log('received the answer');
    myPeerConnection.setRemoteDescription(answer);
});

socket.on('ice', ice => {
    console.log('received candidate');
    myPeerConnection.addIceCandidate(ice);
});

//testing
socket.on("disconnect", (reason) => {
    console.log('disconnected', reason);
    // if (reason === "io server disconnect") {
      // the disconnection was initiated by the server, you need to reconnect manually
    //   socket.connect();
    // }
    // else the socket will automatically try to reconnect
  });

function test() {
    socket.disconnect(true);
}

// html tags, tag events =======================================================

welcomeForm = welcome.querySelector('form');
welcomeForm.addEventListener('submit', handleWelcomeSubmit);

let muted = false;
let cameraOff = false;

muteBtn.addEventListener('click', () => {
    // 송신하는 소리를 차단합니다.
    myStream.getAudioTracks().forEach(track => {
        track.enabled = !track.enabled;
    });
    muteBtn.innerText = (!muted) ? 'Unmute' : 'Mute';
    muted = !muted;
});

cameraBtn.addEventListener('click', () => {
    // 송신하는 영상을 차단합니다.
    myStream.getVideoTracks().forEach(track => {
        track.enabled = !track.enabled;
    });
    cameraBtn.innerText = (cameraOff) ? 'Turn off cam' : 'Turn on cam';
    cameraOff = !cameraOff;
});

camerasSelect.addEventListener('change', async() => {
    // 카메라가 바뀔 때 발생합니다.
    await getMedia(camerasSelect.value);
    // peer의 카메라 변경이 있을시 바뀐 스트림 데이터를 받는다.
    if (myPeerConnection) {
        const videoTrack = myStream.getVideoTracks()[0];
        const videoSender = myPeerConnection
            .getSenders()
            .find(sender => sender.track.kind === 'video');
        videoSender.replaceTrack(videoTrack);
    }
});
