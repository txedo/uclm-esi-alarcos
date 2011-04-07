package presentation;

import java.awt.Dimension;

import model.business.knowledge.Project;

public class JPInfoProject extends javax.swing.JPanel implements JPInfoInterface {
	
	public JPInfoProject() {
		super();
		initGUI();
	}
	
	public JPInfoProject(Project project) {
		super();
		initGUI();
		this.displayInformation(project);
	}

	private void initGUI() {
		try {
			setPreferredSize(new Dimension(400, 300));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayInformation(Object obj) {
		// TODO Auto-generated method stub
		
	}

}
