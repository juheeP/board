<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	Connection conn 			= null;
	PreparedStatement pstmt 	= null;
	ResultSet rs 				= null;
	
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
		
		// 화면에 보여줄 내용
		pstmt = conn.prepareStatement("select board_ID,board_contents,subject,writer,hit,reg_date from board " 
									+ "where board_ID = ?");
		pstmt.setInt(1, Integer.parseInt(num));
		rs = pstmt.executeQuery();
		rs.next();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>게시판 수정 폼</title>
<style type="text/css">
	* {font-size: 9pt;}
	p {width: 600px; text-align: right;}
	table tbody tr th {background-color: gray;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditior/ckeditor.js"></script>
<script type="text/javascript">
	function goUrl(url) {
		location.href=url;
	}
	
	function boardModifyCheck() {
		var form = document.boardModifyForm;
		if (form.subject.value == '') {
			alert('제목을 입력하세요');
			form.subject.focus();
			return false;
		}
		
		if (form.writer.value == '') {
			alert('작성자를 입력하세요');
			form.writer.focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<form name="boardModifyForm" action="boardProcess.jsp" method="post" 
	onsubmit="return boardModifyCheck();">
	<input type="hidden" name="mode" value="M" />
	<input type="hidden" name="num" value="<%= num %>" />
	<input type="hidden" name="pageNum" value="<%= pageNum %>" />
	<input type="hidden" name="searchType" value="<%= searchType %>" />
	<input type="hidden" name="searchText" value="<%= searchText %>" />
	<table border="1" summary="게시판 수정 폼">
		<caption>게시판 수정 폼</caption>
		<colgroup>
			<col width="100" />
			<col width="500" />
		</colgroup>
		<tbody>
			<tr>
				<th align="center">제목</th>
				<td>
					<input type="text" name="subject" size="80" maxlength="100" value="<%= rs.getString("SUBJECT")%>"/>
				</td>
			</tr>
			<tr>
				<th align="center">작성자</th>
				<td>
					<input type="text" name="writer" maxlength="20" value="<%= rs.getString("writer")%>" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea name="contents" rows="10" cols="80"><%= rs.getString("board_contents") %></textarea>
					<script>
					CKEDITOR.replace('contents');
					</script>
				</td>
			</tr>
		</tbody>
	</table>
	
	<p>
		<input type="button" value="목록" onclick="goUrl('boardList.jsp?pageNum=<%=pageNum%>&amp;searchType=<%=searchType%>&amp;searchText=<%=searchText%>');" />
		<input type="submit" value="글수정"/>
	</p>
	</form>
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