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
        <%@include file="/WEB-INF/css/styles.css"%>
    </style>
    <title>Эмулятор настольных игр</title>
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
        <div class="menu-option" method="get" action="${pageContext.request.contextPath}/games">
            <button>Мои игры</button>
        </div>
        <div class="menu-option" method="get" action="${pageContext.request.contextPath}/creator">
            <button>Редактор</button>
        </div>
        <form class="menu-option" method="get" action="${pageContext.request.contextPath}/auth">
            <button type="submit">Войти</button>
        </form>
    </div>

</div>
</body>
</html>
