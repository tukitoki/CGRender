package com.vsu.cgcourse.math;

public class Vector3 extends AbstractVector<Vector3> {

    public Vector3() {
        super(3);
    }

    public Vector3(float[] vectorCoords) {
        super(vectorCoords);
    }

    public Vector3(Vector3 vector) throws Exception {
        super(vector.getVectorCord());
        if (vector.getVectorCord().length != 3) {
            throw new Exception("Vector must have size = 3");
        }
    }

    @Override
    protected AbstractVector<Vector3> createVector() {
        return new Vector3();
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
    } // 1*2 - 2*1 // 2*0-0*2 // 0*1-1*0

    public void vectorMul(Vector3 secondVector) {
        float x = this.getY() * secondVector.getZ() - this.getZ() * secondVector.getY();
        float y = -this.getX() * secondVector.getZ() + this.getZ() * secondVector.getX();
        this.getVectorCord()[2] = this.getX() * secondVector.getY() - this.getY() * secondVector.getX();
        this.getVectorCord()[0] = x;
        this.getVectorCord()[1] = y;
    }

    public Vector3 vectorMultiply(Vector3 secondVector) {
        float x = this.getY() * secondVector.getZ() - this.getZ() * secondVector.getY();
        float y = -this.getX() * secondVector.getZ() + this.getZ() * secondVector.getX();
        float z = this.getX() * secondVector.getY() - this.getY() * secondVector.getX();
        return new Vector3(new float[] {x, y, z});
    }


    public Vector2 getVector2() {
        return new Vector2(new float[] {getX(), getY()});
    }

    @Override
    public String toString() {
        return Float.toString(getX()) + " " + Float.toString(getY()) + " " + Float.toString(getZ());
    }
}
