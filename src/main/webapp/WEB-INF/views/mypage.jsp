<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/controllers.js"></script>
<title>마이 페이지</title>
</head>
<body>  
    <div class="container col-md-4">
        <div class="text-center">
            <h3 class="form-join-heading">Check Your Account</h3>
            <form:form action="${pageContext.request.contextPath}/logout" method="POST"> 
        		<input type="submit" class="btn btn-danger" value="Logout" />
        	</form:form>
        	<a href="<c:url value="/member/update?id=${member.username}" />" class="btn btn-success">수정</a>        	
        </div>
		  
        <div class="form-group row">
			고객 성명 : ${member.name}
        </div>
        <div class="form-group row">
            ID : ${member.username} 
        </div>
        <div class="form-group row">
			전화 번호 : ${member.phonenum}
        </div>
        <div class="form-group row">
           	등급 : ${member.authority}
        </div>
        <a href="<c:url value="javascript:deleteConfirm('${member.username}')"/>" class="btn btn-danger" >회원 탈퇴</a>
    </div>
</body>
</html>