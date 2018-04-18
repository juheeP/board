package servlet.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.board.BoardDAOImpl;
import dao.board.BoardMyBatisDAO;
import model.board.BoardModel;

@WebServlet("/BoardModifyServlet")
public class BoardModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BoardDAOImpl boardDAO = null;
	public BoardModifyServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String num = request.getParameter("num");
		String pageNum = request.getParameter("pageNum");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		String searchTextUTF8 = new String(searchText.getBytes("ISO-8859-1"), "UTF-8");
		
		BoardModel boardModel = new BoardModel();
		boardModel.setNum(Integer.parseInt(num));
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchTextUTF8);

		this.boardDAO = new BoardMyBatisDAO();
		boardModel = this.boardDAO.select(boardModel);

		request.setAttribute("boardModel", boardModel);

		RequestDispatcher requestDispatcher =
				request.getRequestDispatcher("/WEB-INF/jsps/board/boardModify.jsp");
			requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// 사용자가 한글 입력 시에, 필수 사항
		request.setCharacterEncoding("UTF-8");
		
		// 파라미터 가져오기
		String num = request.getParameter("num");
		String subject = request.getParameter("subject");
		String writer = request.getParameter("writer");
		String contents = request.getParameter("contents");
		String pageNum = request.getParameter("pageNum");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		
		//인코더를 또해주네...
		String searchTextUTF8_E = URLEncoder.encode(searchText, "UTF-8");
		String ip = request.getRemoteAddr();

		BoardModel boardModel = new BoardModel();
		boardModel.setNum(Integer.parseInt(num));
		boardModel.setSubject(subject);
		boardModel.setWriter(writer);
		boardModel.setContents(contents);
		boardModel.setIp(ip);
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchText);

		this.boardDAO = new BoardMyBatisDAO();
		this.boardDAO.update(boardModel);

		response.sendRedirect(
			"BoardViewServlet?num="+num+"&pageNum="+pageNum+"&searchType="+searchType+"&searchText="+searchTextUTF8_E);

	}

}
