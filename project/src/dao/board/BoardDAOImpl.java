package dao.board;

import java.util.List;

import model.board.BoardModel;

public interface BoardDAOImpl {

	// 목록조회
	public List<BoardModel> selectList(BoardModel boardModel);
	
	// 게시판 수 조회
	public int selectCount(BoardModel boardModel);
	
	// 상세조회
	public BoardModel select(BoardModel boardModel);
	
	// 등록처리
	public void insert(BoardModel boardModel);
	
	// 수정처리
	public void update(BoardModel boardModel);
	
	// 조회수 증가
	public void updateHit(BoardModel boardModel);
	
	// 삭제처리
	public void delete(BoardModel boardModel);
	
}
