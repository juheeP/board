<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	// POST 한글 파라미터 깨짐 처리
	request.setCharacterEncoding("UTF-8");
	// 사용할 객체 초기화
	Connection conn 		= null;
	PreparedStatement pstmt = null;
	
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String USER = "scott";
	String PASS = "tiger";
	
	// 파라미터
	String mode 		= request.getParameter("mode");
	String subject 		= request.getParameter("subject");
	String writer 		= request.getParameter("writer");
	String contents 	= request.getParameter("contents");
	String num 			= request.getParameter("num");
	String pageNum 		= request.getParameter("pageNum");
	String searchType 	= request.getParameter("searchType");
	String searchText 	= request.getParameter("searchText");
	String ip 			= request.getRemoteAddr();
	try {
		// 데이터베이스 객체 생성
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(URL,USER,PASS);
		// 처리 (W:등록, M:수정, D:삭제)
		if ("W".equals(mode)) {
			pstmt = conn.prepareStatement(
				"INSERT INTO BOARD (board_ID,SUBJECT, WRITER, board_CONTENTS, IP, HIT, REG_DATE, MOD_DATE) "+
				"VALUES (board_ID_seq.nextval,?, ?, ?, ?, 0, sysdate, sysdate)");
			pstmt.setString(1, subject);
			pstmt.setString(2, writer);
			pstmt.setString(3, contents);
			pstmt.setString(4, ip);
			pstmt.executeUpdate();
	
			response.sendRedirect("boardList.jsp");
		} else if ("M".equals(mode)) {
			pstmt = conn.prepareStatement(
				"UPDATE BOARD SET SUBJECT = ?, WRITER = ?, board_CONTENTS = ?, IP = ?, MOD_DATE = sysdate "+
				"WHERE board_ID = ?");
			pstmt.setString(1, subject);
			pstmt.setString(2, writer);
			pstmt.setString(3, contents);
			pstmt.setString(4, ip);
			pstmt.setInt(5, Integer.parseInt(num));
			pstmt.executeUpdate();
			
			response.sendRedirect(
				"boardView.jsp?num="+num+"&pageNum="+pageNum+"&searchType="+searchType+"&searchText="+searchText);
		} else if ("D".equals(mode)) {
			pstmt = conn.prepareStatement("DELETE FROM BOARD WHERE board_ID = ?");
			pstmt.setInt(1, Integer.parseInt(num));
			pstmt.executeUpdate();
			
			response.sendRedirect(
				"boardList.jsp?pageNum="+pageNum+"&searchType="+searchType+"&searchText="+searchText);
		} else {
			response.sendRedirect("boardList.jsp");
		}

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();
	}
%>
