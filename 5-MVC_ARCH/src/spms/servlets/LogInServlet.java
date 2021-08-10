package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RequestDispatcher rd = request.getRequestDispatcher("LogInForm.jsp");
    rd.forward(request, response);
  }
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      ServletContext sc = this.getServletContext();
      conn = (Connection)sc.getAttribute("conn");
      stmt = conn.prepareStatement("select mname,email from members where email=? and pwd=?");
      stmt.setString(1, request.getParameter("email"));
      stmt.setString(2, request.getParameter("password"));
      rs = stmt.executeQuery();
      if(rs.next()) {
        Member member = new Member()
            .setName(rs.getString("mname"))
            .setEmail(rs.getString("email"));
        HttpSession session = request.getSession();
        session.setAttribute("member", member);
        
        response.sendRedirect("../member/list");
        //response.sendRedirect(this.getServletContext().getContextPath() + "/member/list");
      } else {
        RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
        rd.forward(request, response);
      }
    } catch(Exception e) {
      throw new ServletException(e);
    } finally {
      try { if(rs != null) rs.close(); } catch(Exception e) {}
      try { if(stmt != null) stmt.close(); } catch(Exception e) {}
    }
  }
}