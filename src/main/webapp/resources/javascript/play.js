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

function throwDices() {
    let firstMax = document.getElementById('dice1').value
    let secondMax = document.getElementById('dice2').value

    let first = getRandomDiceValue(firstMax);
    let second = getRandomDiceValue(secondMax);
    let total = first + second;

    document.getElementById('dice1Res').textContent = first
    document.getElementById('dice2Res').textContent = second
    document.getElementById('dicesRes').textContent = total
}

function getRandomDiceValue(max) {
    if (max < 1) return 0
    return Math.floor(Math.random() * max) + 1;
}

function takeCard(deckId) {
    if (deckId == null) {
        return;
    }

    $.ajax({
        url: '/play',
        method: 'post',
        dataType: 'json',
        data: {
            "deckToTakeCardFrom": deckId
        },
        success: function (deck) {

            if (deck == null) {
                return;
            }

            let id = deck.deckId;
            let remainDeck = deck.deck;
            let waste = deck.waste;

            document.getElementById('deckCount_' + id).textContent = "Карт: " + remainDeck.length;
            document.getElementById('wasteCount_' + id).textContent = "Сброс: " + waste.length;

            let card = waste[waste.length - 1]
            document.getElementById('cardName_' + id).innerText = card.name;
            document.getElementById('cardDescription_' + id).innerText = card.description;
            document.getElementById('cardValue_' + id).innerText = card.value;
            document.getElementById('cardCurrency' + id).innerText = card.currency;

        },
        error: function (response) {
            alert("Карт больше нет!");
        }
    })
}

function shuffleDeck(deckId) {
    if (deckId == null) {
        return;
    }

    $.ajax({
        url: '/play',
        method: 'post',
        dataType: 'json',
        data: {
            "deckToShuffle": deckId
        },
        success: function (deck) {

            if (deck == null) {
                return;
            }

            let id = deck.deckId;
            let remainDeck = deck.deck;
            let waste = deck.waste;

            document.getElementById('deckCount_' + id).textContent = "Карт: " + remainDeck.length;
            document.getElementById('wasteCount_' + id).textContent = "Сброс: " + waste.length;

            document.getElementById('cardName_' + id).innerText = "";
            document.getElementById('cardDescription_' + id).innerText = "";
            document.getElementById('cardValue_' + id).innerText = "";
            document.getElementById('cardCurrency' + id).innerText = "";

        },
        error: function (response) {
            alert(response);
        }
    })
}