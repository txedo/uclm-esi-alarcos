package model;

import model.business.knowledge.Factory;
import model.knowledge.Vector2f;

public interface IObserverUI {
	public void updateCompanyList();
	public void updateFactoryList(int idCompany);
	public void updateMapList();
	public void updateProjectList();
	public void updateClickedWorldCoords(Vector2f coordinates);
	public void selectFactoryByLocation(int idLocation);
	public void selectProject(int id);
	public void selectFactory(int id);
	public void selectFactory(Factory factory);
	public void selectTower(int id);
}
