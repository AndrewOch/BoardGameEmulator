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
    <title>Мои игры</title>
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
            <button type="submit" class="selected">Мои игры</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/creator">
            <button type="submit">Редактор</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/auth">
            <button type="submit">Войти</button>
        </form>
    </div>

</div>

<div style="margin: 0 auto;
    padding: 0;">

    <div class="menu">

        <h2 class="menu-option">${cookie.get("username").value}</h2>

    </div>

    <div class="wrapper-3">
        <div class="container-item">
            <h2>Игра1</h2>
            <h3>
                <p>Описание игры1</p>
                <p>Колод: 2</p>
                <p>Валют: 2</p>
            </h3>
            <div class="menu">
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/play">
                    <button type="submit">Играть</button>
                </form>
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/creator">
                    <button type="submit">Редактировать</button>
                </form>
            </div>
        </div>
        <div class="container-item">
            <h2>Игра2</h2>
            <h3>
                <p>Описание игры2</p>
                <p>Колод: 4</p>
                <p>Валют: 1</p>
            </h3>
            <div class="menu">
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/play">
                    <button type="submit">Играть</button>
                </form>
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/creator">
                    <button type="submit">Редактировать</button>
                </form>
            </div>
        </div>
        <div class="container-item">
            <h2>Игра3</h2>
            <h3>
                <p>Описание игры3</p>
                <p>Колод: 3</p>
                <p>Валют: 6</p>
            </h3>
            <div class="menu">
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/play">
                    <button type="submit">Играть</button>
                </form>
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/creator">
                    <button type="submit">Редактировать</button>
                </form>
            </div>
        </div>
        <div class="container-item">
            <h2>Игра4</h2>
            <h3>
                <p>Описание игры4</p>
                <p>Колод: 4</p>
                <p>Валют: 1</p>
            </h3>
            <div class="menu">
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/play">
                    <button type="submit">Играть</button>
                </form>
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/creator">
                    <button type="submit">Редактировать</button>
                </form>
            </div>
        </div>
        <div class="container-item">
            <h2>Игра5</h2>
            <h3>
                <p>Описание игры5</p>
                <p>Колод: 4</p>
                <p>Валют: 1</p>
            </h3>
            <div class="menu">
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/play">
                    <button type="submit">Играть</button>
                </form>
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/creator">
                    <button type="submit">Редактировать</button>
                </form>
            </div>
        </div>

    </div>
</div>
</div>
</div>

</body>
</html>
