package com.vsu.cgcourse.math;

public class Vector2 extends AbstractVector<Vector2> {

    public Vector2() {
        super(2);
    }

    public Vector2(float[] vectorCoords) {
        super(vectorCoords);
    }

    @Override
    protected AbstractVector<Vector2> createVector() {
        return new Vector2();
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

    @Override
    public String toString() {
        return Float.toString(getX()) + " " + Float.toString(getY());
    }
}
