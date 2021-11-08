function sendProduct() {
    let title = document.getElementById('title').value
    let cost = document.getElementById('cost').value
    let description = document.getElementById('description').value

    var product = {
        title: title,
        cost: cost,
        description: description
    }
    $.ajax({
        url: '/products',           /* Куда пойдет запрос */
        method: 'post',             /* Метод передачи (post или get) */
        dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
        data: {
            product: JSON.stringify(product) /* Параметры передаваемые в запросе. */
        },
        success: function (data) {   /* функция которая будет выполнена после успешного запроса.  */
            alert(data);            /* В переменной data содержится ответ от /products. */
        }
    })
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