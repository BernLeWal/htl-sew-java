<%--
    login.jsp is used to ask the user for the mail-server settings
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h1>Webmail Login</h1>
<%
    String mailserver = request.getParameter("mailserver");
    String login = request.getParameter("login");
    String pwd = request.getParameter("pwd");
    if (mailserver == null || login == null || pwd == null ||
            mailserver.isEmpty() || login.isEmpty() || pwd.isEmpty()) {
        // Zuerst wird das Formular angezeigt
%>
<h2>Login Settings für den Mailserver:</h2>
<form method="post" action="login.jsp">
    <table>
        <tr>
            <td><label for="mailserver">Mailserver (IMAP):</label></td>
            <td><input type="text" name="mailserver" id="mailserver"/></td>
        </tr>
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
        out.println("<p>Vielen Dank! Benutzer " + login + " wird jetzt am Server " + mailserver + " angemeldet!</p>");

        session.setAttribute( "mailserver", mailserver );
        session.setAttribute( "login", login );
        session.setAttribute( "pwd", pwd );
        response.sendRedirect("index.jsp");
    }
%>
<h2>Navigation:</h2>
<p>
<ul>
    <li><a href="/webmail/">/webmail/ - die Hauptseite</a></li>

    <li><a href="index.jsp">index.jsp - Emails anzeigen</a></li>
    <li><a href="login.jsp">login.jsp - Login und Mail-Server-Settings</a></li>
</ul>
</p>
</body>
</html>

