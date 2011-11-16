<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="cssHorizontalMenu.vm" permissions="rolesAdapter">
	<menu:displayMenu name="Home"/>
	<menu:displayMenu name="Visualization"/>
	<menu:displayMenu name="AdminMenu"/>
	<menu:displayMenu name="UserMenu"/>
	<security:authorize ifNotGranted="ROLE_ADMIN,ROLE_EXECUTIVE,ROLE_MANAGER,ROLE_USER">
		<menu:displayMenu name="Login"/>
	</security:authorize>
	<menu:displayMenu name="Contact"/>
</menu:useMenuDisplayer>