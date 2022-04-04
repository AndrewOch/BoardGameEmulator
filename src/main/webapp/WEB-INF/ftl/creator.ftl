<!doctype html>
<html lang="en">
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script src="/resources/javascript/creator.js"></script>
    <link rel="stylesheet" href="/resources/css/styles.css">
    <title>Редактор</title>
</head>
<body>

<div class="header-bar">
    <div>
        <div class="title"><h1>Эмулятор настольных игр</h1></div>
    </div>
    <div class="menu">
        <form class="menu-option" method="get" name="redirect">
            <button type="submit" name="redirect" value="play">Играть</button>
        </form>
        <form class="menu-option" method="get" name="redirect">
            <button type="submit" name="redirect" value="games">Мои игры</button>
        </form>
        <form class="menu-option" method="get" name="redirect">
            <button class="selected" name="redirect" type="submit" value="creator">Редактор</button>
        </form>
        <form class="menu-option" method="get" name="redirect">
            <button type="submit" name="redirect" value="auth">Вход</button>
        </form>
    </div>
</div>

<div style="margin-top: 1%; text-align: center">

    <div class="container-item" style="margin: 1% auto auto; width: 70%; height: 55%">
        <form method="post">
            <select id="choose_game" name="choose_game" style="margin: 1% auto auto">
                <option disabled selected value="none">Выберите</option>
                <#list games as option>
                    <option value="${option.id}">${option.name}</option>
                </#list>
            </select>

            <p>
                <label for="game_name"> <input id="game_name" type="text" name="game_name"
                                               placeholder="Название" value="${game.name}"
                                               style="font-family: Trattatello, serif; width: 40%"></label>
            </p>
            <p>
                <label for="game_description"> <textarea id="game_description" name="game_description"
                                                         placeholder="Описание"
                                                         style="font-family: Trattatello, serif; height: 55%; width: 80%">${game.description}</textarea></label>
            </p>
            <button type="submit">Сохранить</button>
        </form>
    </div>
</div>


<div class="wrapper-3" style="grid-template-rows: 75% 75% 75%;">

    <div class="container-item">

        <form method="get" style="height: 80%">

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
        <form method="get" style="height: 80%;">
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

        <form method="get" style="height: 70%;">
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

<#--                        <table style="width: 80%; margin: auto">-->
<#--                               <tr>-->
<#--                                        <td> В колоде:</td>--%>-->
<#--                                        <td>-->
<#--                                                <button onclick="removeCard()">-</button>-->
<#--                                            </td>-->
<#--                                        <td><label id="card-count">0</label></td>-->
<#--                                        <td>-->
<#--                                                <button onclick="addCard()">+</button>-->
<#--                                            </td>-->
<#--                                    </tr>-->
<#--                            </table>-->
            <button>Сохранить</button>
        </form>
    </div>
</div>
</body>
</html>
