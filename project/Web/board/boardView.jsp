<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	Connection conn 		= null;
	PreparedStatement pstmt = null;
	ResultSet rs 			= null;

	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String USER = "scott";
	String PASS = "tiger";
	
	String num 			= request.getParameter("num");
	String pageNum 		= request.getParameter("pageNum");
	String searchType 	= request.getParameter("searchType");
	String searchText 	= request.getParameter("searchText");
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(URL,USER,PASS);
		
		// 조회수 증가
		pstmt = conn.prepareStatement("update board set hit = hit + 1 where board_ID = ?");
		pstmt.setInt(1, Integer.parseInt(num));
		pstmt.executeUpdate();

		// 조회
		pstmt = conn.prepareStatement("select board_ID, subject, board_contents, writer, hit, reg_date "
									+ "from board "
									+ "where board_ID = ?");
		pstmt.setInt(1, Integer.parseInt(num));
		rs = pstmt.executeQuery();
		rs.next();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>게시판 상세보기</title>

<style type="text/css">
	* {font-size: 9pt;}
	p {width: 600px; text-align: right;}
	table tbody tr th {background-color: gray;}
</style>

<script type="text/javascript">
	function goUrl(url) {
		location.href=url;
	}
	
	function deleteCheck(url) {
		if (confirm('정말 삭제하시겠습니까?')) {
			location.href=url;
		}	
	}
</script>
</head>
<body><%= searchText %>
	<table border="1" summary="게시판 상세조회">
		<colgroup>
			<col width="100" />
			<col width="500" />
		</colgroup>
		
		<caption>게시판 상세조회</caption>
		
		<tbody>
			<tr>
				<th align="center">제목</th>
				<td><%= rs.getString("subject") %></td>
			</tr>
			<tr>
				<th align="center">작성자/조회수</th>
				<td><%= rs.getString("writer") %>/<%= rs.getInt("hit") %></td>
			</tr>
			<tr>
				<th align="center">등록 일시</th>
				<td><%=rs.getDate("reg_date") %></td>
			</tr>
			<tr>
				<td colspan="2"><%= rs.getString("board_contents") %></td>
			</tr>
		</tbody>	
	</table>
	<p>
		<input type="button" value="목록" onclick="goUrl('boardList.jsp?pageNum=<%=pageNum%>&amp;searchType=<%=searchType%>&amp;searchText=<%=searchText%>');"/>
		<input type="button" value="수정" onclick="goUrl('boardModifyForm.jsp?num=<%=num%>&amp;pageNum=<%=pageNum%>&amp;searchType=<%=searchType%>&amp;searchText=<%=searchText%>');"/>
		<input type="button" value="삭제" onclick="deleteCheck('boardProcess.jsp?mode=D&amp;num=<%=num%>&amp;pageNum=<%=pageNum%>&amp;searchType=<%=searchType%>&amp;searchText=<%=searchText%>');"/>
	</p>
</body>
</html>
<%
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (rs != null) rs.close();
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();
	}
%>