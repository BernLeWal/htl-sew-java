<%--
    jstl_with_sql.jsp demonstrates the basic use of SQL-queries inside JSP files in a web-app.
    This jsp file demonstrates the creation of tables, inserting data and retrieving the table to be shown in the page.

    Remarks: For a productive webapp it is better to create the database outside the web-pages!
    @see server.webapp.hello.HelloWebappSetup
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<sql:setDataSource var="dataSource" driver="org.sqlite.JDBC" url="jdbc:sqlite:src/main/resources/server/webapp/hello/HelloWebapp.db"/>

<!-- recreate the database, that is to remove the table with old data -->
<sql:update dataSource="${dataSource}" var="dropTable">
    drop table if exists articles
</sql:update>

<sql:update dataSource="${dataSource}" var="createTable">
    create table articles (
        article_id integer,
        producer string,
        model string,
        power_idle integer,
        power_max integer,
        benchmark integer,
        price real
    )
</sql:update>

<sql:update dataSource="${dataSource}" var="insertData">
    insert into articles (article_id, producer, model, power_idle, power_max, benchmark, price) values
    (1, 'ASUS', 'Strix GTX 750Ti', 8, 56, 4030, 115),
    (2, 'AMD', 'Radeon R9 270X', 10, 128, 5539, 150),
    (3, 'ASUS', 'Strix GTX 760', 13, 161, 5491, 180);
</sql:update>

<sql:query var="articles" dataSource="${dataSource}">
    select article_id, producer, model, price from articles
</sql:query>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello SQL</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h1>Hello SQL!</h1>
<p>
    Auf <a href="https://www.baeldung.com/jstl">baeldung.com</a> findest du ein JSTL-Tutorial zur Einführung.
</p>
<h2>Demo von SQL-Queries in JSP/JSTL-Seiten</h2>
<p>
<table border="0">
    <thead>
    <tr>
        <th>Nr</th>
        <th>Hersteller</th>
        <th>Modell</th>
        <th>Preis</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="article" items="${articles.rows}">
        <tr>
            <td>${article.article_id}</td>
            <td>${article.producer}</td>
            <td>${article.model}</td>
            <td>${article.price}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</p>
<h2>Seiten dieser Demo-WebApp:</h2>
<p>
<ul>
    <li><a href="/hello/">/hello/ - die Hauptseite</a></li>
    <li><a href="index.html">index.html - eine statische HTML-Seite</a></li>
    <li><a href="index.jsp">index.jsp - eine einfache JSP-Seite</a></li>
    <li><a href="HelloServlet">HelloServlet - ein einfaches Servlet</a></li>
    <li><a href="jstl.jsp">jstl.jsp - eine JSP-Seite mit JSTL</a></li>
    <li><a href="jstl_with_sql.jsp">jstl_with_sql.jsp - eine JSP-Seite mit eingebauten SQL-Abfragen</a></li>
</ul>
</p>
</body>
</html>
