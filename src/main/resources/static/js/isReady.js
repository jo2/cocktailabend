$(document).ready(function () {

    $('#ready').hide();
    $('#waiting').hide();
    $('#button').hide();

    $('#send').on("click", function () {
        var kind = $('#kind').val(), number = $('#number').val();
        var url = "/ready/" + kind + "/" + number;
        $.ajax({
            url: url,
            method: "GET",
            success: function (data) {
                console.log(data);
                $('#ready-form').hide();

                if (data === true) {
                    $('#cocktail-ready').text((kind === "true" ? "Jumbo " : " ") + number);
                    $('#ready').show();
                } else {
                    $('#cocktail-waiting').text((kind === "true" ? "Jumbo " : " ") + number);
                    $('#waiting').show();
                }
                $('#button').show();
            },
            error: function (data) {
                console.log("Etwas hat nicht geklappt.");
                console.log(data);
                $('#ready-form').hide();
            }
        });
    });
});