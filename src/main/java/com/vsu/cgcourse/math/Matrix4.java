package com.vsu.cgcourse.math;

public class Matrix4 extends AbstractMatrix<Matrix4> {

    public Matrix4() {
        super(4);
    }

    public Matrix4(float[][] matrix) throws Exception {
        super(matrix);
    }

    public Matrix4(Matrix4 matrix4) throws Exception {
        super(matrix4.getMatrix());
        if (matrix4.getMatrix().length != 4) {
            throw new Exception("Matrix4 can be only with size 4x4");
        }
        for (float[] floats : matrix4.getMatrix()) {
            if (floats.length != 4) {
                throw new Exception("Matrix4 can be only with size 4x4");
            }
        }
    }

    @Override
    protected AbstractMatrix<Matrix4> createMatrix(int line, int column) {
        return new Matrix4();

    }

    public float determinant() {
        return (this.getMatrix()[0][0] * determinant(decomposition(this.getMatrix(), 0, 0)) -
                this.getMatrix()[0][1] * determinant(decomposition(this.getMatrix(), 0, 1)) +
                this.getMatrix()[0][2] * determinant(decomposition(this.getMatrix(), 0, 2)) -
                this.getMatrix()[0][3] * determinant(decomposition(this.getMatrix(), 0, 3)));
    }

}