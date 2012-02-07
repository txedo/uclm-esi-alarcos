package model.gl.control;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.media.opengl.GL2;

import exceptions.GLSingletonNotInitializedException;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.TextureLoader;

public class GLFontBuilder {
    /**
     * A handle to the unique Singleton instance.
     */
    static private GLFontBuilder _instance = null;
    static private TextureLoader textureLoader = null;
    static private int base;
    static private final int FONTS = 256;
    static private final float LETTER_GAP = 0.5f;

    /**
     * The constructor could be made private to prevent others from
     * instantiating this class. But this would also make it impossible to
     * create instances of Singleton subclasses.
     * 
     * @throws IOException
     * @throws GLSingletonNotInitializedException
     */
    protected GLFontBuilder() throws GLSingletonNotInitializedException,
            IOException {
        textureLoader = new TextureLoader(new String[] { "textures/Font.bmp" });
        textureLoader.loadTexures(true, true, true, false); // Load and Bind Our
                                                            // Font Texture
    }

    /**
     * @return The unique instance of this class.
     * @throws IOException
     * @throws GLSingletonNotInitializedException
     */
    static public GLFontBuilder getInstance()
            throws GLSingletonNotInitializedException, IOException {
        if (null == _instance) {
            _instance = new GLFontBuilder();
        }
        return _instance;
    }

    public void buildFont() throws GLSingletonNotInitializedException {
        GLUtils.enableMultisample();
        GLSingleton.getGL().glEnable(GL2.GL_TEXTURE_2D);
        base = GLSingleton.getGL().glGenLists(FONTS);// Creating 256 Display
                                                     // Lists
        GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D,
                textureLoader.getTextureNames()[0]);

        for (int loop = 0; loop < FONTS; loop++) // Loop Through All 95 Lists
        {
            float cx = (float) (loop % 16) / 16.0f; // X Position Of Current
                                                    // Character
            float cy = (float) (loop / 16) / 16.0f; // Y Position Of Current
                                                    // Character

            GLSingleton.getGL().glNewList(base + loop, GL2.GL_COMPILE);
            GLSingleton.getGL().glBegin(GL2.GL_QUADS);
            GLSingleton.getGL().glTexCoord2f(cx, 1 - cy - 0.0625f); // Texture
                                                                    // Coord
                                                                    // (Bottom
                                                                    // Left)
            GLSingleton.getGL().glVertex2f(0, 0); // Vertex Coord (Bottom Left)
            GLSingleton.getGL().glTexCoord2f(cx + 0.0625f, 1 - cy - 0.0625f); // Texture
                                                                              // Coord
                                                                              // (Bottom
                                                                              // Right)
            GLSingleton.getGL().glVertex2f(LETTER_GAP, 0); // Vertex Coord
                                                           // (Bottom Right)
            GLSingleton.getGL().glTexCoord2f(cx + 0.0625f, 1 - cy); // Texture
                                                                    // Coord
                                                                    // (Top
                                                                    // Right)
            GLSingleton.getGL().glVertex2f(LETTER_GAP, LETTER_GAP); // Vertex
                                                                    // Coord
                                                                    // (Top
                                                                    // Right)
            GLSingleton.getGL().glTexCoord2f(cx, 1 - cy); // Texture Coord (Top
                                                          // Left)
            GLSingleton.getGL().glVertex2f(0, LETTER_GAP); // Vertex Coord (Top
                                                           // Left)
            GLSingleton.getGL().glEnd(); // Done Building Our Quad (Character)
            GLSingleton.getGL().glTranslatef(LETTER_GAP, 0, 0); // Move To The
                                                                // Right Of The
                                                                // Character
            GLSingleton.getGL().glEndList();
        } // Loop Until All 256 Are Built
        GLSingleton.getGL().glDisable(GL2.GL_TEXTURE_2D);
        GLUtils.disableMultisample();
    }

    public void killFont() throws GLSingletonNotInitializedException // Delete
                                                                     // The Font
                                                                     // From
                                                                     // Memory
    {
        GLSingleton.getGL().glDeleteLists(base, FONTS); // Delete All 256
                                                        // Display Lists
    }

    public void glPrint(float x, float y, String text, int set, boolean centered)
            throws GLSingletonNotInitializedException // Where The Printing
                                                      // Happens
    {
        if (set > 1)
            set = 1;
        if (centered)
            x = text.length() / 2 * LETTER_GAP;

        GLSingleton.getGL().glEnable(GL2.GL_TEXTURE_2D);
        GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D,
                textureLoader.getTextureNames()[0]);
        // GLSingleton.getGL().glTexEnvf(GL2.GL_TEXTURE_ENV,
        // GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
        GLSingleton.getGL().glPushMatrix();
        GLSingleton.getGL().glTranslated(-x, y, 0);
        GLSingleton.getGL().glListBase(base - 32 + (128 * set));
        GLSingleton.getGL().glCallLists(text.length(), GL2.GL_UNSIGNED_BYTE,
                ByteBuffer.wrap(text.getBytes()));
        GLSingleton.getGL().glPopMatrix();
        // GLSingleton.getGL().glTexEnvf(GL2.GL_TEXTURE_ENV,
        // GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
        GLSingleton.getGL().glDisable(GL2.GL_TEXTURE_2D);
    }

}
