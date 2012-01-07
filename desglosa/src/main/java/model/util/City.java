package model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLPavement;

public class City {
    private final float X_GAP = 2.5f;
    private final float Y_GAP = 2.5f;

    private String model;
    private List<Neighborhood> neighborhoods;
    private List<GLObject> pavements;
    private Map<String, String> captionLines;
    private Map<String, Object> ratios;

    protected int cols;
    protected int rows;
    protected Vector2f placePoint;

    public City() {
        this.model = "";
        this.neighborhoods = new ArrayList<Neighborhood>();
        this.ratios = new HashMap<String, Object>();
        this.cols = 0;
        this.rows = 0;
        this.captionLines = new HashMap<String, String>();
        placePoint = new Vector2f(0.0f, 0.0f);
        pavements = new ArrayList<GLObject>();
    }

    public City(List<Neighborhood> neighborhoods) {
        this();
        this.neighborhoods = neighborhoods;
        calculateRowsAndCols();
    }

    protected void calculateRowsAndCols() {
        if (neighborhoods != null) {
            if (neighborhoods.size() > 0) {
                this.cols = calculateCols(neighborhoods.size());
                if (neighborhoods.size() % this.cols == 0)
                    this.rows = neighborhoods.size() / this.cols;
                else
                    this.rows = (neighborhoods.size() / this.cols + 1);
            }
        }
    }

    protected int calculateCols(double d) {
        return (Math.sqrt(d) > (double) ((int) (Math.sqrt(d))) ? (int) (Math
                .sqrt(d)) + 1 : (int) (Math.sqrt(d)));
    }

    public void placeNeighborhoods(float maxHeight) {
        calculateRowsAndCols();
        // Once we know how many columns and rows it will have, we can calculate
        // a position for each flat
        float x = placePoint.getX();
        Vector2f dimensions = null;
        double maxDepth = 0.0f;
        for (int i = 0; i < this.rows; i++) {
            maxDepth = 0.0f;
            for (int j = 0; j < this.cols; j++) {
                int index = i * this.cols + j;
                if (neighborhoods.size() > index) {
                    // Configure Pavement
                    GLPavement pavement = new GLPavement();
                    pavement.setPositionX(placePoint.getX()
                            - neighborhoods.get(0).getFlats().get(0)
                                    .getMaxWidth() / 2 - this.X_GAP / 3);
                    pavement.setPositionZ(placePoint.getY()
                            - neighborhoods.get(0).getFlats().get(0)
                                    .getMaxDepth() / 2 - this.Y_GAP / 3);
                    // The neighborhood place its flats by itself
                    dimensions = neighborhoods.get(index).doLayout(placePoint);
                    placePoint.setX(dimensions.getX() + this.X_GAP);
                    if (maxDepth < dimensions.getY())
                        maxDepth = dimensions.getY();
                    // Continue configuring pavement
                    pavement.setWidth(dimensions.getX() + 2 * this.X_GAP / 3);
                    pavement.setDepth((float) maxDepth + 2 * this.Y_GAP / 3);
                    pavement.setTitle(neighborhoods.get(index).getName());
                    pavement.setTitleHeight(maxHeight + GLPavement.TITLE_GAP);
                    pavements.add(pavement);
                }
            }
            placePoint.setX(x);
            if (dimensions != null)
                placePoint.setY((float) maxDepth + this.Y_GAP);
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Neighborhood> getNeighborhoods() {
        return neighborhoods;
    }

    public List<GLObject> getPavements() {
        return pavements;
    }

    public Map<String, String> getCaptionLines() {
        return captionLines;
    }

    public void setCaptionLines(Map<String, String> captionLines) {
        this.captionLines = captionLines;
    }

    public void setPavements(List<GLObject> pavements) {
        this.pavements = pavements;
    }

    public Map<String, Object> getRatios() {
        return ratios;
    }

    public void setRatios(Map<String, Object> ratios) {
        this.ratios = ratios;
    }
    

}
