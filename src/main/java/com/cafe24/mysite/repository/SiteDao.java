package com.cafe24.mysite.repository;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.SiteVo;

@Repository
public class SiteDao {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(SiteVo vo) {
		int count = sqlSession.insert("site.insert", vo);
		return 1 == count;
	}

	public SiteVo get() {
		return sqlSession.selectOne("site.get");
	}
}
