package model;

import java.io.IOException;

import javax.media.opengl.GL;

import persistency.TextureReader;
import model.gl.GLSingleton;
import model.gl.knowledge.Texture;
import exceptions.gl.GLSingletonNotInitializedException;

public class TextureLoader {
	private String tileNames[];
	private int textures[];
	
	public String[] getTileNames() {
		return tileNames;
	}

	public void setTileNames(String[] tileNames) {
		this.tileNames = tileNames;
	}

	public int[] getTextures() {
		return textures;
	}

	public void setTextures(int[] textures) {
		this.textures = textures;
	}

	public TextureLoader (String [] names) {
		this.tileNames = names;
		textures = new int[names.length];
	}
	
    private int[] genTextures(int amount) throws GLSingletonNotInitializedException {
        final int[] tmp = new int[amount];
        GLSingleton.getGL().glGenTextures(amount, tmp, 0);
        return tmp;
    }
    
    private void makeRGBTexture(Texture img, int target, boolean mipmapped) throws GLSingletonNotInitializedException {
        if (mipmapped) {
        	GLSingleton.getGLU().gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), 
                    img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        } else {
        	GLSingleton.getGL().glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), 
                    img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        }
    }
	
	public void loadTexures () throws GLSingletonNotInitializedException, IOException {	
		textures = this.genTextures(textures.length);
		
		for (int i = 0; i < textures.length; i++) {
            Texture texture;
            texture = TextureReader.readTexture(tileNames[i]);
            //Create Nearest Filtered Texture
            GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

            makeRGBTexture(texture, GL.GL_TEXTURE_2D, false);
            
            GLSingleton.getGL().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            GLSingleton.getGL().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);

            GLSingleton.getGL().glTexImage2D(GL.GL_TEXTURE_2D,
                    0,
                    3,
                    texture.getWidth(),
                    texture.getHeight(),
                    0,
                    GL.GL_RGB,
                    GL.GL_UNSIGNED_BYTE,
                    texture.getPixels());
        }
	}
}
