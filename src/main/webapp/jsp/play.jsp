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
    <title>Игра</title>
</head>
<body>

<div class="header-bar">
    <div>
        <div class="title"><h1>Эмулятор настольных игр</h1></div>
    </div>
    <div class="menu">
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/play">
            <button type="submit" class="selected">Играть</button>
        </form>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/games">
            <button type="submit">Мои игры</button>
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
    <div class="wrapper-play-head">
        <div class="container-item">
            <h2>Кубики</h2>

            <div>
                <form action="">
                    <p>
                        <label for="dice-1"> <input id="dice-1" class="dice" type="number" name=""
                                                    placeholder="0"></label>
                        <label id="dice-1-res" class="right">0</label>
                    </p>
                    <p>
                        <label for="dice-2"> <input id="dice-2" class="dice" type="number" name=""
                                                    placeholder="0"></label>
                        <label id="dice-2-res" class="right">0</label>
                    </p>
                    <p>
                        <button name="Throw">Бросить</button>
                        <label id="dices-res" class="right">0</label>
                    </p>

                </form>
            </div>
        </div>
        <div class="container-item">
            <h2>Игра1 - Валюты</h2>
            <div class="wrapper-8">
                <div class="inner-container-item">
                    <h3>10</h3>
                    <h4>Монеты</h4>
                </div>
                <div class="inner-container-item">
                    <h3>16</h3>
                    <h4>Кристаллы</h4>
                </div>
                <div class="inner-container-item">
                    <h3>1000</h3>
                    <h4>Очки</h4>
                </div>
            </div>
        </div>
    </div>
    <div class="menu"><h2 class="menu-option">Колоды</h2></div>
    <div class="wrapper-3-decks">

        <div class="container-item">
            <h2>Колода1</h2>
            <h3>
                <p>Описание Колоды</p>
                <p>Карт: 4</p>
                <p>Сброс: 1</p>
            </h3>
            <div class="card">
                <h3>Карта</h3>
                <h4>Описание карты</h4>
                <h4>Ценность</h4>
                <h4>Валюта</h4>
            </div>
            <div class="menu">
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/play">
                    <button type="submit">Взять</button>
                </form>
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/creator">
                    <button type="submit">Перетасовать</button>
                </form>
            </div>
        </div>
        <div class="container-item">
            <h2>Колода1</h2>
            <h3>
                <p>Описание Колоды</p>
                <p>Карт: 4</p>
                <p>Сброс: 1</p>
            </h3>
            <div class="card">
                <h3>Карта</h3>
                <h4>Описание карты</h4>
                <h4>Ценность</h4>
                <h4>Валюта</h4>
            </div>
            <div class="menu">
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/play">
                    <button type="submit">Взять</button>
                </form>
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/creator">
                    <button type="submit">Перетасовать</button>
                </form>
            </div>
        </div>
        <div class="container-item">
            <h2>Колода1</h2>
            <h3>
                <p>Описание Колоды</p>
                <p>Карт: 4</p>
                <p>Сброс: 1</p>
            </h3>
            <div class="card">
                <h3>Карта</h3>
                <h4>Описание карты</h4>
                <h4>Ценность</h4>
                <h4>Валюта</h4>
            </div>
            <div class="menu">
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/play">
                    <button type="submit">Взять</button>
                </form>
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/creator">
                    <button type="submit">Перетасовать</button>
                </form>
            </div>
        </div>
        <div class="container-item">
            <h2>Колода1</h2>
            <h3>
                <p>Описание Колоды</p>
                <p>Карт: 4</p>
                <p>Сброс: 1</p>
            </h3>
            <div class="card">
                <h3>Карта</h3>
                <h4>Описание карты</h4>
                <h4>Ценность</h4>
                <h4>Валюта</h4>
            </div>
            <div class="menu">
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/play">
                    <button type="submit">Взять</button>
                </form>
                <form class="menu-option" method="post" action="${pageContext.request.contextPath}/creator">
                    <button type="submit">Перетасовать</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
