package com.cafe24.config.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 	 1. ChannelProcessingFilter
	 2. SecurityContextPersistenceFilter		( auto-config default, V(없으면 안돌아감!) )
	 3. ConcurrentSessionFilter
	 4. LogoutFilter							( auto-config default, V )
	 5. UsernamePasswordAuthenticationFilter	( auto-config default, V ) 인증에 관여함. 아주 중요!
	 6. DefaultLoginPageGeneratingFilter		( auto-config default )
	 7. CasAuthenticationFilter
	 8. BasicAuthenticationFilter				( auto-config default, V(아주 중요) )
	 9. RequestCacheAwareFilter					( auto-config default )
	10. SecurityContextHolderAwareRequestFilter	( auto-config default )
	11. JaasApiIntegrationFilter
	12. RememberMeAuthenticationFilter
	13. AnonymousAuthenticationFilter			( auto-config default )
	14. SessionManagementFilter					( auto-config default )
	15. ExceptionTranslationFilter				( auto-config default, V )
	16. FilterSecurityInterceptor				( auto-config default, V ) 중간에서 Interceptor 해야 할 URL(즉, 기존의 Interceptor와는 다른 개념)	
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// spring security 필터 연결
	// WebSecurity 객체는
	// springSecurityFilterChain 라는 이름의 DelegatingFilterProxy Bean 객체를 생성
	// DelegatingFilterProxy Bean은 많은 Spring Security Filter Chain에 위임한다.
	@Override
	public void configure(WebSecurity web) throws Exception {
//		super.configure(web);
	
		// ACL(Access Control List)에 등록하지 않을 URL을 설정	
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 부모가 막는 작업을 하고 있기 때문에, 여기를 주석처리하면 다 뚫리는 것이다.
		super.configure(http);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
	}
}
