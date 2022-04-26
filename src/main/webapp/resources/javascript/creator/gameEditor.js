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

    $('#currentEditGameId').change(function () {
        let option = $('#currentEditGameId option:selected');
        let id;
        if (option.val() !== 'none') {
            id = option.val()
        }
        chooseGame(id)
    })

    let editGameId = getUrlParameter("currentEditGameId");
    if (editGameId != null) {
        chooseGame(editGameId)
    }

    $("#file-upload-form").on("submit", function (e) {
        e.preventDefault();
        setPlayGround()
    })
});

function chooseGame(id) {

    $.ajax({
        url: '/creator',
        method: 'post',
        dataType: 'json',
        data: {
            "editingAction": "chooseGame",
            "currentEditGameId": id
        },
        success: function (game) {

            let i;
            if (game == null) {
                return;
            }

            document.getElementById('gameName').value = game.name;
            document.getElementById('gameDescription').value = game.description;

            let select1 = document.getElementById('currentEditCurrencyId');
            let select2 = document.getElementById('currencyOfACard');

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

            select1 = document.getElementById('currentEditDeckId');
            select2 = document.getElementById('deckToShowCards');

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

            $('#currentEditGameId option[value=' + game.id + ']').prop('selected', true);

        },
        error: function (response) {
        }
    })
}

function editGame(id) {
    if (id === 'null') return
    let gameName = document.getElementById('gameName').value
    let gameDescription = document.getElementById('gameDescription').value

    $.ajax({
        url: '/creator',
        method: 'post',
        dataType: 'json',
        data: {
            "editingAction": "editGame",
            "currentEditGameId": id,
            "gameName": gameName,
            "gameDescription": gameDescription
        },
        success: function (game) {
            if (game == null) {
                return;
            }
            document.getElementById('gameName').value = game.name;
            document.getElementById('gameDescription').value = game.description;
        },
        error: function (response) {
        }
    })
}


function setPlayGround() {
    let gameOption = $('#currentEditGameId option:selected');
    let gameId;
    if (gameOption.val() !== 'none') {
        gameId = gameOption.val()
    }

    let data = new FormData()
    data.append('editingAction', 'setPlayGround')
    data.append('currentEditGameId', gameId)

    var files = $('#playGroundFile')[0].files;
    if(files.length > 0 ) {
        data.append('playGroundFile', files[0]);
    }

    for (var pair of data.entries()) {
        console.log(pair[0]+ ', ' + pair[1]);
    }

    $.ajax({
        url: '/creator',
        type: 'post',
        // dataType: 'json',
        enctype: 'multipart/form-data',
        data: data,
        processData: false,
        contentType: false,
        success: function (fileSrc) {
            if (fileSrc == null) {
                return;
            }
            document.getElementById('playGround').src = fileSrc;
        },
        error: function (response) {
        }
    })
}