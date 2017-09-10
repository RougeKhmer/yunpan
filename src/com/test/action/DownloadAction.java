package com.test.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import com.test.service.FileService;

public class DownloadAction extends ActionSupport implements Serializable{
	private int id;
	private String filename;
	private FileService service; 

	public void setService(FileService service) {
		this.service = service;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void validateDownload() throws Exception{
		filename = new String(filename.getBytes("iso-8859-1"),"UTF-8"); //瀵逛腑鏂囨暟鎹鐞�
		if(id<1)
			addFieldError("", "");
	}
	
	public String download(){
		
		FileInputStream in = null ;
		try {
			String path = service.findFilepathById(id); //鐩稿浜�/upload鐨勮矾寰�
			if(path==null || "".equals(path)){
			    ServletActionContext.getRequest().setAttribute("message", "瀵逛笉璧凤紝鎮ㄨ涓嬭浇鐨勮祫婧愬凡琚垹闄�");
			    return INPUT;
			}
			path = "D:"+File.separator+"upload"+File.separator+path;
			
		    File file = new File(path+File.separator+filename);
			//閫氱煡娴忚鍣ㄤ互涓嬭浇鏂瑰紡鎵撳紑
			ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename,"UTF-8"));
			
			in = new FileInputStream(file);
			int len = 0;
			byte buffer[] = new byte[1024];
			OutputStream out = ServletActionContext.getResponse().getOutputStream();
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
			
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return INPUT;
		}finally{
		   try {
			   if(in != null)
			       in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
		
	}



