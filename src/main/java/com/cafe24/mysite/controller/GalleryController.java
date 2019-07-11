package com.cafe24.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@RequestMapping("")
	public String index(Model model) {
		return "gallery/index";
	}
}
