<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/resources/css/styles.css">
    <title>Вход</title>
</head>
<body>
<div class="header-bar">
    <div>
        <div class="title"><h1>Эмулятор настольных игр</h1></div>
    </div>
</div>
<div class="center, container" style="height: 40%;">
    <div class="sign-in-top-bar">
        <a class="selected" href="/auth">Вход</a>
        <a href="/register">Регистрация</a>
    </div>
    <form method="post">
        <p class="text">Логин</p>
        <p><label for="login"> <input class="register-input" id="login" type="text" name="login"
                                      placeholder="username"></label>
        </p>
        <p class="text">Пароль</p>
        <p><label for="password"> <input class="register-input" id="password" type="password" name="password"
                                         placeholder="password"></label>
        </p>
        <button type="submit">Войти</button>
        <#if error??><p class="info-text" style="margin-top: 0">${signInStatus}</p></#if>
    </form>
</div>
</body>
</html>
