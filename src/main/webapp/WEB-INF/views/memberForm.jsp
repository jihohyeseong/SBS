<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
<title>마이 페이지</title>
</head>
<body>  
    <div class="container col-md-4">
    	<form:form modelAttribute="updateMember" action="./update?${_csrf.parameterName}=${_csrf.token}" class="form-horizontal" enctype="multipart/form-data" method="POST">
        	<div class="text-center">
            	<h3 class="form-join-heading">Update Your Account</h3>
        	</div>
        	
        	<div class="form-group row">
        		<label class="col-sm-5 control-label">USER ID</label>
                        <div class="col-sm-6" style="padding-top: 10px">
                            <form:input id="username" path="username" type="hidden" class="form-control" value="${member.username}" />
                            <span  class="badge badge-info" >${member.username}</span>
                        </div>        		
        	</div>
        			  
        	<div class="form-group row">
                        <label class="col-sm-5 control-label" >고객 성명</label>
                        <div class="col-sm-6">
                            <form:input  path="name"  class="form-control" value="${member.name}"/>
                        </div>
            </div>
                    <div class="form-group row">
                        <label class="col-sm-5 control-label" >전화 번호</label>
                        <div class="col-sm-6">
                            <form:input  path="phonenum" class="form-control" value="${member.phonenum}"/>
                        </div>
                    </div>

        	<div class="form-group row">
        		<label class="col-sm-5 control-label">등급</label>
    			<span  class="badge badge-info" >${member.authority}</span>
            </div>

        	<div class="form-group row">
            <div class="col-sm-offset-2 col-sm-10" >
               	<input type="submit" class="btn btn-primary" value ="수정"/>  
               	<a href=" <c:url value="/home" />" class="btn btn-primary"> 취소</a>  
            </div>
           	</div>
        </form:form>
    </div>
</body>
</html>