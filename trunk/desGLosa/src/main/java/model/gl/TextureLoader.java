package model.gl;

import java.io.IOException;

import javax.media.opengl.GL;

import persistence.TextureReader;
import model.gl.knowledge.Texture;
import exceptions.gl.GLSingletonNotInitializedException;

public class TextureLoader {
	private String tileNames[];
	private int textureNames[];
	private Texture textures[];
	
	public String[] getTileNames() {
		return tileNames;
	}

	public void setTileNames(String[] tileNames) {
		this.tileNames = tileNames;
	}

	public int[] getTextureNames() {
		return textureNames;
	}

	public void setTextureNames(int[] textures) {
		this.textureNames = textures;
	}

	public TextureLoader (String [] names) {
		this.tileNames = names;
		textureNames = new int[names.length];
		textures = new Texture[names.length];
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
		textureNames = this.genTextures(textureNames.length);
		
		for (int i = 0; i < textureNames.length; i++) {
            textures[i] = TextureReader.readTexture(tileNames[i], false);
            //Create Nearest Filtered Texture
            GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, textureNames[i]);

            makeRGBTexture(textures[i], GL.GL_TEXTURE_2D, false);
            
            GLSingleton.getGL().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            GLSingleton.getGL().glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        }
	}

	public Texture[] getTextures() {
		return textures;
	}

	public void setTextures(Texture[] textures) {
		this.textures = textures;
	}
	
}
