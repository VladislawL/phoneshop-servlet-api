$(function () {
    function removeMessages() {
        $("#status").removeClass("success error").hide().text("");
        $("#message").hide().text("");
    }

    $('input[id="add-to-cart"]').on("click", function () {
        var $cartQuantity = $(".cart-quantity");
        $.ajax({
            type: "POST",
            url: document.location.href,
            data: {
                quantity: $('input[name="quantity"]').val()
            },
            success: function (msg) {
                removeMessages();
                $("#status").show().addClass("success").append(msg.message);
                $cartQuantity.text(msg.numberOfCartItems);
            },
            error: function (request, status, error) {
                removeMessages();
                $("#status").show().addClass("error").append("There was an error");
                $("#message").show().append(request.responseText);
            }
        });
    });

    $('.input-quantity-field').on("change", function () {
        var $this = $(this);
        $.ajax({
            type: "PUT",
            url: document.location.href,
            contentType: "application/json",
            data: JSON.stringify({
                quantity: $(this).val(),
                id: $(this).siblings('input[name="id"]').val()
            }),
            success: function (msg) {
                $this.removeClass("input-error");
                $this.siblings(".error").text("");
                $('[id="total-price"]').text(msg);
            },
            error: function (request, status, error) {
                $this.siblings(".error").text(request.responseText);
                $this.addClass("input-error");
            }
        });
    });

    $('.delete-btn').on("click", function () {
        var $parent = $(this).closest(".cart-item");
        var $cartQuantity = $(".cart-quantity");
        $.ajax({
            type: "DELETE",
            url: document.location.href,
            contentType: "application/json",
            data: JSON.stringify({
                id: $(this).siblings('input[name="id"]').val()
            }),
            success: function (msg) {
                $('[id="total-price"]').text(msg);
                $parent.remove();
                var quantity = $cartQuantity.text();
                $cartQuantity.text(parseInt(quantity, 10) - 1);
            },
        });
    });

    M.AutoInit();

    $('.dropdown-trigger').dropdown({
        hover: false,
        coverTrigger: false,
        constrainWidth: false
    });
});