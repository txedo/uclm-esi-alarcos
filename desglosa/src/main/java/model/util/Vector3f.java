package model.util;

public class Vector3f {
    private float x;
    private float y;
    private float z;

    public Vector3f() {
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getLength() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z
                * this.z);
    }

    public Vector3f getNormalizedVector() {
        float length = this.getLength();
        Vector3f res;
        if (length == 0)
            res = new Vector3f(0.0f, 0.0f, 0.0f);
        else {
            res = new Vector3f();
            res.x = this.x / length;
            res.y = this.y / length;
            res.z = this.z / length;
        }

        return res;
    }

    public void normalize() {
        Vector3f aux = this.getNormalizedVector();
        this.x = aux.x;
        this.y = aux.y;
        this.z = aux.z;
    }

    public Vector3f add(Vector3f v) {
        Vector3f res = new Vector3f();
        res.x = this.x + v.x;
        res.y = this.y + v.y;
        res.z = this.z + v.z;
        return res;
    }

    public Vector3f sub(Vector3f v) {
        Vector3f res = new Vector3f();
        res.x = this.x - v.x;
        res.y = this.y - v.y;
        res.z = this.z - v.z;
        return res;
    }

    public Vector3f mult(float r) {
        Vector3f res = new Vector3f();
        res.x = this.x * r;
        res.y = this.y * r;
        res.z = this.z * r;
        return res;
    }

    public float dot(Vector3f v) {
        // Calcula el angulo entre dos vectores
        // http://en.wikipedia.org/wiki/Dot_product
        // If both a and b have length one (i.e., they are unit vectors), their
        // dot product simply gives the cosine of the angle between them.
        // If only b is a unit vector, then the dot product a [dot] b gives |a|
        // cos(fi), i.e., the magnitude of the projection of a in the direction
        // of b, with a minus sign if the direction is opposite. This is called
        // the scalar projection of a onto b, or scalar component of a in the
        // direction of b (see figure). This property of the dot product has
        // several useful applications (for instance, see next section).
        // If neither a nor b is a unit vector, then the magnitude of the
        // projection of a in the direction of b, for example, would be a [dot]
        // (b / |b|) as the unit vector in the direction of b is b / |b|.
        return (this.x * v.x + this.y * v.y + this.z * v.z);
    }

    public Vector3f cross(Vector3f v) {
        // Calcula el vector perpendicular a dos vectores
        Vector3f res = new Vector3f();
        res.x = this.y * v.z - this.z * v.y;
        res.y = this.z * v.x - this.x * v.z;
        res.z = this.x * v.y - this.y * v.x;
        return res;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public String toString() {
        String res = "(" + this.x + "," + this.y + "," + this.z + ")";
        return res;
    }

    public float[] toArray() {
        float res[] = new float[3];
        res[0] = this.x;
        res[1] = this.y;
        res[2] = this.z;
        return res;
    }

    @Override
    public Vector3f clone() {
        return new Vector3f(this.x, this.y, this.z);
    }

}
