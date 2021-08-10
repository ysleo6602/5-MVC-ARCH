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

@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    RequestDispatcher rd = request.getRequestDispatcher("MemberForm.jsp");
    rd.forward(request, response);
  }
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      ServletContext sc = this.getServletContext();
      conn = (Connection)sc.getAttribute("conn");
      stmt = conn.prepareStatement("insert into members(mname, email, pwd, cre_date, mod_date)"
          + "values(?,?,?,now(),now())");
      stmt.setString(1, request.getParameter("name"));
      stmt.setString(2, request.getParameter("email"));
      stmt.setString(3, request.getParameter("password"));
      stmt.executeUpdate();
      
      // 리프래시 - 메타태그를 이용한 방법
      //response.setContentType("text/html;charset=UTF-8");
      //PrintWriter out = response.getWriter();
      //out.println("<html><head><title>회원등록결과</title>");
      //out.println("<meta http-equiv='Refresh' content='5;url=list'></head>");
      //out.println("<body><p>등록 성공입니다.</p></body></html>")
      
      // 리프래시 - 헤더를 이용한 방법;
      //response.setContentType("text/html;charset=UTF-8");
      //response.addHeader("Refresh", "5;url=list");
      
      response.sendRedirect("list");
    } catch(Exception e) {
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);      
    } finally {
      try { if(stmt != null) stmt.close();} catch(Exception e) {}
    }
  }
}