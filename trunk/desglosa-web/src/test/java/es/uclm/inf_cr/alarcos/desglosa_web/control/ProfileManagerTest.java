package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.DeleteProfileException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Metaclass;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.PropertyWrapper;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestCaseBase;

public class ProfileManagerTest extends SpringTestCaseBase {
    //Fixtures
    private List<PropertyWrapper> pwList;
    private Map<String, String> dims;
    private Map<String, String> profiles;
    
    
    @Override
    protected void onSetUp() throws Exception {
        // Do nothing. Calling super would reset database
        // Database is not needed in this test case
    }

    @Override
    protected void onTearDown() throws Exception {
     // Do nothing. Calling super would drop database
     // Database is not needed in this test case
    }
    
    public void testReadEntityAttributesError() {
        try {
            pwList = ProfileManager.readEntityAttributes("es.uclm.inf_cr.alarcos.desglosa_web.model.FooBarBaz");
            fail("ClassNotFoundException should have been thrown.");
        } catch (ClassNotFoundException e) {
            // This is expected.
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testReadEntityAttributes() {
        try {
            pwList = ProfileManager.readEntityAttributes("es.uclm.inf_cr.alarcos.desglosa_web.model.Company");
            assertTrue("Company has no attributes available to create profiles", pwList.size() > 0);
        } catch (ClassNotFoundException e) {
            fail("Could not find Company class");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            pwList = ProfileManager.readEntityAttributes("es.uclm.inf_cr.alarcos.desglosa_web.model.Factory");
            assertTrue("Factory has no attributes available to create profiles", pwList.size() > 0);
        } catch (ClassNotFoundException e) {
            fail("Could not find Factory class");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            pwList = ProfileManager.readEntityAttributes("es.uclm.inf_cr.alarcos.desglosa_web.model.Project");
            assertTrue("Project has no attributes available to create profiles", pwList.size() > 0);
        } catch (ClassNotFoundException e) {
            fail("Project not find Company class");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            pwList = ProfileManager.readEntityAttributes("es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject");
            assertTrue("Subproject has no attributes available to create profiles", pwList.size() > 0);
        } catch (ClassNotFoundException e) {
            fail("Could not find Subproject class");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    public void testReadModelDimensionsError() {
        try {
            pwList = ProfileManager.readEntityAttributes("model.gl.knowledge.FooBarBaz");
            fail("ClassNotFoundException should have been thrown.");
        } catch (ClassNotFoundException e) {
            // This is expected.
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    public void testReadModelDimensions() {
        try {
            dims = ProfileManager.readModelDimensions("model.gl.knowledge.GLTower");
            assertTrue("GLTower has no dimensions available to create profiles", dims.size() > 0);
        } catch (ClassNotFoundException e) {
            fail("Could not find GLTower class");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            dims = ProfileManager.readModelDimensions("model.gl.knowledge.GLAntennaBall");
            assertTrue("GLAntennaBall has no dimensions available to create profiles", dims.size() > 0);
        } catch (ClassNotFoundException e) {
            fail("Could not find GLAntennaBall class");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            dims = ProfileManager.readModelDimensions("model.gl.knowledge.GLFactory");
            assertTrue("GLFactory has no dimensions available to create profiles", dims.size() > 0);
        } catch (ClassNotFoundException e) {
            fail("Could not find GLFactory class");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    public void testGetProfilesForEntity() {
        try {
            profiles = ProfileManager.getProfilesForEntity("project");
            assertTrue(profiles.size() > 0);
        } catch (JAXBException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (InstantiationException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        }
    }
    
    public void testGetProfileByName() {
        try {
            ProfileManager.getProfileByName("project-TestProjectProfile-1326022240854.xml");
        } catch (JAXBException e) {
            fail("project-TestProjectProfile-1326022240854.xml should have been manually created for testing purposes.\n" + e.getMessage());
        } catch (IOException e) {
            fail("project-TestProjectProfile-1326022240854.xml should have been manually created for testing purposes.\n" + e.getMessage());
        } catch (InstantiationException e) {
            fail("project-TestProjectProfile-1326022240854.xml should have been manually created for testing purposes.\n" + e.getMessage());
        } catch (IllegalAccessException e) {
            fail("project-TestProjectProfile-1326022240854.xml should have been manually created for testing purposes.\n" + e.getMessage());
        }
    }
    
    public void testSaveProfile() {
        String profileName = "TestProjectProfile";
        String profileDescription = "This visualization profile is for testing purposes. So you can safely delete it.";
        String entity = "es.uclm.inf_cr.alarcos.desglosa_web.model.Project";
        String model = "model.gl.knowledge.GLAntennaBall";
        String jsonMappings = "[{\"entityAttribute\":{\"name\":\"totalIncidences\",\"type\":\"int\"},\"modelAttribute\":{\"name\":\"scale\",\"type\":\"float_range\"},\"ratio\":null,\"rules\":[{\"low\":0,\"high\":20,\"value\":1},{\"low\":20,\"high\":10000,\"value\":1.5}]},{\"entityAttribute\":{\"name\":\"name\",\"type\":\"string\"},\"modelAttribute\":{\"name\":\"label\",\"type\":\"string\"},\"ratio\":null,\"rules\":[]},{\"entityAttribute\":{\"name\":\"fiabilidad\",\"type\":\"float\"},\"modelAttribute\":{\"name\":\"parentBallRadius\",\"type\":\"float\"},\"ratio\":100,\"rules\":[]}]";
        String jsonConstants = "[{\"name\":\"id\",\"type\":\"int\",\"value\":0},{\"name\":\"progressionMark\",\"type\":\"boolean\",\"value\":true},{\"name\":\"leftChildBallValue\",\"type\":\"string\",\"value\":\"0\"},{\"name\":\"color\",\"type\":\"color\",\"value\":\"781d78\"},{\"name\":\"rightChildBallValue\",\"type\":\"string\",\"value\":\"\"}]";
        String jsonCaptionLines = "[{\"label\":\"test line\",\"color\":\"3636b5\"}]";
        try {
            assertNotNull(ProfileManager.saveProfile(profileName, profileDescription, entity, model, jsonMappings, jsonConstants, jsonCaptionLines));
        } catch (JAXBException e) {
            fail("Could not save the profile.");
        }
    }
    
    public void testSaveAndRemoveProfile() {
        String profileName = "TestCompanyProfile";
        String profileDescription = "This profile has been created for testing purposes and verify that ProfileManager works as expected. It can be deleted.";
        String entity = "es.uclm.inf_cr.alarcos.desglosa_web.model.Company";
        String model = "model.gl.knowledge.GLFactory";
        String jsonMappings = "[{\"entityAttribute\":{\"name\":\"id\",\"type\":\"int\"},\"modelAttribute\":{\"name\":\"id\",\"type\":\"int\"},\"ratio\":null,\"rules\":[]},{\"entityAttribute\":{\"name\":\"numberOfEmployees\",\"type\":\"int\"},\"modelAttribute\":{\"name\":\"smokestackHeight\",\"type\":\"float\"},\"ratio\":null,\"rules\":[]}]";
        String jsonConstants = "[{\"name\":\"scale\",\"type\":\"float\",\"value\":1},{\"name\":\"color\",\"type\":\"color\",\"value\":\"ffffff\"},{\"name\":\"smokestackColor\",\"type\":\"color\",\"value\":\"ffffff\"}]";
        String jsonCaptionLines = "[{\"label\":\"test line\",\"color\":\"ffffff\"}]";
        try {
            // save the profile
            String filename = ProfileManager.saveProfile(profileName, profileDescription, entity, model, jsonMappings, jsonConstants, jsonCaptionLines);
            assertTrue(filename != null && filename != "");
            try {
                // check that it exists
                Metaclass meta = ProfileManager.getProfileByName(filename);
                assertNotNull(meta);
                // Now delete it
                ProfileManager.removeProfile(filename);
                try {
                    // check that it does not exists anymore
                    meta = ProfileManager.getProfileByName(filename);
                    fail("The profile should have been deleted.");
                } catch (IOException e) {
                    // This is expected because the profile should not exists.
                } catch (InstantiationException e) {
                    fail("The profile " + filename + " should have been created.\n" + e.getMessage());
                } catch (IllegalAccessException e) {
                    fail("The profile " + filename + " should have been created.\n" + e.getMessage());
                }
            } catch (IOException e) {
                fail("The profile " + filename + " should have been created.\n" + e.getMessage());
            } catch (InstantiationException e) {
                fail("The profile " + filename + " should have been created.\n" + e.getMessage());
            } catch (IllegalAccessException e) {
                fail("The profile " + filename + " should have been created.\n" + e.getMessage());
            } catch (DeleteProfileException e) {
                fail("The profile " + filename + " should exists before deletion.\n" + e.getMessage());
            }
            
        } catch (JAXBException e) {
            fail("A company profile should have been created.\n" + e.getMessage());
        }
    }
}
