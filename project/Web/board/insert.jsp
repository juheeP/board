<%@ page language="java" 
    contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>

<%
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String USER = "scott";
	String PASS = "tiger";

	Connection conn = null;
	PreparedStatement pstmt = null;
	String query = "insert into" 
				 + "board(board_ID,subject,writer,ip,reg_date)"
				 + "values (?,?,?,?,sysdate)";
	
	try {
		// 드라이버 확인
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		// 데이터베이스 연결
		conn = DriverManager.getConnection(URL,USER,PASS);
		out.println("오라클에 연결됨");
		// 질의문 요청
		pstmt = conn.prepareStatement(query);
		// 값 바인딩
		pstmt.setInt(1, 3);
		pstmt.setString(2, "주제2");
		pstmt.setString(3, "글쓴이2");
		pstmt.setInt(4, 234);
		// 실행
		int cnt=pstmt.executeUpdate();
		out.println(cnt);
		
	} catch (SQLException e) {
		e.printStackTrace();

	} finally {
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();
	}
%>
