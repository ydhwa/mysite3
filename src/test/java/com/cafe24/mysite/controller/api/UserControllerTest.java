package com.cafe24.mysite.controller.api;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import com.cafe24.mysite.vo.UserVo;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, TestWebConfig.class })
@WebAppConfiguration
public class UserControllerTest {
	private MockMvc mockMvc; // container에 있으므로 주입 대상 아님

	@Autowired
	private WebApplicationContext webApplicationContext; // container

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testUserJoin() throws Exception {
		UserVo userVo = new UserVo();
		// 1. Normal User's Join Data
		userVo.setName("양동화");
		userVo.setEmail("ydhwa_17@naver.com");
		userVo.setPassword("asdf1234#");
		userVo.setGender("FEMALE");

		ResultActions resultActions = mockMvc.perform(
				post("/user/api/join", 1L).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));

		resultActions.andExpect(status().isOk()).andDo(print());


		// 2. Invalidation in Name: length must be between 2 and 20
		userVo.setName("양");
		userVo.setEmail("ydhwa_17@naver.com");
		userVo.setPassword("asdf1234#");
		userVo.setGender("FEMALE");

		resultActions = mockMvc.perform(
				post("/user/api/join", 1L).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));

		resultActions.andExpect(status().isBadRequest()).andDo(print())
		.andExpect(jsonPath("$.result", is("fail")));

		// 3. Invalidation in Password: 비밀번호는 8자 이상 20자 이하의 알파벳, 숫자, 특수문자를 조합하여 작성해야 합니다.
		userVo.setName("양동화");
		userVo.setEmail("ydhwa_17@naver.com");
		userVo.setPassword("asdf1");
		userVo.setGender("FEMALE");

		resultActions = mockMvc.perform(
				post("/user/api/join", 1L).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));

		resultActions.andExpect(status().isBadRequest()).andDo(print())
		.andExpect(jsonPath("$.result", is("fail")));
	}
}
