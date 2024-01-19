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
	<div class="mb-3">
	  <label for="pwd" class="form-label">Password</label>
	  <input type="text" name="pwd" class="form-control" id="pwd" placeholder="Password...">
	</div>
	<div class="mb-3">
	  <label for="nickName" class="form-label">NinkName</label><br>
	  <input type="text" name="nickName" class="form-control" id="nickName" placeholder="ninkName...">
	</div>
	<button type="submit" class="btn btn-primary" id="" >Register</button>
	</form>
</div>

<jsp:include page="../layout/footer.jsp"/>

</body>
</html>