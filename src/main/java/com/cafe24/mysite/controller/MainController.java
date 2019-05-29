package com.cafe24.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.service.AdminService;
import com.cafe24.mysite.vo.SiteVo;
import com.cafe24.mysite.vo.UserVo;

@Controller
public class MainController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping({"", "/main"})
	public String main(Model model) {
		SiteVo siteVo = adminService.getSite();
		model.addAttribute("siteVo", siteVo);
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "<h1>안녕하세요!<h1>";
	}
	
	@ResponseBody
	@RequestMapping("/hello2")
	public UserVo hello2() {
		UserVo vo = new UserVo();
		vo.setNo(10L);
		vo.setName("이름");
		vo.setEmail("email@email.com");
		
		return vo;
	}
}
