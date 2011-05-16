<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="cssHorizontalMenu.vm" permissions="rolesAdapter">
	<!-- You can use security:authorize tag also in order to check granted or NOT granted permissions -->
	<!-- <c:if test="${empty pageContext.request.remoteUser}"><li><a href="<c:url value="/login.jsp"/>" class="current"><fmt:message key="login.title"/></a></li></c:if> -->
	<menu:displayMenu name="Home"/>
	<menu:displayMenu name="Visualization"/>
	<menu:displayMenu name="AdminMenu"/>
	<menu:displayMenu name="UserMenu"/>
	<security:authorize ifNotGranted="ROLE_ADMIN,ROLE_EXECUTIVE,ROLE_MANAGER,ROLE_USER">
		<menu:displayMenu name="Login"/>
	</security:authorize>
	<menu:displayMenu name="Logout"/>
	<menu:displayMenu name="Contact"/>
</menu:useMenuDisplayer>