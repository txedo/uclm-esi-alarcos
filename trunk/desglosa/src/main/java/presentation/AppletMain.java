package presentation;

import java.applet.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import exceptions.ViewManagerNotInstantiatedException;

import javax.media.opengl.GLAnimatorControl;

import model.IObserverUI;
import model.NotifyUIManager;
import model.gl.GLDrawer;
import model.gl.IGLFacade;
import model.gl.IGLFacadeImpl;
import model.util.Synchronizer;

public class AppletMain extends Applet implements IObserverUI, IGLFacade {
	private GLAnimatorControl animator;
	private GLDrawer drawer = null;

	@Override
	public void init() {
		System.err.println("GearsApplet: init() - begin");
		// Creating an object to manipulate OpenGL parameters.
		GLProfile.initSingleton(false);
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities capabilities = new GLCapabilities(profile);
		// Setting some OpenGL parameters.
		capabilities.setHardwareAccelerated(true);
		capabilities.setDoubleBuffered(true);
		// Set number of stencil bits in order to have stencil buffer working
		capabilities.setStencilBits(8);
		// Sample buffers are used to get polygon anti-aliasing working
		capabilities.setSampleBuffers(true);
		capabilities.setNumSamples(2);
		setLayout(new BorderLayout());
		GLCanvas canvas = new GLCanvas(capabilities);
		drawer = new GLDrawer();
		canvas.addGLEventListener(drawer);
		canvas.setSize(getSize());
		add(canvas, BorderLayout.CENTER);
		animator = new FPSAnimator(canvas, 60);
		
		NotifyUIManager.attach(this);
		
		animator.start();
		
		System.err.println("GearsApplet: init() - end");
	}
	
