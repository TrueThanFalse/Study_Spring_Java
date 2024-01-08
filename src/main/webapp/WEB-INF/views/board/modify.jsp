<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modify</title>
</head>
<body>

	<jsp:include page="../layout/header.jsp"></jsp:include>
	<jsp:include page="../layout/nav.jsp"></jsp:include>

<div class="container-md">

<h1>Detail Page</h1>
<br>
<form action="/board/edit" method="post">
<table class="table">
	<tr>
	<div class="mb-3">
	  <label for="title" class="form-label">Title</label>
	  <input type="text" name="title" class="form-control" id="title" placeholder="Title..." value="${bvo.title }">
	</div>
	</tr>
	<tr>
	<div class="mb-3">
	  <label for="writer" class="form-label">Writer</label>
	  <input type="text" name="writer" class="form-control" id="writer" placeholder="Writer..." readonly="readonly" value="${bvo.writer }">
	</div>
	</tr>
	
	<tr>
	<div class="mb-3">
	  <label for="content" class="form-label">Content</label><br>
	  <textarea rows="3" cols="30" name="content" id="content" placeholder="Content...">${bvo.content }</textarea>
	</div>
	</tr>
	<tr>
	<div class="mb-3">
	  <label for="regAt" class="form-label">RegAt</label>
	  <span class="badge text-bg-primary">${bvo.readCount }</span>
	  <input type="text" name="regAt" class="form-control" id="regAt" placeholder="RegAt..." readonly="readonly" value="${bvo.regAt }">
	</div>
	</tr>
</table>
	<a href="/board/list"><button type="button" class="btn btn-primary">List Page</button></a>
	<button class="btn btn-warning">Edit</button>
	<input type="hidden" name="bno" value="${bvo.bno }">
	<br><hr>
</form>

</div> <!-- container-md 끝 -->
	
	<jsp:include page="../layout/footer.jsp"></jsp:include>

</body>
</html>