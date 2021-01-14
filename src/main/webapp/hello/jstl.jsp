<%--
  jstl.jsp demonstrates the basic use of JSTL inside JSP files in a web-app.
  For a JSTL-Tutorial see https://www.baeldung.com/jstl
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello JSTL</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h1>Hello JSTL!</h1>
<p>
    Auf <a href="https://www.baeldung.com/jstl">baeldung.com</a> findest du ein JSTL-Tutorial zur Einführung.
</p>
<h2>Demo von JSP-Funktionalitäten</h2>
<p>
    <c:set var="string1" value="This is a string stored in an JSTL variable."/>
    Inhalt der Variable string1=<c:out value="${string1}"/> <br/>
    Anzeige des Ergebnisses einer JSTL-Funktion: ${fn:toUpperCase(string1)} <br/>
</p>
<h2>Seiten dieser Demo-WebApp:</h2>
<p>
<ul>
    <li><a href="/hello/">/hello/ - die Hauptseite</a></li>

    <li><a href="index.html">index.html - eine statische HTML-Seite</a></li>
    <li><a href="index.jsp">index.jsp - eine einfache JSP-Seite</a></li>
    <li><a href="HelloServlet">HelloServlet - ein einfaches Servlet</a></li>
    <li><a href="jstl.jsp">jstl.jsp - eine JSP-Seite mit JSTL</a></li>

    <li><a href="login.jsp">login.jsp - Loginseite mit SQL-Abfrage der registrierten Benutzer</a></li>
    <li><a href="articles.jsp">articles.jsp - Zeigt den Inhalt einer SQL-Tabelle an</a></li>
</ul>
</p>
</body>
</html>
