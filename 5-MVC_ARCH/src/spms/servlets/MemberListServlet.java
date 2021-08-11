package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      ServletContext sc = this.getServletContext();
      response.setContentType("text/html; charset=UTF-8");
      MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
      
      request.setAttribute("members", memberDao.selectList());
            
      RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
      rd.include(request, response);
      
    } catch(Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e);;
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);
    }
  }
}