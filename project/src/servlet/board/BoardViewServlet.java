package servlet.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.board.BoardDAOImpl;
import dao.board.BoardMyBatisDAO;
import model.board.BoardModel;

@WebServlet("/BoardViewServlet")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardDAOImpl boardDAO = null;
	public BoardViewServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//파라미터
		String num 			= request.getParameter("num");
		String pageNum 		= request.getParameter("pageNum");
		String searchType 	= request.getParameter("searchType");
		String searchText 	= request.getParameter("searchText");
		
		//model 객체에 파라미터 값을 셋팅
		BoardModel boardModel = new BoardModel();
		boardModel.setNum(Integer.parseInt(num));
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchText);
		
		// 전체조회
		this.boardDAO = new BoardMyBatisDAO();
		boardModel = this.boardDAO.select(boardModel);
		
		// 조회수 증가, 이건 반환 값이 없음
		this.boardDAO.updateHit(boardModel);
		
		// view 사용될 객체 설정 - JSP에서 쓰려고 변수 만든 것이라 생각하면됨
		request.setAttribute("boardModel", boardModel);
		
		// JSP로 보낼, 주소 입력
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsps/board/boardView.jsp");
		requestDispatcher.forward(request, response);
				
		
	}


}
