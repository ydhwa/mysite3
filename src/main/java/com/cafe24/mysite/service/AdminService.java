package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.SiteDao;
import com.cafe24.mysite.vo.SiteVo;

@Service
public class AdminService {

	@Autowired
	private SiteDao siteDao;

	public SiteVo getSite() {
		return siteDao.get();
	}

	public Boolean addSite(SiteVo siteVo) {
		return siteDao.insert(siteVo);
	}
}
