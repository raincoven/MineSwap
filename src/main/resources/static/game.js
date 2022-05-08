$(document).ready(function() {
    $('div.cell.active').click(function() {
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
                }
                if (resp == 'CHEST') {
                    alert('You won!')
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