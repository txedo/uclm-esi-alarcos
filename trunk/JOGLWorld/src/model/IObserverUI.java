package model;

import model.knowledge.Vector2f;

public interface IObserverUI {
	public void updateCompanyList();
	public void updateFactoryList(int companyId);
	public void updateMapList();
	public void updateClickedWorldCoords(Vector2f coordinates);
	public void selectFactory(int offset);
}
