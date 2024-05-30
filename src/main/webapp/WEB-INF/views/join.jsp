<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
<title>회원가입</title>
</head>
<body>  
    <div class="container col-md-4">
        <div class="text-center">
            <h3 class="form-join-heading">Please Create Your Account</h3>
        </div>
        <form:form modelAttribute="member" method="post">
        <form class="form-join" action="<c:url value="./join"/>" method="post"> 
            <div class="form-group row">
                ID <input type="text" name="username" class="form-control" placeholder="User ID" required> 
            </div>
            <div class="form-group row">
                비밀번호 <input type="password" name="password" class="form-control"  placeholder="Password" required>  
            </div>
            <div class="form-group row">
                고객 성명<input type="text" name="name" class="form-control" placeholder="User Name" required> 
            </div>
            <div class="form-group row">
                전화 번호<input type="text" name="phonenum" class="form-control" placeholder="Phone Num" required> 
            </div>
            
            <div class="form-group row">
    			<form:radiobutton path="authority" value="ROLE_ADMIN" />ADMIN &nbsp;
    			<form:radiobutton path="authority" value="ROLE_USER" />USER
            </div>
            <div class="form-group row">
            	<label for="enabled">회원 가입에 동의하십니까?
            	<br>
            	<input type="checkbox" id="enabled" name="enabled" required>동의함
  				<br></label>

            </div>         
            <div class="form-group row">
                <button class="btn btn-lg btn-success btn-block" type="submit">회원가입</button>                
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />  
            </div>
        </form>
        </form:form>
    </div>
</body>
</html>