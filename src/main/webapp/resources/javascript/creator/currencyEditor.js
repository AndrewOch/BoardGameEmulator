$(document).ready(function () {
    $('#currentEditCurrencyId').change(function () {
        chooseCurrency()
    })
});

function chooseCurrency() {
    let option = $('#currentEditCurrencyId option:selected');
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
        url: '/creator/choose_currency',
        method: 'post',
        dataType: 'json',
        data: {
            "currentEditGameId": gameId,
            "currentEditCurrencyId": id
        },
        success: function (currency) {

            if (currency == null) {
                return;
            }
            document.getElementById('currencyName').value = currency.name;
            document.getElementById('currencyDescription').value = currency.description;
        },
        error: function (response) {
        }
    })
}

function editCurrency() {
    let option = $('#currentEditCurrencyId option:selected');
    let id;
    if (option.val() !== 'none') {
        id = option.val()
    }
    if (id === 'create'){
        createCurrency()
        return
    }
    let gameOption = $('#currentEditGameId option:selected');
    let gameId;
    if (gameOption.val() !== 'none') {
        gameId = gameOption.val()
    }
    let currencyName = document.getElementById('currencyName').value
    let currencyDescription = document.getElementById('currencyDescription').value
    $.ajax({
        url: '/creator/edit_currency',
        method: 'put',
        dataType: 'json',
        data: {
            "currentEditGameId": gameId,
            "currentEditCurrencyId": id,
            "currencyName": currencyName,
            "currencyDescription": currencyDescription,
        },
        success: function (currency) {

            if (currency == null) {
                return;
            }
            document.getElementById('currencyName').value = currency.name;
            document.getElementById('currencyDescription').value = currency.description;
        },
        error: function (response) {
        }
    })
}

function createCurrency() {
    let gameOption = $('#currentEditGameId option:selected');
    let gameId;
    if (gameOption.val() !== 'none') {
        gameId = gameOption.val()
    }
    let currencyName = document.getElementById('currencyName').value
    let currencyDescription = document.getElementById('currencyDescription').value
    $.ajax({
        url: '/creator/create_currency',
        method: 'post',
        dataType: 'json',
        data: {
            "currentEditGameId": gameId,
            "currencyName": currencyName,
            "currencyDescription": currencyDescription
        },
        success: function (currency) {

            if (currency == null) {
                return;
            }
            document.getElementById('currencyName').value = currency.name;
            document.getElementById('currencyDescription').value = currency.description;
        },
        error: function (response) {
        }
    })
}
