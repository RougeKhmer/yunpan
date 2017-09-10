package com.test.action;

import java.io.Serializable;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.test.dao.po.File;
import com.test.dao.po.PageBean;
import com.test.service.FileService;
import com.test.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchUserFileAction extends ActionSupport implements Serializable{

	private int currentpage = 1; //鐢ㄦ埛鎯崇湅鐨勯〉(鐢ㄦ埛鐐瑰嚮鐨勯偅涓�椤�)锛岄粯璁ゆ槸绗�1椤�
	private int pagesize = 5 ;   //姣忎竴涓〉闈㈠憟鐜板嚑鏉℃暟鎹紝榛樿涓�椤垫槸5鏉℃暟鎹�
	private int startindex;      //鐢ㄦ埛鎯崇湅鐨勯〉鐨勬暟鎹湪鏁版嵁搴撶殑璧峰浣嶇疆 鐢变笂闈㈢殑鍊艰绠�
	
	private String filepath; //file琛ㄧ殑鏂囦欢璺緞灏辨槸鎵�灞炵殑鐢ㄦ埛鐨勭敤鎴峰悕
	
	private PageBean pageBean;
	
	private FileService fileService; 
	private UserService userService; 
	
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
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
		this.startindex = (this.currentpage-1)*this.pagesize; 
		return startindex;
	}
	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}
		
	@Override
	public String execute() throws Exception  {
		//鏍规嵁鐢ㄦ埛鏌ユ壘鍑哄畠鎵�鏈夌殑鏂囦欢
		List<File> list;
		try {
			String username = (String) ActionContext.getContext().getSession().get("user_name");
			//session娌℃湁鐢ㄦ埛鍚嶈鏄庢病鏈夌櫥闄嗭紝璁╀粬杞幓涓婚〉
			if(username==null || "".equals(username)){
				return INPUT;
			}
			this.filepath = username;
			list = fileService.getUserFiles(this);
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		} 
		Integer isvip = (Integer) ServletActionContext.getRequest().getAttribute("isvip");
		if(isvip==null){  //娌℃湁涓婁紶鏂囦欢涔嬪墠浼氳皟鐢ㄥ埌杩欓噷鐨勪唬鐮侊紝涓婁紶鐨勬椂鍊欏湪uploadAction閲屼細娣诲姞isvip
		   isvip = userService.isVip(this.filepath);
           ServletActionContext.getRequest().setAttribute("isvip", isvip);   
		}
		//鎷垮埌姣忛〉鐨勬暟鎹紝姣忎釜鍏冪礌灏辨槸涓�鏉¤褰�
		pageBean.setList(list);
		pageBean.setCurrentpage(currentpage);
	    pageBean.setPagesize(pagesize);
		pageBean.setTotalrecord(fileService.countUserFiles(this));
		
		ServletActionContext.getRequest().setAttribute("pagebean", pageBean);
		
		return SUCCESS;
	}
		
}
