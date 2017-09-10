package com.test.action;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.test.service.FileService;
import com.test.service.UserService;

public class UploadAction extends ActionSupport implements Serializable{
	
	private String username;
	
	private File file;  //瀵瑰簲鐨勫氨鏄〃鍗曚腑鏂囦欢涓婁紶鐨勯偅涓緭鍏ュ煙鐨勫悕绉帮紝Struts2妗嗘灦浼氬皝瑁呮垚File绫诲瀷鐨�
	private String fileFileName;//   涓婁紶杈撳叆鍩�+FileName  鏂囦欢鍚�  JavaWeb.pdf
	private String fileContentType;// 涓婁紶鏂囦欢鐨凪IME绫诲瀷  application/pdf

	private static final String storePath = "D:"+File.separator+"upload"; //瀛樺偍鐩綍 D:\\upload
	private static final int normallimit = 20*1000*1000; //鏅�氱敤鎴蜂笂浼犲崟涓枃浠剁殑鏈�澶т綋绉� 20mb
	private static final int viplimit = 50*1000*1000; //鏅�氱敤鎴蜂笂浼犲崟涓枃浠剁殑鏈�澶т綋绉� 50mb
	private static final int factor = 1000000;  //Mb鍒板瓧鑺傜殑杞崲鍥犲瓙
	
	private FileService fileService; 
	private UserService userService; 
	
	private com.test.dao.po.File f ;
	
	public void setF(com.test.dao.po.File f) {
		this.f = f;
	}
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	public String upload(){
		
		//session鍩熷瓨鐨剈sername鍜屼紶杩涙潵鐨剈sername涓�鑷达紝璇存槑鐢ㄦ埛鍚嶆病鏈夐�犲亣
		String user_name = (String) ActionContext.getContext().getSession().get("user_name");
		if(user_name == null || "".equals(user_name) || !user_name.equals(this.username))
			 return SUCCESS;
		
		//浠庢暟鎹簱鏌ヨ璇ョ敤鎴锋槸鍚︿负vip 
		Integer isvip = null;
		try {
			isvip = userService.isVip(user_name);
			//鎶婃槸鍚︽槸vip鐨勪俊鎭甫鍒皍serhome涓婚〉锛岀敤浜庡湪瀹㈡埛绔檺鍒舵枃浠朵笂浼犲ぇ灏�
			ServletActionContext.getRequest().setAttribute("isvip", isvip);
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getRequest().setAttribute("message", "鏈煡閿欒锛岃閲嶈瘯");
			return SUCCESS;
		}  
		
		File store = null;  //鐩殑鏂囦欢
	   	try{
    		//瀛樺湪姣忎釜鐢ㄦ埛鏈変竴涓嚜宸卞悕瀛楀懡鍚嶇殑鏂囦欢澶�
    		 store = new File(storePath+File.separator+username,fileFileName);
    	}catch (Exception e) {
    		ServletActionContext.getRequest().setAttribute("message", "璇峰厛閫夋嫨鏂囦欢锛�");
    		return SUCCESS;
    	}
	   	
	   	long size = this.file.length() ;  //涓婁紶鏂囦欢鐨勫ぇ灏�
	   	
		if(SUCCESS.equals(checkFile(store, storePath, isvip, size)))//妫�鏌ユ枃浠跺ぇ灏忕瓑鏄惁绗﹀悎瑕佹眰
			return SUCCESS; //鏈夐棶棰� 杞彂鍥炵敤鎴风┖闂撮〉闈㈡樉绀哄師鍥�
		
			
		//todo 妫�鏌ョ敤鎴风殑浜戠┖闂存槸鍚﹁秴杩囬檺棰�
		
		
		//楠岃瘉鍏ㄩ儴閫氳繃锛屾妸鏂囦欢澶嶅埗鍒版湰鍦扮‖鐩樼殑鐢ㄦ埛鐨勭洰褰曚笅
		Integer fileid = null;
		try {
			FileUtils.copyFile(file,store);   //涓婁紶鏂囦欢鍒版湰鍦扮‖鐩�
			//鎶婃枃浠朵俊鎭瓨鍏ユ暟鎹簱
			f.setCreatetime(new java.util.Date());
			f.setFilename(fileFileName);
			f.setFilepath(username);
			f.setFilesize(String.valueOf(size/1024+1));
			f.setCanshare(0);
			
		    fileid = fileService.insertFile(f);
			
			ServletActionContext.getRequest().setAttribute("message", "涓婁紶鎴愬姛锛�");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			if(store.exists()){ //涓�斿嚭鐜板紓甯革紝鎶婃嫹璐濈殑鏂囦欢鍒犻櫎
				store.delete();
			}
			if(fileid!=null){
				fileService.deleteFileById(fileid);
			}
			ServletActionContext.getRequest().setAttribute("message", "涓婁紶澶辫触锛佽閲嶈瘯");
			return SUCCESS;
		}
	}

    private String checkFile(File store , String storePath , int isvip , long size){
 
    		
		if(store.exists()){
			ServletActionContext.getRequest().setAttribute("message", "鏂囦欢宸插瓨鍦�");
			return SUCCESS;
		}
		
		if( size == 0 ){
			ServletActionContext.getRequest().setAttribute("message", "鏂囦欢澶у皬涓嶈兘涓�0");
			return SUCCESS;
		}else if(isvip == 0 && size > normallimit){
			ServletActionContext.getRequest().setAttribute("message", "鏅�氱敤鎴锋渶澶у彧鑳戒笂浼�"+normallimit/factor+"Mb鐨勬枃浠�");
			return SUCCESS;
		}else if(isvip == 1 && size > viplimit){
			ServletActionContext.getRequest().setAttribute("message", "VIP鐢ㄦ埛鏈�澶у彧鑳戒笂浼�"+viplimit/factor+"Mb鐨勬枃浠�");
			return SUCCESS;
		}else  return "OK";
     }
 }

