<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 04.10.2021
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        <%@include file="/WEB-INF/css/styles.css"%>
    </style>
    <title>Вход</title>
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
<div class="center, container" style="height: 40%;">
    <div class="sign-in-top-bar">
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/auth">
            <button type="submit" class="selected">Вход</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/register">
            <button type="submit" name="register-button">Регистрация</button>
        </form>
    </div>
    <form action="${pageContext.request.contextPath}/auth" method="post">
        <p class="text">Логин</p>
        <p><label for="login"> <input class="register-input" id="login" type="text" name="login"
                                      placeholder="username"></label>
        </p>
        <p class="text">Пароль</p>
        <p><label for="password"> <input class="register-input" id="password" type="password" name="password"
                                         placeholder="password"></label>
        </p>
        <button type="submit">Войти</button>
        <a href="https://oauth.vk.com/authorize?client_id=7998860&redirect_uri=http://localhost:8080/vk&display=page&v=5.131&scope=status%2Cemail">Войти через VK</a>
        <p class="info-text" style="margin-top: 0">${signInStatus}</p>
    </form>
</div>
</body>
</html>
