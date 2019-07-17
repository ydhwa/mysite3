package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.cafe24.mysite.exception.UserDaoException;
import com.cafe24.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private DataSource dataSource;

	public Boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return 1 == count;
	}

	public Boolean update(UserVo vo) {
		int count = sqlSession.update("user.update", vo);
		return 1 == count;
	}

	public UserVo get(Long no) {
		return sqlSession.selectOne("user.getByNo", no);
	}

	// email exist
	public UserVo get(String email) {
		//		return sqlSession.selectOne("user.getByEmail", email);
		UserVo userVo = new UserVo();
		userVo.setNo(2L);
		userVo.setName("동화");
		userVo.setPassword("1234");
		userVo.setEmail("donghwa@naver.com");
		return userVo;
	}

	// logout, update
	public UserVo get(String email, String password) throws UserDaoException {
		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		UserVo userVo = sqlSession.selectOne("user.getByEmailAndPassword", map);
		return userVo;
	}

	public String getName(Long no) {
		return sqlSession.selectOne("user.getNameByNo", no);
	}

}
