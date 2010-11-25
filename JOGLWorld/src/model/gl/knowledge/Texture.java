package model.gl.knowledge;

import java.nio.ByteBuffer;

import exceptions.gl.GLSingletonNotInitializedException;

import model.gl.GLObject;

public class Texture extends GLObject {
	
    private ByteBuffer pixels;
    private int width;
    private int height;

    public Texture(ByteBuffer pixels, int width, int height) {
        this.height = height;
        this.pixels = pixels;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

	@Override
	public void draw() throws GLSingletonNotInitializedException {
		// TODO Auto-generated method stub
		
	}


}
