package presentation;

import java.applet.Applet;
import java.awt.BorderLayout;

import model.MainManager;

public class AppletMain extends Applet {

	@Override
	public void init() {
	    setLayout(new BorderLayout());
	    MainManager.getInstance().configure();
	}

	@Override
	public void start() {
	    GLInit.init();
	    GLInit.getGLCanvas().setSize(getSize());
	    add(GLInit.getGLCanvas(), BorderLayout.CENTER);
	    GLInit.getAnimator().start();
	}

	@Override
	public void stop() {
		GLInit.getAnimator().stop();
	}

}
