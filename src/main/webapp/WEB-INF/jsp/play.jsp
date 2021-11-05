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
    <title>Игра</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>

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
            <table>
                <tbody>
                <tr>
                    <td><label for="dice-1"> <input id="dice-1" class="dice" value="6" min="0" type="number" name=""
                                                    placeholder="0"></label></td>
                    <td><label id="dice-1-res" class="right">0</label></td>
                </tr>
                <tr>
                    <td><label for="dice-2"> <input id="dice-2" class="dice" value="6" min="0" type="number" name=""
                                                    placeholder="0"></label></td>
                    <td><label id="dice-2-res" class="right">0</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button name="Throw" onclick="throwDices()">Бросить</button>
                    </td>
                    <td><label id="dices-res" class="right">0</label>
                    </td>
                </tr>
                </tbody>
            </table>
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

        <c:forEach var="game" items="${decks}">
            <div class="container-item">
                <h2><c:out value="${game.name}"/></h2>
                <h3>
                    <p><c:out value="${game.description}"/></p>
                    <p>Карт: <c:out value="${game.cards.size()}"/></p>
                    <p>Сброс: <c:out value="${game.waste.size()}"/></p>
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
        </c:forEach>
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


<script>
    function throwDices() {
        let firstMax = document.getElementById('dice-1').value
        let secondMax = document.getElementById('dice-2').value

        let first = getRandomDiceValue(firstMax);
        let second = getRandomDiceValue(secondMax);
        let total = first + second;

        document.getElementById('dice-1-res').textContent = first
        document.getElementById('dice-2-res').textContent = second
        document.getElementById('dices-res').textContent = total
    }

    function getRandomDiceValue(max) {
        if (max < 1) return 0
        return Math.floor(Math.random() * max) + 1;
    }

    function isAuthenticated() {
        var docCookies = document.cookie;
        var prefix = "auth=";
        var begin = docCookies.indexOf("; " + prefix);
        if (begin === -1) {
            begin = docCookies.indexOf(prefix);
            if (begin !== 0) return false;
        }
        return true;
    }

    function editGame(id) {

        if (!isAuthenticated()) {
            return;
        }

        $.ajax({
            url: '/games',           /* Куда пойдет запрос */
            method: 'get',             /* Метод передачи (post или get) */
            dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
            data: {
                current_edit_game_id: id
            },
            success: function () {

            }
        })
    }
</script>
</body>
</html>
