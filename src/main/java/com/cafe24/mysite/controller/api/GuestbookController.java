package com.cafe24.mysite.controller.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mysite.dto.JSONResult;

@RestController("guestbookAPIController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	
	@RequestMapping(value="/list/{no}", method=RequestMethod.GET)
	public JSONResult list(@PathVariable(value="no") int no) {
		return JSONResult.success(null);
	}

}
