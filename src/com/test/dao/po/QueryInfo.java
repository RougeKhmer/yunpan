package com.test.dao.po;

public class QueryInfo {
	private int currentpage = 1; //用户想看的页(用户点击的那一页)，默认是第1页
	private int pagesize = 5 ;   //每一个页面呈现几条数据，默认一页是5条数据
	private int startindex;      //用户想看的页的数据在数据库的起始位置
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		if(this.currentpage<0) {
			this.currentpage = 1;
		}else {
			this.currentpage= currentpage;
		}
		this.currentpage = currentpage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		if(this.pagesize<0) {
			this.pagesize = 5;
		}else {
			this.pagesize = pagesize;
		}
		this.pagesize = pagesize;
	}
	public int getStartindex() {
		this.startindex = (this.currentpage-1)*this.pagesize; 
		return startindex;
	}
	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}
	
}
