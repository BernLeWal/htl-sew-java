<%--
    index.jsp shows a simple JSP file containing HTML-code and generic embeded java source

    see also: https://javapointers.com/how-to/creating-web-application-using-maven-in-intellij/
--%>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.time.LocalDateTime" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello JSP</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h1>Hello JSP</h1>
<h2>Demo von JSP-Funktionalitäten</h2>
<p>
    Anzeige eines berechneten Wertes: <%= 12.5f %><br/>
    Anzeige des Ergebnisses eines Funktionsaufrufs: <%= LocalDateTime.now() %><br/>
</p>
<p>
    Ausführen von JAVA-Code:
    <%
        for (int i = 0; i < 10; i++) {
            out.println(i);
        }
    %>
</p>
<p>
    Einlesen von Parametern: <br/>
    <a href="index.jsp?param1=Hallo&param2=Welt">index.jsp?param1=Hallo&amp;param2=Welt</a><br/>
    <%
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            out.println( name + ": " + request.getParameter(name) + "<br/>");
        }
    %>
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
