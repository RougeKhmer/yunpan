package com.test.action;

import java.io.File;
import java.io.Serializable;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.test.service.FileService;

public class DeleteFileAction extends ActionSupport implements Serializable{
	private int currentpage;
	private int pagesize;
	private int startindex;
	private int id; //鏂囦欢id
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
	
	
	public String deleteFile(){
	   System.out.println(id);	
	   
	   //鍒ゆ柇璇ョ敤鎴锋槸鍚︽嫢鏈夋鏂囦欢
	   try{
		   String username = service.findFilepathById(id);
		   String login_user = (String) ActionContext.getContext().getSession().get("user_name");
		   String filename = service.findFilenameById(id); //鏌ュ嚭鏂囦欢鍚�
		   if(username!=null && login_user.equals(username) ){
			   service.deleteFileById(id); //鍒犻櫎鏁版嵁搴撶殑璇ユ枃浠惰褰�
			   //浠庣‖鐩樹笂鍒犻櫎鏂囦欢
			   String storepath = new String("D:"+File.separator+"upload"+File.separator+login_user+File.separator);
			   storepath = storepath+filename;
			   System.out.println(storepath);
			   File file = new File(storepath);
			   if(file.exists()){
				   file.delete();
			   }else{
				   ServletActionContext.getRequest().setAttribute("globalmessage", "鏂囦欢宸蹭笉瀛樺湪");
				   return ERROR;
			   }
			   return SUCCESS;
		   }else{ //涓嶉�氳繃锛屽彲鑳芥槸浜轰负绡℃敼鏁版嵁锛岃浆鍙戣嚦鍏ㄥ眬娑堟伅椤甸潰
			ServletActionContext.getRequest().setAttribute("globalmessage", "璇ユ枃浠跺彲鑳戒笉灞炰簬浣�");
			return ERROR;
		   }
	   }catch(Exception e){
		   e.printStackTrace();
		   ServletActionContext.getRequest().setAttribute("globalmessage", "璇ユ枃浠跺彲鑳戒笉灞炰簬浣�");
		   return ERROR;
	   }
	}
}
