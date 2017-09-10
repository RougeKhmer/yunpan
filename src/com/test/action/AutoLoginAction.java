package com.test.action;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AutoLoginAction extends ActionSupport implements Serializable{
	private String user_name;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public void validateAutoLogin(){
		
		if(user_name == null){
			addFieldError("", "");
		}
		if(user_name != null)
			try {
				user_name = new String(user_name.getBytes("iso8859-1"),"utf-8");
				if("".equals(user_name)){
					addFieldError("", "");
				} 
				//铏界劧鏈夛紝浣嗘槸鍊间笉鍖归厤锛屼篃涓嶈兘璁╁畠鑷姩鐧婚檰銆傚洜涓烘湁鍙兘浜轰负浼�爑ser_name鏁版嵁浼犻�佽繃鏉�
				if(!user_name.equals(ActionContext.getContext().getSession().get("user_name"))){
					addFieldError("", "");
				}
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				addFieldError("", "");
			}
	}
	
	public String autoLogin(){
		
		return SUCCESS;
		
	}
}

	