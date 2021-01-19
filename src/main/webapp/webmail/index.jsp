<%--
    index.jsp shows the content of a IMAP-folder

    documentation on the Jakarta Mail API see: https://jakarta.ee/specifications/mail/2.0/apidocs/index.html
--%>

<%@ page import="java.util.Properties" %>
<%@ page import="javax.mail.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EMail Client</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h1>EMail Client</h1>
<%
    String mailserver = (String) session.getAttribute("mailserver");
    String login = (String) session.getAttribute("login");
    String pwd = (String) session.getAttribute("pwd");
    if (mailserver == null || mailserver.isBlank() ||
            login == null || login.isBlank() ||
            pwd == null || pwd.isBlank()) {
        response.sendRedirect("login.jsp");
    } else {
%>

<form action="index.jsp" method="POST" name="form1" id="form1">
    <table border="0">
        <tr>
            <th> Subject</th>
            <th> From</th>
            <th> Date</th>
            <th> Actions</th>
        </tr>

        <%
            /* Properties props = System.getProperties();
               props.setProperty("mail.store.protocol", "imaps");
               Session session2 = Session.getDefaultInstance(props, null);
             */
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imap");
            props.setProperty("mail.debug", "true");
            props.setProperty("mail.imap.host", mailserver);
            props.setProperty("mail.imap.port", "993");
            props.setProperty("mail.imap.ssl.enable", "true");

            Session session2 = Session.getDefaultInstance(props, null);
            session2.setDebug(true);

            Folder folder = null;
            Store store = null;
            try {
                store = session2.getStore("imap");
                store.connect(mailserver, login, pwd);
                folder = store.getFolder("inbox");
                if (!folder.isOpen()) {
                    folder.open(Folder.READ_WRITE);
                }

                int indexToggleFlag = -1;
                if (request.getParameter("Index") != null)
                    indexToggleFlag = Integer.parseInt(request.getParameter("Index"));

                Message[] messages = folder.getMessages();
                for (int i = 0; i < messages.length; i++) {
                    Message msg = messages[i];
                    boolean seen = msg.isSet(Flags.Flag.SEEN);
                    if (indexToggleFlag == i) {
                        seen = !seen;
                        msg.setFlag(Flags.Flag.SEEN, seen);
                    }
        %>
        <tr>
            <td><% if (!seen) {%><strong><%}%> <%= msg.getSubject()%>  <% if (!seen) {%></strong><%}%></td>
            <td><% if (!seen) {%><strong><%}%> <%= msg.getFrom()[0].toString().replace("<", "&lt").replace(">", "&gt")%> <% if (!seen) {%></strong><%}%>
            </td>
            <td><% if (!seen) {%><strong><%}%> <%= msg.getReceivedDate()%> <% if (!seen) {%></strong><%}%></td>

            <td><input type="submit" onclick="document.getElementById('Index').value =<%= i%>"
                       value="<% if (seen) {%>ungelesen<%} else {%>gelesen<%}%>"/></td>  <!-- LÖSUNG -->
        </tr>
        <%
                }
            } catch( Exception e) {
                response.sendError(500, e.getLocalizedMessage() );
            } finally {
                if (folder != null && folder.isOpen()) {
                    folder.close(true);
                }
                if (store != null) {
                    store.close();
                }
            }

        %>
    </table>
    <input type="hidden" id="Index" name="Index" value="-2"/>
</form>
<% } %>
</body>
</html>

