package it.unimol.my.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * La servlet che gestisce le richieste di login restituisce le informazioni di
 * base dell'utente sottoforma di JSON
 * 
 * @author Ivan Di Rienzo
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/test-credentials" })
public class LoginServlet extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String token = request.getParameter("token");

		// TODO integrare (quando sara' pronta) la componente di validazione del
		// token

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		/**
		 * Questo potrebbe essere l'URL adatto all'estrazione delle informazioni
		 * base dell'utente
		 */
		URL targetURL = new URL("https://unimol.esse3.cineca.it/Home.do");

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		LoginParser loginParser = LoginParserManager.getLoginParser();
		UserInformation userInformation = loginParser.getLoginInformation(
				targetURL, username, password);

		if (userInformation == null /* OR token non valido */) {
			// TODO restituire JSON login fallito
			// esempio
			out.println("{'login':'failure'}");
		} else {
			// TODO restituire JSON con info utente
		}

		out.close();
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
