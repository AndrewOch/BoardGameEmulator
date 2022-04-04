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
        <form class="menu-option" method="get" name="redirect">
            <button type="submit" name="redirect" value="play">Играть</button>
        </form>
        <form class="menu-option" method="get" name="redirect">
            <button class="selected" name="redirect" type="submit" value="games">Мои игры</button>
        </form>
        <form class="menu-option" method="get" name="redirect">
            <button type="submit" name="redirect" value="creator">Редактор</button>
        </form>
        <form class="menu-option" method="get" name="redirect">
            <button type="submit" name="redirect" value="auth">Вход</button>
        </form>
    </div>
</div>

<div style="margin: 0 auto;
    padding: 0;">

    <div class="menu">

        <h2 class="menu-option">${username}</h2>

    </div>

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
                        <button type="submit" name="current_play_game_id" value="${game.getId()}">Играть</button>
                    </form>
                    <form class="menu-option" method="get">
                        <button type="submit" name="current_edit_game_id" value="${game.getId()}">Редактировать</button>
                    </form>
                </div>
            </div>
        </#list>
        <div class="container-item">
            <h2>Создайте новую игру!</h2>
            <form method="post">
                <p>
                    <label for="game_name"> <input id="game_name" type="text" name="game_name"
                                                   placeholder="Название"
                                                   style="font-family: Trattatello, serif;"></label>
                </p>
                <p>
                    <label for="game_description"> <input id="game_description" type="text" name="game_description"
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
