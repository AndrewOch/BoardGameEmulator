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
    <title>Редактор</title>
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
            <button type="submit" class="selected">Редактор</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/auth">
            <button type="submit">Войти</button>
        </form>
    </div>
</div>


<div style="margin-top: 1%; text-align: center">
    <div class="menu">

        <h2 class="menu-option">Игра</h2>
        <div class="menu-option">
            <select id="choose-game">
                <option value="Игра1">Игра1</option>
                <option value="Игра2">Игра2</option>
                <option value="Игра3">Игра3</option>
                <option value="Игра4">Игра4</option>
            </select>
        </div>
    </div>
    <div class="wrapper-play-head">
        <div class="container-item">

            <h2>Валюта</h2>
            <form action="">
                <div class="menu-option">
                    <select id="choose-currency" style="height: 20%; width: 80%;">
                        <option value="create">Создать</option>
                        <option value="1">Валюта1</option>
                        <option value="2">Валюта2</option>
                        <option value="3">Валюта3</option>
                        <option value="4">Валюта4</option>
                    </select>
                </div>
                <p>
                    <label for="currency-name"> <input id="currency-name" type="text" name=""
                                                       placeholder="Название" style="font-family: Trattatello, serif;"></label>
                </p>
                <p>
                    <label for="currency-description"> <input id="currency-description" type="text" name=""
                                                              placeholder="Описание"
                                                              style="font-family: Trattatello, serif;"></label>
                </p>
                <button name="submit">Сохранить</button>
            </form>
        </div>
        <div class="container-item">

        </div>
    </div>
</div>
</body>
</html>
