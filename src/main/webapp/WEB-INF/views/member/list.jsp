<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<jsp:include page="../layout/header.jsp"/>
<jsp:include page="../layout/nav.jsp"/>

<h1>member List Page</h1><hr>

<div class="container-md">
<table class="table">
  <thead>
    <tr>
      <th scope="col">email</th>
      <th scope="col">nickName</th>
      <th scope="col">regAt</th>
      <th scope="col">lastLogin</th>
      <th scope="col">authList</th>
    </tr>  
  <tbody>
  	<c:forEach items="${list }" var="mvo">
    <tr>
      <th>${mvo.email }</th>
      <td>${mvo.nickName }</td>
      <td>${mvo.regAt }</td>
      <td>${mvo.lastLogin }</td>
      <td>
      	<ul class="list-group">
			<c:forEach items="${mvo.authList }" var="authVO">
				<li class="list-group-item">${authVO.auth }</li>	
			</c:forEach>
		</ul>
      </td>
    </tr>
    </c:forEach>
  </tbody>
</table>
</div>

<jsp:include page="../layout/footer.jsp"/>

</body>
</html>