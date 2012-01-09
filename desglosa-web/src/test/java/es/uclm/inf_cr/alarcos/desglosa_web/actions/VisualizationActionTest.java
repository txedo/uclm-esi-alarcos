package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import com.opensymphony.xwork2.Action;

import es.uclm.inf_cr.alarcos.desglosa_web.utils.StrutsSpringTestCaseBase;

public class VisualizationActionTest extends StrutsSpringTestCaseBase {

    public void testExecute() throws Exception {
        configureProxy("/", "visualization", "admin");
        assertTrue(action instanceof ProfileAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(2, ((VisualizationAction) action).getCompanies().size());
        assertEquals(3, ((VisualizationAction) action).getFactories().size());
        assertEquals(2, ((VisualizationAction) action).getProjects().size());
        assertEquals(5, ((VisualizationAction) action).getSubprojects().size());
    }
    
    public void testfactoryById() throws Exception {
        // get all factories
        configureProxy("/", "json_factoryById", "admin");
        assertTrue(action instanceof ProfileAction);
        ((VisualizationAction) action).setId(0);
        ((VisualizationAction) action).setGenerateGLObjects(true);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(3, ((VisualizationAction) action).getFactories().size());
        
        // get only one factory
        configureProxy("/", "json_factoryById", "admin");
        assertTrue(action instanceof ProfileAction);
        ((VisualizationAction) action).setId(1);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(1, ((VisualizationAction) action).getFactories().size());
    }
    
    public void testfactoryByIdError() throws Exception {
        configureProxy("/", "json_factoryById", "admin");
        assertTrue(action instanceof ProfileAction);
        ((VisualizationAction) action).setId(50);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(1, ((VisualizationAction) action).getFactories().size());
    }
}
