<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 04.10.2021
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

    <style>
        <%@include file="/WEB-INF/css/styles.css"%>
    </style>
    <title>Редактор</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</head>
<body>

<div class="header-bar">
    <div>
        <div class="title"><h1>Эмулятор настольных игр</h1></div>
    </div>
    <div class="menu">
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/play">
            <button type="submit">Играть</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/games">
            <button type="submit">Мои игры</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/creator">
            <button type="submit" class="selected">Редактор</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/auth">
            <button type="submit">Войти</button>
        </form>
    </div>
</div>


<div style="margin-top: 1%; text-align: center">

    <div class="container-item" style="margin: 1% auto auto; width: 70%; height: 55%">
        <form action="${pageContext.request.contextPath}/creator" method="get">
            <select id="choose_game" name="choose_game" style="margin: 1% auto auto">
                <option disabled selected value="none">Выберите</option>
                <c:forEach var="game" items="${games}">
                    <option value="${game.id}"><c:out value="${game.name}"/></option>
                </c:forEach>
            </select>

            <p>
                <label for="game_name"> <input id="game_name" type="text" name="game_name"
                                               placeholder="Название"
                                               style="font-family: Trattatello, serif; width: 40%"></label>
            </p>
            <p>
                <label for="game_description"> <textarea id="game_description" name="game_description"
                                                         placeholder="Описание"
                                                         style="font-family: Trattatello, serif; height: 55%; width: 80%"></textarea></label>
            </p>
            <button type="submit">Сохранить</button>
        </form>
    </div>
</div>


<div class="wrapper-3" style="grid-template-rows: 75% 75% 75%;">

    <div class="container-item">

        <form action="${pageContext.request.contextPath}/creator" method="get" style="height: 80%">

            <h2>Валюты</h2>
            <select id="choose_currency" name="choose_currency" style="width: 80%;">
                <option value="create" selected>-Создать</option>
            </select>
            <p>
                <label for="currency_name"> <input id="currency_name" type="text" name="currency_name"
                                                   placeholder="Название"
                                                   style="font-family: Trattatello, serif;"></label>
            </p>
            <p>
                <label for="currency_description"> <textarea id="currency_description" type="text"
                                                             name="currency_description"
                                                             placeholder="Описание"
                                                             style="font-family: Trattatello, serif; height: 60%; width: 80%"></textarea></label>
            </p>
            <button>Сохранить</button>

        </form>
    </div>

    <div class="container-item">
        <form action="${pageContext.request.contextPath}/creator" method="get" style="height: 80%;">
            <h2>Колоды</h2>
            <select id="choose_deck" name="choose_deck" style="width: 80%;">
                <option value="create" selected>-Создать</option>
            </select>
            <p>
                <input id="deck_name" type="text" name="deck_name"
                       placeholder="Название"
                       style="font-family: Trattatello, serif;"></label>
            </p>
            <p>
               <textarea id="deck_description" type="text" name="deck_description"
                         placeholder="Описание"
                         style="font-family: Trattatello, serif; height: 60%; width: 80%"></textarea></label>
            </p>
            <button>Сохранить</button>
        </form>
    </div>


    <div class="container-item">

        <form action="${pageContext.request.contextPath}/creator" method="get" style="height: 70%;">
            <h2>Карты</h2>
            <select id="card_deck" name="card_deck" style="width: 80%;">
                <option value="none" selected disabled>Выбрать колоду</option>
            </select>
            <p>
                <select id="choose_card" name="choose_card" style="width: 80%;">
                    <option value="create" selected>-Создать</option>
                </select></p>
            <p>
                <input id="card_name" type="text" name="card_name"
                       placeholder="Название"
                       style="font-family: Trattatello, serif;"></label>
            </p>
            <p>
                <textarea id="card_description" type="text" name="card_description"
                          placeholder="Описание"
                          style="font-family: Trattatello, serif; height: 25%; width: 80%"></textarea></label>
            </p>
            <p style="width: 80%; margin: auto">
                <select id="currency_of_a_card" name="currency_of_a_card"
                        style="width: 80%; margin: auto">
                    <option value="none" selected>Нет валюты</option>
                </select>
                <label for="card_value"> <input id="card_value" class="dice" min="-100" max="100" type="number"
                                                name="card_value"
                                                placeholder="0" style="height: 45px; margin: auto"></label>
            </p>
            <br><br>

            <%--            <table style="width: 80%; margin: auto">--%>
            <%--                <tr>--%>
            <%--                    <td> В колоде:</td>--%>
            <%--                    <td>--%>
            <%--                        <button onclick="removeCard()">-</button>--%>
            <%--                    </td>--%>
            <%--                    <td><label id="card-count">0</label></td>--%>
            <%--                    <td>--%>
            <%--                        <button onclick="addCard()">+</button>--%>
            <%--                    </td>--%>
            <%--                </tr>--%>
            <%--            </table>--%>
            <button>Сохранить</button>
        </form>
    </div>
</div>


<script>

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
</script>

</body>
</html>
