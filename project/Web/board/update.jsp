<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>

<%
	String URL 	= "jdbc:oracle:thin:@localhost:1521:xe";
	String USER = "scott";
	String PASS = "tiger";
	
	Connection conn 		= null;
	PreparedStatement pstmt = null;
	ResultSet rs 			= null;
	String query = "update board set subject = ?, writer = ?, mod_date = sysdate where board_ID = ?";
	
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(URL, USER, PASS);
		out.println("오라클 연결");
		
		// query 생성
		pstmt = conn.prepareStatement(query);
		
		// query 값 대입
		pstmt.setString(1,"수정된 주제");
		pstmt.setString(2,"수정된 글쓴이");
		pstmt.setInt(3,1);
		
		// 실행
		int cnt = pstmt.executeUpdate();
		out.println(cnt);
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (rs != null) rs.close();
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();
	}
%>