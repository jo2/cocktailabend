$(document).ready(function () {
    $('#clear-database').on("click", function () {
        $.ajax({
            url: "/admin/clear",
            method: "DELETE",
            success: function (data) {
                console.log("delete success.")
                console.log(data);
            },
            error: function (data) {
                console.log("Etwas hat nicht geklappt.");
                console.log(data);
            }
        });
    });
});