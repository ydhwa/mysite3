<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<sec:csrfMetaTags/>
<link href="${ pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function() {
	var csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
	var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
	var csrfToken = $('meta[name="_csrf"]').attr('content');
	console.log(csrfParameter);
	console.log(csrfHeader);
	console.log(csrfToken);	
});
</script>
</head>
<body>
	<div id="container">
		<c:import url='/WEB-INF/views/includes/header.jsp' />

		<div id="content">
			<div id="user">
				<form id="login-form" name="loginform" method="post"
					action="${ pageContext.servletContext.contextPath }/user/auth">
					<sec:csrfInput/>
					<label class="block-label" for="email">이메일</label> <input
						id="email" name="email" type="text" value=""> <label
						class="block-label">패스워드</label> <input name="password"
						type="password" value="">

					<c:if test="${ param.result == 'fail' }">
						<p>로그인이 실패 했습니다.</p>
					</c:if>
					
					<input type="submit" value="로그인">
				</form>
			</div>
		</div>

		<c:import url='/WEB-INF/views/includes/navigation.jsp' />
		<c:import url='/WEB-INF/views/includes/footer.jsp' />
	</div>
</body>
</html>