package model.gl.knowledge.caption;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.GLObject;
import model.util.Color;
import model.util.Vector3f;

import exceptions.GLSingletonNotInitializedException;

public class Caption extends GLObject {

    private final int pxGAP = 10; // px
    private List<Line> lines;
    private Frame frame;
    private float width;
    private float height;

    public Caption() {
        this(0.0f, 0.0f);
    }

    public Caption(float pos_x, float pos_y) {
        this.positionX = pos_x;
        this.positionY = pos_y;
        this.lines = new ArrayList<Line>();
        this.width = 0.0f;
        this.height = 0.0f;
    }

    public void addLine(Color c, String t) {
        Line l = new Line();
        l.getIcon().setColor(c);
        l.getText().setText(t);
        lines.add(l);
    }

    /**
     * @param lines
     *            A Map which keys are the caption line label and values are the
     *            icon color
     */
    public void addLines(Map<String, String> lines) {
        // Configure caption lines
        Iterator it = lines.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            this.addLine(new Color((String) pairs.getValue()),
                    (String) pairs.getKey());
        }
    }

    public void draw() throws GLSingletonNotInitializedException {
        // Calculamos el tamano de la leyenda en funcion del numero de lineas y
        // la longitud de la mas larga
        int num_lines = lines.size();
        int max_length = 0;
        for (Line l : lines) {
            int aux = l.getLengthPX();
            if (aux > max_length)
                max_length = aux;
        }

        if (num_lines > 0) {
            width = this.pxGAP * 2 + max_length;
            frame = new Frame((int) Math.ceil(width),
                    (int) Math.ceil((num_lines + 1) * this.pxGAP + num_lines
                            * lines.get(0).getHeightPX()));
        }

        GLSingleton.getGL().glPushMatrix();
        GLSingleton.getGL().glTranslatef(this.positionX, this.positionY, 0.0f);
        frame.draw();
        GLSingleton.getGL().glPopMatrix();

        GLSingleton.getGL().glPushMatrix();
        GLSingleton.getGL().glLoadIdentity();
        Vector3f oglGAP = GLUtils.getScreen2World(this.pxGAP, this.pxGAP, true);
        float vgap = GLUtils.getScreen2World(0, 20, true).getY();
        GLSingleton.getGL().glPopMatrix();
        GLSingleton.getGL().glPushMatrix();
        GLSingleton.getGL().glTranslatef(this.positionX + oglGAP.getX(),
                this.positionY - oglGAP.getY() * 2, 0.0f);
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0)
                GLSingleton.getGL().glTranslatef(0.0f, 0.0f, 0.0f);
            else
                GLSingleton.getGL().glTranslatef(0.0f, -vgap, 0.0f);
            lines.get(i).draw();
        }
        GLSingleton.getGL().glPopMatrix();
    }

    public List<Line> getLines() {
        return lines;
    }

    public float getHeight() {
        try {
            int aux = (int) Math.ceil((lines.size() + 1) * this.pxGAP
                    + lines.size() * lines.get(0).getHeightPX());
            Vector3f v = GLUtils.getScreen2World(0, aux, true);
            height = v.getY();
        } catch (GLSingletonNotInitializedException e) {
            height = 0.0f;
        }
        return height;
    }

    public float getWidth() {
        return this.width;
    }

}
