package com.cafe24.mysite.controller.api;

import static org.junit.Assert.assertNotNull;
// 강제로 import
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.mysite.config.AppConfig;
import com.cafe24.mysite.config.TestWebConfig;
import com.cafe24.mysite.service.GuestbookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, TestWebConfig.class})
@WebAppConfiguration
public class GuestbookControllerTest {
	private MockMvc mockMvc;	// container에 있으므로 주입 대상 아님
	
	@Autowired
	private WebApplicationContext webApplicationContext;	// container
	
	@Autowired
	private GuestbookService guestbookService;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.build();
	}
	
	@Test
	public void testDIGuestbookService() {
		assertNotNull(guestbookService);
	}
	
	@Test
	public void testFetchGuestbookList() throws Exception {
		mockMvc
			.perform(get("/api/guestbook/list/{no}", 1L))
			// MVCConfig의 configureDefaultServletHandling때문에 자꾸 isOk가 통과되었던 것임
			// 하지만 저 메소드는 반드시 필요하므로 TestWebConfig를 만든다.
			.andExpect(status().isOk()).andDo(print());
	}
	
}
