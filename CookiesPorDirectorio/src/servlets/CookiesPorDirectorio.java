package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/CookiesPorDirectorio", 
							"/uno/CookiesPorDirectorio", 
							"/uno/dos/CookiesPorDirectorio"})
public class CookiesPorDirectorio extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        procesaSolicitud(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        procesaSolicitud(request, response);
    }

    protected void procesaSolicitud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String nombreCookie = request.getParameter("nombreCookie");
        String contenidoCookie = request.getParameter("contenidoCookie");
        String mensajeEnvio = "";
        String rutaContexto = request.getContextPath();
        String rutaServletEnContexto = request.getServletPath();
 		String rutaCompletaServlet = request.getContextPath() + request.getServletPath();
 		
        if (nombreCookie != null && contenidoCookie != null) {  // se ha recibido un dato para una nueva cookie
       		Cookie laCookie;
       		laCookie = new Cookie(nombreCookie, contenidoCookie);       		
       		response.addCookie(laCookie);  // Añadir la cookie a la respuesta
       		mensajeEnvio = "Enviada al cliente la cookie de nombre <b>" + nombreCookie + 
       						"</b> y contenido <b>" + contenidoCookie + "</b> <br />";
       		mensajeEnvio += "Ruta de la cookie (del servlet que la crea): <b>" + rutaCompletaServlet + "</b> <br />";
        }
        		
       // Listado de cookies
       Cookie[] listaCookies = request.getCookies();
            
       out.println("<html>");
       out.println("<head>");
       out.println("	<meta charset=\"utf-8\" />");
       out.println("	<title>Cookies por directorio</title>");            
       out.println("</head>");
       out.println("<body>");
		

		
       out.println("<h1>Cookies por directorio</h1>");
       out.println(mensajeEnvio + " <br />");
       out.println("<h2>Listado de cookies en " + rutaCompletaServlet + "</h2>");
       out.println("<table border=\"1\">");
       out.println("<tr>");
       out.println("  <th>Nombre</th>");
       out.println("  <th>Contenido</th>");
       out.println("  <th>Fecha expiración</th>");
       out.println("  <th>Ruta</th>");
       out.println("</tr>");
       for (Cookie unaCookie : listaCookies) {
    	   out.println("<tr>");
    	   out.println("  <td>" + unaCookie.getName() + "</td>");
           out.println("  <td>" + unaCookie.getValue() + "</td>");
           out.println("  <td>" + unaCookie.getMaxAge() + "</td>");
           out.println("  <td>" + unaCookie.getPath() + "</td>");
           out.println("</tr>");
       }
       out.println("</table>");
       out.println("<br />");
       out.println("<a href=\"/CookiesPorDirectorio/cookiesPorDirectorio.html\">Volver a la primera página</a>");
       out.println("</body>");
       out.println("</html>");
    }




}
