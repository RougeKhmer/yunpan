package com.test.dao.mapper;

import java.util.List;

import com.test.action.ChangeFileStatusAction;
import com.test.action.SearchFileAction;
import com.test.action.SearchUserFileAction;
import com.test.dao.po.File;

public interface FileMapper {
	
	public List<File> getAllFiles(SearchFileAction searchFileAction) throws Exception;
	public int count(SearchFileAction searchFileAction) throws Exception;
	public String findFilepathById(int id) throws Exception;
	public Integer insertFile(File file) throws Exception;
	public List<File> getUserFiles(SearchUserFileAction action)throws Exception;
	public int countUserFiles(SearchUserFileAction action) throws Exception;
	public void updateFileById(ChangeFileStatusAction changeFileStatusAction) throws Exception;
	public void deleteFileById(int id);
	public String findFilenameById(int id);
	
}
