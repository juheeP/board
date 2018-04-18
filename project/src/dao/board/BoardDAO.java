package dao.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.board.BoardModel;

public class BoardDAO {
	private Connection conn 		= null;
	private PreparedStatement pstmt = null;
	private ResultSet rs 			= null;
	
	private final String JDBC_DRIVER 	= "oracle.jdbc.driver.OracleDriver";
	private final String DB_URL 		= "jdbc:oracle:thin:@localhost:1521:xe";
	private final String DB_ID 			= "scott";
	private final String DB_PWD 		= "tiger";
	
	// 게시판 목록 조회
	public List<BoardModel> selectList(BoardModel boardModel) {
		int pageNum = Integer.parseInt(boardModel.getPageNum());
		int listCount = boardModel.getListCount();
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		String whereSQL = "";
		
		// 반환할 리스트 타입 객체 정의
		List<BoardModel> boardList = null;
		
		try {			
			if (!"".equals(searchText)) {
				if ("ALL".equals(searchType)) {
					whereSQL = " WHERE SUBJECT LIKE '%' || ? || '%' OR WRITER LIKE '%' || ? || '%' OR board_CONTENTS LIKE '%' || ? || '%' ";
				} else if ("SUBJECT".equals(searchType)) {
					whereSQL = " WHERE SUBJECT LIKE '%' || ? || '%' ";
				} else if ("WRITER".equals(searchType)) {
					whereSQL = " WHERE writer LIKE '%' || ? || '%' ";
				} else if ("CONTENT".equals(searchType)) {
					whereSQL = " WHERE board_contents LIKE '%' || ? || '%' ";
				}
			}
			
			// 본격적인 쿼리 시작
			Class.forName(this.JDBC_DRIVER);
			this.conn = DriverManager.getConnection(this.DB_URL,this.DB_ID,this.DB_PWD);
			
			// 총 찾은 값의 수
			this.pstmt = conn.prepareStatement("select board_ID, SUBJECT, WRITER, REG_DATE, HIT" 
											 + " FROM (select board_ID, SUBJECT, WRITER, REG_DATE, HIT from board"+whereSQL+" ORDER BY board_ID DESC)"
											 + " where ROWNUM >= ? AND ROWNUM <= ?");
										
			if (!"".equals(whereSQL)) {
				if ("ALL".equals(searchType)) {
					this.pstmt.setString(1, searchText);
					this.pstmt.setString(2, searchText);
					this.pstmt.setString(3, searchText);
					this.pstmt.setInt(4, listCount * (pageNum-1));
					this.pstmt.setInt(5, listCount);			
				} else {
					this.pstmt.setString(1, searchText);
					this.pstmt.setInt(2, listCount * (pageNum-1));
					this.pstmt.setInt(3, listCount);			
				}
			} else {	
				this.pstmt.setInt(1, listCount * (pageNum-1));
				this.pstmt.setInt(2, listCount);
			}
			// 조회
			this.rs = this.pstmt.executeQuery();
			boardList = new ArrayList<BoardModel>();
			// LIST 객체에 담기
			while(this.rs.next()) {
				// 왜, 새로운 객체를 생성하는 거지???????, 아 리스트에 담아둘 건데, 그때마다 BoardModel 타입으로 쌓을 것이기 때문이다. 
				boardModel = new BoardModel();
				boardModel.setNum(this.rs.getInt("board_ID"));
				boardModel.setSubject(this.rs.getString("SUBJECT"));
				boardModel.setWriter(this.rs.getString("WRITER"));
				boardModel.setHit(this.rs.getInt("HIT"));
				boardModel.setRegDate(this.rs.getDate("REG_DATE"));
				boardList.add(boardModel);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(this.rs,this.pstmt,this.conn);
		}
		
		return boardList;
	}
	
	// 게시판 수 조회
	public int selectCount(BoardModel boardModel) {
		int totalCount = 0;
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		String whereSQL = "";
		try {
			if (!"".equals(searchText)) {
				if ("ALL".equals(searchType)) {
					whereSQL = " WHERE SUBJECT LIKE '%' || ? || '%' OR WRITER LIKE '%' || ? || '%' OR board_CONTENTS LIKE '%' || ? || '%' ";
				} else if ("SUBJECT".equals(searchType)) {
					whereSQL = " WHERE SUBJECT LIKE '%' || ? || '%' ";
				} else if ("WRITER".equals(searchType)) {
					whereSQL = " WHERE writer LIKE '%' || ? || '%' ";
				} else if ("CONTENT".equals(searchType)) {
					whereSQL = " WHERE board_contents LIKE '%' || ? || '%' ";
				}
			}

			Class.forName(this.JDBC_DRIVER);
			this.conn = DriverManager.getConnection(this.DB_URL, this.DB_ID, this.DB_PWD);
			
			this.pstmt = this.conn.prepareStatement("select count(board_ID) as total from board" + whereSQL);
			if (!"".equals(whereSQL)) {
				if ("ALL".equals(searchType)) {
					this.pstmt.setString(1, searchText);
					this.pstmt.setString(2, searchText);
					this.pstmt.setString(3, searchText);
				} else {
					this.pstmt.setString(1, searchText);
				}
			}
			this.rs = this.pstmt.executeQuery();
			if (this.rs.next()) {
				totalCount = this.rs.getInt("TOTAL");
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(this.rs,this.pstmt,this.conn);
		}
		return totalCount;
	}
	
	
	// 게시판 상세 조회
	public BoardModel select(BoardModel boardModel) {
		try {
			Class.forName(this.JDBC_DRIVER);
			this.conn = 
					DriverManager.getConnection(this.DB_URL, this.DB_ID, this.DB_PWD);
			this.pstmt = this.conn.prepareStatement("select board_ID, SUBJECT, board_contents, WRITER, REG_DATE, HIT" 
												  + "  FROM board"
												  + " where board_ID = ?"
												   );
			
			this.pstmt.setInt(1, boardModel.getNum());
			this.rs = this.pstmt.executeQuery();
			
			if (this.rs.next()) {
				boardModel.setNum(this.rs.getInt("board_ID"));
				boardModel.setSubject(this.rs.getString("subject"));
				boardModel.setContents(this.rs.getString("board_contents"));
				boardModel.setWriter(this.rs.getString("writer"));
				boardModel.setHit(this.rs.getInt("hit"));
				boardModel.setRegDate(this.rs.getDate("reg_date"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(this.rs,this.pstmt,this.conn);
		}
			
		return boardModel;
	}
	

	// 등록처리
	public void insert(BoardModel boardModel) {
		try {
			Class.forName(this.JDBC_DRIVER);
			this.conn = DriverManager.getConnection(this.DB_URL, this.DB_ID, this.DB_PWD);
			this.pstmt = this.conn.prepareStatement(
				"INSERT INTO BOARD (board_ID, SUBJECT, WRITER, board_CONTENTS, IP, HIT, REG_DATE, MOD_DATE) "+
				"VALUES (board_ID_seq.nextval,?, ?, ?, ?, 0, sysdate, sysdate)");
			this.pstmt.setString(1, boardModel.getSubject());
			this.pstmt.setString(2, boardModel.getWriter());
			this.pstmt.setString(3, boardModel.getContents());
			this.pstmt.setString(4, boardModel.getIp());
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, this.pstmt, this.conn);
		}	
	}
	
	// 수정처리
	public void update(BoardModel boardModel) {
		try {
			Class.forName(this.JDBC_DRIVER);
			this.conn = DriverManager.getConnection(this.DB_URL, this.DB_ID, this.DB_PWD);
			this.pstmt = this.conn.prepareStatement(
				"UPDATE BOARD SET SUBJECT = ?, WRITER = ?, board_CONTENTS = ?, IP = ?, MOD_DATE = sysdate "+
				"WHERE board_ID = ?");
			this.pstmt.setString(1, boardModel.getSubject());
			this.pstmt.setString(2, boardModel.getWriter());
			this.pstmt.setString(3, boardModel.getContents());
			this.pstmt.setString(4, boardModel.getIp());
			this.pstmt.setInt(5, boardModel.getNum());
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, this.pstmt, this.conn);
		}	

	}
	
	// 조회수 증가 수정
	public void updateHit(BoardModel boardModel) {
		try {
			Class.forName(this.JDBC_DRIVER);
			this.conn = DriverManager.getConnection(this.DB_URL, this.DB_ID, this.DB_PWD);

			this.pstmt = this.conn.prepareStatement("UPDATE BOARD SET HIT = HIT + 1 WHERE board_ID = ?");
			this.pstmt.setInt(1, boardModel.getNum());
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, this.pstmt, this.conn);
		}
	}
	
	// 삭제처리
	public void delete(BoardModel boardModel) {
		try {
			Class.forName(this.JDBC_DRIVER);
			this.conn = DriverManager.getConnection(this.DB_URL, this.DB_ID, this.DB_PWD);

			this.pstmt = this.conn.prepareStatement("DELETE FROM BOARD WHERE board_ID = ?");
			this.pstmt.setInt(1, boardModel.getNum());
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, this.pstmt, this.conn);
		}
	}

	private void close(ResultSet rs2, PreparedStatement pstmt2, Connection conn2) {
		if(rs != null) {
			try {rs.close();} catch(SQLException e) { e.printStackTrace(); }
		}
		if(pstmt != null) {
			try {pstmt.close();} catch(SQLException e) { e.printStackTrace(); }
		}
		if(conn != null) {
			try {conn.close();} catch(SQLException e) { e.printStackTrace(); }
		}
	}
}
