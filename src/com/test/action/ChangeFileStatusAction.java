package com.test.action;

import java.io.Serializable;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.test.service.FileService;

public class ChangeFileStatusAction extends ActionSupport implements Serializable {
	private int currentpage;
	private int pagesize;
	private int startindex;
	private int id; //鏂囦欢id
	private int canshare; //1 鍏变韩    0 绉佹湁
	private FileService service; 

	public void setService(FileService service) {
		this.service = service;
	}
	
	
	public int getStartindex() {
		return startindex;
	}

	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCanshare() {
		return canshare;
	}

	public void setCanshare(int canshare) {
		this.canshare = canshare;
	}

	//淇敼鐢ㄦ埛鐨勬煇涓枃浠剁殑鐘舵�侊紙鍏变韩/绉佹湁锛�
	public String changeFileStatus(){
		
		//鎶奵anshare淇敼杩涙暟鎹簱
		try {
			//妫�鏌ヨ鏂囦欢鏄惁灞炰簬璇ョ敤鎴�,鍚﹀垯涓嶅厑璁镐慨鏀规枃浠剁姸鎬�
			String username = service.findFilepathById(id);
			String login_user = (String) ActionContext.getContext().getSession().get("user_name");
			if(username!=null && login_user.equals(username) ){
				service.updateFileById(this);
			}else{ //涓嶉�氳繃锛屽彲鑳芥槸浜轰负绡℃敼鏁版嵁锛岃浆鍙戣嚦棣栭〉
				ServletActionContext.getRequest().setAttribute("globalmessage", "璇ユ枃浠跺彲鑳戒笉灞炰簬浣�");
				return INPUT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getRequest().setAttribute("globalmessage", "鏈煡閿欒锛屽彲鑳芥槸鍙傛暟寮傚父");
			return INPUT;
		}
		
		//杞彂鍒皊earchUserFile鏄剧ず鐢ㄦ埛鐨勬枃浠�
		return SUCCESS;
	}
	

}
