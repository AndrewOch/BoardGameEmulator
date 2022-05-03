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
</div>
<div class="center, container" style="height: 65%;">
    <div class="sign-in-top-bar">
        <a href="/auth">Вход</a>
        <a class="selected" href="/register">Регистрация</a>
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
