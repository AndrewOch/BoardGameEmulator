
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

    function setCookie(cname, cvalue) {
    document.cookie = cname + "=" + cvalue;
}

    function deleteCookie(cname) {
    var d = new Date(); //Create an date object
    d.setTime(d.getTime() - (1000 * 60 * 60 * 24)); //Set the time to the past. 1000 milliseonds = 1 second
    var expires = "expires=" + d.toGMTString(); //Compose the expirartion date
    document.cookie = cname + "=; " + expires;//Set the cookie with name and the expiration date
}

    function getUrlParameter(sParam) {
    let sPageURL = window.location.search.substring(1),
    sURLVariables = sPageURL.split('&'),
    sParameterName,
    i;

    for (i = 0; i < sURLVariables.length; i++) {
    sParameterName = sURLVariables[i].split('=');

    if (sParameterName[0] === sParam) {
    return sParameterName[1];
}
}
    return false;
}

    $(document).ready(function () {

    $('#choose-game').change(function () {
        let option = $('#choose-game option:selected');
        let id;
        if (option.val() !== 'none') {
            id = option.val()
        }
        editGame(id)
    })

    $('#choose-currency').change(function () {
    editCurrency()
})

    $('#choose-deck').change(function () {
    editDeck()
})

    $('#card-deck').change(function () {
    getCardsOfDeck()
})

    $('#choose-card').change(function () {
    editCard()
})

    let editGameId = getUrlParameter("current_edit_game_id");
    if (editGameId != null) {
    editGame(editGameId)
}
});

    function editGame(id) {
    if (!isAuthenticated()) {
    return;
}
    $.ajax({
    url: '/creator',           /* Куда пойдет запрос */
    method: 'post',             /* Метод передачи (post или get) */
    dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
    data: {
    "current_edit_game_id": id,
},
    success: function (game) {

    let i;
    if (game == null) {
    return;
}

    document.getElementById('game-name').value = game.name;
    document.getElementById('game-description').value = game.description;

    let select1 = document.getElementById('choose-currency');
    let select2 = document.getElementById('currency-of-a-card');

    let currencies = game.currencies;

    let options = "";
    select1.options.length = 0;
    select2.options.length = 0;
    $(select1).append(`<option value="create" selected>-Создать</option>`);
    $(select2).append(`<option value="none" selected>Нет валюты</option>`);

    for (i = 0; i < currencies.length; i++) {
    let id = currencies[i].id;
    let name = currencies[i].name;
    options += `<option value=` + id + `>` + name + `</option>`;
}
    $(select1).append(options);
    $(select2).append(options);

    select1 = document.getElementById('choose-deck');
    select2 = document.getElementById('card-deck');

    select1.options.length = 0;
    select2.options.length = 0;
    $(select1).append(`<option value="create" selected>-Создать</option>`);
    $(select2).append(`<option value="none" selected disabled>Выбрать колоду</option>`);
    select1.append();
    select2.append();
    let decks = game.decks;
    options = "";

    for (i = 0; i < decks.length; i++) {
    let id = decks[i].id;
    let name = decks[i].name;
    options += `<option value=` + id + `>` + name + `</option>`;
}
    $(select1).append(options);
    $(select2).append(options);

    $('#choose-game option[value=' + game.id + ']').prop('selected', true);

},
    error: function (response) {
}
})
}


    function editCurrency() {
    if (!isAuthenticated()) {
    return;
}

    let option = $('#choose-currency option:selected');
    let id;
    if (option.val() !== 'none') {
    id = option.val()
}

    $.ajax({
    url: '/creator',           /* Куда пойдет запрос */
    method: 'post',             /* Метод передачи (post или get) */
    dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
    data: {
    "current_edit_currency_id": id
},
    success: function (currency) {

    if (currency == null) {
    return;
}
    document.getElementById('currency-name').value = currency.name;
    document.getElementById('currency-description').value = currency.description;
},
    error: function (response) {
}
})
}


    function editDeck() {
    if (!isAuthenticated()) {
    return;
}

    let option = $('#choose-deck option:selected');
    let id;
    if (option.val() !== 'none') {
    id = option.val()
}

    $.ajax({
    url: '/creator',           /* Куда пойдет запрос */
    method: 'post',             /* Метод передачи (post или get) */
    dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
    data: {
    "current_edit_deck_id": id
},
    success: function (deck) {

    if (deck == null) {
    return;
}
    document.getElementById('deck-name').value = deck.name;
    document.getElementById('deck-description').value = deck.description;
},
    error: function (response) {
}
})
}


    function getCardsOfDeck() {
    if (!isAuthenticated()) {
    return;
}

    let option = $('#card-deck option:selected');
    let id;
    if (option.val() !== 'none') {
    id = option.val()
}

    $.ajax({
    url: '/creator',           /* Куда пойдет запрос */
    method: 'post',             /* Метод передачи (post или get) */
    dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
    data: {
    "deck_to_show_cards": id
},
    success: function (deck) {

    if (deck == null) {
    return;
}

    let cards = deck.cards
    let select = document.getElementById('choose-card');

    let options = "";
    select.options.length = 0;
    $(select).append(`<option value="create" selected>-Создать</option>`);

    for (let i = 0; i < cards.length; i++) {
    let id = cards[i].id;
    let name = cards[i].name;
    options += '<option value=' + id + '>' + name + '</option>';
}
    $(select).append(options);
},
    error: function (response) {
}
})
}


    function editCard() {
    if (!isAuthenticated()) {
    return;
}

    let option = $('#choose-card option:selected');
    let id;
    if (option.val() !== 'none') {
    id = option.val()
}

    $.ajax({
    url: '/creator',           /* Куда пойдет запрос */
    method: 'post',             /* Метод передачи (post или get) */
    dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
    data: {
    "current_edit_card_id": id
},
    success: function (card) {

    if (card == null) {
    return;
}

    document.getElementById('card-name').value = card.name;
    document.getElementById('card-description').value = card.description;
    document.getElementById('card-value').value = card.value;

    $('#currency-of-a-card option[value=' + card.currencyId + ']').prop('selected', true);
},
    error: function (response) {
}
})
}
