package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import project.Member;

public class MemberDAOImpl{
    String url,user,pwd;
	
	//생성자->디비 연결
	public MemberDAOImpl() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url="jdbc:oracle:thin:@localhost:1521:xe";
			user="system";
			pwd="1234";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//회원가입-점수는 0으로 우선 설정
		public void memberInsert(Member m) {
				Connection con=null;
				PreparedStatement ps=null;
				try {
					con=DriverManager.getConnection(url,user,pwd);
					String sql="INSERT INTO member VALUES (member_seq.nextval,?,?,?,?,?)";
					ps=con.prepareStatement(sql);
					ps.setString(1, m.getName());
					ps.setString(2, m.getId());
					ps.setString(3, m.getPw());
					ps.setString(4, m.getEmail());
					ps.setInt(5, 0);
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					closeConnection(con,ps);
				}
			}
    //로그인-로그인 하면 id와 pw가 맞는지 체크
	public int loginCheck(String id, String pw) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		int result=0;
		try {
			con=DriverManager.getConnection(url, user, pwd);
			st=con.createStatement();
			String sql="select * from member where id='" + id + "'";
            rs = st.executeQuery(sql); 
            if (rs.next()==false || (id.isEmpty())==true) { // id가 존재x
                result=1;
            } else { //id가 존재O
                sql="select * from (select * from member where id='" + id + "')";
                rs=st.executeQuery(sql);
                while (rs.next()==true) {         // 다음값의
                    if (rs.getString(4).equals(pw)) { // member DB의 4째열 PW와 같은지 비교
                        result=0;         // 같으면 로그인 성공
                    }else {                // 아이디는같고 pw가 다른경우
                        result=1;
                    }
                }
            }
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return result;
	}
	//닫기 종료 메소드
	public void closeConnection(Connection con, Statement st, ResultSet rs) {
		try {
			if(con!=null) con.close();
			if(st!=null) st.close();
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void closeConnection(Connection con, PreparedStatement ps) {
		try {
			if(con!=null) con.close();
			if(ps!=null) ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
