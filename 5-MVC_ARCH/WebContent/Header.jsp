<%@ page import="spms.vo.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
</head>
<body>
<%
Member member = (Member)session.getAttribute("member");
%>
<div style="background-color:#00008b;color:#ffffff;height:20px;padding:5px;">
SPMS(Simple Project Management System)
<span style="float:right;">
<%if(member != null) { %>
<%=member.getName() %>
<a style="color:white;" href="<%=request.getContextPath() %>/auth/logout">로그아웃</a>
</span>
<%} else { %>
<a style="color:white;" href="<%=request.getContextPath() %>/auth/login">로그인</a>
</span>
<% } %>
</div>
</body>
</html>