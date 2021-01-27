/**
 * 
 */
var stompClient = null;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	$("#connection-status").text(connected ? 'Połączono' : 'Rozłączono');
}

function showNotification(message) {
	$("#notifications").append("<b>" + message + "</b></br>");
}

function sendNotification(receiver, text) {
	stompClient.send("/app/notifications", {}, JSON.stringify({ 'receiver': receiver, 'text': text }));
}

function connect() {
	var socket = new SockJS('/room');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function() {
		setConnected(true);
		stompClient.subscribe('/user/queue/notifications', function(message) {
			var notification = JSON.parse(message.body);
			var str = notification.timestamp + ' ' + notification.receiver + ': ' + notification.text;
			showNotification(str);
		});
	});
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	setConnected(false);
}

$(function() {
	$("#connect").click(function() { connect(); });
	$("#disconnect").click(function() { disconnect(); });
	$("#send").click(function() {
		sendNotification($('#receiver').val(), $('#text').val());
	});
});