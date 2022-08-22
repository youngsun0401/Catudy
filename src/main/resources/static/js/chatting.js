socketSetting();
function socketSetting(){
	let addr = "wss://localhost:8443/chat?room="+room_no+"&member="+member_no;// ??? 현재 주소 로컬호스트
	console.log(addr);
	let socket = new WebSocket(addr);
	// let socket = new WebSocket("ws://localhost:8080/chatt");

	socket.onopen = function(e) {
		alert("[open] 커넥션이 만들어졌습니다. 데이터를 서버에 전송해봅시다.");
		socket.send("My name is SUN");
	};

	socket.onmessage = function(event) {
		alert(`[message] 서버로부터 전송받은 데이터: ${event.data}`);
	};

	socket.onclose = function(event) {
		if (event.wasClean) {
			alert(`[close] 커넥션이 정상적으로 종료되었습니다(code=${event.code} reason=${event.reason})`);
		} else {
			// 예시: 프로세스가 죽거나 네트워크에 장애가 있는 경우
			// event.code가 1006이 됩니다.
			alert('[close] 커넥션이 죽었습니다.');
		}
	};

	socket.onerror = function(error) {
		alert(`[error] ${error.message}`);
	};
}