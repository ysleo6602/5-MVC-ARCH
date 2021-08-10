package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      ServletContext sc = this.getServletContext();
      conn = (Connection)sc.getAttribute("conn");
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select mno, email, mname, cre_date from members where mno="
          + request.getParameter("no")
          );
      rs.next();
      Member member = new Member()
          .setNo(rs.getInt("mno"))
          .setName(rs.getString("mname"))
          .setEmail(rs.getString("email"))
          .setCreatedDate(rs.getDate("cre_date"));
      request.setAttribute("member", member);
      
      response.setContentType("text/html;charset=UTF-8");
      RequestDispatcher rd = request.getRequestDispatcher("MemberUpdateForm.jsp");
      rd.forward(request, response);
    } catch(Exception e) {
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);
      
    } finally {
      try { if(rs != null) rs.close(); } catch(Exception e) {}
      try { if(stmt != null) stmt.close(); } catch(Exception e) {}
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
      ServletContext sc = this.getServletContext();
      conn = (Connection)sc.getAttribute("conn");
      stmt = conn.prepareStatement("update members set email=?, mname=?, mod_date=now() where mno=?");
      stmt.setString(1, request.getParameter("email"));
      stmt.setString(2, request.getParameter("name"));
      stmt.setString(3, request.getParameter("no"));
      
      stmt.executeUpdate();
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