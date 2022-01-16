function isAuthenticated() {
    let docCookies = document.cookie;
    let prefix = "auth=";
    let begin = docCookies.indexOf("; " + prefix);
    if (begin === -1) {
        begin = docCookies.indexOf(prefix);
        if (begin !== 0) return false;
    }
    return true;
}


$(document).ready(function () {
    $('#choose-game').change(function () {
        editGame()
    })
    //
    // $('#choose-game').change(function () {
    //     editGame()
    // })
});

function editGame() {
    if (!isAuthenticated()) {
        return;
    }

    let option = $('#choose-game option:selected');
    let id;
    if (option.val() !== 'none') {
        id = option.val()
    }

    $.ajax({
        url: '/creator',           /* Куда пойдет запрос */
        method: 'post',             /* Метод передачи (post или get) */
        dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
        data: {
            "current_edit_game_id": id
        },
        success: function (game) {

            if (game == null) {
                return;
            }

            $('#game-name').val(game.name);
            $('#game-description').html(game.description);
        },
        error: function (response) {
            alert(response);
        }
    })
}

