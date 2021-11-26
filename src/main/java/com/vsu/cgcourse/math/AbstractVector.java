package com.vsu.cgcourse.math;

public abstract class AbstractVector<T> {

    private float[] vectorCoords;

    protected AbstractVector(float[] vectorCoords) {
        this.vectorCoords = new float[vectorCoords.length];
        for (int i = 0; i < vectorCoords.length; i++) {
            this.vectorCoords[i] = vectorCoords[i];
        }
    }

    protected AbstractVector(int size) {
        this.vectorCoords = new float[size];
    }

    protected abstract AbstractVector<T> createVector();

    public float[] getVectorCoords() {
        return this.vectorCoords;
    }

    public void setVectorCoords(float[] newVectorCoords) {
        this.vectorCoords = newVectorCoords;
    }

    public void setVectorCoords(AbstractVector<T> vector) {
        for (int i = 0; i < this.vectorCoords.length; i++) {
            this.vectorCoords[i] = vector.vectorCoords[i];
        }
    }

    public float getCoord(int ind) throws Exception {
        if (this.vectorCoords.length - 1 < ind) {
            throw new Exception("You use incorrect index");
        }
        return this.vectorCoords[ind];
    }

    public void setCoord(int ind, float newCoord) throws Exception {
        if (this.vectorCoords.length - 1 < ind) {
            throw new Exception("You use incorrect index");
        }
        this.vectorCoords[ind] = newCoord;
    }

    public void plus(AbstractVector<T> vector) throws Exception {
        if (this.vectorCoords.length != vector.vectorCoords.length) {
            throw new Exception("You use Vectors with different sizes");
        }
        for (int i = 0; i < this.vectorCoords.length; i++) {
            this.vectorCoords[i] += vector.vectorCoords[i];
        }
    }

    public void sub(AbstractVector<T> vector) throws Exception {
        if (this.vectorCoords.length != vector.vectorCoords.length) {
            throw new Exception("You use Vectors with different sizes");
        }
        for (int i = 0; i < this.vectorCoords.length; i++) {
            this.vectorCoords[i] -= vector.vectorCoords[i];
        }
    }

    public AbstractVector<T> multiplyConst(float scal) {
        AbstractVector<T> resultVector = createVector();
        for (int i = 0; i < this.vectorCoords.length; i++) {
            resultVector.vectorCoords[i] = this.vectorCoords[i] * scal;
        }
        return resultVector;
    }

    public AbstractVector<T> divideConst(float scal) {
        assert (Math.abs(scal) > 0.1E-6f);
        AbstractVector<T> resultVector = createVector();
        for (int i = 0; i < this.vectorCoords.length; i++) {
            resultVector.vectorCoords[i] = this.vectorCoords[i] / scal;
        }
        return resultVector;
    }

    public float length() {
        float squareSum = 0f;
        for (int i = 0; i < this.vectorCoords.length; i++) {
            squareSum += this.vectorCoords[i] * this.vectorCoords[i];
        }
        return (float) Math.sqrt(squareSum);
    }

    public void normal() {
        float length = this.length();
        assert (Math.abs(length) > 0.1E-6f);
        for (int i = 0; i < this.vectorCoords.length; i++) {
            this.vectorCoords[i] /= length;
        }
    }

    public float scalarMultiply(AbstractVector<T> vector) throws Exception {
        if (this.vectorCoords.length != vector.vectorCoords.length) {
            throw new Exception("You use Vectors with different sizes");
        }
        float result = 0f;
        for (int i = 0; i < this.vectorCoords.length; i++) {
            result += this.vectorCoords[i] * vector.vectorCoords[i];
        }
        return result;
    }

}
