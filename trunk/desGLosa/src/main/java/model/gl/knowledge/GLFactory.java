package model.gl.knowledge;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;

import com.sun.opengl.util.BufferUtil;

import model.gl.GLSingleton;
import model.knowledge.Color;
import exceptions.gl.GLSingletonNotInitializedException;

public class GLFactory extends GLObject {
	// We define the base dimensions
	private float baseLength;
	private float baseWidth;
	private float baseHeight;
	// Smokestack radius and building length will depend on base dimensions
	private int smokestackHeight;
	private float smokestackRadius;
	private float buildingHeight;
	// Roof height will be 1/3 of building height
	private float roofHeight;
	private final float ROOFANGLE = 45.0f;
	
	FloatBuffer normals;
	FloatBuffer vertices;
	FloatBuffer colors;
	IntBuffer indices;
	
	float vNormals[] = { 0,0,1 , 0,0,1 , 0,0,1 , 0,0,1 , 0,0,1 , 0,0,1 , 0,0,1 , 0,0,1 , 0,0,1 , 0,0,1};// , 
			//0,0,-1 , 0,0,-1 , 0,0,-1 , 0,0,-1 , 0,0,-1 , 0,0,-1 , 0,0,-1 , 0,0,-1 , 0,0,-1 , 0,0,-1};
	float vVertices[] = { -1.0f,0.0f,0.5f , -1.0f,0.4f,0.5f , 0.0f,0.4f,0.5f , 0.0f,1.6f,0.5f , 0.33f,2.0f,0.5f , 0.33f,1.6f,0.5f , 0.67f,2.0f,0.5f , 0.67f,1.6f,0.5f , 1.0f,2.0f,0.5f , 1.0f,0.0f,0.5f};// , 
			//-1,0,-0.5f , 1,0,-0.5f , 1,2.5f,-0.5f , 0.67f,2,-0.5f , 0.67f,2.5f,-0.5f , 0.33f,2,-0.5f , 0.33f,2.5f,-0.5f , 0,2,-0.5f , 0,0.3f,-0.5f , -1,0.3f,-0.5f};
	int vIndices[] = { 0,1,2,3,9 };// , 
			//10,11,12,13,14,15,16,17,18,19};
	
	
	public GLFactory (float pos_x, float pos_y) {
		this.positionX = pos_x;
		this.positionY = pos_y;
		this.color = new Color(0.0f, 0.0f, 1.0f, 1.0f);
		
		this.baseLength = 2.0f;
		this.baseWidth = 1.0f;
		this.baseHeight = 0.5f;
		
		this.smokestackHeight = 0;
		if (this.baseLength/2 > this.baseWidth)	this.smokestackRadius = this.baseWidth/2;
		else this.smokestackRadius = this.baseLength/2;
		
		this.buildingHeight = 3.0f;
		this.roofHeight = this.buildingHeight/3;
		
		this.normals = BufferUtil.newFloatBuffer(vNormals.length);
		for (int i = 0; i < vNormals.length; i++) {
			this.normals.put(vNormals[i]);
		}
		this.normals.rewind();

		this.vertices = BufferUtil.newFloatBuffer(vVertices.length);
		for (int i = 0; i < vVertices.length; i++) {
			this.vertices.put(vVertices[i]);
		}
		this.vertices.rewind();
		
		this.indices = BufferUtil.newIntBuffer(vIndices.length);
		for (int i = 0; i < vIndices.length; i++) {
			this.indices.put(vIndices[i]);
		}
		this.indices.rewind();
	}
	
	@Override
	public void draw() throws GLSingletonNotInitializedException {
		// Enable and specificy pointers to vertex arrays
		GLSingleton.getGL().glEnableClientState(GL.GL_NORMAL_ARRAY);
		GLSingleton.getGL().glEnableClientState(GL.GL_VERTEX_ARRAY);
		
		GLSingleton.getGL().glNormalPointer(GL.GL_FLOAT, 0, normals);
		GLSingleton.getGL().glVertexPointer(3, GL.GL_FLOAT, 0, vertices);
		
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glColor4fv(this.color.getColorFB());
			GLSingleton.getGL().glTranslatef(this.positionX, 1.0f, this.positionY);
			GLSingleton.getGL().glDrawElements(GL.GL_POLYGON, 5, GL.GL_UNSIGNED_INT, indices);
			//GLSingleton.getGL().glDrawRangeElements(GL.GL_POLYGON, 10, 19, 10, GL.GL_UNSIGNED_INT, indices);
		GLSingleton.getGL().glPopMatrix();
		
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glColor4fv(this.color.getColorFB());
			GLSingleton.getGL().glTranslatef(0.0f, 1.0f, 0.0f);
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
			GLSingleton.getGL().glBegin(GL.GL_POLYGON);
//				GLSingleton.getGL().glVertex3f(-1.0f,0.0f,0.5f);
//				GLSingleton.getGL().glVertex3f(1.0f,0.0f,0.5f);
//				GLSingleton.getGL().glVertex3f(0.0f,1.6f,0.5f);
//				GLSingleton.getGL().glVertex3f(0.0f,0.4f,0.5f);
//				GLSingleton.getGL().glVertex3f(-1.0f,0.4f,0.5f);
				GLSingleton.getGL().glVertex3f(1.0f,0.0f,0.5f);
				GLSingleton.getGL().glVertex3f(1.0f,2.0f,0.5f);
				GLSingleton.getGL().glVertex3f(0.66f,1.6f,0.5f);
				GLSingleton.getGL().glVertex3f(0.66f,2.0f,0.5f);
				GLSingleton.getGL().glVertex3f(0.33f,1.6f,0.5f);
				GLSingleton.getGL().glVertex3f(0.33f,2.0f,0.5f);
				GLSingleton.getGL().glVertex3f(0.0f,1.6f,0.5f);
				GLSingleton.getGL().glVertex3f(0.0f,0.4f,0.5f);
				GLSingleton.getGL().glVertex3f(-1.0f,0.4f,0.5f);
				GLSingleton.getGL().glVertex3f(-1.0f,0.0f,0.5f);
			GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glPopMatrix();
		
		GLSingleton.getGL().glDisableClientState(GL.GL_VERTEX_ARRAY);
		GLSingleton.getGL().glDisableClientState(GL.GL_NORMAL_ARRAY);
	}
	
	private void drawBase() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glBegin(GL.GL_QUADS);
			// Back
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, -1.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, this.baseHeight, 0.0f);
			GLSingleton.getGL().glVertex3f(this.baseLength, this.baseHeight, 0.0f);
			GLSingleton.getGL().glVertex3f(this.baseLength, 0.0f, 0.0f);
			// Right
			GLSingleton.getGL().glNormal3f(1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, this.baseHeight, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, this.baseHeight, this.baseWidth);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, this.baseWidth);
			// Front
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			// Left
			GLSingleton.getGL().glNormal3f(-1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			// Top
			GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
		GLSingleton.getGL().glEnd();
	}
	
	private void drawBuilding() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glBegin(GL.GL_QUADS);
			// Front
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			// Right
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			// Back
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			// Left
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
		GLSingleton.getGL().glEnd();
	}
	
	private void drawBuildingRoof() throws GLSingletonNotInitializedException {
		
	}
	
	private void drawSmokestack() throws GLSingletonNotInitializedException {
		
	}

	public int getSmokestackHeight() {
		return smokestackHeight;
	}

	public void setSmokestackHeight(int smokestackHeight) {
		this.smokestackHeight = smokestackHeight;
	}

}
