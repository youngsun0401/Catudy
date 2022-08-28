let socket;
socketSetting();

//// 채팅 보내기
function chat(){
	let value = document.getElementById('chattingInput').value;
	if( value == '' ) return;// 빈 문자열은 전송 안 함.
	socket.send(value);
}

//// 채팅 설정
function socketSetting(){
	let addr = "wss://192.168.10.97:8443/chat?room="+room_no+"&member="+member_no+"&password="+chatting_password;// ??? 현재 주소 로컬호스트
	console.log(addr);
	socket = new WebSocket(addr);
	// let socket = new WebSocket("ws://localhost:8080/chatt");

	socket.onopen = function(e) {
		console.log("[open] 커넥션이 만들어졌습니다. 데이터를 서버에 전송해봅시다.");
	};

	socket.onmessage = function(event) {
		oneChat = JSON.parse(`${event.data}`);
		addChatDiv(oneChat.member_nick, oneChat.msg);
		console.log('발화자: '+oneChat.member_nick);
		console.log('발화내용: '+oneChat.msg);
	};

	socket.onclose = function(event) {
		if (event.wasClean) {
			console.log(`[close] 커넥션이 정상적으로 종료되었습니다(code=${event.code} reason=${event.reason})`);
		} else {
			// 예시: 프로세스가 죽거나 네트워크에 장애가 있는 경우
			// event.code가 1006이 됩니다.
			console.log('[close] 커넥션이 죽었습니다.');
		}
	};

	socket.onerror = function(error) {
		console.log(`[error] ${error.message}`);
	};
}

//// 새 말풍선 삽입
function addChatDiv(speaker, msg){
	let wholeBox = document.getElementsByClassName('chatScreen')[0];
	let oneChat = document.createElement('div');
	oneChat.className = "item"
	oneChat.innerHTML = '<div class="box"><div>'+speaker+'</div><p class="msg">'+msg+'</p></div>';
	wholeBox.appendChild(oneChat);
	//// 맨 아래로 스크롤
	wholeBox.scrollTop = wholeBox.scrollHeight;
	//// 입력창 비우기
	document.getElementById('chattingInput').value = '';
}
