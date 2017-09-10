package com.test.action;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.test.dao.po.File;
import com.test.dao.po.PageBean;
import com.test.service.FileService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchFileAction extends ActionSupport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String searchcontent; //鎼滅储鐨勫唴瀹�

	private int currentpage = 1; //鐢ㄦ埛鎯崇湅鐨勯〉(鐢ㄦ埛鐐瑰嚮鐨勯偅涓�椤�)锛岄粯璁ゆ槸绗�1椤�
	private int pagesize = 5 ;   //姣忎竴涓〉闈㈠憟鐜板嚑鏉℃暟鎹紝榛樿涓�椤垫槸5鏉℃暟鎹�
	private int startindex;      //鐢ㄦ埛鎯崇湅鐨勯〉鐨勬暟鎹湪鏁版嵁搴撶殑璧峰浣嶇疆
	
	private PageBean pageBean;
	private FileService service; 

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public void setService(FileService service) {
		this.service = service;
	}
		
	public String getSearchcontent() {
		return searchcontent;
	}
	public void setSearchcontent(String searchcontent) {
		this.searchcontent = searchcontent;
	}
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		if(currentpage <= 0)
			this.currentpage = 1;
		else
			this.currentpage = currentpage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		    if(pagesize<=0)
		    	this.pagesize = 5;
		    else
			    this.pagesize = pagesize;
	}
	public int getStartindex() {
		//姣斿鐢ㄦ埛鎯崇湅绗�2椤碉紝姣忛〉5鏉℃暟鎹紝閭ｄ箞鏁版嵁鍦ㄦ暟鎹簱閲岀殑璧峰浣嶇疆鏄� 5
		this.startindex = (this.currentpage-1)*this.pagesize; 
		return startindex;
	}
	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}
		
	public String listFiles() throws Exception{
		//瑙ｅ喅get鍙傛暟涔辩爜
		searchcontent = new String(searchcontent.getBytes("iso8859-1"),"utf-8");
		return execute();
	}

	public void validateExecute(){
		if("".equals(searchcontent) || searchcontent==null){
			addFieldError("", "");
		}
	}
	
	@Override
	public String execute() throws Exception  {
		
		List<File> list;
		try {
			list = service.getAllFiles(this);
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		} 
		//鎷垮埌姣忛〉鐨勬暟鎹紝姣忎釜鍏冪礌灏辨槸涓�鏉¤褰�
		pageBean.setList(list);
		pageBean.setCurrentpage(currentpage);
		pageBean.setPagesize(pagesize);
		pageBean.setTotalrecord(service.countShareFiles(this));
		
		ServletActionContext.getRequest().setAttribute("pagebean", pageBean);
		ServletActionContext.getRequest().setAttribute("searchcontent", searchcontent);
		
		return SUCCESS;
	}
}
