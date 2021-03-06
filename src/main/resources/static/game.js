$(document).ready(function() {
    $(document).on('click', 'div.cell.active', function() {
        var cell = $(this);
        var x = cell.data("x");
        var y = cell.data("y");

        $.ajax({
            url: "/cellValue/" + x + "/" + y,
            error: function(e) {
                alert("Something went wrong! Reload the page and start an new game.");
                console.log(e)
            },
            success: function(resp) {
                cell.addClass(resp);
                cell.removeClass("active");
                if (resp == 'DYNAMITE') {
                    alert("Game over! Start an new game");
                    $(".active").removeClass("active");
                }
                if (resp == 'CHEST') {
                    alert('You won!')
                    $(".active").removeClass("active");
                }
            }
        });
    });

    $('#newGame').click(function() {
        $.ajax({
            url: "/restart",
            type: 'POST',
            error: function() {
                alert("Something went wrong!");
            },
            success: function(resp) {
                location.reload();
            }
        });
    });
});