package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	// 방명록 리스트 보여줌
	@RequestMapping({"/list", ""})
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.getGuestbooks();
		model.addAttribute("list", list);
		
		return "guestbook/list";
	}
	
	// 방명록 추가
	@RequestMapping("/add")
	public String add(GuestbookVo guestbookVo) {
		guestbookService.addGuestbook(guestbookVo);
		return "redirect:/guestbook/list";
	}
	
	// 방명록 삭제
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(
			@PathVariable(value = "no") Long no,
			Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam("no") Long no, @RequestParam("password") String password) {
		guestbookService.deleteGuestbook(new GuestbookVo(no, password));
		return "redirect:/guestbook/list";
	}

	// 방명록 AJAX 페이지
	@RequestMapping("/timeline")
	public String timeline() {
		return "guestbook/index-ajax";
	}
}
