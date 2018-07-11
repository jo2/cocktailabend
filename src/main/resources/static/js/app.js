var stompClient = null;
var list = null;

var h1Height;

(function($) {
    $.fn.hasScrollBar = function() {
        return this.get(0).scrollHeight > this.height();
    }
})(jQuery);

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
        /*setConnected(true);*/
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
    var li = document.createElement('li');
    li.appendChild(document.createTextNode(message.number));
    list.append(li);

    if (list.hasScrollBar()) {
        list.children("li").first().remove();
    }
}

function showJumbos(message) {
    list = $('#jumboList');
    var li = document.createElement('li');
    li.appendChild(document.createTextNode(message.number));
    list.append(li);

    if (list.hasScrollBar()) {
        list.children("li").first().remove();
    }
}

$(function () {
    h1Height = $('h1').height();
    $('#jumboList').height(document.body.clientHeight - h1Height);
    $('#cocktailList').height(document.body.clientHeight - h1Height);

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
        url: "/first/cocktail",
        method: "GET",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                showCocktails(data[i]);
            }
        }
    });
});