<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/resources/css/styles.css">
    <title>Регистрация</title>
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
            <button type="submit" name="redirect" value="creator">Редактор</button>
        </form>
        <form class="menu-option" method="get" name="redirect">
            <button type="submit" name="redirect" value="auth">Вход</button>
        </form>
    </div>

</div>
<div class="center, container" style="height: 65%;">
    <div class="sign-in-top-bar">
        <form class="menu-option" method="get">
            <button name="redirect" type="submit" value="auth">Вход</button>
        </form>
        <form class="menu-option" method="get">
            <button class="selected" type="submit" name="redirect" value="register">Регистрация</button>
        </form>
    </div>
    <form method="post">
        <p class="text">Логин</p>
        <p><label for="username"> <input class="register-input" id="username" type="text" name="username"
                                         placeholder="username"></label>
        </p>
        <p class="text">Электронная почта</p>
        <p><label for="email"> <input class="register-input" id="email" type="email" name="email"
                                      placeholder="email"></label>
        </p>
        <p class="text">Пароль</p>
        <p><label for="password"> <input class="register-input" id="password" type="password" name="password"
                                         placeholder="password"></label>
        </p>
        <p class="text">Подтвердить пароль</p>
        <p><label for="retype_password"> <input class="register-input" id="retype_password" type="password"
                                                name="retype_password"
                                                placeholder="password"></label>
        </p>
        <button type="submit">Создать аккаунт</button>
        <#if error??>
        <p class="info-text" style="margin-top: 0">${validation}</p>
        </#if>
    </form>
</div>
</div>

</body>
</html>
