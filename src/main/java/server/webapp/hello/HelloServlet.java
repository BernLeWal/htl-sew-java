package server.webapp.hello;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Enumeration;

/**
 * HelloServlet demonstrates how simple Servlets are programmed and executed in a web-app.
 */
public class HelloServlet implements Servlet {
    private String message;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // do required initialization here
        message = "Hello Servlet!";
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
        out.print("""
            <h2>Demo von Servlet-Funktionalitäten</h2>
            <p>
                Anzeige eines berechneten Wertes: 
                """);
        out.println( 12.5f );
        out.println("""
                <br/>
                Anzeige des Ergebnisses eines Funktionsaufrufs: 
                """);
        out.println( LocalDateTime.now() );
        out.println("""
            <br/>
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
                <a href="HelloServlet?param1=Hallo&param2=Welt">HelloServlet?param1=Hallo&amp;param2=Welt</a><br/>
                """);
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            out.println( name + ": " + request.getParameter(name) + "<br/>");
        }
        out.println("""
            </p>
            <h2>Seiten dieser Demo-WebApp:</h2>
            <p>
            <ul>
                <li><a href="/hello/">/hello/ - die Hauptseite</a></li>
                <li><a href="index.html">index.html - eine statische HTML-Seite</a></li>
                <li><a href="index.jsp">index.jsp - eine einfache JSP-Seite</a></li>
                <li><a href="HelloServlet">HelloServlet - ein einfaches Servlet</a></li>
                <li><a href="hello_jstl.jsp">hello_jstl.jsp - eine JSP-Seite mit JSTL</a></li>
            </ul>
            </p>
                """);
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
