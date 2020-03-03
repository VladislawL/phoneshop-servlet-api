$(function() {
    function removeMessages() {
        $("#status").removeClass("success error").hide().text("");
        $("#message").hide().text("");
    }

    $('input[type="submit"]').on("click", function() {
        $.ajax({
            type: "POST",
            url: document.location.href,
            data: {
                quantity: $('input[name="quantity"]').val()
            },
            success: function(msg) {
                removeMessages();
                $("#status").show().addClass("success").append(msg);
            },
            error: function (request, status, error) {
                removeMessages();
                $("#status").show().addClass("error").append("There was an error");
                $("#message").show().append(request.responseText);
            }
        });
    });

    M.AutoInit();

    $('.dropdown-trigger').dropdown({
        hover: false,
        coverTrigger: false,
        constrainWidth: false
    });
});