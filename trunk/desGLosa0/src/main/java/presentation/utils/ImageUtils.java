package presentation.utils;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageUtils {
	public static ImageIcon readIcon (String path) {
		return readIcon(path, 64, 64);
	}
	
	public static ImageIcon readIcon (String path, int width, int height) {
		ImageIcon originalImage = new ImageIcon(path);
		Image img = originalImage.getImage();
		Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(scaledImg);
	}
}
