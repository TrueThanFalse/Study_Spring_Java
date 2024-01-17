<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list</title>
</head>
<body>

	<jsp:include page="../layout/header.jsp"></jsp:include>
	<jsp:include page="../layout/nav.jsp"></jsp:include>

<!-- BootStrap 활용 -->
<div class="container-md">
<h1>List Page</h1>
<br>

<!-- 검색 라인 -->
<div>
	<form action="/board/list" method="get">
		<!-- BoardController의 getMapping list Method는 매개변수로 PagingVO를 받아야 함 -->
		<!-- 검색 후 항상 1page를 화면에 표시할 것임 -->
		<input type="hidden" name="pageNo" value="1">
		<input type="hidden" name="qty" value="${ph.pgvo.qty }">
		
		<div class="input-group mb-3">
		<select name="type" class="form-select" id="inputGroupSelect02">
			<c:set value="${ph.pgvo.type }" var="typed"></c:set>
			<!-- 검색 후 선택한 option 태그를 유지하기 위해 selected 속성 셋팅 설정 -->
			<option ${typed == null ? 'selected' : '' }>Choose...</option>
			<option value="t" ${typed eq 't' ? 'selected' : '' }>Title</option>
			<option value="w" ${typed eq 'w' ? 'selected' : '' }>Writer</option>
			<option value="c" ${typed eq 'c' ? 'selected' : '' }>Content</option>
			<option value="tc" ${typed eq 'tc' ? 'selected' : '' }>Title&Writer</option>
			<option value="tw" ${typed eq 'tw' ? 'selected' : '' }>Title&Content</option>
			<option value="wc" ${typed eq 'wc' ? 'selected' : '' }>Writer&Content</option>
			<option value="twc" ${typed eq 'twc' ? 'selected' : '' }>All</option>
		</select>
		<input class="form-control me-2" type="search" placeholder="Search..." aria-label="Search" name="keyword" value="${ph.pgvo.keyword }">
		<!-- value="${ph.pgvo.keyword }" 속성을 추가하면 검색 후 검색 값이 유지됨 -->
      	<button class="btn btn-outline-success" type="submit">
      		Search
			<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">${ph.totalCount}</span>
			<!-- 검색된 게시글의 총 수량 -->
		</button>
		</div>
	</form>
</div>
<!-- 검색 끝 -->

<!-- boardVO list 출력 라인 -->
<table class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">title</th>
      <th scope="col">writer</th>
      <th scope="col">reg_date</th>
      <th scope="col">read_count</th>
    </tr>  
  <tbody>
  	<c:forEach items="${list }" var="bvo">
    <tr>
      <th scope="row">${bvo.bno }</th>
      <td>
      	<a href="/board/detail?bno=${bvo.bno }">${bvo.title }</a>
      </td>
      <td>${bvo.writer }</td>
      <td>${bvo.regAt }</td>
      <td>${bvo.readCount }</td>
    </tr>
    </c:forEach>
  </tbody>
</table>

<!-- 페이지네이션 라인 -->
<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
  	<!-- prev -->
  	<%-- <c:if test="${ph.prev }"> --%>
    <li class="page-item ${(ph.prev eq false) ? 'disabled' : '' }">
    <!-- c:if를 사용하지 않고 class에 disabled 속성을 활용하여 컨트롤 할 수도 있다. -->
      <a class="page-link" href="/board/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty }&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <%-- </c:if> --%>
    
    <!-- pageNo -->
    <c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
    <li class="page-item"><a class="page-link" href="/board/list?pageNo=${i }&qty=${ph.pgvo.qty }&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">${i }</a></li>
    </c:forEach>
    
    <!-- next -->
    <c:if test="${ph.next }">
    <li class="page-item">
      <a class="page-link" href="/board/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty }&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
    </c:if>
  </ul>
</nav>
<!-- 페이지네이션 끝 -->

<a href="/board/list"><button type="button" class="btn btn-primary position-relative">All list</button></a>
</div> <!-- class="container-md" 끝 -->
	
	<script type="text/javascript">
		const deleteMsg = `<c:out value="${deleteMsg}"/>`;
		if(deleteMsg > 0){
			alert("게시글 삭제 성공");
		};
		const editMsg = `<c:out value="${editMsg}"/>`;
		if(editMsg > 0){
			alert("게시글 수정 성공");
		};
		const registerMsg = `<c:out value="${registerMsg}"/>`;
		if(registerMsg > 0){
			alert("게시글 등록 성공");
		};
	</script>
	
	<jsp:include page="../layout/footer.jsp"></jsp:include>

</body>
</html>