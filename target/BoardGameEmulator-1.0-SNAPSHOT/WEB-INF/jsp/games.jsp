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
        <c:forEach var="game" items="${games}">
            <div class="container-item">
                <h2><c:out value="${game.getName()}"/></h2>
                <h3>
                    <p><c:out value="${game.getDescription()}"/></p>
                    <p>Колод: <c:out value="${game.getDecks().size()}"/></p>
                    <p>Валют: <c:out value="${game.getCurrencies().size()}"/></p>
                </h3>
                <div class="menu">
                    <form class="menu-option" method="get" action="${pageContext.request.contextPath}/games">
                        <button type="submit" name="current_play_game_id" value="${game.getId()}">Играть</button>
                    </form>
                    <form class="menu-option" method="get" action="${pageContext.request.contextPath}/games">
                        <button type="submit" name="current_edit_game_id" value="${game.getId()}">Редактировать</button>
                    </form>
                </div>
            </div>
        </c:forEach>
        <div class="container-item">
            <h2>Создайте новую игру!</h2>
            <form action="${pageContext.request.contextPath}/games" method="post">
                <p>
                    <label for="game-name"> <input id="game-name" type="text" name="game-name"
                                                   placeholder="Название"
                                                   style="font-family: Trattatello, serif;"></label>
                </p>
                <p>
                    <label for="game-description"> <input id="game-description" type="text" name="game-description"
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
