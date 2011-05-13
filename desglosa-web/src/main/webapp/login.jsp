<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="Desglosa.title" /></title>
		<script type="text/javascript">		    
		    function saveUsername(theForm) {
		        var expires = new Date();
		        expires.setTime(expires.getTime() + 24 * 30 * 60 * 60 * 1000); // sets it for approx 30 days.
		        setCookie("username",theForm.j_username.value,expires,"<c:url value="/"/>");
		    }
		    
		    function validateForm(form) {                                                               
		        return validateRequired(form); 
		    }
		</script>
	</head>
	<body>
	<menu:useMenuDisplayer name="Velocity" config="cssHorizontalMenu.vm" permissions="rolesAdapter">
		<c:if test="${empty pageContext.request.remoteUser}"><li><a href="<c:url value="/login.jsp"/>" class="current"><fmt:message key="login.title"/></a></li></c:if>
		<menu:displayMenu name="AdminMenu" />
		<menu:displayMenu name="UserMenu" />
		<menu:displayMenu name="Logout" />
	</menu:useMenuDisplayer>
		<c:if test="${param.result == 'failed' }">
		    error login in
		    Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
		    <!-- "Bad credentials" -->
		</c:if>
		<c:if test="${param.result == 'success' }">
		    you are logged in
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
	</body>
</html>
