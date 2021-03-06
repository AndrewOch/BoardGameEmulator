<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/resources/css/styles.css">
    <title>${(game.name)!""}</title>
</head>
<body onload="configureOutPut(${game.decks?size}, ${game.currencies?size}, '${game.playGround!""}')">
<div class="header-bar">
    <div>
        <div class="title"><h1>Эмулятор настольных игр</h1></div>
    </div>
    <div class="menu">
        <a class="menu-option" href="/">Главная</a>
        <a class="menu-option" href="/games">Мои игры</a>
        <a class="menu-option" href="/creator">Редактор</a>
        <a class="menu-option" href="/logout">Выход</a>
    </div>
</div>
<div style="margin: 0 auto;
    padding: 0;">
    <div class="wrapper-play-head">
        <div class="container-item-play">
            <h2>Кубики</h2>
            <table>
                <tbody>
                <tr>
                    <td><label for="dice1"> <input id="dice1" class="dice" value="6" min="0" type="number" name=""
                                                   placeholder="0"></label></td>
                    <td><label id="dice1Res" class="right">0</label></td>
                </tr>
                <tr>
                    <td><label for="dice2"> <input id="dice2" class="dice" value="6" min="0" type="number" name=""
                                                   placeholder="0"></label></td>
                    <td><label id="dice2Res" class="right">0</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button type="submit" name="Throw" onclick="throwDices()">Бросить</button>
                    </td>
                    <td><label id="dicesRes" class="right">0</label>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="container-item-play">
            <h2 id="title">${(game.name)!""}</h2>
            <h4 id="description">${(game.description)!""}</h4>
        </div>
    </div>
    <br>
    <br>
    <div id="currenciesBlock">
        <div class="menu"><h2 class="menu-option">Валюты</h2></div>
        <div class="wrapper-8" id="currenciesContainer">
            <#list game.currencies as currency>
                <div class="square">
                    <h3>${currency.name}</h3>
                    <label>
                        <input min="-1000" max="1000" type="number"
                               placeholder="0"
                               style="height: 45px; margin: auto auto 10%; width: 60%">
                    </label>
                </div>
            </#list>
        </div>
        <br>
        <br>
    </div>
    <div id="playGroundBlock">
        <br>
        <div class="menu"><h2 class="menu-option">Игровое поле</h2></div>
        <div style="width: 100%; margin: auto; text-align: center;">
            <img src="" id="playGround" alt="Play Ground" style="width: 97%">
        </div>
        <br>
    </div>
    <div id="decksBlock">
        <div class="menu"><h2 class="menu-option">Колоды</h2></div>
        <div class="wrapper-3-decks" id="decksContainer">
            <#list game.decks as deck>
                <div class="container-item-card">
                    <h2>${deck.name}</h2>
                    <div class="big-text" style="padding-left: 5%; padding-right: 5%">${deck.description}</div>
                    <div class="big-text" id="deckCount_${deck.id}">Карт: ${deck.cards?size}</div>
                    <div class="big-text" id="wasteCount_${deck.id}">Сброс: 0</div>
                    <div class="card" id="card_${deck.id}">
                        <h3 id="cardName_${deck.id}"></h3>
                        <h4 id="cardDescription_${deck.id}"></h4>
                        <h4 id="cardValue_${deck.id}"></h4>
                        <h4 id="cardCurrency_${deck.id}"></h4>
                    </div>
                    <table>
                        <tr>
                            <td>
                                <button onclick="takeCard(${deck.id})">Взять</button>
                            </td>
                            <td>
                                <button onclick="shuffleDeck(${deck.id})">Перетасовать</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </#list>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script src="/resources/javascript/play.js"></script>
</body>
</html>
