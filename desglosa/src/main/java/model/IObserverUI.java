package model;

public interface IObserverUI {
    void showMessage(String message);

    void selectAntennaBall(int id, int clickButton, int clickCount);

    void selectBuilding(int id, int clickButton, int clickCount);

    void selectTower(int id, int clickButton, int clickCount);
}
