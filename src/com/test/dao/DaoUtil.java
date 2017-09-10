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
		//����sessionfactory
		//mybatis�����ļ�
		String resource = "SqlMapConfig.xml";
		//�õ������ļ���
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