	@Override
	public void start() {
		System.err.println("GearsApplet: start() - begin");
		
//		try {
//			Synchronizer.getInstance().solicitar();
//			IGLFacadeImpl.getInstance().visualizeTowers("{\"neighborhoods\":[{\"flats\":[{\"width\":2.2362,\"height\":11.694,\"depth\":2.3505,\"color\":\"00ff00\",\"fill\":4.9452,\"id\":1},{\"width\":2.2362,\"height\":11.694,\"depth\":2.3505,\"color\":\"00ff00\",\"fill\":4.9452,\"id\":4}]}]}");
//		} catch (ViewManagerNotInstantiatedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		try {
//			Synchronizer.getInstance().solicitar();
//			// get project by id=0 group by company
//			//IGLFacadeImpl.getInstance().visualizeProjects("{\"captionLines\":{\"Arquitectura\":\"00649a\",\"Defensa\":\"298400\",\"Sanitario\":\"ff8c00\"},\"neighborhoods\":[{\"captionLines\":{},\"depth\":6.6,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":4,\"label\":\"asdf\",\"leftChildBallValue\":32,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.64285713,\"positionX\":7.8,\"positionZ\":0.0,\"progression\":true,\"rightChildBallValue\":-9,\"scale\":1.0},{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":1,\"label\":\"desglosa\",\"leftChildBallValue\":100,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":1.5,\"positionX\":11.1,\"positionZ\":0.0,\"progression\":true,\"rightChildBallValue\":400,\"scale\":1.0},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"id\":2,\"label\":\"vilma\",\"leftChildBallValue\":2,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.64285713,\"positionX\":7.8,\"positionZ\":3.6,\"progression\":false,\"rightChildBallValue\":8,\"scale\":1.0}],\"name\":\"Indra\",\"neighborhoods\":[],\"pavements\":[],\"width\":6.3},{\"captionLines\":{},\"depth\":6.6,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":4,\"label\":\"asdf\",\"leftChildBallValue\":32,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.64285713,\"positionX\":7.8,\"positionZ\":0.0,\"progression\":true,\"rightChildBallValue\":-9,\"scale\":1.0},{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":1,\"label\":\"desglosa\",\"leftChildBallValue\":100,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":1.5,\"positionX\":11.1,\"positionZ\":0.0,\"progression\":true,\"rightChildBallValue\":400,\"scale\":1.0},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"id\":2,\"label\":\"vilma\",\"leftChildBallValue\":2,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.64285713,\"positionX\":7.8,\"positionZ\":3.6,\"progression\":false,\"rightChildBallValue\":8,\"scale\":1.0},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.54901963,\"r\":1.0},\"id\":3,\"label\":\"w2p\",\"leftChildBallValue\":21,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.85714287,\"positionX\":11.1,\"positionZ\":3.6,\"progression\":true,\"rightChildBallValue\":39,\"scale\":1.0}],\"name\":\"IECISA\",\"neighborhoods\":[],\"pavements\":[],\"width\":6.3}],\"pavements\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":7.9333334,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-2.0,\"positionZ\":-2.1666667,\"scale\":1.0,\"texture\":-1,\"title\":\"Indra\",\"width\":7.3},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":7.9333334,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":5.8,\"positionZ\":-2.1666667,\"scale\":1.0,\"texture\":-1,\"title\":\"IECISA\",\"width\":7.3}]}");
//			// get project by id=0 group by market
//			//IGLFacadeImpl.getInstance().visualizeProjects("{\"captionLines\":{\"Arquitectura\":\"00649a\",\"Defensa\":\"298400\",\"Sanitario\":\"ff8c00\"},\"neighborhoods\":[{\"captionLines\":{},\"depth\":3.0,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":4,\"label\":\"asdf\",\"leftChildBallValue\":32,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.64285713,\"positionX\":0.0,\"positionZ\":0.0,\"progression\":true,\"rightChildBallValue\":-9,\"scale\":1.0},{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":1,\"label\":\"desglosa\",\"leftChildBallValue\":100,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":1.5,\"positionX\":3.3,\"positionZ\":0.0,\"progression\":true,\"rightChildBallValue\":400,\"scale\":1.0}],\"name\":\"Arquitectura\",\"neighborhoods\":[],\"pavements\":[],\"width\":6.3},{\"captionLines\":{},\"depth\":3.0,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"id\":2,\"label\":\"vilma\",\"leftChildBallValue\":2,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.64285713,\"positionX\":7.8,\"positionZ\":0.0,\"progression\":false,\"rightChildBallValue\":8,\"scale\":1.0}],\"name\":\"Defensa\",\"neighborhoods\":[],\"pavements\":[],\"width\":3.0},{\"captionLines\":{},\"depth\":3.0,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.54901963,\"r\":1.0},\"id\":3,\"label\":\"w2p\",\"leftChildBallValue\":21,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.85714287,\"positionX\":0.0,\"positionZ\":5.0,\"progression\":true,\"rightChildBallValue\":39,\"scale\":1.0}],\"name\":\"Sanitario\",\"neighborhoods\":[],\"pavements\":[],\"width\":3.0}],\"pavements\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":4.3333335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-2.0,\"positionZ\":-2.1666667,\"scale\":1.0,\"texture\":-1,\"title\":\"Arquitectura\",\"width\":7.3},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":4.3333335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":5.8,\"positionZ\":-2.1666667,\"scale\":1.0,\"texture\":-1,\"title\":\"Defensa\",\"width\":4.0},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":4.3333335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-2.0,\"positionZ\":2.8333333,\"scale\":1.0,\"texture\":-1,\"title\":\"Sanitario\",\"width\":4.0}]}");
//			// get project by id=0 group by factory
//			//IGLFacadeImpl.getInstance().visualizeProjects("{\"captionLines\":{\"Arquitectura\":\"00649a\",\"Defensa\":\"298400\",\"Sanitario\":\"ff8c00\"},\"neighborhoods\":[{\"captionLines\":{},\"depth\":3.0,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":4,\"label\":\"asdf\",\"leftChildBallValue\":32,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.64285713,\"positionX\":0.0,\"positionZ\":5.0,\"progression\":true,\"rightChildBallValue\":-9,\"scale\":1.0},{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":1,\"label\":\"desglosa\",\"leftChildBallValue\":100,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":1.5,\"positionX\":3.3,\"positionZ\":5.0,\"progression\":true,\"rightChildBallValue\":400,\"scale\":1.0}],\"name\":\"Indra Ciudad Real\",\"neighborhoods\":[],\"pavements\":[],\"width\":6.3},{\"captionLines\":{},\"depth\":3.0,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"id\":2,\"label\":\"vilma\",\"leftChildBallValue\":2,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.64285713,\"positionX\":7.8,\"positionZ\":5.0,\"progression\":false,\"rightChildBallValue\":8,\"scale\":1.0}],\"name\":\"Indra Madrid\",\"neighborhoods\":[],\"pavements\":[],\"width\":3.0},{\"captionLines\":{},\"depth\":6.6,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":4,\"label\":\"asdf\",\"leftChildBallValue\":32,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.64285713,\"positionX\":0.0,\"positionZ\":5.0,\"progression\":true,\"rightChildBallValue\":-9,\"scale\":1.0},{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":1,\"label\":\"desglosa\",\"leftChildBallValue\":100,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":1.5,\"positionX\":3.3,\"positionZ\":5.0,\"progression\":true,\"rightChildBallValue\":400,\"scale\":1.0},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.54901963,\"r\":1.0},\"id\":3,\"label\":\"w2p\",\"leftChildBallValue\":21,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.85714287,\"positionX\":0.0,\"positionZ\":8.6,\"progression\":true,\"rightChildBallValue\":39,\"scale\":1.0}],\"name\":\"Factoría en Miguelturra\",\"neighborhoods\":[],\"pavements\":[],\"width\":6.3},{\"captionLines\":{},\"depth\":3.0,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"id\":2,\"label\":\"vilma\",\"leftChildBallValue\":2,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":0.64285713,\"positionX\":7.8,\"positionZ\":5.0,\"progression\":false,\"rightChildBallValue\":8,\"scale\":1.0}],\"name\":\"Factoría en Mirasierra\",\"neighborhoods\":[],\"pavements\":[],\"width\":3.0}],\"pavements\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":4.3333335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-2.0,\"positionZ\":-2.1666667,\"scale\":1.0,\"texture\":-1,\"title\":\"Indra Ciudad Real\",\"width\":7.3},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":4.3333335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":5.8,\"positionZ\":-2.1666667,\"scale\":1.0,\"texture\":-1,\"title\":\"Indra Madrid\",\"width\":4.0},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":7.9333334,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-2.0,\"positionZ\":2.8333333,\"scale\":1.0,\"texture\":-1,\"title\":\"Factoría en Miguelturra\",\"width\":7.3},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":7.9333334,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":5.8,\"positionZ\":2.8333333,\"scale\":1.0,\"texture\":-1,\"title\":\"Factoría en Mirasierra\",\"width\":4.0}]}");
//			// get project by id=1 group by company
//			//IGLFacadeImpl.getInstance().visualizeProjects("{\"captionLines\":{\"Arquitectura\":\"00649a\"},\"neighborhoods\":[{\"captionLines\":{},\"depth\":3.0,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":1,\"label\":\"desglosa\",\"leftChildBallValue\":100,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":1.5,\"positionX\":4.5,\"positionZ\":0.0,\"progression\":true,\"rightChildBallValue\":400,\"scale\":1.0}],\"name\":\"Indra\",\"neighborhoods\":[],\"pavements\":[],\"width\":3.0},{\"captionLines\":{},\"depth\":3.0,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"id\":1,\"label\":\"desglosa\",\"leftChildBallValue\":100,\"maxDepth\":3.0,\"maxWidth\":3.0,\"parentBallRadius\":1.5,\"positionX\":4.5,\"positionZ\":0.0,\"progression\":true,\"rightChildBallValue\":400,\"scale\":1.0}],\"name\":\"IECISA\",\"neighborhoods\":[],\"pavements\":[],\"width\":3.0}],\"pavements\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":4.3333335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-2.0,\"positionZ\":-2.1666667,\"scale\":1.0,\"texture\":-1,\"title\":\"Indra\",\"width\":4.0},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":4.3333335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":2.5,\"positionZ\":-2.1666667,\"scale\":1.0,\"texture\":-1,\"title\":\"IECISA\",\"width\":4.0}]}");
//		} catch (ViewManagerNotInstantiatedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		try {
//			Synchronizer.getInstance().solicitar();
//			// group by company
//			//IGLFacadeImpl.getInstance().visualizeFactories("{\"caption\":null,\"neighborhoods\":[{\"caption\":null,\"depth\":1.25,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":1,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":0.0,\"positionZ\":0.0,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":2},{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":2,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":2.8,\"positionZ\":0.0,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"smokestackHeight\":1}],\"name\":\"Indra\",\"neighborhoods\":[],\"pavements\":[],\"width\":5.3},{\"caption\":null,\"depth\":1.25,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":3,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":6.8,\"positionZ\":0.0,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":3},{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":4,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":9.6,\"positionZ\":0.0,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"smokestackHeight\":1}],\"name\":\"IECISA\",\"neighborhoods\":[],\"pavements\":[],\"width\":5.3}],\"pavements\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":2.5833335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-1.75,\"positionZ\":-1.2916667,\"scale\":1.0,\"texture\":-1,\"width\":6.3},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":2.5833335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":5.05,\"positionZ\":-1.2916667,\"scale\":1.0,\"texture\":-1,\"width\":6.3}]}");
//			// group by market
//			//IGLFacadeImpl.getInstance().visualizeFactories("{\"caption\":null,\"neighborhoods\":[{\"caption\":null,\"depth\":1.25,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":3,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":0.0,\"positionZ\":0.0,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":3},{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":1,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":2.8,\"positionZ\":0.0,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":2}],\"name\":\"Arquitectura\",\"neighborhoods\":[],\"pavements\":[],\"width\":5.3},{\"caption\":null,\"depth\":1.25,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":4,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":6.8,\"positionZ\":0.0,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"smokestackHeight\":1},{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":2,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":9.6,\"positionZ\":0.0,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"smokestackHeight\":1}],\"name\":\"Defensa\",\"neighborhoods\":[],\"pavements\":[],\"width\":5.3}],\"pavements\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":2.5833335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-1.75,\"positionZ\":-1.2916667,\"scale\":1.0,\"texture\":-1,\"width\":6.3},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":2.5833335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":5.05,\"positionZ\":-1.2916667,\"scale\":1.0,\"texture\":-1,\"width\":6.3}]}");
//			// group by project
//			//IGLFacadeImpl.getInstance().visualizeFactories("{\"caption\":null,\"neighborhoods\":[{\"caption\":null,\"depth\":1.25,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":3,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":4.0,\"positionZ\":3.25,\"scale\":0.75,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":3},{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":1,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":6.8,\"positionZ\":3.25,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":2}],\"name\":\"desglosa\",\"neighborhoods\":[],\"pavements\":[],\"width\":5.3},{\"caption\":null,\"depth\":1.25,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":4,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":6.8,\"positionZ\":0.0,\"scale\":1.25,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"smokestackHeight\":1},{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":2,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":9.6,\"positionZ\":0.0,\"scale\":1.25,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"smokestackHeight\":1}],\"name\":\"vilma\",\"neighborhoods\":[],\"pavements\":[],\"width\":5.3},{\"caption\":null,\"depth\":1.25,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":3,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":4.0,\"positionZ\":3.25,\"scale\":0.75,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":3}],\"name\":\"w2p\",\"neighborhoods\":[],\"pavements\":[],\"width\":2.5},{\"caption\":null,\"depth\":1.25,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":3,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":4.0,\"positionZ\":3.25,\"scale\":0.75,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":3},{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":1,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":6.8,\"positionZ\":3.25,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":2}],\"name\":\"asdf\",\"neighborhoods\":[],\"pavements\":[],\"width\":5.3}],\"pavements\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":2.5833335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-1.75,\"positionZ\":-1.2916667,\"scale\":1.0,\"texture\":-1,\"width\":6.3},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":2.5833335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":5.05,\"positionZ\":-1.2916667,\"scale\":1.0,\"texture\":-1,\"width\":6.3},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":2.5833335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-1.75,\"positionZ\":1.9583333,\"scale\":1.0,\"texture\":-1,\"width\":3.5},{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":2.5833335,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":2.25,\"positionZ\":1.9583333,\"scale\":1.0,\"texture\":-1,\"width\":6.3}]}");
//			// no group by
//			//IGLFacadeImpl.getInstance().visualizeFactories("{\"caption\":null,\"neighborhoods\":[{\"caption\":null,\"depth\":3.1,\"flats\":[{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":3,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":0.0,\"positionZ\":0.0,\"scale\":0.75,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":3},{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":4,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":2.8,\"positionZ\":0.0,\"scale\":1.25,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"smokestackHeight\":1},{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":1,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":0.0,\"positionZ\":1.85,\"scale\":1.0,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0.0},\"smokestackHeight\":2},{\"color\":{\"alpha\":1.0,\"b\":1.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1.0,\"r\":1.0},\"id\":2,\"maxDepth\":1.25,\"maxWidth\":2.5,\"positionX\":2.8,\"positionZ\":1.85,\"scale\":1.25,\"smokestackColor\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"smokestackHeight\":1}],\"name\":\"\",\"neighborhoods\":[],\"pavements\":[],\"width\":5.3}],\"pavements\":[{\"color\":{\"alpha\":1.0,\"b\":0.0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.0,\"r\":0.0},\"depth\":4.4333334,\"id\":-1,\"maxDepth\":0.0,\"maxWidth\":0.0,\"positionX\":-1.75,\"positionZ\":-1.2916667,\"scale\":1.0,\"texture\":-1,\"width\":6.3}]}");
//		} catch (ViewManagerNotInstantiatedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.err.println("GearsApplet: start() - end");
	}

