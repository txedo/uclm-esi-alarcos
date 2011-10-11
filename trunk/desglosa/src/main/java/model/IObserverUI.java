package model;

public interface IObserverUI {
	public void showMessage(String message);
	public void selectAntennaBall(int id, int clickButton, int clickCount);
	public void selectBuilding(int id, int clickButton, int clickCount);
	public void selectTower(int id, int clickButton, int clickCount);
}
