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
    <title>Игра</title>
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
            <button type="submit" class="selected">Играть</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/games">
            <button type="submit">Мои игры</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/creator">
            <button type="submit">Редактор</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/auth">
            <button type="submit">Войти</button>
        </form>
    </div>

</div>
<div style="margin: 0 auto;
    padding: 0;">
    <div class="wrapper-play-head">
        <div class="container-item">
            <h2>Кубики</h2>
            <table>
                <tbody>
                <tr>
                    <td><label for="dice-1"> <input id="dice-1" class="dice" value="6" min="0" type="number" name=""
                                                    placeholder="0"></label></td>
                    <td><label id="dice-1-res" class="right">0</label></td>
                </tr>
                <tr>
                    <td><label for="dice-2"> <input id="dice-2" class="dice" value="6" min="0" type="number" name=""
                                                    placeholder="0"></label></td>
                    <td><label id="dice-2-res" class="right">0</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button name="Throw" onclick="throwDices()">Бросить</button>
                    </td>
                    <td><label id="dices-res" class="right">0</label>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="container-item">
            <h2 id="title"></h2>
            <h4 id="description"></h4>
        </div>
    </div>
    <div class="menu"><h2 class="menu-option">Валюты</h2></div>
    <div class="wrapper-8" id="currencies-container"></div>
    <br>
    <br>
    <div class="menu"><h2 class="menu-option">Колоды</h2></div>
    <div class="wrapper-3-decks" id="decks-container"></div>

</div>

<script>

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

        ${cookie.get("username").value}
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
</script>
</body>
</html>
