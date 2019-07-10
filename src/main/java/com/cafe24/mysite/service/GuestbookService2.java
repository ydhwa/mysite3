package com.cafe24.mysite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cafe24.mysite.vo.GuestbookVo;

@Service
public class GuestbookService2 {

	public List<GuestbookVo> getContentsList(int i) {
		GuestbookVo first = new GuestbookVo(1L, "user1", "1234", "안녕하세요~", "2019-07-10 09:20:35");
		GuestbookVo second = new GuestbookVo(2L, "user2", "1234", "Hello world", "2019-08-21 13:22:59");
		
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		list.add(first);
		list.add(second);
		
		return list;
	}

	public GuestbookVo addContents(GuestbookVo guestbookVo) {
		guestbookVo.setNo(10L);
		guestbookVo.setRegDate("2019-07-10 00:00:00");
		return guestbookVo;
	}

	public Long deleteContents(Long no, String password) {
		return no;
	}

}
