<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 04.10.2021
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <style>
        <%@include file="/css/styles.css"%>
    </style>
    <title>Регистрация</title>
    <script src="../javascript/main.js"></script>
</head>
<body>

<div class="header-bar">
    <div>
        <div class="title"><h1>Эмулятор настольных игр</h1></div>
    </div>
    <div class="menu">
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/play">
            <button type="submit">Играть</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/games">
            <button type="submit">Мои игры</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/creator">
            <button type="submit">Редактор</button>
        </form>
        <form class=" menu-option" method="get" action="${pageContext.request.contextPath}/auth">
            <button class="selected" type="submit">Войти</button>
        </form>
    </div>

</div>
<div class="center, container" style="height: 65%;">
    <div class="sign-in-top-bar">
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/auth">
            <button type="submit">Вход</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/register">
            <button type="submit" class="selected">Регистрация</button>
        </form>
    </div>
    <form action="${pageContext.request.contextPath}/register" method="post">
        <p class="text">Логин</p>
        <p><label for="login"> <input class="register-input" id="login" type="text" name="login"
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
        <p class="info-text" style="margin-top: 0">${validation}</p>
    </form>
</div>
</div>

</body>
</html>
