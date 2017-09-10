package com.test.action;

import java.io.File;
import java.io.Serializable;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import com.test.dao.po.User;
import com.test.service.UserService;

public class LogupAction  extends ActionSupport implements Serializable{
	private String username;
	private String password;
	private UserService service; 
	private User user;
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setService(UserService service) {
		this.service = service;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	//褰撹繘琛屾敞鍐屽墠锛屾鏌ュ弬鏁版槸鍚︽纭�
	public void validateLogup(){

		if("".equals(username) || "".equals(password)){
			ServletActionContext.getRequest().setAttribute("usernameerror", "鐢ㄦ埛鍚嶅繀椤�6-20浣�");
			ServletActionContext.getRequest().setAttribute("passworderror", "瀵嗙爜蹇呴』6-20浣�");
		    addFieldError("", "");
	    }else if(username.length() > 20 || username.length() < 6){
			ServletActionContext.getRequest().setAttribute("usernameerror", "鐢ㄦ埛鍚嶅繀椤�6-20浣�");
	        addFieldError("", "");
		}else if(password.length() > 20 || password.length() < 6){
			ServletActionContext.getRequest().setAttribute("passworderror", "瀵嗙爜蹇呴』6-20浣�");
			addFieldError("", "");
		}
	}
	
	public String logup(){
		
		user.setUsername(username);
		user.setPassword(password);
		try {
			service.createUser(user); //濡傛灉鐢ㄦ埛宸叉敞鍐� 涓嬪眰鐨剆ervice浼氭姏鍑哄紓甯�
			//娉ㄥ唽鎴愬姛锛屽氨鍦╱pload涓嬪垎閰嶄竴涓浜虹殑鏂囦欢澶�
			String path = ServletActionContext.getServletContext().getRealPath("WEB-INF/upload");
			File file = new File(path+File.separator+username);
			file.mkdir();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getRequest().setAttribute("usernameerror", "璇ョ敤鎴峰凡娉ㄥ唽");
			return ERROR;
		}
	}
}
