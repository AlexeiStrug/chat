var userName = window.prompt("Enter your name", "user");
var toUserName = window.prompt("Enter name of chatting person", "user");

//var userName = "lol";

function post(url, data) {
    return $.ajax({
        type: 'POST',
        url: url,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(data)
    })
}

function appendMessage(message) {
    var time = message.time;
    var fromNow = time.year +"-" + time.monthValue + "-" + time.dayOfMonth + " " + time.hour + ":" + time.minute + ":" +time.second;
    var $message = $(`<li class="clearfix">
        <div class="message-data ${message.from == userName ? 'align-left': 'align-right'}">
        <span class="message-data-name">${message.from}</span>
        <span class="message-data-time">${fromNow}</span>
    </div>
    <div class="message ${message.from == userName ? 'my-message': 'other-message float-right'}">
        ${message.message}
    </div>
    </li>`);
    var $messages = $('#messages');
    $messages.append($message);
    $messages.scrollTop($messages.prop("scrollHeight"));
}

function getPreviousMessages() {
    $.get('/chat').done(messages => messages.forEach(appendMessage));
}

function sendMessage() {
    var $messageInput = $('#messageInput');
    if($messageInput.val() ==null || $messageInput.val() ==""){
        alert("不允许发空消息");
        return;
    }
    //TODO: time
    var message = {message: $messageInput.val(), from: userName, time: new Date()};
    $messageInput.val('');
    //post('/chat', message);
    stompClient.send('/topic/' + toUserName + '/messages', {}, JSON.stringify(message));
    appendMessage(message);
}

function onNewMessage(result) {
    var message = JSON.parse(result.body);
    appendMessage(message);
}

function connectWebSocket() {
    var socket = new SockJS('/chatWS');
    stompClient = Stomp.over(socket);
    //stompClient.debug = null;
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/' + userName + '/messages', onNewMessage);
});
}

getPreviousMessages();
connectWebSocket();