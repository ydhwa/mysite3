package com.cafe24.mysite.repository;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	private static int COUNT_PER_PAGE = 5;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean delete(GuestbookVo vo) {
		int count = sqlSession.delete("guestbook.delete", vo);
		return 1 == count;
	}

	public Boolean insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert", vo);
		return 1 == count;
	}

	public List<GuestbookVo> getList() {
//		List<GuestbookVo> result = new ArrayList<>();
		return sqlSession.selectList("guestbook.getList");
	}
	
	public List<GuestbookVo> getList(Long lastNo) {
		return sqlSession.selectList("guestbook.getList2", lastNo);
	}
}
