package servlet.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.board.BoardDAOImpl;
import dao.board.BoardMyBatisDAO;
import model.board.BoardModel;
import util.PageNavigator;

@WebServlet("/BoardListServlet2")
public class BoardListServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BoardDAOImpl boardDAO = null;
	public BoardListServlet2() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String pageNum 		= request.getParameter("pageNum");
		String searchType 	= request.getParameter("searchType");
		String searchText 	= request.getParameter("searchText");
		
		// 파라미터
		if (pageNum == null) {
			pageNum = "1";
		}
		if (searchText == null) {
			searchType = "";
			searchText = "";
		}
		
		String searchTextUTF8 = new String(searchText.getBytes("UTF-8"), "UTF-8");
		
		BoardModel boardModel = new BoardModel();
		boardModel.setPageNum(pageNum);
		boardModel.setSearchText(searchTextUTF8);
		boardModel.setSearchType(searchType);
		
		this.boardDAO = new BoardMyBatisDAO();
		int totalCount = this.boardDAO.selectCount(boardModel);
		List<BoardModel> boardList = this.boardDAO.selectList(boardModel);
				
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("pageNavigator", new PageNavigator().getPageNavigator(
			totalCount, boardModel.getListCount(), boardModel.getPagePerBlock(), 
				Integer.parseInt(pageNum), searchType, searchTextUTF8));
		request.setAttribute("boardList", boardList);
		request.setAttribute("boardModel", boardModel);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsps/board/boardList.jsp");
		requestDispatcher.forward(request, response);
		
	}
}

