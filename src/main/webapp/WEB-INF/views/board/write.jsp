<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">

<link href="${ pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
<script>
var CONTEXT_PATH = '${ pageContext.servletContext.contextPath }';
</script>
<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>

</head>
<body>
	<div id="container">
		<c:import url='/WEB-INF/views/includes/header.jsp'/>

		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="${ pageContext.servletContext.contextPath }/board/write">
					<table class="tbl-ex">
						<!-- 답글일 경우에만 해당 -->
						<input type="hidden" name="groupNo" value="${ groupNo }">
						<input type="hidden" name="orderNo" value="${ orderNo }">
						<input type="hidden" name="depth" value="${ depth }">
					
						<tr>
							<th colspan="2">글쓰기</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="contents" name="contents" style="min-width: 485px; width: 485px;"></textarea>
							</td>
						
<!-- Smart Editor -->	
<script type="text/javascript">
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "contents",
		sSkinURI: "${ pageContext.servletContext.contextPath }/resources/editor/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});
	
	/* 편집 내용 서버로 전송 */
	// 저장을 위한 액션 시 submitContents 호출된다고 하자.
	function submitContents(elClickedObj) {
		// 에디터 내용이 textarea에 적용됨
		oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
		
		// 에디터의 내용에 대한 값 검증은 document.getElementById("contents").value를 이용하여 처리한다.
		
		try {
			elClickedObj.form.submit();
		} catch(e) {}		
	}
</script>
						</tr>
					</table>
					<div class="bottom">
						<a href="${ pageContext.servletContext.contextPath }/board">취소</a>
<!-- 						<input type="submit" value="등록"> -->
							<input type="button" value="등록" onclick="submitContents(this);">
					</div>
				</form>				
			</div>
		</div>
				
		<c:import url='/WEB-INF/views/includes/navigation.jsp'>
			<c:param name='menu' value='board'/>
		</c:import>
		<c:import url='/WEB-INF/views/includes/footer.jsp'/>
	</div>
</body>
</html>