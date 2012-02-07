package es.uclm.inf_cr.alarcos.desglosa_web.actions;


import com.opensymphony.xwork2.Action;

import es.uclm.inf_cr.alarcos.desglosa_web.control.ProfileManager;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.StrutsSpringTestCaseBase;

public class ProfileActionTest extends StrutsSpringTestCaseBase {

    public void testExecute() throws Exception {
        configureProxy("/", "listProfiles", "admin");
        assertTrue(action instanceof ProfileAction);
        assertEquals(Action.SUCCESS, proxy.execute());
    }
    
    public void testShowForm() throws Exception {
        configureProxy("/", "showProfileForm", "admin");
        assertTrue(action instanceof ProfileAction);
        assertEquals(Action.SUCCESS, proxy.execute());
    }
    
    public void testLoadEntityAttributes() throws Exception {
        configureProxy("/", "json_p_loadEntityAttributes", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setEntity("es.uclm.inf_cr.alarcos.desglosa_web.model.Company");
        assertEquals(Action.SUCCESS, proxy.execute());
        assertNotNull(((ProfileAction) action).getEntityAttributes());
    }
    
    public void testLoadEntityAttributesError() throws Exception {
        configureProxy("/", "json_p_loadEntityAttributes", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setEntity("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
    }
    
    public void testLoadModelAttributes() throws Exception {
        configureProxy("/", "json_p_loadModelAttributes", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setModel("model.gl.knowledge.GLTower");
        assertEquals(Action.SUCCESS, proxy.execute());
        assertNotNull(((ProfileAction) action).getModelAttributes());
    }
    
    public void testLoadModelAttributesError() throws Exception {
        configureProxy("/", "json_p_loadModelAttributes", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setModel("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
    }
    
    public void testSave() throws Exception {
        String profileName = "TestProjectProfile";
        String profileDescription = "This visualization profile is for testing purposes. So you can safely delete it.";
        String entity = "es.uclm.inf_cr.alarcos.desglosa_web.model.Project";
        String model = "model.gl.knowledge.GLAntennaBall";
        String jsonMappings = "[{\"entityAttribute\":{\"name\":\"totalIncidences\",\"type\":\"int\"},\"modelAttribute\":{\"name\":\"scale\",\"type\":\"float_range\"},\"ratio\":null,\"rules\":[{\"low\":0,\"high\":20,\"value\":1},{\"low\":20,\"high\":10000,\"value\":1.5}]},{\"entityAttribute\":{\"name\":\"name\",\"type\":\"string\"},\"modelAttribute\":{\"name\":\"label\",\"type\":\"string\"},\"ratio\":null,\"rules\":[]},{\"entityAttribute\":{\"name\":\"fiabilidad\",\"type\":\"float\"},\"modelAttribute\":{\"name\":\"parentBallRadius\",\"type\":\"float\"},\"ratio\":100,\"rules\":[]}]";
        String jsonConstants = "[{\"name\":\"id\",\"type\":\"int\",\"value\":0},{\"name\":\"progressionMark\",\"type\":\"boolean\",\"value\":true},{\"name\":\"leftChildBallValue\",\"type\":\"string\",\"value\":\"0\"},{\"name\":\"color\",\"type\":\"color\",\"value\":\"781d78\"},{\"name\":\"rightChildBallValue\",\"type\":\"string\",\"value\":\"\"}]";
        String jsonCaptionLines = "[{\"label\":\"test line\",\"color\":\"3636b5\"}]";
        
        configureProxy("/", "saveProfile", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setProfileName(profileName);
        ((ProfileAction) action).setProfileDescription(profileDescription);
        ((ProfileAction) action).setEntity(entity);
        ((ProfileAction) action).setModel(model);
        ((ProfileAction) action).setJsonMappings(jsonMappings);
        ((ProfileAction) action).setJsonConstants(jsonConstants);
        ((ProfileAction) action).setJsonCaptionLines(jsonCaptionLines);
        assertEquals(Action.SUCCESS, proxy.execute());
    }
    
    public void testSaveError() throws Exception {
        String profileName = "";
        String profileDescription = "";
        String entity = "";
        String model = "";
        String jsonMappings = "[]";
        String jsonConstants = "[{\"name\":\"id\",\"type\":\"int\",\"value\":0},{\"name\":\"progressionMark\",\"type\":\"boolean\",\"value\":true},{\"name\":\"leftChildBallValue\",\"type\":\"string\",\"value\":\"0\"},{\"name\":\"color\",\"type\":\"color\",\"value\":\"781d78\"},{\"name\":\"rightChildBallValue\",\"type\":\"string\",\"value\":\"\"}]";
        String jsonCaptionLines = "[{\"label\":\"test line\",\"color\":\"3636b5\"}]";
        
        configureProxy("/", "saveProfile", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setProfileName(profileName);
        ((ProfileAction) action).setProfileDescription(profileDescription);
        ((ProfileAction) action).setEntity(entity);
        ((ProfileAction) action).setModel(model);
        ((ProfileAction) action).setJsonMappings(jsonMappings);
        ((ProfileAction) action).setJsonConstants(jsonConstants);
        ((ProfileAction) action).setJsonCaptionLines(jsonCaptionLines);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testRemove() throws Exception {
        String profileName = "TestProjectProfile";
        String profileDescription = "This visualization profile is for testing purposes. So you can safely delete it.";
        String entity = "es.uclm.inf_cr.alarcos.desglosa_web.model.Project";
        String model = "model.gl.knowledge.GLAntennaBall";
        String jsonMappings = "[{\"entityAttribute\":{\"name\":\"totalIncidences\",\"type\":\"int\"},\"modelAttribute\":{\"name\":\"scale\",\"type\":\"float_range\"},\"ratio\":null,\"rules\":[{\"low\":0,\"high\":20,\"value\":1},{\"low\":20,\"high\":10000,\"value\":1.5}]},{\"entityAttribute\":{\"name\":\"name\",\"type\":\"string\"},\"modelAttribute\":{\"name\":\"label\",\"type\":\"string\"},\"ratio\":null,\"rules\":[]},{\"entityAttribute\":{\"name\":\"fiabilidad\",\"type\":\"float\"},\"modelAttribute\":{\"name\":\"parentBallRadius\",\"type\":\"float\"},\"ratio\":100,\"rules\":[]}]";
        String jsonConstants = "[{\"name\":\"id\",\"type\":\"int\",\"value\":0},{\"name\":\"progressionMark\",\"type\":\"boolean\",\"value\":true},{\"name\":\"leftChildBallValue\",\"type\":\"string\",\"value\":\"0\"},{\"name\":\"color\",\"type\":\"color\",\"value\":\"781d78\"},{\"name\":\"rightChildBallValue\",\"type\":\"string\",\"value\":\"\"}]";
        String jsonCaptionLines = "[{\"label\":\"test line\",\"color\":\"3636b5\"}]";
        
        String filename = ProfileManager.saveProfile(profileName, profileDescription, entity, model, jsonMappings, jsonConstants, jsonCaptionLines);
        configureProxy("/", "deleteProfile", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setFilename(filename);
        assertEquals(Action.SUCCESS, proxy.execute());
    }
    
    public void testRemoveError() throws Exception {
        configureProxy("/", "deleteProfile", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setFilename("FooBarBaz");
        assertEquals(Action.ERROR, proxy.execute());
    }
    
    public void testGetProfileNames() throws Exception {
        configureProxy("/", "json_p_get", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setEntity("project");
        assertEquals(Action.SUCCESS, proxy.execute());
        assertTrue(((ProfileAction) action).getProfileNames().size() > 0);
    }
    
    public void testGetProfileNamesError() throws Exception {
        configureProxy("/", "json_p_get", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setEntity("FooBarBaz");
        assertEquals(Action.SUCCESS, proxy.execute());
        assertTrue(((ProfileAction) action).getProfileNames().size() == 0);
    }
    
    public void testGetProfile() throws Exception {
        configureProxy("/", "json_p_get", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setProfileName("project-ProjectDefault-1325631634846.xml");
        assertEquals(Action.SUCCESS, proxy.execute());
        assertNotNull(((ProfileAction) action).getProfile());
    }
    
    public void testGetProfileError() throws Exception {
        configureProxy("/", "json_p_get", "admin");
        assertTrue(action instanceof ProfileAction);
        ((ProfileAction) action).setProfileName("FooBarBaz.xml");
        assertEquals(Action.ERROR, proxy.execute());
    }
}
