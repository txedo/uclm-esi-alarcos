<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html lang="en">
	<head>
		<title><s:text name="desglosa.title" /></title>
		<meta name="menu" content="Login"/>
		
		<link href="<s:url value='/styles/login.css?version=1'/>" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript">
		    function saveUsername(theForm) {
		        var expires = new Date();
		        expires.setTime(expires.getTime() + 24 * 30 * 60 * 60 * 1000); // sets it for approx 30 days.
		        setCookie("username",theForm.j_username.value,expires,"<c:url value='/'/>");
		    }
		    
		    function validateForm(form) {
		    	$("#errorMessage").removeClass("messageBox");
		    	$("#errorMessage").removeClass("error");
		    	$("#errorMessage").html("");
		    	return validateRequired(form); 
		    }
		    
		    function required () { 
		        this.aa = new Array("j_username", "<s:text name='error.required_field'><s:param><s:text name='label.username'/></s:param></s:text>", "#j_usernameError");
		        this.ab = new Array("j_password", "<s:text name='error.required_field'><s:param><s:text name='label.password'/></s:param></s:text>", "#j_passwordError");
		    }
		</script>
	</head>
	<body id="login">
		<fieldset id="loginWrapper">
			<!--  Evaluate login operation -->
			<c:choose>
				<c:when test="${param.result == 'success'}">
			    	<p><fmt:message key="message.login.sucess"/>
			    	<br /><fmt:message key="message.redirect.index"/></p>
			    	<c:url var="url" value="/"></c:url>
			    	<script> setTimeout("window.location.href='<c:out value="${url}"/>'",5000); </script> 
				</c:when>
				<c:otherwise>
					<c:if test="${param.result == 'failed'}">
					    <div id="errorMessage" class='messageBox error'><fmt:message key="error.login"/></div>
					    <!-- "Bad credentials" -->
					    <!-- <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/> -->
					</c:if>
					
					<form class="form" method="post" id="loginForm" action="<c:url value='/j_spring_security_check'/>" onsubmit="saveUsername(this);return validateForm(this)">
						<ul>
							<li>
								<label for="j_username">
									<fmt:message key="label.username" />
								</label>
								<s:textfield id="j_username" name="j_username" value="" tabindex="1" />
								<span id="j_usernameError"></span>
							</li>
							<li>
								<label for="j_password">
									<fmt:message key="label.password" />
								</label>
								<s:password id="j_password" name="j_password" value="" tabindex="2" />
								<span id="j_passwordError"></span>
							</li>
							<li>
								<input type="submit" id="submit" name="login" value="<fmt:message key='button.login'/>" tabindex="3" />
							</li>
						</ul>
					</form>
				</c:otherwise>
			</c:choose>
		</fieldset>
		<div class='clear'></div>
		
		<script type="text/javascript">
	    if (getCookie("username") != null) {
	        $("j_username").value = getCookie("username");
	        $("j_password").focus();
	    } else {
	        $("j_username").focus();
	    }
		</script>
	</body>
</html>
