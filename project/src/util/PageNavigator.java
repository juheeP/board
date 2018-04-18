package util;

public class PageNavigator {
	public String getPageNavigator(int totalCount, int listCount, int pagePerBlock,
			int pageNum, String searchType, String searchText) {
		StringBuffer sb = new StringBuffer();
		if(totalCount > 0) {
			int totalNumOfPage = (totalCount % listCount == 0) ? 
					totalCount / listCount :
					totalCount / listCount + 1;
			
			int totalNumOfBlock = (totalNumOfPage % pagePerBlock == 0) ?
					totalNumOfPage / pagePerBlock :
					totalNumOfPage / pagePerBlock + 1;
			
			int currentBlock = (pageNum % pagePerBlock == 0) ? 
					pageNum / pagePerBlock :
					pageNum / pagePerBlock + 1;
			
			int startPage = (currentBlock - 1) * pagePerBlock + 1;
			int endPage = startPage + pagePerBlock - 1;
			
			if(endPage > totalNumOfPage)
				endPage = totalNumOfPage;
			boolean isNext = false;
			boolean isPrev = false;
			if(currentBlock < totalNumOfBlock)
				isNext = true;
			if(currentBlock > 1)
				isPrev = true;
			if(totalNumOfBlock == 1){
				isNext = false;
				isPrev = false;
			}
			if(pageNum > 1){
				sb.append("<a href=\"").append("BoardListServlet2?pageNum=1&amp;searchType="+searchType+"&amp;searchText="+searchText);
				sb.append("\" title=\"<<\"><<</a>&nbsp;");
			}
			if (isPrev) {
				int goPrevPage = startPage - pagePerBlock;			
				sb.append("&nbsp;&nbsp;<a href=\"").append("BoardListServlet2?pageNum="+goPrevPage+"&amp;searchType="+searchType+"&amp;searchText="+searchText);
				sb.append("\" title=\"<\"><</a>");
			} else {
				
			}
			for (int i = startPage; i <= endPage; i++) {
				if (i == pageNum) {
					sb.append("<a href=\"#\"><strong>").append(i).append("</strong></a>&nbsp;&nbsp;");
				} else {
					sb.append("<a href=\"").append("BoardListServlet2?pageNum="+i+"&amp;searchType="+searchType+"&amp;searchText="+searchText);
					sb.append("\" title=\""+i+"\">").append(i).append("</a>&nbsp;&nbsp;");
				}
			}
			if (isNext) {
				int goNextPage = startPage + pagePerBlock;
	
				sb.append("<a href=\"").append("BoardListServlet2?pageNum="+goNextPage+"&amp;searchType="+searchType+"&amp;searchText="+searchText);
				sb.append("\" title=\">\">></a>");
			} else {
				
			}
			if(totalNumOfPage > pageNum){
				sb.append("&nbsp;&nbsp;<a href=\"").append("BoardListServlet2?pageNum="+totalNumOfPage+"&amp;searchType="+searchType+"&amp;searchText="+searchText);
				sb.append("\" title=\">>\">>></a>");
			}
			
		}
		return sb.toString();
	}
}
