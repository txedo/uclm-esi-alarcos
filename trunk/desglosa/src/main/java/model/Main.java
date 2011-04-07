package model;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainManager.getInstance().configure();
		MainManager.getInstance().run(args);
	}

}
