<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!-- Security Taglib 추가 -->

<!-- Bootstrap 내비게이션 바 -->
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="/">Home</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
      
      	<!-- 게시글 목록은 로그인 & 비로그인 사용자 모두 볼 수 있다. -->
        <!-- => Security 관련 설정을 하지 않음 -->
        <li class="nav-item">
          <a class="nav-link" href="/board/list">게시글 목록</a>
        </li>
      	
      	<!-- sec:authorize 태그 : Security 관련 설정 할 수 있는 태그 -->
        <sec:authorize access="isAnonymous()">
        <!-- isAnonymous() : 로그인된 권한은 허용하지 않음 -->
        <!-- => 인증된 권한은 보이지 않음 -->
        <!-- sec:authorize 태그 내부의 내용을 볼 수 없음 -->
        <li class="nav-item">
          <a class="nav-link" href="/member/login">로그인</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/member/register">회원가입</a>
        </li>
        </sec:authorize>

        <!-- 현재 인증한 사용자의 정보를 가져와서 해당 권한이 있는지 확인하는 방법 -->
        <!-- 현재 사용자의 정보는 Authentication 내부의 principal 객체에 저장됨 -->
        <sec:authorize access="isAuthenticated()">
        <!-- isAuthenticated() : 로그인된 권한에게만 허용함 -->
        <!-- => 인증된 사용자만 sec:authorize 태그 내부의 내용을 볼 수 있음 -->
        
        <sec:authentication property="principal.mvo.email" var="authEmail"/>
        <sec:authentication property="principal.mvo.nickName" var="authNick"/>
        <sec:authentication property="principal.mvo.authList" var="auths"/>
        <!-- sec:authentication : property 설정 가능한 속성 (Alias 같은 기능) -->
        
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/board/register">게시글 작성</a>
        </li>
        
        <c:choose>
        	<c:when test="${auths.stream().anyMatch(authVO -> authVO.auth.equals('ROLE_ADMIN')).get() }">
        	<!-- anyMatch() : stream 매칭 메서드(최소한 1개의 요소가 주어진 조건에 맞는지 확인) -->
        		<li class="nav-item">
          			<a class="nav-link" href="/member/list">회원리스트 ${authNick }(${authEmail }/ADMIN)</a>
        		</li>
        	</c:when>
        	<c:otherwise>
        		<!-- ADMIN 권한이 아닐 경우... -->
        		<li class="nav-item">
          			<a class="nav-link" href="/member/modify">회원정보수정 ${authNick }(${authEmail })</a>
        		</li>
        	</c:otherwise>
        </c:choose>
        
        <li class="nav-item">
          <a class="nav-link" href="" id="logoutLink">로그아웃</a>
          <!-- 로그아웃도 로그인처럼 post method로 전송해야 SecurityConfig에서 인식 가능 -->
          <!-- 따라서 id="logoutLink" 속성을 추가한 후 클릭하면 id="logoutForm" 태그를 submit하는 JS 작성 필요 -->
        </li>
        <form action="/member/logout" method="post" id="logoutForm">
        	<input type="hidden" name="email" value="${authEmail }">
        	<!-- 인증된(로그인한 계정의) 이메일 -->
        </form>
        </sec:authorize>

      </ul>
    </div>
  </div>
</nav>

<!-- 로그아웃 JS -->
<script type="text/javascript">
	document.getElementById('logoutLink').addEventListener('click', (e)=>{
	    e.preventDefault(); // 먼저 실행되는 이벤트가 존재하면 실행하지 말것 => href 활성화 막기
	    document.getElementById('logoutForm').submit();
	});
</script>