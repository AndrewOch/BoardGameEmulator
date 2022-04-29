<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/resources/css/styles.css">
    <title>Эмулятор настольных игр</title>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
</head>
<body onload="connect(${user.id})">

<div class="header-bar">
    <div>
        <div class="title"><h1>Эмулятор настольных игр</h1></div>
    </div>
    <div class="menu">
        <a class="menu-option selected" href="/">Главная</a>
        <a class="menu-option" href="/games">Мои игры</a>
        <a class="menu-option" href="/creator">Редактор</a>
        <a class="menu-option" href="/logout">Выход</a>
    </div>
</div>
<br>
<div class="menu"><h2 class="menu-option">Чат:</h2></div>
<div style="padding-right: 5%; padding-left: 5%">
    <div id="chatScrollView" style="overflow:scroll; height:400px;">
        <div id="messagesList"></div>
    </div>
    <div style="text-align: center"><button id="enterChatButton" onclick="enterChat('${user.username}')">Войти</button></div>
    <label for="message"></label><input name="message" id="message" disabled placeholder="Сообщение" hidden>
    <button onclick="sendMessage('${user.username}', $('#message').val())" id="sendMessageButton" hidden>Отправить</button>
</div>
<br>
<div class="menu"><h2 class="menu-option">Игры</h2></div>
<br>
<div style="margin: 0 auto;
    padding: 0;">
    <div class="wrapper-3">
        <#list games as game>
            <div class="container-item">
                <h2>${game.getName()}</h2>
                <h3>Автор: ${game.user[0].username}</h3>
                <h3>${game.getDescription()}</h3>
                <h3>Колод: ${game.getDecks()?size}</h3>
                <h3>Валют: ${game.getCurrencies()?size}</h3>
                <div class="menu">
                    <form class="menu-option" method="post" action="/play_game">
                        <button type="submit" name="currentPlayGameId" value="${game.getId()}">Играть</button>
                    </form>
                </div>
            </div>
        </#list>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script src="/resources/javascript/chat.js"></script>
</body>
</html>
