package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.vo.UserVo;

// service는 root application context에 설정한다.
@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public Boolean existEmail(String email) {
		UserVo userVo = userDao.get(email);
		return userVo != null;
	}

	public Boolean join(UserVo userVo) {
		// 원래 insert는 count를 return해줘야 함
		return userDao.insert(userVo);
	}

	public UserVo getUser(UserVo userVo) {
		return userDao.get(userVo.getEmail(), userVo.getPassword());
	}
	
	public UserVo getUser(Long userNo) {
		return userDao.get(userNo);
	}

	// 업데이트를 한 후 업데이트가 된 유저 정보를 반환한다.
	public UserVo update(UserVo userVo) {
		userDao.update(userVo);
		
		// authUser에는 최소한의 정보(번호, 이름)만 전달시키려 함
		Long no = userVo.getNo();
		return new UserVo(no, userDao.getName(no));
	}
	
}
