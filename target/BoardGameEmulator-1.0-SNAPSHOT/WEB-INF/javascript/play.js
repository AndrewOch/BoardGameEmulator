function throwDices() {
    let firstMax = document.getElementById('dice-1').value
    let secondMax = document.getElementById('dice-2').value

    let first = getRandomDiceValue(firstMax);
    let second = getRandomDiceValue(secondMax);
    let total = first + second;

    document.getElementById('dice-1-res').textContent = first
    document.getElementById('dice-2-res').textContent = second
    document.getElementById('dices-res').textContent = total
}

function getRandomDiceValue(max) {
    if (max < 1) return 0
    return Math.floor(Math.random() * max) + 1;
}

function isAuthenticated() {
    var docCookies = document.cookie;
    var prefix = "auth=";
    var begin = docCookies.indexOf("; " + prefix);
    if (begin === -1) {
        begin = docCookies.indexOf(prefix);
        if (begin !== 0) return false;
    }
    return true;
}

function editGame(id) {

    if (!isAuthenticated()) {
        return;
    }

    $.ajax({
        url: '/games',           /* Куда пойдет запрос */
        method: 'get',             /* Метод передачи (post или get) */
        dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
        data: {
            current_edit_game_id: id
        },
        success: function () {

        }
    })
}