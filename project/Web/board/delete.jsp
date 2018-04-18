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
	String query = "delete from board where board_ID = ?";
	
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(URL, USER, PASS);
		out.println("오라클 연결");
		
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1,1);
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