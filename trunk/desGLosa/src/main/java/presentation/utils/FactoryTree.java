package presentation.utils;

import java.sql.SQLException;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import model.business.control.BusinessManager;
import model.business.knowledge.Company;
import model.business.knowledge.Factory;

public class FactoryTree extends JTree {
	private static final long serialVersionUID = 7235693035277673840L;

	public FactoryTree () throws SQLException {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Companies and factories");
		createNodes(rootNode);
		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		treeModel.setAsksAllowsChildren(true);
		setModel(treeModel);
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setOpenIcon(ImageUtils.readIcon("src/main/java/presentation/resources/icons/Company_256.png", 16, 16));
		renderer.setClosedIcon(ImageUtils.readIcon("src/main/java/presentation/resources/icons/Company_256.png", 16, 16));
		renderer.setLeafIcon(ImageUtils.readIcon("src/main/java/presentation/resources/icons/Factory_256.png", 16, 16));
		setCellRenderer(renderer);
		// Hide the root element "Companies and factories"
		setRootVisible(false);
		// Show root handles
		setShowsRootHandles(true);
		setEditable(false);
		expandWholeTree();
		if (getRowCount() > 0) setSelectionRow(0);
	}
	
	public void expandWholeTree() {
		// Expand the whole tree
		for (int i = 0; i < getRowCount(); i++) {
			expandRow(i);
		}
	}
	
	public void collapseWholeTree() {
		// Expand the whole tree
		for (int i = 0; i < getRowCount(); i++) {
			collapseRow(i);
		}
	}

	private void createNodes(DefaultMutableTreeNode rootNode) throws SQLException {
		DefaultMutableTreeNode companyNode = null;
		DefaultMutableTreeNode factoryNode = null;
		
		for (Company c : BusinessManager.getAllCompanies()) {
			companyNode = new DefaultMutableTreeNode(c);
			companyNode.setAllowsChildren(true);
			rootNode.add(companyNode);
			for (Factory f : BusinessManager.getFactories(c)) {
				factoryNode = new DefaultMutableTreeNode(f);
				factoryNode.setAllowsChildren(false);
				companyNode.add(factoryNode);
			}
			expandPath(new TreePath(factoryNode));
		}
	}

}
