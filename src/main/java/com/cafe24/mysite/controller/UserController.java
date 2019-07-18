package com.cafe24.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.security.AuthUser;
import com.cafe24.mysite.security.SecurityUser;
import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	// 회원가입
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(		// validation check
			@ModelAttribute @Valid UserVo userVo,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error: list) {
				System.out.println(error);
			}
			model.addAllAttributes(result.getModel());
			
			return "user/join";
		}
		
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	// 로그인 폼
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	// 개인정보 수정
//	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(@AuthUser SecurityUser securityUser, Model model) {
		model.addAttribute("userVo", userService.getUser(securityUser.getNo()));

		return "user/update";
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			@ModelAttribute UserVo userVo,
			@AuthUser SecurityUser securityUser,
			HttpSession session,
			Model model) {
		
		userVo.setNo(securityUser.getNo());
		
		session.setAttribute("authUser", userService.update(userVo));
		model.addAttribute("result", "success");
		
		return "user/update";
	}
	
	//===================== SecurityConfig에서 경로를 찾지 못하는 문제가 발생하여 명시해줌
	@RequestMapping(value="/auth", method=RequestMethod.POST)
	public void auth() { }
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout() { }
}
