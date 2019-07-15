package com.cafe24.mysite.scenario;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.cafe24.mysite.controller.api.GuestbookControllerTest;
import com.cafe24.mysite.controller.api.UserControllerTest;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	GuestbookControllerTest.class,
	UserControllerTest.class
})
public class MemberOrderScenarioTest {

	public static Test suite() {
		return new TestSuite("회원 주문 시나리오 테스트");
	}
}
