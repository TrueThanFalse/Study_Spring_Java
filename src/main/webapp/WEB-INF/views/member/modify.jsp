<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<jsp:include page="../layout/header.jsp"/>
<jsp:include page="../layout/nav.jsp"/>

<div class="container-md">
	<h1>member Modify Page</h1><br>

	<form action="/member/modify" method="post">
	<div class="mb-3">
	  <label for="email" class="form-label">Email</label>
	  <input type="text" name="email" class="form-control" id="email" value="${mvo.email }">
	</div>
	  <label for="pwd" class="form-label">Password</label>
	  <input type="text" name="pwd" class="form-control" id="pwd" placeholder="Password...">
	<div class="mb-3">
	  <label for="nickName" class="form-label">NinkName</label><br>
	  <input type="text" name="nickName" class="form-control" id="nickName" value="${mvo.nickName }">
	</div>
	<!-- 회원 수정할 해당 멤버의 권한을 화면에 보여줄 것임 (권한이 2개 이상일 수도 있음) -->
	<div class="mb-3" >
		<ul class="list-group">
			<c:forEach items="${mvo.authList }" var="authVO">
				<li class="list-group-item">${authVO.auth }</li>	
			</c:forEach>
		</ul>
	</div>

	<button type="submit" class="btn btn-primary">Edit</button>
	<a href="/member/Withdrawal?email=${mvo.email }"><button type="button" class="btn btn-danger">Withdrawal</button></a>
	</form>
</div>

<jsp:include page="../layout/footer.jsp"/>

</body>
</html>