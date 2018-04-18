package dao.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import model.board.BoardModel;
import mybatis.MyBatis;

public class BoardMyBatisDAO implements BoardDAOImpl {

	private SqlSessionFactory sessionFactory = null;	
	public BoardMyBatisDAO() {
		this.sessionFactory = MyBatis.getSqlSessionFactory();
	}
	
	public List<BoardModel> selectList(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try {
			return session.selectList("board.selectList", boardModel);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
		return null;
	}
	
	public int selectCount(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try {
			return session.selectOne("board.selectCount", boardModel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
		return 0;
	}
	
	public BoardModel select(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try {
			return session.selectOne("board.select", boardModel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
		return null;
	}
	
	public void insert(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try {
			session.insert("board.insert", boardModel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
	}
	
	public void update(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try {
			session.update("board.update", boardModel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
	}
	
	public void updateHit(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try {
			session.update("board.updateHit", boardModel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
	}
	

	public void delete(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try {
			session.delete("board.delete", boardModel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) session.close();
		}
	}
}
