<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
$(function(){
	//업로드 다이알로그
	var dialogDelete = $( "#dialog-delete-form" ).dialog({
		autoOpen: false,
		height: 150,
		width: 300,
		modal: true,
		buttons: {
			"삭제": function() {
				$( "#dialog-delete-form form" ).submit();
				$( this ).dialog( "close" );
			},
			"취소" : function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			$( "#dialog-delete-form form" ).get(0).reset();	
		}
	});
	$("ul#list-guestbook li a").click( function(event) {
		event.preventDefault();
		dialogDelete.dialog( "open" );
	});
});

var isEnd = false;
var render = function(vo) {
	// 실제로는 template library 사용한다.
	// -> ejs, underscore, mustache
	var html =
		'<li data-no="'+ vo.no +'">' + 
			'<strong>' + vo.name + '</strong>' + 
			'<p>' + vo.contents.replace(/</gi, '&lt;').replace(/>/gi, '&gt;').replace(/\n/gi, '<br>') + '</p>' + 
			'<strong></strong>' + 
			'<a href="" data-no="' + vo.no + '">삭제</a>' + 
		'</li>';
	
		$('#list-guestbook').append(html);
}

var fetchList = function() {
	if(isEnd) {
		return;
	}
	
	var lastNo = $('#list-guestbook li').last().data('no') || 0;	// 최초 호출 시 li element가 없음을 고려
	
	$.ajax({
		url: "${pageContext.request.contextPath}/api/guestbook/list/" + lastNo,
		type: "get",
		//contentType: "application/json",	// POST 방식으로 JSON Type의 데이터를 보낼 때
		dataType: "json",
		data: "",
		success: function(response) {
			if(response.result != "success") {
				console.error(response.message);
				return ;
			}
			
			// detect end
			if(response.data.length == 0) {
				isEnd = true;
				$('#btn-next').prop('disabled', true);
				return ;
			}
			
			// rendering
			$.each(response.data, function(index, vo) {
				render(vo);
			});
//				$.each(response.data, render);
		},
		error: function(jqXHR, status, e) {
			console.err(status + ": " + e);
		}
	});
}

$(function() {
	$('#btn-next').click(function() {
		fetchList();
	});
	
	$(window).scroll(function() {
		var $window = $(this);
		
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $(document).height();
		if(scrollTop + windowHeight + 10 > documentHeight) {
			fetchList();
		}
	});
	
	// 최초 리스트 가져오기
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
									
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<button id="btn-next">Next Page</button>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>