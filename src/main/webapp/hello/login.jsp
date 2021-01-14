<%--
    login.jsp demonstrates the basic implementation of a login page,
    which will query the users-table in an sql-database

    Hint: for a successful login use credentials 'java'/'class'.

    Remarks: For a productive webapp it is better to create the database outside the web-pages!
    @see server.webapp.hello.HelloWebappSetup
--%>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.Connection" %>
<%@ page import="utils.HashUtils" %>

<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<sql:setDataSource var="dataSource" driver="org.sqlite.JDBC" url="jdbc:sqlite:src/main/resources/server/webapp/hello/HelloWebapp.db"/>

<!-- recreate the database, that is to remove the table with old data -->
<sql:update dataSource="${dataSource}" var="dropTable">
    drop table if exists users
</sql:update>

<sql:update dataSource="${dataSource}" var="createTable">
    create table users (
    id integer,
    name string,
    login string,
    password_hash string
    )
</sql:update>

<sql:update dataSource="${dataSource}" var="insertData">
    insert into users (id, name, login, password_hash) values
    (1, 'Administrator', 'admin', '21232f297a57a5a743894a0e4a801fc3'),
    (2, 'Java Programmer', 'java', 'a2f2ed4f8ebc2cbb4c21a29dc40ab61d'),
    (3, 'NoPwd User', 'nopwd', 'd41d8cd98f00b204e9800998ecf8427e');
</sql:update>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h1>Login - Demo</h1>
<%
    String login = request.getParameter("login");
    String pwd = request.getParameter("pwd");
    if (login == null || pwd == null || login.isEmpty() || pwd.isEmpty()) {
        // Zuerst wird das Formular angezeigt
%>
<h2>Logge dich als Benuzter ein:</h2>
<form method="post" action="login.jsp">
    <table>
        <tr>
            <td><label for="login">Login:</label></td>
            <td><input type="text" name="login" id="login"/></td>
        </tr>
        <tr>
            <td><label for="pwd">Passwort:</label></td>
            <td><input type="password" name="pwd" id="pwd"/></td>
        </tr>

        <tr>
            <td colspan="2"><center><input type="submit" value="Einloggen"/></center></td>
        </tr>
    </table>
</form>
<%
    } else {
        // User hat Formular ausgefüllt und auf [SUBMIT] geklickt
        int id = -1;    // default: no user logged in

        try {
            // Verbindung zur Datenbank herstellen
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(
                    "jdbc:sqlite:src/main/resources/server/webapp/hello/HelloWebapp.db", "", "");

            // das SQL-Statement
            String query = "SELECT id FROM users WHERE login=? AND password_hash=?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, login);                   // 1. Fragezeichen
            preparedStmt.setString(2, HashUtils.hash(pwd));     // 2. Fragezeichen
            preparedStmt.execute();

            ResultSet result = preparedStmt.getResultSet();
            if( result.next() ) {
                id = result.getInt(1);
            }
            else {
                System.out.println("login.jsp WARNING: User '" + login + "' with password '" + pwd + "' not found!");
            }

            conn.close();
        } catch (Exception e) {
            System.err.println("login.jsp ERROR: " + e.getMessage() );
            out.println("<p><strong>FEHLER! Benutzer "
                    + login
                    + " konnte nicht eingeloggt werden!</strong><br/>"
                    + e.getLocalizedMessage() + "</p>");
        }

        if (id > 0) {
            out.println("<p>Vielen Dank! Neuer Benutzer " + login + " wurde erfolgreich angemeldet!</p>");
            response.sendRedirect("articles.jsp");
        } else {
            out.println("<p>Falsches Login oder Passwort!</p>");
        }
    }
%>
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

