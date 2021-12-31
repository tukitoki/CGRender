package com.vsu.cgcourse.math;

public abstract class AbstractVector<T> {

    private float[] vectorCord;

    protected AbstractVector(float[] vectorCoords) {
        this.vectorCord = new float[vectorCoords.length];
        for (int i = 0; i < vectorCoords.length; i++) {
            this.vectorCord[i] = vectorCoords[i];
        }
    }

    protected AbstractVector(int size) {
        this.vectorCord = new float[size];
    }

    protected abstract AbstractVector<T> createVector();

    public float[] getVectorCord() {
        return this.vectorCord;
    }

    public void setVectorCord(float[] newVectorCoords) {
        this.vectorCord = newVectorCoords;
    }

    public void setVectorCoords(AbstractVector<T> vector) {
        for (int i = 0; i < this.vectorCord.length; i++) {
            this.vectorCord[i] = vector.vectorCord[i];
        }
    }

    public float getCoord(int ind) throws Exception {
        if (this.vectorCord.length - 1 < ind) {
            throw new Exception("You use incorrect index");
        }
        return this.vectorCord[ind];
    }

    public void setCoord(int ind, float newCoord) throws Exception {
        if (this.vectorCord.length - 1 < ind) {
            throw new Exception("You use incorrect index");
        }
        this.vectorCord[ind] = newCoord;
    }

    public void sum(AbstractVector<T> vector) throws Exception {
        if (this.vectorCord.length != vector.vectorCord.length) {
            throw new Exception("You use Vectors with different sizes");
        }
        for (int i = 0; i < this.vectorCord.length; i++) {
            this.vectorCord[i] += vector.vectorCord[i];
        }
    }

    public float[] summation(AbstractVector<T> vector) throws Exception {
        if (this.vectorCord.length != vector.vectorCord.length) {
            throw new Exception("You use Vectors with different sizes");
        }
        float[] newVectorCord = new float[this.vectorCord.length];
        for (int i = 0; i < this.vectorCord.length; i++) {
            newVectorCord[i] = this.vectorCord[i] + vector.vectorCord[i];
        }
        return newVectorCord;
    }

    public void sub(AbstractVector<T> vector) throws Exception {
        if (this.vectorCord.length != vector.vectorCord.length) {
            throw new Exception("You use Vectors with different sizes");
        }
        for (int i = 0; i < this.vectorCord.length; i++) {
            this.vectorCord[i] -= vector.vectorCord[i];
        }
    }

    public float[] subtract(AbstractVector<T> vector) throws Exception {
        if (this.vectorCord.length != vector.vectorCord.length) {
            throw new Exception("You use Vectors with different sizes");
        }
        float[] newVectorCoord = new float[this.vectorCord.length];
        for (int i = 0; i < this.vectorCord.length; i++) {
            newVectorCoord[i] = this.vectorCord[i] - vector.vectorCord[i];
        }
        return newVectorCoord;
    }

    public void mulScal(float scal) {
        for (int i = 0; i < this.vectorCord.length; i++) {
            this.vectorCord[i] *= scal;
        }
    }

    public float[] multiplyScal(float scal) {
        float[] newVectorCord = new float[this.vectorCord.length];
        for (int i = 0; i < this.vectorCord.length; i++) {
            newVectorCord[i] = this.vectorCord[i] * scal;
        }
        return newVectorCord;
    }

    public void mulMatrix(AbstractMatrix matrix) throws Exception {
        if (matrix.getMatrix()[0].length != this.getVectorCord().length) {
            throw new Exception("Size of vector must be equals size of matrix line");
        }
        float[] newCoords = new float[this.vectorCord.length];
        for (int line = 0; line < matrix.getMatrix().length; line++) {
            float var = 0;
            for (int column = 0; column < matrix.getMatrix()[line].length; column++) {
                var += matrix.getMatrix()[line][column] * this.vectorCord[column];
            }
            newCoords[line] = var;
        }
        this.vectorCord = newCoords;
    }

    public AbstractVector<T> divideConst(float scal) {
        assert (Math.abs(scal) > 0.1E-6f);
        AbstractVector<T> resultVector = createVector();
        for (int i = 0; i < this.vectorCord.length; i++) {
            resultVector.vectorCord[i] = this.vectorCord[i] / scal;
        }
        return resultVector;
    }

    public float length() {
        float squareSum = 0f;
        for (int i = 0; i < this.vectorCord.length; i++) {
            squareSum += this.vectorCord[i] * this.vectorCord[i];
        }
        return (float) Math.sqrt(squareSum);
    }

    public void normal() {
        float length = this.length();
        assert (Math.abs(length) > 0.1E-6f);
        for (int i = 0; i < this.vectorCord.length; i++) {
            this.vectorCord[i] /= length;
        }
    }

    public float scalarMultiply(AbstractVector<T> vector) throws Exception {
        if (this.vectorCord.length != vector.vectorCord.length) {
            throw new Exception("You use Vectors with different sizes");
        }
        float result = 0f;
        for (int i = 0; i < this.vectorCord.length; i++) {
            result += this.vectorCord[i] * vector.vectorCord[i];
        }
        return result;
    }

}
