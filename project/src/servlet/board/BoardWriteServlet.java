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

@WebServlet("/BoardWriteServlet")
public class BoardWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BoardDAOImpl boardDAO = null;
    public BoardWriteServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsps/board/boardWrite.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String subject = request.getParameter("subject");
		String writer = request.getParameter("writer");
		String contents = request.getParameter("contents");
		String ip = request.getRemoteAddr();

		BoardModel boardModel = new BoardModel();
		boardModel.setSubject(subject);
		boardModel.setWriter(writer);
		boardModel.setContents(contents);
		boardModel.setIp(ip);

		this.boardDAO = new BoardMyBatisDAO();
		this.boardDAO.insert(boardModel);

		response.sendRedirect("BoardListServlet2");
		}

	}

