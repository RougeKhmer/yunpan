package com.test.dao.po;

public class QueryInfo {
	private int currentpage = 1; //�û��뿴��ҳ(�û��������һҳ)��Ĭ���ǵ�1ҳ
	private int pagesize = 5 ;   //ÿһ��ҳ����ּ������ݣ�Ĭ��һҳ��5������
	private int startindex;      //�û��뿴��ҳ�����������ݿ����ʼλ��
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
