package spms.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      ServletContext sc = this.getServletContext();
      MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
      request.setAttribute("member", memberDao.selectOne(Integer.parseInt(request.getParameter("no")))
          );
     
      response.setContentType("text/html;charset=UTF-8");
      
      RequestDispatcher rd = request.getRequestDispatcher("MemberUpdateForm.jsp");
      rd.forward(request, response);

    } catch(Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      ServletContext sc = this.getServletContext();
      MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
      Member member = new Member()
          .setNo(Integer.parseInt(request.getParameter("no")))
          .setName(request.getParameter("name"))
          .setEmail(request.getParameter("email"));
      memberDao.update(member);
          
      response.sendRedirect("list");
      
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);
    }
  }
}