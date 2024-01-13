<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail</title>
</head>
<body>

	<jsp:include page="../layout/header.jsp"></jsp:include>
	<jsp:include page="../layout/nav.jsp"></jsp:include>

<div class="container-md">

<h1>Detail Page</h1>
<br>
<table class="table">
	<tr>
	<div class="mb-3">
	  <label for="title" class="form-label">Title</label>
	  <input type="text" name="title" class="form-control" id="title" placeholder="Title..." readonly="readonly" value="${bvo.title }">
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
	  <textarea rows="3" cols="30" name="content" id="content" placeholder="Content..." readonly="readonly">${bvo.content }</textarea>
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
	<a href="/board/modify?bno=${bvo.bno }"><button type="button" class="btn btn-warning">Modify</button></a>
	<a href="/board/delete?bno=${bvo.bno }"><button type="button" class="btn btn-danger">Delete</button></a>
	<br><hr>
	
<!-- Comment 라인 -->
<!-- 댓글 등록 라인 -->
<nav class="navbar bg-body-tertiary">
  <form class="container-fluid">
    <div class="input-group">
      <span class="input-group-text" id="cmtWriter">${bvo.writer }</span>
      <input type="text" id="cmtText" class="form-control" placeholder="Add Comment..." aria-label="comment" aria-describedby="cmtWriter">
      <button class="btn btn-outline-success" type="button" id="cmtPostBtn">Post</button>
    </div>
  </form>
</nav>
<!-- 댓글 표시 라인 -->
<div class="accordion" >
	<ul class="list-group" id="cmtListArea">
		<li class="list-group-item">
			<div class="mb-3">
				<div class="fw-bold">Writer</div>
				content
			</div>
			<span class="badge rounded-pill text-bg-success">modAt</span>
		</li>
	</ul>
</div>
<!-- Comment 라인 끝 -->

<!-- 댓글 더보기 버튼 라인 -->
<div>
	<button id="moreBtn" data-page="1" class="btn btn-outline-success" type="button" style="visibility: hidden">MORE+</button>
	<!-- data-page 속성으로 page 데이터를 setting하여 이 page 값을 활용해서 더보기 버튼을 컨트롤 할 수 있다. -->
</div>

<!-- 모달창 라인 -->
<div class="modal" tabindex="-1" id="myModal">
<!-- id 추가하였고 boardComment.js에서 댓글 수정 버튼과 id를 연결시켜야 함 -->
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Writer</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        <!-- 모달창 닫기 아이콘 -->
      </div>
      <div class="modal-body">
      	<div class="input-group mb-3">
        	<input type="text" id="cmtTextModal" class="form-control">
        	<button type="button" id="cmtEditBtn" class="btn btn-primary" >Edit</button>
        	<!-- id 추가하였음(Edit 버튼 이벤트 리스너 추가) -->
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
      </div>
    </div>
  </div>
</div>

</div> <!-- container-md 끝 -->
	
	<jsp:include page="../layout/footer.jsp"></jsp:include>
	
	<!-- Comment 비동기 통신 -->
	<script type="text/javascript">
		let bnoVal = `<c:out value="${bvo.bno}"/>`;
		console.log(bnoVal);
	</script>
	<script type="text/javascript" src="/resources/js/boardComment.js"></script>
	<script type="text/javascript">
		spreadCommentList(bnoVal); // detail.jsp 첫 진입시 Comment 화면에 뿌리기
	</script>

</body>
</html>