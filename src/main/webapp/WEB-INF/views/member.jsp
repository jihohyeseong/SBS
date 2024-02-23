<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
<title>전체 회원 페이지</title>
</head>
<body>  
    <div class="container col-md-4">
        <div class="text-center">
            <h3 class="form-join-heading">Check All Member Account</h3>
        </div>
        <c:forEach items="${memberList}" var="member">
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
            <p><a href="<c:url value="/member/mypage?id=${member.username}"/>" class="btn btn-Secondary" role="button">마이 페이지 &raquo;</a>
		</c:forEach>
    </div>
</body>
</html>