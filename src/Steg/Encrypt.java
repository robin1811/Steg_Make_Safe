package Steg;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Encrypt
 */
@WebServlet("/Encrypt")
public class Encrypt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Encrypt() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String msg = request.getParameter("msg");
		String loc = request.getServletContext().getRealPath("") + request.getParameter("image");
		String pass = request.getParameter("Pass");
		try {
			System.out.println("in try block ");
			System.out.println("msg " + msg + " location " + loc);
			System.out.println("pass " + pass);
			int i = Stegno.func(msg, pass, loc);
			System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" + i);
			System.out.println("Hiiiiiiii");
			response.sendRedirect("encryption1.html#services");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
