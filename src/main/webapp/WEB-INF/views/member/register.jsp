<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

<jsp:include page="../layout/header.jsp"/>
<jsp:include page="../layout/nav.jsp"/>

<div class="container-md">
	<h1>member Register Page</h1><br>
	
	<form action="/member/register" method="post">
	<div class="mb-3">
	  <label for="email" class="form-label">Email</label>
	  <input type="text" name="email" class="form-control" id="email" placeholder="email...">
	</div>
	<div class="text-bg-danger p-3" hidden="true" id="emailWarningDiv"></div>
	<!--  -->
	<div class="mb-3">
	  <label for="pwd" class="form-label">Password</label>
	  <input type="password" name="pwd" class="form-control" id="pwd" placeholder="Password...">
	</div>
	<div class="text-bg-danger p-3" hidden="true" id="pwdWarningDiv">비밀번호는 영문자+숫자 조합의 6~20개의 문자열을 입력해 주세요.</div>
	<!--  -->
	<div class="mb-3">
	  <label for="nickName" class="form-label">NinkName</label><br>
	  <input type="text" name="nickName" class="form-control" id="nickName" placeholder="ninkName...">
	</div>
	<div class="text-bg-danger p-3" hidden="true" id="nickNameWarningDiv">옳바른 닉네임이 아닙니다. 다시 작성해 주세요.</div>
	<!--  -->
	<button type="submit" class="btn btn-primary" id="regBtn" disabled>Register</button>
	</form>
</div>

<jsp:include page="../layout/footer.jsp"/>

<script type="text/javascript" src="/resources/js/memberRegister.js"></script>

</body>
</html>