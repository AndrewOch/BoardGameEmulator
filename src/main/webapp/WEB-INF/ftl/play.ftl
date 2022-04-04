<!doctype html>
<html lang="en">
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script src="/resources/javascript/play.js"></script>

    <link rel="stylesheet" href="/resources/css/styles.css">
    <title>Игра</title>
</head>
<body>

<div class="header-bar">
    <div>
        <div class="title"><h1>Эмулятор настольных игр</h1></div>
    </div>
    <div class="menu">
        <form class="menu-option" method="get" name="redirect">
            <button class="selected" name="redirect" type="submit" value="play">Играть</button>
        </form>
        <form class="menu-option" method="get" name="redirect">
            <button type="submit" name="redirect" value="games">Мои игры</button>
        </form>
        <form class="menu-option" method="get" name="redirect">
            <button  type="submit" name="redirect" value="creator">Редактор</button>
        </form>
        <form class="menu-option" method="get" name="redirect">
            <button type="submit" name="redirect" value="auth">Вход</button>
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
                        <button type="submit" name="Throw" onclick="throwDices()">Бросить</button>
                    </td>
                    <td><label id="dices-res" class="right">0</label>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="container-item">
            <h2 id="title">${game.name}</h2>
            <h4 id="description">${game.description}</h4>
        </div>
    </div>
    <div class="menu"><h2 class="menu-option">Валюты</h2></div>
    <div class="wrapper-8" id="currencies-container"></div>
    <br>
    <br>
    <div class="menu"><h2 class="menu-option">Колоды</h2></div>
    <div class="wrapper-3-decks" id="decks-container"></div>

</div>
</body>
</html>
