package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import com.opensymphony.xwork2.Action;

import es.uclm.inf_cr.alarcos.desglosa_web.control.GLObjectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.StrutsSpringTestCaseBase;

public class VisualizationActionTest extends StrutsSpringTestCaseBase {

    public void testExecute() throws Exception {
        configureProxy("/", "visualization", "admin");
        assertTrue(action instanceof VisualizationAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(2, ((VisualizationAction) action).getCompanies().size());
        assertEquals(3, ((VisualizationAction) action).getFactories().size());
        assertEquals(2, ((VisualizationAction) action).getProjects().size());
        assertEquals(5, ((VisualizationAction) action).getSubprojects().size());
    }
    
    public void testFactoryById() throws Exception {
        // get all factories
        configureProxy("/", "json_factoryById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("factory-FactoryDefault-1325612322955.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 3 factories in factories array
        assertEquals(3, ((VisualizationAction) action).getFactories().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 3 factories (all)
        assertEquals(3, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
        
        // get only one factory
        configureProxy("/", "json_factoryById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("factory-FactoryDefault-1325612322955.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 1 factory in factories array
        assertEquals(1, ((VisualizationAction) action).getFactories().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 1 factories
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
        // check that globject id = 1
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().get(0).getId());
    }
    
    public void testFactoryByIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_factoryById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_factoryById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
    
    public void testFactoriesByCompanyId() throws Exception {
        // get all factories from all companies
        configureProxy("/", "json_factoriesByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.GROUP_BY_COMPANY);
        ((VisualizationAction) action).setProfileFileName("factory-FactoryDefault-1325612322955.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 3 factories in factories array (all factories from all companies)
        assertEquals(3, ((VisualizationAction) action).getFactories().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 2 neighborhood (2 companies and group by company)
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        
        // get factories from only one company
        configureProxy("/", "json_factoriesByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.GROUP_BY_COMPANY);
        ((VisualizationAction) action).setProfileFileName("factory-FactoryDefault-1325612322955.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 2 factory in factories array (2 factories from company 1)
        assertEquals(2, ((VisualizationAction) action).getFactories().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (1 company and group by company)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 2 factories
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
    }
    
    public void testFactoriesByCompanyIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_factoriesByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_factoriesByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
    
    public void testFactoriesByProjectId() throws Exception {
        // get all factories
        configureProxy("/", "json_factoriesByProjectId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.GROUP_BY_PROJECT);
        ((VisualizationAction) action).setProfileFileName("factory-FactoryDefault-1325612322955.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 3 factories in factories array
        assertEquals(3, ((VisualizationAction) action).getFactories().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 2 neighborhood (2 projects and group by project)
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        
        // get only one factory
        configureProxy("/", "json_factoriesByProjectId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.GROUP_BY_PROJECT);
        ((VisualizationAction) action).setProfileFileName("factory-FactoryDefault-1325612322955.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 2 factory in factories array
        assertEquals(2, ((VisualizationAction) action).getFactories().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (1 project and group by project)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 2 factories
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
    }
    
    public void testFactoriesByProjectIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_factoriesByProjectId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there is no factories
        assertEquals(0, ((VisualizationAction) action).getFactories().size());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_factoriesByProjectId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
    
    public void testCompanyById() throws Exception {
        // get all companies
        configureProxy("/", "json_companyById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("company-CompanyDefault-1326105751903.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 2 companies in companies array
        assertEquals(2, ((VisualizationAction) action).getCompanies().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 2 companies (all)
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
        
        // get only one factory
        configureProxy("/", "json_companyById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("company-CompanyDefault-1326105751903.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 1 company in companies array
        assertEquals(1, ((VisualizationAction) action).getCompanies().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 1 company
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
        // check that globject id = 1
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().get(0).getId());
    }
    
    public void testCompanyByIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_companyById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_companyById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
    
    public void testProjectsByCompanyId() throws Exception {
        // get all projects for all companies
        configureProxy("/", "json_projectsByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.GROUP_BY_COMPANY);
        ((VisualizationAction) action).setProfileFileName("project-ProjectDefault-1325631634846.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 2 projects in projects array (all projects from all companies)
        assertEquals(2, ((VisualizationAction) action).getProjects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 2 neighborhood (2 companies and group by company)
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        
        // get projects from only one company
        configureProxy("/", "json_projectsByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.GROUP_BY_COMPANY);
        ((VisualizationAction) action).setProfileFileName("project-ProjectDefault-1325631634846.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 2 projects in projects array (2 projects from company 1)
        assertEquals(2, ((VisualizationAction) action).getProjects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (1 company and group by company)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 2 projects
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
    }
    
    public void testProjectsByCompanyIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_projectsByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_projectsByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
    
    public void testProjectsByFactoryId() throws Exception {
        // get all projects for all factories
        configureProxy("/", "json_projectsByFactoryId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.GROUP_BY_FACTORY);
        ((VisualizationAction) action).setProfileFileName("project-ProjectDefault-1325631634846.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 2 projects in projects array (all projects from all factories)
        assertEquals(2, ((VisualizationAction) action).getProjects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 2 neighborhood (2 factories and group by factory)
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        
        // get projects from only one factory
        configureProxy("/", "json_projectsByFactoryId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.GROUP_BY_FACTORY);
        ((VisualizationAction) action).setProfileFileName("project-ProjectDefault-1325631634846.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 2 projects in projects array (2 projects from factory 1)
        assertEquals(2, ((VisualizationAction) action).getProjects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 2 neighborhood (2 projects and group by factory, but 2 factories works in both projects)
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The both neighborhood has 2 projects
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().get(1).getFlats().size());
    }
    
    public void testProjectsByFactoryIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_projectsByFactoryId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_projectsByFactoryId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
    
    public void testProjectById() throws Exception {
        // get all projects
        configureProxy("/", "json_projectById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("project-ProjectDefault-1325631634846.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 2 projects in projects array
        assertEquals(3, ((VisualizationAction) action).getProjects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 2 projects (all)
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
        
        // get only one project
        configureProxy("/", "json_projectById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("project-ProjectDefault-1325631634846.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 1 project in projects array
        assertEquals(1, ((VisualizationAction) action).getProjects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 1 project
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
        // check that globject id = 1
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().get(0).getId());
    }
    
    public void testProjectByIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_projectById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_projectById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
    
    public void testSubprojectsByCompanyId() throws Exception {
        // get all subprojects for all companies
        configureProxy("/", "json_subprojectsByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("subproject-SubprojectDefault-1326106098741.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 5 subprojects in subprojects array (all subprojects from all companies)
        assertEquals(5, ((VisualizationAction) action).getSubprojects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood should have 5 flats
        assertEquals(5, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
        
        // get subprojects from only one company
        configureProxy("/", "json_subprojectsByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("subproject-SubprojectDefault-1326106098741.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 5 subprojects in subprojects array (5 subprojects from company 1)
        assertEquals(5, ((VisualizationAction) action).getSubprojects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 5 subprojects
        assertEquals(5, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
    }
    
    public void testSubprojectsByCompanyIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_subprojectsByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_subprojectsByCompanyId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
    
    public void testSubprojectsByFactoryId() throws Exception {
        // get all subprojects for all factories
        configureProxy("/", "json_subprojectsByFactoryId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("subproject-SubprojectDefault-1326106098741.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 5 subprojects in projects array (all projects from all factories)
        assertEquals(5, ((VisualizationAction) action).getSubprojects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        
        // get subprojects from only one factory
        configureProxy("/", "json_subprojectsByFactoryId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("subproject-SubprojectDefault-1326106098741.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 3 subprojects in projects array (3 subprojects from factory 1)
        assertEquals(3, ((VisualizationAction) action).getSubprojects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 3 projects
        assertEquals(3, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
    }
    
    public void testSubprojectsByFactoryIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_subprojectsByFactoryId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_subprojectsByFactoryId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
    
    public void testSubprojectsByProjectId() throws Exception {
        // get all subprojects for all projects
        configureProxy("/", "json_subprojectsByProjectId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.GROUP_BY_PROJECT);
        ((VisualizationAction) action).setProfileFileName("subproject-SubprojectDefault-1326106098741.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 5 subprojects in projects array (all projects from all factories)
        assertEquals(5, ((VisualizationAction) action).getSubprojects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 2 neighborhood (2 projects)
        assertEquals(2, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        
        // get subprojects from only one project
        configureProxy("/", "json_subprojectsByProjectId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.GROUP_BY_PROJECT);
        ((VisualizationAction) action).setProfileFileName("subproject-SubprojectDefault-1326106098741.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 3 subprojects in subprojects array (3 subprojects from project 1)
        assertEquals(3, ((VisualizationAction) action).getSubprojects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (group by project)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 3 subprojects
        assertEquals(3, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
    }
    
    public void testSubprojectsByProjectIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_subprojectsByProjectId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_subprojectsByProjectId", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
    
    public void testSubprojectById() throws Exception {
        // get all subprojects
        configureProxy("/", "json_subprojectsById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("subproject-SubprojectDefault-1326106098741.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 5 subprojects in subprojects array
        assertEquals(5, ((VisualizationAction) action).getProjects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 5 subprojects (all)
        assertEquals(5, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
        
        // get only one project
        configureProxy("/", "json_subprojectsById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(1);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("subproject-SubprojectDefault-1326106098741.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check there are 1 subproject in subprojects array
        assertEquals(1, ((VisualizationAction) action).getSubprojects().size());
        // Check that GL city is not null
        assertNotNull(((VisualizationAction) action).getCity());
        // The city has 1 neighborhood (no group by)
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().size());
        // The neighborhood has 1 subprojects
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().size());
        // check that globject id = 1
        assertEquals(1, ((VisualizationAction) action).getCity().getNeighborhoods().get(0).getFlats().get(0).getId());
    }
    
    public void testSubprojectByIdError() throws Exception {
        // Invalid id
        configureProxy("/", "json_subprojectById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(50);
        ((VisualizationAction) action).setGenerateGLObjects(false);
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
        
        // Invalid profile name
        // Invalid id
        configureProxy("/", "json_subprojectById", "admin");
        assertTrue(action instanceof VisualizationAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        ((VisualizationAction) action).setGroupBy(GLObjectManager.NO_GROUP_BY);
        ((VisualizationAction) action).setProfileFileName("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
        // Check that GL city is null
        assertNull(((VisualizationAction) action).getCity());
    }
}
