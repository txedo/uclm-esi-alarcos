<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="desglosa.title" /></title>
		<meta name="menu" content="Login"/>
		<script type="text/javascript">		    
		    function saveUsername(theForm) {
		        var expires = new Date();
		        expires.setTime(expires.getTime() + 24 * 30 * 60 * 60 * 1000); // sets it for approx 30 days.
		        setCookie("username",theForm.j_username.value,expires,"<c:url value="/"/>");
		    }
		    
		    function validateForm(form) {                                                               
		    	return validateRequired(form); 
		    }
		    
		    function required () { 
		        this.aa = new Array("j_username", "<s:text name="errors.requiredField"><s:param><s:text name="label.username"/></s:param></s:text>", new Function ("varName", " return this[varName];"));
		        this.ab = new Array("j_password", "<s:text name="errors.requiredField"><s:param><s:text name="label.password"/></s:param></s:text>", new Function ("varName", " return this[varName];"));
		    }
		</script>
	</head>
	<body>
		<!--  Evaluate login operation -->
		<c:choose>
			<c:when test="${param.result == 'success'}">
		    	<p><fmt:message key="message.login.sucess"/>
		    	</br><fmt:message key="message.redirect.index"/></p>
		    	<c:url var="url" value="/index.action"></c:url>
		    	<script> setTimeout("window.location.href='<c:out value="${url}"/>'",5000); </script> 
			</c:when>
			<c:otherwise>
				<c:if test="${param.result == 'failed'}">
				    <p><fmt:message key="message.login.fail"/>
				    </br><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.</pa>
				    <!-- "Bad credentials" -->
				</c:if>
				<form method="post" id="loginForm" action="<c:url value='/j_spring_security_check'/>" onsubmit="saveUsername(this);return validateForm(this)">
					<ul>
						<li>
							<label for="j_username">
								<fmt:message key="label.username" />
							</label>
							<s:textfield id="j_username" name="j_username" value="" tabindex="1" />
						</li>
						<li>
							<label for="j_password">
								<fmt:message key="label.password" />
							</label>
							<s:password id="j_password" name="j_password" value="" tabindex="2" />
						</li>
						<li>
							<input type="submit" name="login" value="<fmt:message key='button.login'/>" tabindex="3" />
						</li>
					</ul>
				</form>
			</c:otherwise>
		</c:choose>
	</body>
</html>
