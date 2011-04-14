package presentation;

public interface WindowNotifierObserverInterface {
	public void manageOperation(WindowNotifierOperationCodes code, Object... objects);
}
