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

@WebServlet(urlPatterns = { "/CookiesPorDirectorio",
		"/uno/CookiesPorDirectorio", "/uno/dos/CookiesPorDirectorio" })
public class CookiesPorDirectorio extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreCookie;
	private String contenidoCookie;
	private String mensajeEnvio;
	private String rutaCompletaServlet;
	private static final String paginaInicio="/CookiesPorDirectorio/cookiesPorDirectorio.html";

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		procesaSolicitud(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		procesaSolicitud(request, response);
	}

	protected void procesaSolicitud(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		obtenerParametros(request);

		// String rutaContexto = request.getContextPath();
		// String rutaServletEnContexto = request.getServletPath();
		String rutaCompletaServlet = request.getContextPath()
				+ request.getServletPath();

		if (parametrosNoVacios()) { // se ha recibido un dato para una nueva
									// cookie
			mensajeEnvio = crearCookie(response);
		}

		imprimirPagina(request, out);
	}

	private void obtenerParametros(HttpServletRequest request) {
		nombreCookie = request.getParameter("nombreCookie");
		contenidoCookie = request.getParameter("contenidoCookie");
	}

	private boolean parametrosNoVacios() {
		return nombreCookie != null && contenidoCookie != null;
	}

	private String crearCookie(HttpServletResponse response	) {
		String mensajeEnvio;
		Cookie laCookie;
		laCookie = new Cookie(nombreCookie, contenidoCookie);
		response.addCookie(laCookie); // Añadir la cookie a la respuesta
		mensajeEnvio = "Enviada al cliente la cookie de nombre <b>"
				+ nombreCookie + "</b> y contenido <b>" + contenidoCookie
				+ "</b> <br />";
		mensajeEnvio += "Ruta de la cookie (del servlet que la crea): <b>"
				+ rutaCompletaServlet + "</b> <br />";
		return mensajeEnvio;
	}

	private void imprimirPagina(HttpServletRequest request, PrintWriter out) {
		// Listado de cookies
		Cookie[] listaCookies = request.getCookies();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("	<meta charset=\"utf-8\" />");
		out.println("	<title>Cookies por directorio</title>");
		out.println("</head>");
		out.println("<body>");

		out.println("<h1>Cookies por directorio</h1>");
		out.println(mensajeEnvio + " <br />");
		out.println("<h2>Listado de cookies en " + rutaCompletaServlet
				+ "</h2>");
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
		out.println("<a href=\"+paginaInicio+\">Volver a la primera página</a>");
		out.println("</body>");
		out.println("</html>");
	}

}
