<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/resources/css/styles.css">
    <title>Редактор</title>
</head>
<body onload="configureOutPut('${(game.playGround)!""}')">
<div class="header-bar">
    <div>
        <div class="title"><h1>Эмулятор настольных игр</h1></div>
    </div>
    <div class="menu">
        <a class="menu-option" href="/">Главная</a>
        <a class="menu-option" href="/games">Мои игры</a>
        <a class="menu-option selected" href="/creator">Редактор</a>
        <a class="menu-option" href="/logout">Выход</a>
    </div>
</div>
<div style="margin-top: 1%; text-align: center">
    <div class="container-item" style="margin: 1% auto auto; width: 70%; height: 55%">
        <form>
            <label for="currentEditGameId"></label><select id="currentEditGameId" name="currentEditGameId"
                                                           style="margin: 1% auto auto">
                <option disabled selected value="none">Выберите</option>
                <#list games as option>
                    <option value="${option.id}">${option.name}</option>
                </#list>
            </select>
            <p>
                <label for="gameName"> <input id="gameName" type="text" name="gameName"
                                              placeholder="Название" value="${(game.name)!""}"
                                              style="font-family: Trattatello, serif; width: 40%"></label>
            </p>
            <p>
                <label for="gameDescription"> <textarea id="gameDescription" name="gameDescription"
                                                        placeholder="Описание"
                                                        style="font-family: Trattatello, serif; height: 55%; width: 80%">${(game.description)!""}</textarea></label>
            </p>
            <button style="width: 50%" onclick="editGame(${(game.id)!"null"})">Сохранить</button>
        </form>
    </div>
</div>


<div class="wrapper-3" style="grid-template-rows: 75% 75% 75%;">
    <div class="container-item">
        <div style="height: 80%">
            <h2>Валюты</h2>
            <select id="currentEditCurrencyId" name="currentEditCurrencyId" style="width: 80%;">
                <option value="create" selected>-Создать</option>
            </select>
            <p>
                <label for="currencyName"> <input id="currencyName" type="text" name="currencyName"
                                                  placeholder="Название"
                                                  style="font-family: Trattatello, serif;"></label>
            </p>
            <p>
                <label for="currencyDescription"> <textarea id="currencyDescription"
                                                            name="currencyDescription"
                                                            placeholder="Описание"
                                                            style="font-family: Trattatello, serif; height: 60%; width: 80%"></textarea></label>
            </p>
            <button onclick="editCurrency()">Сохранить</button>
        </div>
    </div>

    <div class="container-item">
        <div style="height: 80%;">
            <h2>Колоды</h2>
            <label for="currentEditDeckId"></label><select id="currentEditDeckId" name="currentEditDeckId"
                                                           style="width: 80%;">
                <option value="create" selected>-Создать</option>
            </select>
            <p>
                <label for="deckName"></label><input id="deckName" type="text" name="deckName"
                                                     placeholder="Название"
                                                     style="font-family: Trattatello, serif;">
            </p>
            <p>
                <label for="deckDescription"></label><textarea id="deckDescription" name="deckDescription"
                                                               placeholder="Описание"
                                                               style="font-family: Trattatello, serif; height: 60%; width: 80%"></textarea>
            </p>
            <button onclick="editDeck()">Сохранить</button>
        </div>
    </div>

    <div class="container-item">
        <div style="height: 70%;">
            <h2>Карты</h2>
            <label for="deckToShowCards"></label><select id="deckToShowCards" name="deckToShowCards"
                                                         style="width: 80%;">
                <option value="none" selected disabled>Выбрать колоду</option>
            </select>
            <p>
                <label for="currentEditCardId"></label><select id="currentEditCardId" name="currentEditCardId"
                                                               style="width: 80%;">
                    <option value="create" selected>-Создать</option>
                </select>
            </p>
            <p>
                <label for="cardName"></label><input id="cardName" type="text" name="cardName"
                                                     placeholder="Название"
                                                     style="font-family: Trattatello, serif;">
            </p>
            <p>
                <label for="cardDescription"></label><textarea id="cardDescription" name="cardDescription"
                                                               placeholder="Описание"
                                                               style="font-family: Trattatello, serif; height: 25%; width: 80%"></textarea>
            </p>
            <p style="width: 80%; margin: auto">
                <label for="currencyOfACard"></label><select id="currencyOfACard" name="currencyOfACard"
                                                             style="width: 80%; margin: auto">
                    <option value="none" selected>Нет валюты</option>
                </select>
                <label for="cardValue"><input id="cardValue" class="dice" min="-100" max="100" type="number"
                                              name="cardValue"
                                              placeholder="0" style="height: 45px; margin: auto"></label>
            </p>
            <p style="width: 80%; margin: auto">
                <label for="cardCount"><input id="cardCount" class="dice" min="-100" max="100" type="number"
                                              name="cardCount" value="1"
                                              placeholder="Количество дублей в колоде"
                                              style="height: 45px; margin: auto"></label>
            </p>
            <button onclick="editCard()">Сохранить</button>
        </div>
    </div>
</div>
<h2>Игровое поле</h2>
<div style="width: 100%; margin: auto; text-align: center;">
    <form id="file-upload-form" enctype="multipart/form-data" method="post">
        <input type="file" id="playGroundFile" title="Выбрать файл" style="width: 30%">
        <button type="submit">Сохранить</button>
    </form>
    <img src="/resources/images/playGroundPlaceholder.png" id="playGround" alt="Play Ground" style="width: 97%">
</div>

<script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script src="/resources/javascript/creator/gameEditor.js"></script>
<script src="/resources/javascript/creator/currencyEditor.js"></script>
<script src="/resources/javascript/creator/deckEditor.js"></script>
<script src="/resources/javascript/creator/cardEditor.js"></script>
</body>
</html>
