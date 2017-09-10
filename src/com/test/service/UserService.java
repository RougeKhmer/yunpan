package com.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.mapper.UserMapper;
import com.test.dao.po.User;

@Service(value="userService")
public class UserService {

	@Autowired
	private UserMapper dao;

	public void createUser(User user) throws Exception{
		Boolean found = findUser(user.getUsername());
		if(!found)
		   dao.createUser(user);
		else
			throw new RuntimeException();
	}
	
	public String checkUser(User user ) throws Exception{
		return dao.checkUser(user);
	}
	
	public boolean findUser(String username) throws Exception{
		Integer found = dao.findUser(username);
		if(found==null || found<1)  return false;
		return true;
	}

	public int isVip(String user_name)throws Exception {
		return dao.isVip(user_name);
	}

}
