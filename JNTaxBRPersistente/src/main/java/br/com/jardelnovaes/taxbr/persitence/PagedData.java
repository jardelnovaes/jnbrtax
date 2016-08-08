package br.com.jardelnovaes.taxbr.persitence;

public class PagedData {
	public static final int DEFAULT_TOTAL_DISP_PAGES = 5;
	private int pageNumber;
	private int pageSize;
	private int totalDisplayablePages;
	private long totalRecords;
	
	private boolean descending;
	private String orderPropertyName;
	
	public PagedData(){
		this.totalDisplayablePages = DEFAULT_TOTAL_DISP_PAGES;
		this.orderPropertyName = "";
	}
	
	public PagedData(int pageNumber, int pageSize){
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalDisplayablePages = DEFAULT_TOTAL_DISP_PAGES;
		this.orderPropertyName = "";
	}
	
	public PagedData(int pageNumber, int pageSize, int totalDisplayablePages){
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalDisplayablePages = totalDisplayablePages;
		this.orderPropertyName = "";
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		if(pageNumber < 0)
			pageNumber = 0;
		
		if(pageNumber > getPageCount()-1)
			pageNumber = getPageCount()-1;
		
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getTotalDisplayablePages() {
		return totalDisplayablePages;
	}
	public void setTotalDisplayablePages(int totalDisplayablePages) {
		this.totalDisplayablePages = totalDisplayablePages;
	}
	
	public int getPreviousPage(){
		if(getPageNumber() == 0)
			return 0;
		else
			return getPageNumber()-1;
	}
	
	public int getNextPage(){
		if(getPageNumber() == getPageCount()-1)
			return getPageCount()-1;
		else
			return getPageNumber()+1;
	}
	
	public int getPageCount() {
		if (totalRecords == 0) { 
			return 1; 
		}
		return (int) (((totalRecords-1) / pageSize)+1);
		
	}
	
	public int getFirstDisplayablePage(){
		// 1  2  3  4  5
		//10 11 12 13 14
		int p = -1;
		if((getPageNumber() +1 ) % getTotalDisplayablePages() == 0){
			p = getPageNumber();
			if(p == getPageCount()-1)
				p -= getTotalDisplayablePages() -1; 
		}
		else if(getPageNumber() > 0){
			p = (getPageNumber() +1 ) / getTotalDisplayablePages() * getTotalDisplayablePages();			
		}
		else {
			p = 0;
		}
	
		return p;
	}
	
	public int getLastDisplayablePage(){
		int p = getFirstDisplayablePage() + getTotalDisplayablePages() -1;
		
		if(p > getPageCount()-1)
			p = getPageCount()-1;
		
		return  p;
	}
	
	public boolean getLastPage(){
		//TODO Revisar a formula.
		return (pageNumber == getPageCount());
	}
	
	public boolean isDescending() {
		return descending;
	}
	public void setDescending(boolean isDescending) {
		this.descending = isDescending;
	}
	public String getOrderPropertyName() {
		return orderPropertyName;
	}
	public void setOrderPropertyName(String orderPropertyName) {
		this.orderPropertyName = orderPropertyName;
	}
	
	
}
