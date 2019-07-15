package com.cafe24.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@RequestMapping("")
	public String index(Model model) {
		return "gallery/index";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable Long no) {
		return "redirect:gallery/index";
	}
}