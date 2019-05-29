package com.cafe24.config.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
	
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		/*
		 * 리소스 형태로 전달해야 함.
		 * 따라서, Context를 사용해야 한다.
		 */
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:com/cafe24/config/app/mybatis/configuration.xml"));
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
			return new SqlSessionTemplate(sqlSessionFactory);
	}
}
