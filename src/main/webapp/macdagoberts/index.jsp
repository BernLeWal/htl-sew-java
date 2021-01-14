<%-- 
    Document   : index
    Created on : 26.12.2017, 10:04:24
    Author     : Bernhard
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.net.*" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MacDagobert's</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="../static/assets/css/main.css" />
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

        <!-- Banner -->
        <!--
                Note: To show a background image, set the "data-bg" attribute below
                to the full filename of your image. This is used in each section to set
                the background image.
        -->
        <section id="banner" class="bg-img" data-bg="banner.jpg">
            <div class="inner">
                <header>
                    <h1>Willkommen bei MacDagobert's</h1>
                </header>
                <div class="content">
                    <p>Bitte auf den anderen Geräten die folgende Adresse<br/>
                        im WebBrowser eingeben:</p>

                    <%
                        String hostname, serverAddress;
                        hostname = "error";
                        serverAddress = "error";
                        try {
                            InetAddress inetAddress;
                            inetAddress = InetAddress.getLocalHost();
                            hostname = inetAddress.getHostName();
                            serverAddress = inetAddress.toString();
                            serverAddress = serverAddress.substring(hostname.length());
                        } catch (UnknownHostException e) {

                            e.printStackTrace();
                        }
                    %>                                                     
                    <p>
                        <strong><a href="http:/<%=serverAddress%>:8080/macdagoberts">http:/<%=serverAddress%>:8080/macdagoberts</a></strong>
                    </p>
                    <ul class="actions">
                        <li><a href="neu.jsp" class="button special">Neue Bestellung</a></li>
                        <li><a href="alle.jsp" class="button">Alle Bestellungen</a></li>
                        <li><a href="#" class="button alt">SETUP</a></li>
                    </ul>
                </div>
            </div>
            <a href="neu.jsp" class="more">Neue Bestellung...</a>
        </section>

        <!-- Scripts -->
        <script src="../static/assets/js/jquery.min.js"></script>
        <script src="../static/assets/js/jquery.scrolly.min.js"></script>
        <script src="../static/assets/js/jquery.scrollex.min.js"></script>
        <script src="../static/assets/js/skel.min.js"></script>
        <script src="../static/assets/js/util.js"></script>
        <script src="../static/assets/js/main.js"></script>
    </body>
</html>
