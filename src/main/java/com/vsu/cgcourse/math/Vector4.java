package com.vsu.cgcourse.math;

public class Vector4 extends AbstractVector<Vector4> {

    public Vector4() {
        super(4);
    }

    public Vector4(float[] vectorCoords) {
        super(vectorCoords);
    }

    @Override
    protected AbstractVector<Vector4> createVector() {
        return new Vector4();
    }

    public float getX() {
        return this.getVectorCoords()[0];
    }

    public void setX(float x) {
        this.getVectorCoords()[0] = x;
    }

    public float getY() {
        return this.getVectorCoords()[1];
    }

    public void setY(float y) {
        this.getVectorCoords()[1] = y;
    }

    public float getZ() {
        return this.getVectorCoords()[2];
    }

    public void setZ(float z) {
        this.getVectorCoords()[2] = z;
    }

    public float getW() {
        return this.getVectorCoords()[3];
    }

    public void setW(float w) {
        this.getVectorCoords()[3] = w;
    }

}
