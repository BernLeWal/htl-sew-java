<%-- 
    Document   : index
    Created on : 26.12.2017, 10:04:24
    Author     : Bernhard
--%>

<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>MacDagobert's - Alle Bestellungen</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="../static/assets/css/main.css"/>
    <meta http-equiv="refresh" content="10">
</head>
<body>
<!-- Header -->
<header id="header">
    <div class="logo"><a href="index.jsp">MacDagobert's <span>for MATTHIAS</span></a></div>
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

<%
    // Ist die Bestellung erledigt und soll gelöscht werden?
    String done = request.getParameter("Done");
    if (done != null && done != "") {
        application.removeAttribute("Counter");
    }

    int counter;
    try {
        counter = (int) application.getAttribute("Counter");
    } catch (Exception ex) {
        counter = 0;
    }
%>

<!-- Banner -->
<!--
        Note: To show a background image, set the "data-bg" attribute below
        to the full filename of your image. This is used in each section to set
        the background image.
-->
<section id="banner" class="bg-img" data-bg="banner2.jpg">
    <div class="inner">
        <header>
            <h1>Einen Guten Tag in die Küche!</h1>
            <h2>Es gibt <strong><%=counter%>
            </strong> offene Bestellungen.</h2>
        </header>
    </div>
    <a href="#one" class="more">Offene Bestellungen...</a>
</section>

<!-- One -->
<section id="one" class="wrapper post bg-img" data-bg="banner3.jpg">
    <div class="inner">
        <article class="box">
            <header>
                <h2>Bestellungen:</h2>
            </header>
            <div class="content">
                <table>
                    <tr>
                        <th>Nr</th>
                        <th>Beschreibung</th>
                        <th>Preis</th>
                    </tr>
                    <%
                        for (int nr = 1; nr <= counter; nr++) {
                            ArrayList<String[]> order = (ArrayList<String[]>) application.getAttribute(Integer.toString(nr));
                            if (order != null && order.size() > 0) {
                                double totalPrice = 0.0;
                    %>
                    <tr onclick="window.location.href = 'bestellung.jsp?Nr=<%=nr%>'">
                        <td><a href="bestellung.jsp?Nr=<%=nr%>"><%=nr%>
                        </a></td>
                        <td><a href="bestellung.jsp?Nr=<%=nr%>">
                            <%
                                for (String[] item : order) {
                                    try {
                                        int amount = Integer.parseInt(item[1]);
                                        double price = 0.0;
                                        if (amount > 0) {
                                            price = Double.parseDouble(item[2]);
                                            totalPrice += price * amount;
                            %><%=amount%>*<%=item[0]%> <%
                                    }
                                } catch (Exception ex) {
                                    //out.println(ex.toString());
                                }
                            }
                        %>
                        </a></td>
                        <td><a href="bestellung.jsp?Nr=<%=nr%>"><%=totalPrice%> &euro;</a></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>
            </div>
        </article>
    </div>
</section>

<!-- Footer -->
<footer id="footer">
    <div class="inner">
        <center>
            <ul class="actions">
                <li><a href="alle.jsp#one" class="button alt">Aktualisieren</a></li>
                <li><a href="alle.jsp?Done=1" class="button">Alles erledigt</a></li>
            </ul>
        </center>
    </div>
</footer>

<!-- Scripts -->
<script src="../static/assets/js/jquery.min.js"></script>
<script src="../static/assets/js/jquery.scrolly.min.js"></script>
<script src="../static/assets/js/jquery.scrollex.min.js"></script>
<script src="../static/assets/js/skel.min.js"></script>
<script src="../static/assets/js/util.js"></script>
<script src="../static/assets/js/main.js"></script>
</body>
</html>
