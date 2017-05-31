/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databeans.UserBean;
import model.Model;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}

	public void init() throws ServletException {
		Model model = new Model(getServletConfig());

		Action.add(new LoginAction(model));
		Action.add(new RegisterAction(model));
		Action.add(new ManageAction(model));
		Action.add(new LogoutAction(model));
		Action.add(new ListAction(model));
		Action.add(new UpdateFavoriteAction(model));
		Action.add(new UpdateClickAction(model));
		Action.add(new RemoveAction(model));
		Action.add(new ChangePasswordAction(model));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextPage = performAction(request);
		sendToNextPage(nextPage, request, response);
	}

	private String performAction(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String servletPath = request.getServletPath();
		UserBean user = (UserBean) session.getAttribute("user");
		String action = getActionName(servletPath);

		if (action.equals("register.do") || action.equals("login.do") || action.equals("list.do")
				|| action.equals("update-click.do")) {
			return Action.perform(action, request);
		}

		if (user == null) {
			return Action.perform("login.do", request);
		}

		return Action.perform(action, request);
	}

	private String getActionName(String servletPath) {
		int index = servletPath.lastIndexOf('/');
		return servletPath.substring(index + 1);
	}

	private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getServletPath());
			return;
		}

		if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
		}

		if (nextPage.endsWith(".jsp")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/" + nextPage);
			dispatcher.forward(request, response);
			return;
		}

		throw new ServletException(
				Controller.class.getName() + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");

	}
}
