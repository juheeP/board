package servlet.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.board.BoardDAOImpl;
import dao.board.BoardMyBatisDAO;
import model.board.BoardModel;

@WebServlet("/BoardDeleteServlet")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardDAOImpl boardDAO = null;
	public BoardDeleteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		String num = request.getParameter("num");
		String pageNum = request.getParameter("pageNum");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		String searchTextUTF8 = new String(searchText.getBytes("UTF-8"), "UTF-8");
		String searchTextUTF8_E = URLEncoder.encode(searchTextUTF8, "UTF-8");

		BoardModel boardModel = new BoardModel();
		boardModel.setNum(Integer.parseInt(num));
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchTextUTF8);	

		this.boardDAO = new BoardMyBatisDAO();
		this.boardDAO.delete(boardModel);

		response.sendRedirect(
			"BoardListServlet?pageNum="+pageNum+"&searchType="+searchType+"&searchText="+searchTextUTF8_E);
	}
		
}
