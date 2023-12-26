<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Web 2</title>
    <link rel="icon" type="image/x-icon" href="img/favicon.ico">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="wrapper">
    <div class="header sector">
        <div class="container">
            <div class="name">Тиме Олег Евгеньевич(P3225)</div>
            <div class="option">861209</div>
        </div>
    </div>
    <div class="graph-block sector">
        <div class="container">
            <div class="block-header">График</div>
            <div class="area-wrapper">
                <img src="img/area.png" alt="area">
                <c:forEach var="hit" items="${hitsData}">
                    <div class="dot"
                         style="top: ${175 - hit.getYVal()*175/(1.25*hit.getRVal())-4}px;
                                 left: ${175 + hit.getXVal() * 175 / (1.25*hit.getRVal())}px;"></div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="values-block sector">
        <div class="container">
            <div class="block-header">Значения</div>
            <form id="values-form" action="" method="get">
                <div class="values">
                    <div class="values-row">
                        <div class="value-header">X</div>
                        <div class="value-choose-block" id="buttonsX">
                            <input type="hidden" name="xVal"/>
                            <% for (int i = -4; i <= 4; i++) { %>
                               <button class="value-button" value="<%= i %>"><%= i %></button>
                            <% } %>
                        </div>
                    </div>

                    <div class="values-row">
                        <div class="value-header">Y</div>
                        <div class="value-choose-block">
                            <input type="text" name="yVal" placeholder="от -3 до 3">
                        </div>
                    </div>

                    <div class="values-row">
                        <div class="value-header">R</div>
                        <div class="value-choose-block">
                            <select name="rVal">
                                <option disabled>Выберите значение R</option>
                                <% for (int i = 1; i <= 5; i++) { %>
                                    <option value="<%= i %>"><%= i %></option>
                                <% } %>
                            </select>
                        </div>
                    </div>

                    <div class="error">
                        <div class="error-text">
                        </div>
                    </div>
                </div>
                <div class="buttons">
                    <button type="submit">Отправить</button>
                    <button type="reset">Очистить форму</button>
                    <button type="button" id="clear_picture">Очистить таблицу</button>
                </div>
            </form>
        </div>
    </div>
    <div class="table-block sector">
        <div class="container">
            <div class="block-header">Таблица</div>
            <div class="table"></div>
        </div>
    </div>
    <div class="buttons-block">
        <div class="buttons">
            <button type="submit" id="go_back">Вернуться назад</button>
            <button type="button" id="clear_table">Очистить таблицу</button>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="module" src="js/main.js"></script>
</body>
</html>