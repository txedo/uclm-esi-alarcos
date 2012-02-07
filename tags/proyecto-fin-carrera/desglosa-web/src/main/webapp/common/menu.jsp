<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="cssHorizontalMenu.vm" permissions="rolesAdapter">
	<menu:displayMenu name="Home"/>
	<menu:displayMenu name="Visualization"/>
	<menu:displayMenu name="ManagerCheckMenu"/>
	<menu:displayMenu name="UserCheckMenu"/>
	<menu:displayMenu name="AdminMenu"/>
	<menu:displayMenu name="ManagerMenu"/>
	<security:authorize ifNotGranted="ROLE_ADMIN,ROLE_MANAGER,ROLE_USER">
		<menu:displayMenu name="Login"/>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_MANAGER,ROLE_USER">
		<menu:displayMenu name="Logout"/>
	</security:authorize>
</menu:useMenuDisplayer>