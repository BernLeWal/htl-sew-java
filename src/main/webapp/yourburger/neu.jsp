<%-- 
    Document   : index
    Created on : 26.12.2017, 10:04:24
    Author     : Bernhard
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sql:setDataSource var="dataSource" driver="org.sqlite.JDBC"
                   url="jdbc:sqlite:src/main/resources/server/webapp/foodorder/YourBurger.db"/>

<sql:query var="speisen" dataSource="${dataSource}">
    select id, name, kategorie, preis from speisen where kategorie='BUN'
</sql:query>

<sql:query var="beilagen" dataSource="${dataSource}">
    select id, name, kategorie, preis from speisen where kategorie='SOSSE'
</sql:query>

<sql:query var="getraenke" dataSource="${dataSource}">
    select id, name, kategorie, preis from speisen where kategorie='FLEISCH'
</sql:query>

<sql:query var="desserts" dataSource="${dataSource}">
    select id, name, kategorie, preis from speisen where kategorie='VEGI'
</sql:query>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Your Burger - Neue Bestellung</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="../static/assets/css/main.css"/>
    <script type="text/javascript">
        function modify(id, val) {
            item = document.getElementById(id);
            amount = parseInt(item.value) + val;
            if (amount < 0)
                amount = 0;
            if (amount > 10)
                amount = 10;
            item.value = amount;
        }
    </script>
</head>
<body>
<!-- Header -->
<header id="header">
    <div class="logo"><a href="index.jsp">Your Burger <span>served by MATTHIAS</span></a></div>
    <a href="#menu"><span>Men&uuml;</span></a>
</header>

<!-- Nav -->
<nav id="menu">
    <ul class="links">
        <li><a href="index.jsp">Start</a></li>
        <li><a href="neu.jsp">Neue Bestellung</a></li>
        <li><a href="alle.jsp">Alle Bestellungen</a></li>
    </ul>
</nav>

<form action="bestellung.jsp" method="post">
    <!-- Banner -->
    <!--
            Note: To show a background image, set the "data-bg" attribute below
            to the full filename of your image. This is used in each section to set
            the background image.
    -->
    <section id="banner" class="bg-img" data-bg="banner2.jpg">
        <div class="inner">
            <header>
                <h1>Ihre Bestellung bitte</h1>
            </header>
        </div>
        <a href="#one" class="more">Brot und Gebäck, das schmeckt...</a>
    </section>

    <!-- One -->
    <section id="one" class="wrapper post bg-img" data-bg="banner5.jpg">
        <div class="inner">
            <article class="box">
                <header>
                    <h2>Brot und Gebäck, das schmeckt</h2>
                </header>
                <div class="content">
                    <div class="row">
                        <c:forEach var="speise" items="${speisen.rows}">
                            <div>
                                <img src="images/${speise.name}.png" onclick="modify('${speise.name}', +1)"/>
                                <p><strong>${speise.name}</strong><br/>
                                        ${speise.preis} &euro;</p>
                                <p><a href="javascript:modify('${speise.name}',+1)" class="button" style="float: left;">+</a>
                                    <input type="text" id="${speise.name}" name="${speise.name}"
                                           style="float: left; width: 64px;" value="0"/>
                                    <a href="javascript:modify('${speise.name}',-1)" class="button"
                                       style="float: left;">-</a>
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </article>
        </div>
        <a href="#two" class="more">Sauce in den Burger...</a>
    </section>

    <!-- Two -->
    <section id="two" class="wrapper post bg-img" data-bg="banner3.jpg">
        <div class="inner">
            <article class="box">
                <header>
                    <h2>Sauce in den Burger?</h2>
                </header>
                <div class="content">
                    <div class="row">
                        <c:forEach var="beilage" items="${beilagen.rows}">
                            <div>
                                <img src="images/${beilage.name}.png" onclick="modify('${beilage.name}', +1)"/>
                                <p><strong>${beilage.name}</strong><br/>
                                        ${beilage.preis} &euro;</p>
                                <p><a href="javascript:modify('${beilage.name}',+1)" class="button"
                                      style="float: left;">+</a>
                                    <input type="text" id="${beilage.name}" name="${beilage.name}"
                                           style="float: left; width: 64px;" value="0"/>
                                    <a href="javascript:modify('${beilage.name}',-1)" class="button"
                                       style="float: left;">-</a>
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </article>
        </div>
        <a href="#three" class="more">Des beste G'mias is des Fleisch...</a>
    </section>

    <!-- Three -->
    <section id="three" class="wrapper post bg-img" data-bg="banner4.jpg">
        <div class="inner">
            <article class="box">
                <header>
                    <h2>Des beste G'mias is des Fleisch</h2>
                </header>
                <div class="content">
                    <div class="row">
                        <c:forEach var="getraenk" items="${getraenke.rows}">
                            <div>
                                <img src="images/${getraenk.name}.png" onclick="modify('${getraenk.name}', +1)"/>
                                <p><strong>${getraenk.name}</strong><br/>
                                        ${getraenk.preis} &euro;</p>
                                <p><a href="javascript:modify('${getraenk.name}',+1)" class="button"
                                      style="float: left;">+</a>
                                    <input type="text" id="${getraenk.name}" name="${getraenk.name}"
                                           style="float: left; width: 64px;" value="0"/>
                                    <a href="javascript:modify('${getraenk.name}',-1)" class="button"
                                       style="float: left;">-</a>
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </article>
        </div>
        <a href="#four" class="more">Noch Vitamine gefällig...</a>
    </section>

    <!-- Four -->
    <section id="four" class="wrapper post bg-img" data-bg="banner.jpg">
        <div class="inner">
            <article class="box">
                <header>
                    <h2>Noch Vitamine gefällig?</h2>
                </header>
                <div class="content">
                    <div class="row">
                        <c:forEach var="dessert" items="${desserts.rows}">
                            <div>
                                <img src="images/${dessert.name}.png" onclick="modify('${dessert.name}', +1)"/>
                                <p><strong>${dessert.name}</strong><br/>
                                        ${dessert.preis} &euro;</p>
                                <p><a href="javascript:modify('${dessert.name}',+1)" class="button"
                                      style="float: left;">+</a>
                                    <input type="text" id="${dessert.name}" name="${dessert.name}"
                                           style="float: left; width: 64px;" value="0"/>
                                    <a href="javascript:modify('${dessert.name}',-1)" class="button"
                                       style="float: left;">-</a>
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </article>
        </div>
    </section>

    <!-- Footer -->
    <footer id="footer">
        <div class="inner">

            <h2>Bestellung abschließen</h2>

            <center>
                <ul class="actions">
                    <li><input value="Bestellen" class="button" type="submit"></li>
                    <li><input value="Zur&uuml;cksetzen" class="button alt" type="reset"></li>
                </ul>
            </center>

        </div>
    </footer>

</form>

<!-- Scripts -->
<script src="../static/assets/js/jquery.min.js"></script>
<script src="../static/assets/js/jquery.scrolly.min.js"></script>
<script src="../static/assets/js/jquery.scrollex.min.js"></script>
<script src="../static/assets/js/skel.min.js"></script>
<script src="../static/assets/js/util.js"></script>
<script src="../static/assets/js/main.js"></script>
</body>
</html>
