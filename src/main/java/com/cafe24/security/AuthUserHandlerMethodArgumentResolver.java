package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafe24.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		if(!supportsParameter(parameter)) {
			// 관심 없는 경우
			return WebArgumentResolver.UNRESOLVED;
		}
		
		// 톰캣의 request를 받아옴
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}
	
	@Override
	// 내가 지원하는 파라미터인지 아닌지 판별
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser가 붙어있지 않다면
		if(authUser == null) {
			return false;
		}
		
		// @AuthUser가 붙어있긴 한데 파라미터 타입이 UserVo가 아니라면
		if(!parameter.getParameterType().equals(UserVo.class)) {
			return false;
		}
		
		return true;
	}

}
