$(document).ready(function () {
    $('#clear-database').on("click", function () {
        $.ajax({
            url: "/admin/clear",
            method: "DELETE",
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