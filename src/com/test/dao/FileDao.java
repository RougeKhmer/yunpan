package com.test.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.test.dao.po.File;

import com.test.action.ChangeFileStatusAction;
import com.test.action.SearchUserFileAction;
import com.test.dao.DaoUtil;

import com.test.action.SearchFileAction;

import com.test.dao.mapper.FileMapper;

public class FileDao {
	
	public List<File> getAllFiles(SearchFileAction searchFileAction) throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		FileMapper mapper = session.getMapper(FileMapper.class);
		List<File> allFiles = mapper.getAllFiles(searchFileAction);
		session.close();
		return allFiles;
	}
	public int countShareFiles(SearchFileAction searchFileAction )throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		FileMapper mapper = session.getMapper(FileMapper.class);
		int count = mapper.count(searchFileAction);
		session.close();
		return count;
	}
	public String findFilepathById(int id) throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		FileMapper mapper = session.getMapper(FileMapper.class);
		String filepath = mapper.findFilenameById(id);
		session.close();
		return filepath;
		
	}
	public Integer insertFile(File file)throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		FileMapper mapper = session.getMapper(FileMapper.class);
		Integer fileid = mapper.insertFile(file);
		session.commit();
		session.close();
		return fileid;
		
	}
	public List<File> getUserFiles(SearchUserFileAction action) throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		FileMapper mapper = session.getMapper(FileMapper.class);
		List<File> userFiles = mapper.getUserFiles(action);
		session.close();
		return userFiles;
	}
	public int countUserFiles(SearchUserFileAction action) throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		FileMapper mapper = session.getMapper(FileMapper.class);
		int count = mapper.countUserFiles(action);
		session.close();
		return count;
	}
	public void updateFileById(ChangeFileStatusAction changeFileStatusAction) throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		FileMapper mapper = session.getMapper(FileMapper.class);
		mapper.updateFileById(changeFileStatusAction);
		session.commit();
		session.close();
	}
	public void deleteFileById(int id) {
		SqlSession session = DaoUtil.getSqlSession();
		FileMapper mapper = session.getMapper(FileMapper.class);
		mapper.deleteFileById(id);
		session.commit();
		session.close();
	}

	public String findFilenameById(int id) {
		SqlSession session = DaoUtil.getSqlSession();
		FileMapper mapper = session.getMapper(FileMapper.class);
		String filename = mapper.findFilenameById(id);
		session.close();
	    return filename;
	}

}
