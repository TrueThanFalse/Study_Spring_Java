<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>register</title>
</head>
<body>

	<jsp:include page="../layout/header.jsp"/>
	<jsp:include page="../layout/nav.jsp"/>

<!-- Bootstrap 활용 -->
<div class="container-md">
	<h1>Register Page</h1><br>
	
	<form action="/board/register" method="post" enctype="multipart/form-data">
	<div class="mb-3">
	  <label for="title" class="form-label">Title</label>
	  <!-- name의 값이 BoardVO의 멤버변수명과 일치하면 자동으로 추적하여 저장됨 -->
	  <input type="text" name="title" class="form-control" id="title" placeholder="Title...">
	</div>
	<div class="mb-3">
	  <label for="writer" class="form-label">Writer</label>
	  <input type="text" name="writer" class="form-control" id="writer" placeholder="Writer...">>
	</div>
	<div class="mb-3">
	  <label for="content" class="form-label">Content</label><br>
	  <textarea rows="3" cols="30" name="content" id="content" placeholder="Content..."></textarea>
	</div>
	
	<!-- file upload 관련 추가 라인 -->
	<!-- 입력 라인 -->
	<div class="mb-3">
	  <label for="files" class="form-label">[files]</label>
	  <input type="file" name="files" class="form-control" id="files" multiple="multiple" style="display: none"><br>
	  <!-- <input type="file">태그는 모양을 변화시킬 수 없으므로 트리거를 활용하여 다양한 모양으로 버튼을 만들 수 있음 -->
	  <button type="button" class="btn btn-primary" id="trigger">fileUpload</button>
	  <!-- 트리거를 사용하기 위한 버튼 -->
	</div>
	<!-- 파일 목록 표기 라인 -->
	<div class="mb-3" id="fileZone">
		<!-- 첨부 파일이 화면에 표시될 영역 -->
	</div>

	<button type="submit" class="btn btn-primary" id="regBtn" >Register</button>
	<!-- 파일 upload는 있을수도 있고 없을수도 있으므로 Register 버튼을 disabled 속성으로 비활성화 하지 않음 -->
	</form>
</div>

	<jsp:include page="../layout/footer.jsp"/>
	
	<script type="text/javascript" src="/resources/js/boardRegister.js"></script>
	
</body>
</html>