package presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.WindowConstants;

import org.apache.commons.configuration.ConfigurationException;
import org.jdesktop.application.Application;

import model.app.control.OperationManager;
import model.app.knowledge.Operation;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class JFConfiguration extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6634273495930911729L;
	private JPanel rootPanel;
	private JPanel menuPanel;
	private JPanel operationPanel;
	
	public JFConfiguration() {
		super();
		initGUI();
	}
	
	private void initOperationList() {
		int addedButtons = 0;
		try {
			List<Operation> lop = OperationManager.getAll();
			for (Operation op : lop) {
				JButton button = createOperationButton(op);
				menuPanel.add(button);
				button.setName("button" + ++addedButtons);
				// Emulate a mouse click on the first added button
				if (addedButtons == 1) button.doClick();
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private JButton createOperationButton(final Operation op) {
		JButton jbutton = new JButton();
		jbutton.setSize(new Dimension(100, 100));
		jbutton.setPreferredSize(jbutton.getSize());
		// Add label and tooltip
		jbutton.setText(op.getName());
		jbutton.setToolTipText(op.getDescription());
		// Scale the icon to 64x64
		ImageIcon originalImage = new ImageIcon(op.getIcon());
		Image img = originalImage.getImage();
		Image scaledImg = img.getScaledInstance(64, 64, Image.SCALE_DEFAULT);
		// Add the scaled icon to the button
		jbutton.setIcon(new ImageIcon(scaledImg));
		// Place icon at the center and text at the bottom
		jbutton.setHorizontalTextPosition(JButton.CENTER);
		jbutton.setVerticalTextPosition(JButton.BOTTOM);
		// Fine tunning the button appareance
		jbutton.setContentAreaFilled(false);
		jbutton.setFocusPainted(false);
		jbutton.setBorderPainted(true);
		// Add an action listener
		jbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("jButton1.actionPerformed, event="+evt);
				try {
					// Remove existing components to clean the operationPanel
					operationPanel.removeAll();
					for (Component c : menuPanel.getComponents()) {
						if (c instanceof JButton) ((JButton)c).setContentAreaFilled(false);
					}
					// Add the new panel
					Component aux = (Component) (Class.forName(op.getContainer()).newInstance());
					operationPanel.add((JPanel)aux);
					aux.setVisible(true);
					// The recently added panel won't be repainted until validate()
					operationPanel.validate();
					((JButton)evt.getSource()).setContentAreaFilled(true);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return jbutton;
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setName("parent");
			{
				rootPanel = new JPanel();
				BoxLayout rootPanelLayout = new BoxLayout(rootPanel, javax.swing.BoxLayout.X_AXIS);
				rootPanel.setLayout(rootPanelLayout);
				getContentPane().add(rootPanel, BorderLayout.CENTER);
				rootPanel.setPreferredSize(new java.awt.Dimension(539, 324));
				{
					menuPanel = new JPanel();
					rootPanel.add(menuPanel);
					FlowLayout menuPanelLayout = new FlowLayout();
					menuPanel.setLayout(menuPanelLayout);
					menuPanel.setPreferredSize(new java.awt.Dimension(128, 276));
					menuPanel.setName("menuPanel");
				}
				{
					operationPanel = new JPanel();
					BorderLayout operationPanelLayout = new BorderLayout();
					operationPanel.setLayout(operationPanelLayout);
					rootPanel.add(operationPanel);
					operationPanel.setPreferredSize(new java.awt.Dimension(238, 262));
				}
			}
			initOperationList();
			pack();
			this.setSize(701, 385);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
