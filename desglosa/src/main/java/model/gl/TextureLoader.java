package model.gl;

import java.io.IOException;

import javax.media.opengl.GL2;

import persistence.TextureReader;
import model.gl.knowledge.GLTexture;
import exceptions.GLSingletonNotInitializedException;

public class TextureLoader {
	private String tileNames[];
	private int textureNames[];
	private GLTexture textures[];
	private boolean texturesLoaded;
	
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
		textures = new GLTexture[names.length];
		this.texturesLoaded = false;
	}
	
    private int[] genTextures(int amount) throws GLSingletonNotInitializedException {
        final int[] tmp = new int[amount];
        GLSingleton.getGL().glGenTextures(amount, tmp, 0);
        return tmp;
    }
    
    private void makeRGBTexture(GLTexture img, int target, boolean mipmapped) throws GLSingletonNotInitializedException {
        if (mipmapped) {
        	GLSingleton.getGLU().gluBuild2DMipmaps(target, GL2.GL_RGBA, img.getWidth(), 
                    img.getHeight(), GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, img.getPixels());
        } else {
        	GLSingleton.getGL().glTexImage2D(target, 0, GL2.GL_RGB8, img.getWidth(), 
                    img.getHeight(), 0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, img.getPixels());
        }
    }
	
	public void loadTexures (boolean useAlphaChannel, boolean useMipmaps, boolean linearFiltering, boolean isSkyBox) throws GLSingletonNotInitializedException, IOException {
		if (textureNames.length > 0) {
			textureNames = this.genTextures(textureNames.length);
			
			for (int i = 0; i < textureNames.length; i++) {
	            textures[i] = TextureReader.readTexture(tileNames[i], useAlphaChannel);
	            //Create Nearest Filtered Texture
	            GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D, textureNames[i]);
	
	            if (isSkyBox) {
	            	//GLSingleton.getGL().glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
	            	//GLSingleton.getGL().glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
	            	GLSingleton.getGL().glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
	            	GLSingleton.getGL().glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
	                makeRGBTexture(textures[i], GL2.GL_TEXTURE_2D, true);
	            }
	            else {
		            if (useMipmaps) {
		            	GLSingleton.getGL().glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
		            	GLSingleton.getGL().glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
		            }
		            else {
		                if (linearFiltering) {
		                    GLSingleton.getGL().glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		                    GLSingleton.getGL().glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		                } else {
		                    GLSingleton.getGL().glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
		                    GLSingleton.getGL().glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
		                }
		            }
		            makeRGBTexture(textures[i], GL2.GL_TEXTURE_2D, useMipmaps);
	            }
	        }
			
			this.texturesLoaded = true;
		}
	}

	public GLTexture[] getTextures() {
		return textures;
	}

	public void setTextures(GLTexture[] textures) {
		this.textures = textures;
	}

	public boolean isTexturesLoaded() {
		return texturesLoaded;
	}

	public void setTexturesLoaded(boolean texturesLoaded) {
		this.texturesLoaded = texturesLoaded;
	}
	
}