	@Override
	public void stop() {
		System.err.println("GearsApplet: stop() - begin");
		animator.stop();
		System.err.println("GearsApplet: stop() - end");
	}

	public void destroy() {
		System.err.println("GearsApplet: destroy() - X");
	}

	@Override
	public void selectProject(int id, int clickCount){
		this.handleEvent(EOperationCodes.ProjectSelection, id, clickCount);
	}

	@Override
	public void selectFactory(int id, int clickCount){
		this.handleEvent(EOperationCodes.FactorySelection, id, clickCount);
	}

	@Override
	public void selectTower(int id, int clickCount){
		this.handleEvent(EOperationCodes.TowerSelection, id, clickCount);
	}
	
	private void handleEvent (EOperationCodes opCode, Object ob, int clickCount) {
		try {
			switch (opCode) {
			case TowerSelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectTower(" + id + "," + clickCount +")"));
				}
				break;
			case FactorySelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectFactory(" + id + "," + clickCount +")"));
				}
				break;
			case ProjectSelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectProject(" + id + "," + clickCount +")"));
				}
				break;
			default:
				getAppletContext().showDocument(new URL("javascript:alert(\'Undefined operation\')"));
				break;
			}
		} catch (MalformedURLException e) {
			try {
				getAppletContext().showDocument(new URL("javascript:alert(\'" + e.toString() + "\')"));
			} catch (MalformedURLException e1) {}
		}
	}

	@Override
	public void visualizeFactories(String JSONtext) throws ViewManagerNotInstantiatedException {
		Synchronizer.getInstance().solicitar();
		IGLFacadeImpl.getInstance().visualizeFactories(JSONtext);
	}

	@Override
	public void visualizeProjects(String JSONtext) throws ViewManagerNotInstantiatedException {
		Synchronizer.getInstance().solicitar();
		IGLFacadeImpl.getInstance().visualizeProjects(JSONtext);
	}

	@Override
	public void visualizeTowers(String JSONtext) throws ViewManagerNotInstantiatedException {
		Synchronizer.getInstance().solicitar();
		IGLFacadeImpl.getInstance().visualizeTowers(JSONtext);
	}
	
}
