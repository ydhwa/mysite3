package com.cafe24.config.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig2 {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean(name="springSecurityFilterChain")
	public FilterChainProxy filterChainProxy() throws Exception {
		// filter를 걸어줄 것이 많기 때문에(AppSecurityConfig.java 참고)
		// 생성자 중 filterChain 리스트를 넣어주는 방법을 택한다.
		List<SecurityFilterChain> filterChains = new ArrayList<>();
		
		filterChains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/assets/**")));
		filterChains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/favicon.ico")));
		filterChains.add(new DefaultSecurityFilterChain(
				new AntPathRequestMatcher("/**"),
		
				// filterChains
				// 1. SecurityContextPersistenceFilter
				securityContextPersistenceFilter(),
				
				// 2. LogoutFilter
//				logoutFilter(),
				
				// 3. UsernamePasswordAuthenticationFilter
//				usernamePasswordAuthenticationFilter(),
				
				// 4. AnonymousAuthenticationFilter
				
				
				// 5. ExceptionTranslationFilter
//				exceptionTranslationFilter(),
				
				// 6. FilterSecurityInterceptor
				filterSecurityInterceptor()
				
		));
		
		return new FilterChainProxy(filterChains);
	}

	@Bean
	public SecurityContextPersistenceFilter securityContextPersistenceFilter() {
		return new SecurityContextPersistenceFilter(new HttpSessionSecurityContextRepository());		
	}
	
	@Bean
	public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		
		filterSecurityInterceptor.setAuthenticationManager(authenticationManager());
		filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
		
		// ACL 생성
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();
		requestMap.put(new AntPathRequestMatcher("/user/update"), SecurityConfig.createList("isAuthenticated()"));
		requestMap.put(new AntPathRequestMatcher("/user/logout"), SecurityConfig.createList("isAuthenticated()"));
		requestMap.put(new AntPathRequestMatcher("/board/write"), SecurityConfig.createList("isAuthenticated()"));
		requestMap.put(new AntPathRequestMatcher("/board/delete"), SecurityConfig.createList("isAuthenticated()"));
		requestMap.put(new AntPathRequestMatcher("/board/modify"), SecurityConfig.createList("isAuthenticated()"));
		requestMap.put(new AntPathRequestMatcher("/admin**"), SecurityConfig.createList("hasRole('ADMIN')"));
		requestMap.put(new AntPathRequestMatcher("/gallery/upload"), SecurityConfig.createList("hasRole('ADMIN')"));
		requestMap.put(new AntPathRequestMatcher("/gallery/delete/**"), SecurityConfig.createList("hasRole('ADMIN')"));
		requestMap.put(new AntPathRequestMatcher("/**"), SecurityConfig.createList("permitAll()"));
			
		FilterInvocationSecurityMetadataSource newSource = new ExpressionBasedFilterInvocationSecurityMetadataSource(
				requestMap
				,new DefaultWebSecurityExpressionHandler());
		
		filterSecurityInterceptor.setSecurityMetadataSource(newSource);
		filterSecurityInterceptor.afterPropertiesSet();
		
		return filterSecurityInterceptor;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		AuthenticationManager authenticationManager = new ProviderManager(Arrays.asList(authenticationProvider()));

		return authenticationManager;
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
	
	@Bean
	public AffirmativeBased accessDecisionManager() throws Exception {
		RoleVoter roleVoter = new RoleVoter();
		roleVoter.setRolePrefix("ROLE_");
		
		AffirmativeBased affirmativeBased = new AffirmativeBased(Arrays.asList(roleVoter, new WebExpressionVoter(), new AuthenticatedVoter()));
		affirmativeBased.setAllowIfAllAbstainDecisions(false);
		affirmativeBased.afterPropertiesSet();
		
		return affirmativeBased;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
