<%-- 
    Document   : index
    Created on : 26.12.2017, 10:04:24
    Author     : Bernhard
--%>

<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Your Burger - Bestellung</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="../static/assets/css/main.css"/>
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

<%
    int nr = 0;
    ArrayList<String[]> order = new ArrayList<>();

    // Soll eine bestimmte Bestellung angezeigt werden?
    try {
        nr = Integer.parseInt(request.getParameter("Nr"));
        if (nr > 0) {
            order = (ArrayList<String[]>) application.getAttribute(Integer.toString(nr));
            if (order == null) {
                order = new ArrayList<>();
                order.add(new String[]{"", "0", "0.0"});
            }
        }
    } catch (Exception ex) {
        nr = 0;
    }

    int newNr = 0;
    if (nr == 0) {
        // Neue Bestellung anlegen
        try {
            newNr = (int) application.getAttribute("Counter");
            newNr++;
        } catch (Exception ex) {
            newNr = 1;
        }
        if (newNr <= 0) {
            newNr = 1;
        }
        application.setAttribute("Counter", newNr);
    }

    double totalPrice = 0.0;

%>

<!-- Banner -->
<!--
        Note: To show a background image, set the "data-bg" attribute below
        to the full filename of your image. This is used in each section to set
        the background image.
-->
<% if (newNr > 0) {%>
<section id="banner" class="bg-img" data-bg="banner2.jpg">
    <div class="inner">
        <header>
            <h1>Vielen Dank für ihre Bestellung</h1>
            <h2>Ihre Bestellung hat die Nummer:</h2>
            <p style="font-size: 72px; font-weight: bold;"><br/><%=(nr > 0) ? nr : newNr%>
            </p>
        </header>
    </div>
    <a href="#one" class="more">Ihre Bestellung...</a>
</section>
<% }%>

<!-- One -->
<section id="one" class="wrapper post bg-img" data-bg="banner3.jpg">
    <div class="inner">
        <article class="box">
            <header>
                <h2>Bestellung Nr <%=(nr > 0) ? nr : newNr%>:</h2>
            </header>
            <div class="content">
                <div class="row">
                    <%
                        if (newNr > 0) {
                            // Neue Bestellung anlegen

                            Class.forName("org.sqlite.JDBC");   // load the sqlite-JDBC driver using the current class loader

                            try (// create a database connection
                                 Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/server/webapp/foodorder/YourBurger.db");
                                 Statement statement = connection.createStatement()
                            ) {
                                statement.setQueryTimeout(30);  // set timeout to 30 sec.
                                ResultSet rs = statement.executeQuery("select * from speisen");
                                while (rs.next()) {
                                    try {
                                        String name = rs.getString("name");
                                        int amount = Integer.parseInt(request.getParameter(name));
                                        float price = 0.0f;
                                        if (amount > 0) {
                                            price = rs.getFloat("preis");
                                            totalPrice += price * amount;
                                            order.add(new String[]{name, Integer.toString(amount), rs.getString("preis")});
                                            out.write( String.format("<div ><img src=\"images/%s.png\"/><p><strong>%s</strong><br/>%d * %.2f &euro;</p></div>\n", name, name, amount, price) );
                                        }
                                    } catch (Exception ex) {
                                        System.err.println(ex.toString());
                                    }

                                }
                            } catch (SQLException e) {
                                // if the error message is "out of memory",
                                // it probably means no database file is found
                                System.err.println(e.getMessage());
                            }


                            application.setAttribute(Integer.toString(newNr), order);
                        } else if (nr > 0) {
                            // Vorhandene Bestellung anzeigen
                            order = (ArrayList<String[]>) application.getAttribute(Integer.toString(nr));
                            for (String[] item : order) {
                                try {
                                    int amount = Integer.parseInt(item[1]);
                                    double price = 0.0;
                                    if (amount > 0) {
                                        price = Double.parseDouble(item[2]);
                                        totalPrice += price * amount;
                    %>
                    <div>
                        <img src="images/<%=item[0]%>.png"/>
                        <p><strong><%=item[0]%>
                        </strong><br/>
                            <%=amount%> * <%=item[2]%> &euro;</p>
                    </div>
                    <% }
                    } catch (Exception ex) {
                        //out.println(ex.toString());
                    }

                    }
                    }
                    %>
                </div>
            </div>
            <footer>
                <h2>Bitte bezahlen Sie: </h2>
                <p style="font-size: 48px;"><%=totalPrice%> &euro;</p>
            </footer>
        </article>
    </div>
</section>

<!-- Footer -->
<% if (nr > 0) {%>
<footer id="footer">
    <div class="inner">
        <center>
            <ul class="actions">
                <li><a href="bestellung.jsp?Nr=<%=nr%>&Done=1" class="button">Erledigt</a></li>
                <li><a href="alle.jsp#one" class="button alt">Zurück zu allen Bestellungen</a></li>
            </ul>
        </center>
    </div>
</footer>
<%
        // Ist die Bestellung erledigt und soll gelöscht werden?
        String done = request.getParameter("Done");
        if (done != null && !done.isEmpty()) {
            application.removeAttribute(Integer.toString(nr));
            response.sendRedirect("alle.jsp#one");
        }
    } %>

<!-- Scripts -->
<script src="../static/assets/js/jquery.min.js"></script>
<script src="../static/assets/js/jquery.scrolly.min.js"></script>
<script src="../static/assets/js/jquery.scrollex.min.js"></script>
<script src="../static/assets/js/skel.min.js"></script>
<script src="../static/assets/js/util.js"></script>
<script src="../static/assets/js/main.js"></script>
</body>
</html>
