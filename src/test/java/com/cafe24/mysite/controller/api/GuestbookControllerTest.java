package com.cafe24.mysite.controller.api;

// static method 강제로 import
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.mysite.config.AppConfig;
import com.cafe24.mysite.config.TestWebConfig;
import com.cafe24.mysite.vo.GuestbookVo;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, TestWebConfig.class})
@WebAppConfiguration
public class GuestbookControllerTest {
	private MockMvc mockMvc;	// container에 있으므로 주입 대상 아님
	
	@Autowired
	private WebApplicationContext webApplicationContext;	// container
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.build();
	}
	
	/*
{
	"result": "success",
	"message": null,
	"data" :[{
		"no": 1,
		"name": "user1",
		"password": "1234",
		"contents": "안녕하세요~",
		"regDate": "2019-07-10 09:20:35"
	},{
		"no": 2,
		"name": "user2",
		"password": "1234",
		"contents": "Hello world",
		"regDate": "2019-08-21 13:22:59"
	}]
}
	 */
	
	
	@Test
	public void testFetchGuestbookList() throws Exception {
		ResultActions resultActions = mockMvc
			.perform(get("/api/guestbook/list/{no}", 1L).contentType(MediaType.APPLICATION_JSON));
			// MVCConfig의 configureDefaultServletHandling때문에 자꾸 isOk가 통과되었던 것임
			// 하지만 저 메소드는 반드시 필요하므로 TestWebConfig를 만든다.
		
			resultActions
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.result", is("success")))
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.data[0].no", is(1)))
				.andExpect(jsonPath("$.data[0].name", is("user1")))
				.andExpect(jsonPath("$.data[1].no", is(2)))
				.andExpect(jsonPath("$.data[1].name", is("user2")));
	}
	
	@Test
	public void testInsertGuestbook() throws Exception{
		GuestbookVo vo = new GuestbookVo();
		vo.setName("user1");
		vo.setContents("안녕하세요~");
		vo.setContents("test1");
		
//		MailSender mailSender = Mockito.mock(MailSender.class);
//		Mockito.when(mailSender.sendMail("")).thenReturn(true);
//		isSuccess = mailSender.sendMail("");
		
//		Mockito.when(voMock.getNo2()).thenReturn("10L");
//		Long no = (Long)voMock.getNo2();
		
		ResultActions resultActions = mockMvc
			.perform(post("/api/guestbook/add").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(vo)));
		
			resultActions
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.result", is("success")))
				.andExpect(jsonPath("$.data.name", is(vo.getName())))
				.andExpect(jsonPath("$.data.contents", is(vo.getContents())));
	}
	
	@Test
	public void testDeleteGuestbook() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", 1L);
		map.put("password", "1234");
		
		
		ResultActions resultActions = mockMvc
			.perform(delete("/api/guestbook/delete").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(map)));
		
			resultActions
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.result", is("success")));
	}
	
}
