package com.test.dao;

import org.apache.ibatis.session.SqlSession;

import com.test.dao.mapper.UserMapper;
import com.test.dao.po.User;

import com.test.dao.DaoUtil;

public class UserDao {
	public void createUser(User user) throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		mapper.createUser(user);
		session.commit();
		session.close();
	}
	public String checkUser(User user) throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		String user_name = mapper.checkUser(user);
		session.close();
		return user_name;
	}
	public boolean findUser(String username) throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		Integer found = mapper.findUser(username);
		session.close();
		if(found==null||found<1) return false;
		return true;
	}
	public Integer isVip(String user_name)throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		Integer isvip = mapper.isVip(user_name);
		session.close();
		if(isvip==null || isvip == 1)  return 1;
		else return 0;
	}
}
