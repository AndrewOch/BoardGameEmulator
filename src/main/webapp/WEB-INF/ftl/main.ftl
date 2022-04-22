<!doctype html>
<html lang="en">
<head>

    <link rel="stylesheet" href="/resources/css/styles.css">
    <title>Мои игры</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</head>
<body>

<div class="header-bar">
    <div>
        <div class="title"><h1>Эмулятор настольных игр</h1></div>
    </div>
    <div class="menu">
        <a class="menu-option selected" href="/main">Главная</a>
        <a class="menu-option" href="/games">Мои игры</a>
        <a class="menu-option" href="/creator">Редактор</a>
        <a class="menu-option" href="/logout">Выход</a>
    </div>
</div>

<div style="margin: 0 auto;
    padding: 0;">
    <div class="wrapper-3">
        <#list games as game>
            <div class="container-item">
                <h2>${game.getName()}</h2>
                <h3>
                    <p>${game.getDescription()}</p>
                    <p>Колод: ${game.getDecks()?size}</p>
                    <p>Валют: ${game.getCurrencies()?size}</p>
                </h3>
                <div class="menu">
                    <form class="menu-option" method="get">
                        <button type="submit" name="currentPlayGameId" value="${game.getId()}">Играть</button>
                    </form>
                    <form class="menu-option" method="get">
                        <button type="submit" name="currentEditGameId" value="${game.getId()}">Редактировать</button>
                    </form>
                </div>
            </div>
        </#list>
        <div class="container-item">
            <h2>Создайте новую игру!</h2>
            <form method="post">
                <p>
                    <label for="gameName"> <input id="gameName" type="text" name="gameName"
                                                  placeholder="Название"
                                                  style="font-family: Trattatello, serif;"></label>
                </p>
                <p>
                    <label for="gameDescription"> <input id="gameDescription" type="text" name="gameDescription"
                                                         placeholder="Описание"
                                                         style="font-family: Trattatello, serif; height: 40%"></label>
                </p>
                <button name="submit">Создать</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>