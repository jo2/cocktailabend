var stompClient = null;
var list = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/cocktails', function (message) {
            showCocktails(JSON.parse(message.body));
        });
        stompClient.subscribe('/topic/jumbos', function (message) {
            showJumbos(JSON.parse(message.body));
        });
    });
}

function showCocktails(message) {
    list = $('#cocktailList');
    if (list.children("li").length === 5) {
        list.children("li").first().remove();
    }
    var li = document.createElement('li');
    li.appendChild(document.createTextNode(message.number));
    list.append(li);
}

function showJumbos(message) {
    list = $('#jumboList');
    if (list.children("li").length === 5) {
        list.children("li").first().remove();
    }
    var li = document.createElement('li');
    li.appendChild(document.createTextNode(message.number));
    list.append(li);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    connect();

    $.ajax({
        url: "/first/jumbo",
        method: "GET",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                showJumbos(data[i]);
            }
        }
    });

    $.ajax({
        url: "/first/cocktails",
        method: "GET",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                showCocktails(data[i]);
            }
        }
    });
});

