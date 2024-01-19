<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

	<jsp:include page="../layout/header.jsp"></jsp:include>
	<jsp:include page="../layout/nav.jsp"></jsp:include>

<div class="container-md">

<c:set value="${bdto.bvo }" var="bvo"/>
<!-- flist 정보를 담아서 화면에 표시하기 위해서 BoardVO 대신 BoardDTO가 jsp에 전송되도록 수정되었다. -->
<!-- 따라서 jsp의 태그들의 value 값들을 bdto로 수정하여야 하는데 직접 수정하는 것 보다 -->
<!-- c:set 기능을 활용하여 수 많은 태그들을 직접 수정하지 않고 효율적으로 세팅할 수 있다. -->

<h1>Detail Page</h1>
<br>
<!-- File upload를 위한 multipart/form-data 추가 -->
<form action="/board/edit" method="post" enctype="multipart/form-data">
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
	
	<!-- 파일 표시 라인 -->
	<!-- detail.jsp 파일 표시 라인 복사 -->
	<c:set value="${bdto.flist }" var="flist"/>
	<div class="mb-3">
		<ul class="list-group list-group-flush">
	  		<c:forEach items="${flist }" var="fvo">
	  			<li class="list-group-item">
	  				<c:choose>
	  					<c:when test="${fvo.fileType == 1 }">
	  					<!-- 만약 이미지 파일이라면 (이미지 파일만 fileType 값이 1 -->
	  						<div>
	  							<img alt="" src="/upload/${fvo.saveDir }/${fvo.uuid}_th_${fvo.fileName}">
	  						</div>
	  					</c:when>
	  					<c:otherwise>
	  					<!-- 만약 이미지 파일이 아니라면 -->
	  						<div>
	  							<!-- 일반 파일 아이콘 -->
	  							<i class="bi bi-file-earmark-fill"></i>
	  						</div>
	  					</c:otherwise>
	  				</c:choose>
	  				<div class="mb-2 me-auto">
	  					<div class="fw-bold">${fvo.fileName }</div>
	  					<span class="badge rounded-pill text-bg-primary">${fvo.fileSize }Byte</span>
	  					<!-- 파일만 삭제하기 위한 X 버튼 -->
	  					<button type="button" data-uuid="${fvo.uuid }" class="btn btn-sm btn-outline-danger file-x">X</button>
	  				</div>
	  			</li>
	  		</c:forEach>
		</ul>
	</div>	
	<!-- 파일 표시 라인 끝 -->
	
	<!-- file upload 등록 라인 -->
	<!-- register.jsp 등록 라인 복사 -->
	<div class="mb-3">
	  <label for="files" class="form-label">files...</label>
	  <input type="file" name="files" class="form-control" id="files" multiple="multiple" style="display: none">
	  <!-- <input type="file">태그는 모양을 변화시킬 수 없으므로 트리거를 활용하여 다양한 모양으로 버튼을 만들 수 있음 -->
	  <br>
	  <button type="button" class="btn btn-primary" id="trigger">fileUpload</button>
	  <!-- 트리거를 사용하기 위한 버튼 -->
	</div>
	<!-- 파일 목록 표기 라인 -->
	<div class="mb-3" id="fileZone">
		<!-- 첨부 파일이 표시될 영역 -->
	</div>
	<!-- file upload 라인 끝 -->
	
</table>
	<a href="/board/list"><button type="button" class="btn btn-primary">List Page</button></a>
	<!-- File Upload Validation을 위해서 Edit 버튼에 id="regBtn" 추가 -->
	<button class="btn btn-warning" id="regBtn">Edit</button>
	<input type="hidden" name="bno" value="${bvo.bno }">
	<br><hr>
</form>

</div> <!-- container-md 끝 -->
	
	<jsp:include page="../layout/footer.jsp"></jsp:include>
	
	<!-- File upload 관련 JS -->
	<script type="text/javascript" src="/resources/js/boardRegister.js"></script>
	<!-- File 삭제 관련 JS -->
	<script type="text/javascript" src="/resources/js/boardModify.js"></script>

</body>
</html>