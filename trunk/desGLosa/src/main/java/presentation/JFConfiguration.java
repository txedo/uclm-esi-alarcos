package presentation;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import exceptions.NoActiveMapException;
import exceptions.gl.GLSingletonNotInitializedException;

import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.WindowConstants;

import org.apache.commons.configuration.ConfigurationException;
import org.jdesktop.application.Application;

import presentation.utils.ImageUtils;

import model.app.control.OperationManager;
import model.app.knowledge.Operation;
import model.business.control.BusinessManager;
import model.business.knowledge.Map;


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
	private JPanel menuPanel;
	private JPanel operationPanel;
	private IAppCore core;
	private Map activeMap;
	
	public JFConfiguration(IAppCore appCore) {
		super();
		initGUI();
		this.core = appCore;
		try {
			this.activeMap = (Map) BusinessManager.getActiveMap().clone();
			// TODO guardar las factorias mostradas y highlighted
		} catch (NoActiveMapException e) {
		}
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
		// Add the scaled icon to the button
		jbutton.setIcon(ImageUtils.readIcon(op.getIcon()));
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
				try {
					// Remove existing components to clean the operationPanel
					operationPanel.removeAll();
					for (Component c : menuPanel.getComponents()) {
						if (c instanceof JButton) ((JButton)c).setContentAreaFilled(false);
					}
					// Create the new panel
					Component aux = (Component) (Class.forName(op.getContainer()).newInstance());
					// Configure the panel
					WindowNotifier.attach((WindowNotifierObserverInterface)aux);
					if (((JPConfigureInterface)aux).isApplicationCoreNeeded())
						((JPConfigureInterface)aux).setApplicationCore(core);
					// Add the new panel
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
			setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			FormLayout thisLayout = new FormLayout(
					"104dlu, 284dlu", 
					"359dlu");
			getContentPane().setLayout(thisLayout);
			this.setName("parent");
			this.setPreferredSize(new java.awt.Dimension(686, 736));
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					thisWindowClosing(evt);
				}
			});
			{
				operationPanel = new JPanel();
				getContentPane().add(operationPanel, new CellConstraints("2, 1, 1, 1, fill, fill"));
				BorderLayout operationPanelLayout = new BorderLayout();
				operationPanel.setLayout(operationPanelLayout);
				operationPanel.setPreferredSize(new java.awt.Dimension(238, 262));
			}
			{
				menuPanel = new JPanel();
				getContentPane().add(menuPanel, new CellConstraints("1, 1, 1, 1, fill, fill"));
				FlowLayout menuPanelLayout = new FlowLayout();
				menuPanel.setLayout(menuPanelLayout);
				menuPanel.setPreferredSize(new java.awt.Dimension(128, 276));
				menuPanel.setName("menuPanel");
			}
			initOperationList();
			pack();
			this.setSize(686, 736);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void thisWindowClosing(WindowEvent evt) {
		try {
			WindowNotifier.clear();
			BusinessManager.setActiveMap(this.activeMap);
			// TODO restaurar las factorias mostradas y highlighted
		} catch (GLSingletonNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dispose();
	}

}
