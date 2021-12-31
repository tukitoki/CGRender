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
        return this.getVectorCord()[0];
    }

    public void setX(float x) {
        this.getVectorCord()[0] = x;
    }

    public float getY() {
        return this.getVectorCord()[1];
    }

    public void setY(float y) {
        this.getVectorCord()[1] = y;
    }

    public float getZ() {
        return this.getVectorCord()[2];
    }

    public void setZ(float z) {
        this.getVectorCord()[2] = z;
    }

    public float getW() {
        return this.getVectorCord()[3];
    }

    public void setW(float w) {
        this.getVectorCord()[3] = w;
    }

}
