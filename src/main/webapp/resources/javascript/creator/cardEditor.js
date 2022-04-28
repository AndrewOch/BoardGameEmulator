$(document).ready(function () {
    $('#deckToShowCards').change(function () {
        getCardsOfDeck()
    })
    $('#currentEditCardId').change(function () {
        chooseCard()
    })
});

function getCardsOfDeck() {
    let option = $('#deckToShowCards option:selected');
    let id;
    if (option.val() !== 'none') {
        id = option.val()
    }
    let gameOption = $('#currentEditGameId option:selected');
    let gameId;
    if (gameOption.val() !== 'none') {
        gameId = gameOption.val()
    }
    $.ajax({
        url: '/creator',
        method: 'post',
        dataType: 'json',
        data: {
            "editingAction": "chooseDeckOfCards",
            "currentEditGameId": gameId,
            "deckToShowCards": id
        },
        success: function (cards) {
            let select = document.getElementById('currentEditCardId');
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


function chooseCard() {
    let option = $('#currentEditCardId option:selected');
    let id;
    if (option.val() !== 'none') {
        id = option.val()
    }
    let deckOption = $('#deckToShowCards option:selected');
    let deckId;
    if (deckOption.val() !== 'none') {
        deckId = deckOption.val()
    }
    let gameOption = $('#currentEditGameId option:selected');
    let gameId;
    if (gameOption.val() !== 'none') {
        gameId = gameOption.val()
    }
    $.ajax({
        url: '/creator',
        method: 'post',
        dataType: 'json',
        data: {
            "editingAction": "chooseCard",
            "currentEditGameId": gameId,
            "deckToShowCards": deckId,
            "currentEditCardId": id
        },
        success: function (card) {
            if (card == null) {
                return;
            }
            document.getElementById('cardName').value = card.name;
            document.getElementById('cardDescription').value = card.description;
            document.getElementById('cardValue').value = card.value;
            document.getElementById('cardCount').value = card.copiesCount;
            if (card.currencyId != null) {
                $('#currencyOfACard option[value=' + card.currencyId + ']').prop('selected', true);
            }
        },
        error: function (response) {
        }
    })
}

function editCard() {
    let option = $('#currentEditCardId option:selected');
    let id;
    if (option.val() !== 'none') {
        id = option.val()
    }
    if (id === 'create') {
        createCard()
        return
    }
    let deckOption = $('#deckToShowCards option:selected');
    let deckId;
    if (deckOption.val() !== 'none') {
        deckId = deckOption.val()
    }
    let gameOption = $('#currentEditGameId option:selected');
    let gameId;
    if (gameOption.val() !== 'none') {
        gameId = gameOption.val()
    }
    let cardName = document.getElementById('cardName').value
    let cardDescription = document.getElementById('cardDescription').value
    let currencyOption = $('#currencyOfACard option:selected');
    let cardCurrencyId;
    if (currencyOption.val() !== 'none') {
        cardCurrencyId = currencyOption.val()
    }
    let cardValue = document.getElementById('cardValue').value
    let cardCount = document.getElementById('cardCount').value
    $.ajax({
        url: '/creator',
        method: 'post',
        dataType: 'json',
        data: {
            "editingAction": "editCard",
            "currentEditGameId": gameId,
            "deckToShowCards": deckId,
            "currentEditCardId": id,
            "cardName": cardName,
            "cardDescription": cardDescription,
            "cardCurrencyId": cardCurrencyId,
            "cardValue": cardValue,
            "cardCopiesCount": cardCount
        },
        success: function (card) {
            if (card == null) {
                return;
            }
            document.getElementById('cardName').value = card.name;
            document.getElementById('cardDescription').value = card.description;
            document.getElementById('cardValue').value = card.value;
            if (card.currencyId != null) {
                $('#currencyOfACard option[value=' + card.currencyId + ']').prop('selected', true);
            }
        },
        error: function (response) {
        }
    })
}


function createCard() {
    let deckOption = $('#deckToShowCards option:selected');
    let deckId;
    if (deckOption.val() !== 'none') {
        deckId = deckOption.val()
    }
    let gameOption = $('#currentEditGameId option:selected');
    let gameId;
    if (gameOption.val() !== 'none') {
        gameId = gameOption.val()
    }
    let cardName = document.getElementById('cardName').value
    let cardDescription = document.getElementById('cardDescription').value
    let currencyOption = $('#currencyOfACard option:selected');
    let cardCurrencyId;
    if (currencyOption.val() !== 'none') {
        cardCurrencyId = currencyOption.val()
    }
    let cardValue = document.getElementById('cardValue').value
    let cardCount = document.getElementById('cardCount').value
    $.ajax({
        url: '/creator',
        method: 'post',
        dataType: 'json',
        data: {
            "editingAction": "createCard",
            "currentEditGameId": gameId,
            "deckToShowCards": deckId,
            "cardName": cardName,
            "cardDescription": cardDescription,
            "cardCurrencyId": cardCurrencyId,
            "cardValue": cardValue,
            "cardCopiesCount": cardCount
        },
        success: function (card) {
            if (card == null) {
                return;
            }
            document.getElementById('cardName').value = card.name;
            document.getElementById('cardDescription').value = card.description;
            document.getElementById('cardValue').value = card.value;

            if (card.currencyId != null) {
                $('#currencyOfACard option[value=' + card.currencyId + ']').prop('selected', true);
            }
        },
        error: function (response) {
        }
    })
}
