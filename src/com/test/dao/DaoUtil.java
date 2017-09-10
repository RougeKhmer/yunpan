package com.test.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DaoUtil{
	
	private static SqlSessionFactory sqlSessionFactory;
	
	static {
		//创建sessionfactory
		//mybatis配置文件
		String resource = "SqlMapConfig.xml";
		//得到配置文件流
		InputStream inputStream;
		try {
			
			inputStream = Resources.getResourceAsStream(resource);

		}
		
		catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		
	}
	public static SqlSession getSqlSession(){
		return sqlSessionFactory.openSession();
	}


}