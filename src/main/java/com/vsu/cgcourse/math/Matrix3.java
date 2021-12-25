package com.vsu.cgcourse.math;

public class Matrix3 extends AbstractMatrix<Matrix3> {

    public Matrix3() {
        super(3);
    }

    public Matrix3(float[][] matrix) throws Exception {
        super(matrix);
        if (matrix.length != 3) {
            throw new Exception("Matrix3 can be only with size 3x3");
        }
        for (float[] floats : matrix) {
            if (floats.length != 3) {
                throw new Exception("Matrix3 can be only with size 3x3");
            }
        }
    }

    @Override
    protected Matrix3 createMatrix(int line, int column) {
        return new Matrix3();
    }

    public float determinant() {
        return (this.getMatrix()[0][0] * this.getMatrix()[1][1] * this.getMatrix()[2][2] +
                this.getMatrix()[2][0] * this.getMatrix()[0][1] * this.getMatrix()[1][2] +
                this.getMatrix()[0][2] * this.getMatrix()[1][0] * this.getMatrix()[2][1] -
                this.getMatrix()[0][2] * this.getMatrix()[1][1] * this.getMatrix()[2][0] -
                this.getMatrix()[0][0] * this.getMatrix()[2][1] * this.getMatrix()[1][2] -
                this.getMatrix()[2][2] * this.getMatrix()[0][1] * this.getMatrix()[1][0]);
    }

}
