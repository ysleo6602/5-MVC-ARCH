package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>회원 등록</title></head>");
    out.println("<body><h1>회원 등록</h1>");
    out.println("<form action='add' method='post'>");
    out.println("이름: <input type='text' name='name'><br>");
    out.println("이메일: <input type='text' name='email'><br>");
    out.println("암호: <input type='password' name='password'><br>");
    out.println("<input type='submit' value='추가'>");
    out.println("<input type='reset' value='취소'>");
    out.println("</form>");
    out.println("</body></html>");
  }
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      ServletContext sc = this.getServletContext();
      Class.forName(sc.getInitParameter("driver"));
      conn = DriverManager.getConnection(sc.getInitParameter("url"),
          sc.getInitParameter("username"), sc.getInitParameter("password"));
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
      throw new ServletException(e);
    } finally {
      try { if(stmt != null) stmt.close();} catch(Exception e) {}
      try { if(conn != null) conn.close();} catch(Exception e) {}
    }
  }
}