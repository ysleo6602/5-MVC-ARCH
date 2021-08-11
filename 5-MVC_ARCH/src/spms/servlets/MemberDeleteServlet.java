package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;

@SuppressWarnings("serial")
@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PreparedStatement stmt = null;
    try {
      ServletContext sc = this.getServletContext();
      MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
      memberDao.delete(Integer.parseInt(request.getParameter("no")));
      
      response.sendRedirect("list");
    } catch (Exception e) {
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);
    } finally {
      try { if(stmt != null) stmt.close(); } catch(Exception e) {}
    }
  }
}