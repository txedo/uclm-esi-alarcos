package presentation;

import java.awt.Frame;

public class Main extends Frame {
	
	private static final long serialVersionUID = -8016019048808055367L;
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public static void main(String[] args) {
		Frame frame = new Frame("");
		frame.setSize(WIDTH, HEIGHT);
		GLInit.init();
		GLInit.setContext(frame, frame, null);
	}
}
