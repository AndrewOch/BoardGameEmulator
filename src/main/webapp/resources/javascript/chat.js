var webSocket;

function connect(id) {
    document.cookie = 'X-Authorization=' + '123456' + ';path=/';
    webSocket = new WebSocket('ws://localhost:8080/chat');
    webSocket.onmessage = function receiveMessage(response) {
        let data = response['data'];
        let json = JSON.parse(data);
        $('#messagesList').last().before("<p style='font-size: larger; height: 3%'>" + json['username'] + ": " + json['body'] + "</p>")
        let scrollView = document.getElementById('chatScrollView')
        scrollView.scrollTop = scrollView.scrollHeight;
    };
    webSocket.onerror = function errorShow() {
        alert('Ошибка авторизации')
    }
}

function enterChat(from) {
    let message = {
        "messageType": "enterChat",
        "username": from,
        "body": ""
    };
    webSocket.send(JSON.stringify(message));
    document.getElementById('message').disabled = false
    document.getElementById('message').hidden = false
    document.getElementById('sendMessageButton').hidden = false
    document.getElementById('enterChatButton').hidden = true
}

function sendMessage(from, text) {
    if (from == null || text === '') return

    let message = {
        "messageType": "chat",
        "username": from,
        "body": text
    };
    webSocket.send(JSON.stringify(message));
    document.getElementById('message').value = ''
}