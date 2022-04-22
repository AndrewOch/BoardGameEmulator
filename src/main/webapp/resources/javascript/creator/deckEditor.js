$(document).ready(function () {

    $('#currentEditDeckId').change(function () {
        chooseDeck()
    })
});


function chooseDeck() {

    let option = $('#currentEditDeckId option:selected');
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
            "editingAction": "chooseDeck",
            "currentEditGameId": gameId,
            "currentEditDeckId": id
        },
        success: function (deck) {

            if (deck == null) {
                return;
            }
            document.getElementById('deckName').value = deck.name;
            document.getElementById('deckDescription').value = deck.description;
        },
        error: function (response) {
        }
    })
}

function editDeck() {
    let option = $('#currentEditDeckId option:selected');
    let id;
    if (option.val() !== 'none') {
        id = option.val()
    }
    if (id === 'create'){
        createDeck()
        return
    }
    let gameOption = $('#currentEditGameId option:selected');
    let gameId;
    if (gameOption.val() !== 'none') {
        gameId = gameOption.val()
    }

    let deckName = document.getElementById('deckName').value
    let deckDescription = document.getElementById('deckDescription').value

    $.ajax({
        url: '/creator',
        method: 'post',
        dataType: 'json',
        data: {
            "editingAction": "editDeck",
            "currentEditGameId": gameId,
            "currentEditDeckId": id,
            "deckName": deckName,
            "deckDescription": deckDescription
        },
        success: function (deck) {

            if (deck == null) {
                return;
            }
            document.getElementById('deckName').value = deck.name;
            document.getElementById('deckDescription').value = deck.description;
        },
        error: function (response) {
        }
    })
}


function createDeck() {

    let deckName = document.getElementById('deckName').value
    let deckDescription = document.getElementById('deckDescription').value

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
            "editingAction": "createDeck",
            "currentEditGameId": gameId,
            "deckName": deckName,
            "deckDescription": deckDescription
        },
        success: function (deck) {

            if (deck == null) {
                return;
            }
            document.getElementById('deckName').value = deck.name;
            document.getElementById('deckDescription').value = deck.description;
        },
        error: function (response) {
        }
    })
}

