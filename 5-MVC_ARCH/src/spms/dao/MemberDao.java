package spms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import spms.vo.Member;

public class MemberDao {
  Connection conn;
  
  public void setConnection(Connection conn) {
    this.conn = conn;
  }
  
  public List<Member> selectList() throws Exception {
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conn.createStatement();
      rs = stmt.executeQuery("seletct mno, mname, email, cre_date from members order by mno asc");
      ArrayList<Member> members = new ArrayList<Member>();
      while(rs.next()) {
        members.add(new Member().setNo(rs.getInt("mno"))
            .setName(rs.getString("mname"))
            .setEmail(rs.getString("email"))
            .setCreatedDate(rs.getDate("cre_date"))
            );      
      }
        return members;
      } catch(Exception e) {    
        throw e;
      } finally {
        try { if(rs != null) rs.close(); } catch(Exception e) {}
        try { if(stmt != null) stmt.close(); } catch(Exception e) {}
      }
  }  
}