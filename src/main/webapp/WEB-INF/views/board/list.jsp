<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${ pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">

</head>
<body>
	<div id="container">
		<c:import url='/WEB-INF/views/includes/header.jsp' />

		<div id="content">
			<div id="board">
				<form id="search_form" action="${ pageContext.servletContext.contextPath }/board/list" method="get">
					<input type="text" id="kwd" name="keyword" value="${ keyword }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:set var='count' value="${ fn:length(map.boardList) }"/>
					<c:forEach items='${ map.boardList }' var='vo' varStatus='s'>
						<c:choose>
							<c:when test="${ vo.status == 'ACTIVE' }">
								<tr>
									<td>${ count - s.index }</td>
									<td style="text-align: left; padding-left: ${ 15 * vo.depth }px;">
										<c:if test="${ vo.depth > 0 }">
											<img src="${ pageContext.servletContext.contextPath }/assets/images/reply.png">
										</c:if>
										<a href="${ pageContext.servletContext.contextPath }/board/view/${ vo.no }">${ vo.title }</a>
									</td>
									<td>${ vo.userName }</td>
									<td>${ vo.hit }</td>
									<td>${ vo.regDate }</td>
									<td>
										<c:if test="${ authUser.no == vo.userNo }">
											<a href="${ pageContext.servletContext.contextPath }/board/delete/${ vo.no }" class="del">삭제</a>
										</c:if>
									</td>
								</tr>
							</c:when>
							
							<c:when test="${ vo.status == 'DELETED' }">
								<tr>
									<td>${ count - s.index }</td>
									<td colspan="5" style="color: #bbb; text-align: center;">! 삭제된 게시글입니다.</td>
								</tr>
							</c:when>
							
						</c:choose>
						
					</c:forEach>
					
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${ map.prevPage != -1 }">
								<li><a href="${ pageContext.servletContext.contextPath }/board/list?p=${ map.prevPage }&keyword=${ map.keyword }">◀</a></li>
							</c:when>
							<c:otherwise>
								<li style="color: #ddd">◀</li>
							</c:otherwise>
						</c:choose>
						
						<c:forEach var="i" begin="${ map.startPage }" end="${ map.startPage + map.countPerPager - 1 }" step="1">
							<c:choose>
								<c:when test="${ map.currPage == i }">
									<li class="selected">${ i }</li>
								</c:when>
								<c:when test="${ i <= map.totalPageCount }">
									<li><a href="${ pageContext.servletContext.contextPath }/board/list?p=${ i }&keyword=${ map.keyword }">${ i }</a></li>
								</c:when>
								<c:otherwise>
									<li style="color: #ddd">${ i }</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<c:choose>
							<c:when test="${ map.nextPage != -1 }">
								<li><a href="${ pageContext.servletContext.contextPath }/board/list?p=${ map.nextPage }&keyword=${ map.keyword }">▶</a></li>
							</c:when>
							<c:otherwise>
								<li style = "color: #ddd">▶</li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${ authUser != null }">
						<a href="${ pageContext.servletContext.contextPath }/board/write" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		
		<c:import url='/WEB-INF/views/includes/navigation.jsp'>
			<c:param name='menu' value='board'/>
		</c:import>
		<c:import url='/WEB-INF/views/includes/footer.jsp'/>
	</div>
</body>
</html>