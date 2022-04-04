
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
    let playGameId = getUrlParameter("current_play_game_id");
    if (playGameId != null) {
    playGame(playGameId)
}
});

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
    let docCookies = document.cookie;
    let prefix = "auth=";
    let begin = docCookies.indexOf("; " + prefix);
    if (begin === -1) {
    begin = docCookies.indexOf(prefix);
    if (begin !== 0) return false;
}
    return true;
}

    function playGame(id) {

    if (!isAuthenticated()) {
    return;
}

    $.ajax({
    url: '/play',           /* Куда пойдет запрос */
    method: 'post',             /* Метод передачи (post или get) */
    dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
    data: {
    current_play_game_id: id
},
    success: function (game) {

    let i;
    if (game == null) {
    return;
}
    let decks = game.decks;
    let currencies = game.currencies;

    document.getElementById('title').textContent = game.name;
    document.getElementById('description').textContent = game.description;

    let currenciesContainer = document.getElementById('currencies-container');

    let newContent = ""
    if (currencies.length === 0) {
    newContent = "<h3 class='center'>Нет валют</h3>";
} else {
    for (i = 0; i < currencies.length; i++) {
    let name = currencies[i].name;
    newContent += `<div class="square"><h3>` + name + `</h3><input min="-1000" max="1000" type="number"
                                                placeholder="0" style="height: 45px; margin: auto auto 10%; width: 60%"></div>`;
}
}
    currenciesContainer.innerHTML = newContent;

    let decksContainer = document.getElementById('decks-container');

    newContent = ""
    if (decks.length === 0) {
    newContent = "<h3 class='center'>Нет колод</h3>";
} else {
    for (i = 0; i < decks.length; i++) {
    let id = decks[i].id;
    let name = decks[i].name;
    let description = decks[i].description;
    let cards = decks[i].cards;
    let count = cards.length

    newContent += `<div class="container-item">
            <h2>` + name + `</h2>
            <h3>
                <p>` + description + `</p>
                <p id="deck-count-` + id + `">Карт: ` + count + `</p>
                <p id="waste-count-` + id + `">Сброс: 0</p>
            </h3>
            <div class="card" id="card-` + id + `">
                <h3 id="card-name-` + id + `"></h3>
                <h4 id="card-description-` + id + `"></h4>
                <h4 id="card-value-` + id + `"></h4>
                <h4 id="card-currency-` + id + `"></h4>
            </div>
             <table>
                <tr>
                    <td>
                        <button onclick="takeCard(` + id + `)">Взять</button>
                    </td>
                    <td>
                        <button onclick="shuffleDeck(` + id + `)">Перетасовать</button>
                    </td>
                </tr>
             </table>

</div>
        </div>`;
}
}

    decksContainer.innerHTML = newContent;

},
    error: function () {

}
})
}

    function takeCard(deckId) {
    if (!isAuthenticated() || deckId == null) {
    return;
}

    $.ajax({
    url: '/play',           /* Куда пойдет запрос */
    method: 'post',             /* Метод передачи (post или get) */
    dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
    data: {
    "deck_to_take_card_from": deckId
},
    success: function (deck) {

    if (deck == null) {
    return;
}

    let id = deck.deckId;
    let remainDeck = deck.deck;
    let waste = deck.waste;

    document.getElementById('deck-count-' + id).textContent = "Карт: " + remainDeck.length;
    document.getElementById('waste-count-' + id).textContent = "Сброс: " + waste.length;

    let card = waste[waste.length - 1]
    document.getElementById('card-name-' + id).innerText = card.name;
    document.getElementById('card-description-' + id).innerText = card.description;
    document.getElementById('card-value-' + id).innerText = card.value;
    document.getElementById('card-currency' + id).innerText = card.currency;

},
    error: function (response) {
    alert("Карт больше нет!");
}
})
}

    function shuffleDeck(deckId) {
    if (!isAuthenticated() || deckId == null) {
    return;
}

    $.ajax({
    url: '/play',           /* Куда пойдет запрос */
    method: 'post',             /* Метод передачи (post или get) */
    dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
    data: {
    "deck_to_shuffle": deckId
},
    success: function (deck) {

    if (deck == null) {
    return;
}

    let id = deck.deckId;
    let remainDeck = deck.deck;
    let waste = deck.waste;

    document.getElementById('deck-count-' + id).textContent = "Карт: " + remainDeck.length;
    document.getElementById('waste-count-' + id).textContent = "Сброс: " + waste.length;

    document.getElementById('card-name-' + id).innerText = "";
    document.getElementById('card-description-' + id).innerText = "";
    document.getElementById('card-value-' + id).innerText = "";
    document.getElementById('card-currency' + id).innerText = "";

},
    error: function (response) {
    alert(response);
}
})
}