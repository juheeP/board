package model.board;

import java.sql.Date;

public class BoardModel {

	// num 타입은 주의가 필요하다.
	private int num;
	private String subject;
	private String writer;
	private String contents;
	private String ip;
	private int hit = 0;
	
	// 아직, date 타입인데 이렇게 써도 되는지가 의문
	private Date regDate;
	private Date modeDate;
	
	// URL의 매개변수로 들어간다.
	private String pageNum = "1";
	private String searchType = "";
	private String searchText = "";
	
	// 페이징 처리시에 필요하다.
	private int listCount = 10;
	private int pagePerBlock = 10;
		
	// 생성자
	public BoardModel() {
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date date) {
		this.regDate = date;
	}
	public Date getModeDate() {
		return modeDate;
	}
	public void setModeDate(Date modeDate) {
		this.modeDate = modeDate;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public int getListCount() {
		return listCount;
	}	
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	public int getPagePerBlock() {
		return pagePerBlock;
	}
	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}
}

