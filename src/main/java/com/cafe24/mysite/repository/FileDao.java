package com.cafe24.mysite.repository;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.FileVo;

@Repository
public class FileDao {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(FileVo vo) {
		int count = sqlSession.insert("file.insert", vo);
		return 1 == count;
	}
	
	public Boolean delete(Long boardNo) {
		int count = sqlSession.delete("file.delete", boardNo);
		return 1 == count;
	}
}
