<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

<jsp:include page="../layout/header.jsp"/>
<jsp:include page="../layout/nav.jsp"/>

<div class="container-md">
	<h1>member Login Page</h1><br>
	
	<form action="/member/login" method="post">
	<!-- /member/login 경로의 요청이 발생하면 SecurityConfig의 formLogin()의 loginPage가 -->
	<!-- 해당 요청을 인식하여 formLogin() Method를 실행한다. -->
	<!-- formLogin() Method 파라미터는 화면의 name을 탐색하여 가져간다. -->
	<!-- 주의 사항 : method가 반드시 post 방식으로 전송되어야 함 -->
	<div class="mb-3">
	  <label for="email" class="form-label">Email</label>
	  <input type="text" name="email" class="form-control" id="email" placeholder="email...">
	</div>
	<div class="mb-3">
	  <label for="pwd" class="form-label">Password</label>
	  <input type="text" name="pwd" class="form-control" id="pwd" placeholder="Password...">
	</div>
	
	<!-- 로그인 실패시 표시되는 라인 -->
	<c:if test="${not empty param.errMsg }">
		<div class="mb-3 text-danger">
			<c:choose>
				<c:when test="${param.errMsg eq 'Bad credentials' }">
					<c:set value="이메일 & 비밀번호가 일치하지 않습니다." var="errText"></c:set>
				</c:when>
				<c:otherwise>
					<c:set value="관리자에게 문의해 주세요." var="errText"></c:set>
				</c:otherwise>
			</c:choose>
			${errText }
		</div>
	</c:if>
	
	<button type="submit" class="btn btn-primary">Login</button>
	</form>
</div>

<jsp:include page="../layout/footer.jsp"/>

</body>
</html>