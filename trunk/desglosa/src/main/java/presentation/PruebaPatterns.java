package presentation;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PruebaPatterns {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Matcher matcher;
		String foo = "src/main/resources/anonymous.jpg";
		foo = "c:\\hola\\quetal\\feo.bmp";
//		Pattern pathPattern = Pattern.compile("([a-zA-Z]:\\\\)(([a-zA-Z0-9]+\\\\)*[a-zA-Z0-9]+)?.(bmp|gif|jpg|jpeg|png)");
		Pattern pathPattern = Pattern.compile("([a-zA-Z]:(\\\\|/))?(([a-zA-Z0-9]+(\\\\|/))*[a-zA-Z0-9]+)?.(bmp|gif|jpg|jpeg|png)");
		matcher = pathPattern.matcher(foo);
	    if(matcher.matches()) {
	    	System.err.println("true");
	    } else {
	    	System.err.println("false");
	    }
	}

}
