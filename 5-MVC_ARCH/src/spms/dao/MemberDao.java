package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.vo.Member;

public class MemberDao {
  DataSource ds;
  
  public void setDataSource(DataSource ds) {
    this.ds = ds;
  }
  
  public List<Member> selectList() throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select mno, mname, email, cre_date from members order by mno asc");
      ArrayList<Member> members = new ArrayList<Member>();
      while(rs.next()) {
        members.add(new Member().setNo(rs.getInt("mno"))
            .setName(rs.getString("mname"))
            .setEmail(rs.getString("email"))
            .setCreatedDate(rs.getDate("cre_date")) );      
      }
        return members;
      } catch(Exception e) {    
        throw e;
      } finally {
        try { if(rs != null) rs.close(); } catch(Exception e) {}
        try { if(stmt != null) stmt.close(); } catch(Exception e) {}
        try { if(conn != null) conn.close(); } catch(Exception e) {}
      }
  }
  
  public int insert(Member member) throws Exception {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.prepareStatement("insert into members(mname, email, pwd, cre_date, mod_date)"
          + "values(?,?,?,now(),now())");
      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPassword());
      
      return stmt.executeUpdate();
    } catch(Exception e) {
      throw e;
    } finally {
      try { if(stmt != null) stmt.close(); } catch(Exception e) {}
      try { if(conn != null) conn.close(); } catch(Exception e) {}
    }
  }
  
  public int delete(int no) throws Exception {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.prepareStatement("delete from members where mno=?");
      stmt.setString(1, Integer.toString(no));
      
      return stmt.executeUpdate();
    } catch(Exception e) {
      throw e;
    } finally {
      try { if(stmt != null) stmt.close(); } catch(Exception e) {}
      try { if(conn != null) conn.close(); } catch(Exception e) {}
    }
  }
  
  public Member selectOne(int no) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select mno, email, mname, cre_date from members where mno="
          + Integer.toString(no)
          );
      rs.next();
      return new Member()
          .setNo(rs.getInt("mno"))
          .setEmail(rs.getString("email"))
          .setName(rs.getString("mname"))
          .setCreatedDate(rs.getDate("cre_date")); 
    } catch(Exception e) {
      throw e;
    } finally {
      try { if(rs != null) rs.close(); } catch(Exception e) {}
      try { if(stmt != null) stmt.close(); } catch(Exception e) {}
      try { if(conn != null) conn.close(); } catch(Exception e) {}
    }
  }
  
  public int update(Member member) throws Exception {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.prepareStatement("update members set email=?, mname=?, mod_date=now() where mno=?");
      stmt.setString(1, member.getEmail());
      stmt.setString(2, member.getName());
      stmt.setInt(3, member.getNo());
      
      return stmt.executeUpdate();
    } catch(Exception e) {
      throw e;
    } finally {
      try { if(stmt != null) stmt.close(); } catch(Exception e) {}
      try { if(conn != null) conn.close(); } catch(Exception e) {}
    }
  }
  
  public Member exist(String email, String password) throws Exception {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.prepareStatement("select mname, email from members where email=? and pwd=?");
      stmt.setString(1, email);
      stmt.setString(2, password);
      rs = stmt.executeQuery();
      if(rs.next()) {
        return new Member()
            .setName(rs.getString("mname"))
            .setEmail(rs.getString("email"));
      } else {
        return null;
      }
    } catch(Exception e) {
      throw e;
    } finally {
      try { if(rs != null) rs.close(); } catch(Exception e) {}
      try { if(stmt != null) stmt.close(); } catch(Exception e) {}
      try { if(conn != null) conn.close(); } catch(Exception e) {}
    }
  }
}