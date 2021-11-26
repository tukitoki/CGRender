package com.vsu.cgcourse.math;

public class Vector3 extends AbstractVector<Vector3> {

    public Vector3() {
        super(3);
    }

    public Vector3(float[] vectorCoords) {
        super(vectorCoords);
    }

    public Vector3(Vector3 vector) throws Exception {
        super(vector.getVectorCoords());
        if (vector.getVectorCoords().length != 3) {
            throw new Exception("Vector must have size = 3");
        }
    }

    @Override
    protected AbstractVector<Vector3> createVector() {
        return new Vector3();
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
    } // 1*2 - 2*1 // 2*0-0*2 // 0*1-1*0

    public void vectorMultiply(Vector3 secondVector) {
        float x = this.getY() * secondVector.getZ() - this.getZ() * secondVector.getY();
        float y = this.getZ() * secondVector.getX() + this.getX() * secondVector.getZ();
        this.getVectorCoords()[2] = this.getX() * secondVector.getY() - this.getY() * secondVector.getX();
        this.getVectorCoords()[0] = x;
        this.getVectorCoords()[1] = y;
    }
}
