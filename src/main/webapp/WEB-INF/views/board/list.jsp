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
	</script>
	
	<jsp:include page="../layout/footer.jsp"></jsp:include>

</body>
</html>