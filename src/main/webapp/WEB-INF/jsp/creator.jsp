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
    <title>Редактор</title>
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
        <h2 class="menu-option" id="game-title"><c:out value='${requestScope["current_game"]}'/></h2>
        <div class="menu-option">
            <select id="choose-game">
                <option value="none">Выберите</option>
                <c:forEach var="game" items="${games}">
                    <option value="${game.id}"><c:out value="${game.name}"/></option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="wrapper-play-head">
        <div class="container-item">

            <h2>Валюта</h2>
            <form action="${pageContext.request.contextPath}/creator" method="get">
                <div class="menu-option">
                    <select id="choose-currency" style="width: 80%;">
                        <option value="create">Создать</option>
                        <c:forEach var="currency" items="${currencies}">
                            <option><c:out value="${currency.name}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </form>
            <form action="${pageContext.request.contextPath}/creator" method="post">
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


<script>

    $(document).ready(function () {
        if (!isAuthenticated()) return;
        let id = $.urlParam('current_edit_game_id');

        $('choose-game').val('none');
        document.getElementById(id).selected
        //
        // if (id != null) {
        //     editGame(id)
        // } else {
        //     $('choose-game').val('none');
        // }
    });

    $.urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results == null) {
            return null;
        } else {
            return results[1] || 0;
        }
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

    $(function () {
        document.getElementById('choose-game').change(function () {
            var text = $("#choose-game option:selected").text();
            $("#currency-name").val(text)
            $("#div").css('background-color', 'blue');
        })
    })


    $(document).ready(function () {
        $('#choose-game').change(function () {
            editGame()
        })
    });


    function editGame() {
        if (!isAuthenticated()) {
            return;
        }

        let option = $('#choose-game option:selected');
        let id;
        if (option.val() !== 'none') {
            id = option.val()
            $('#game-title').html(option.text());
        }

        $.ajax({
            url: '/creator',           /* Куда пойдет запрос */
            method: 'post',             /* Метод передачи (post или get) */
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
