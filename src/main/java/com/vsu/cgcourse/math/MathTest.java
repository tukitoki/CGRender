package com.vsu.cgcourse.math;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MathTest {

    @Test
    public void testVectorPlus() throws Exception {
        Vector4 vector4 = new Vector4(new float[] {1f, 2f, 3f, 4f});
        vector4.plus(new Vector4(new float[] {3, 2, 1, 0}));
        Assertions.assertEquals(vector4.getX(), 0);
        Assertions.assertEquals(vector4.getY(), 0);
        Assertions.assertEquals(vector4.getZ(), 0);
        Assertions.assertEquals(vector4.getW(), 0);
    }

    @Test
    public void testVectorSubtract() throws Exception {
        Vector4 vector4 = new Vector4(new float[] {1, 2, 3, 4});
        vector4.sub(new Vector4(new float[] {1, 2, 3, 4}));
        Assertions.assertEquals(vector4.getX(), 0);
        Assertions.assertEquals(vector4.getY(), 0);
        Assertions.assertEquals(vector4.getZ(), 0);
        Assertions.assertEquals(vector4.getW(), 0);
        }

    @Test
    public void testVectorScalarMultiply() throws Exception {
        Vector4 vector4 = new Vector4(new float[] {1, 5, 10, 100});
        Assertions.assertEquals(vector4.scalarMultiply(new Vector4(new float[] {100,20, 10, 1})), 400);
    }

    @Test
    public void testVectorMultiply() {
        Vector3 vector3 = new Vector3(new float[] {1, 2, 3});
        vector3.vectorMultiply(new Vector3(new float[] {3, 2, 1}));
        Assertions.assertEquals(vector3.getX(), -4);
        Assertions.assertEquals(vector3.getY(), 8);
        Assertions.assertEquals(vector3.getZ(), -4);
    }

    @Test
    public void testVectorDivideZero() {
        Vector4 vector4 = new Vector4(new float[] {1,2, 3, 4});
        Assertions.assertThrows(AssertionError.class, () -> vector4.divideConst(0));
    }

    @Test
    public void testVectorLength() {
        Vector4 vector4 = new Vector4(new float[] {2, 2, 2, 2});
        Assertions.assertEquals(vector4.length(), 4);
    }

    @Test
    public void testVectorNormalLength() {
        Vector4 vector4 = new Vector4(new float[] {2, 2, 2, 2});
        vector4.normal();
        Assertions.assertEquals(vector4.length(), 1);
    }

    @Test
    public void testZeroVector() {
        Vector4 vector4 = new Vector4(new float[] {0, 0, 0, 0});
        Assertions.assertThrows(AssertionError.class, vector4::normal);
    }

    @Test
    public void testMatrixPlus() throws Exception {
        Matrix4 matrix4 = new Matrix4(new float[][] {{1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}});
        Matrix4 matrix41 = new Matrix4(new float[][] {{4, 4, 4, 4}, {4, 4, 4, 4}, {4, 4, 4, 4}, {4, 4, 4, 4}});
        Matrix4 matrix42 = (Matrix4) matrix4.plus(new Matrix4(new float[][] {{3, 2, 1, 0}, {3, 2, 1, 0}, {3, 2, 1, 0}, {3, 2, 1, 0}}));
        for (int i = 0; i < matrix4.getMatrix().length; i++) {
            for (int j = 0; j < matrix4.getMatrix()[i].length; j++) {
                Assertions.assertEquals(matrix42.getMatrix()[i][j], matrix41.getMatrix()[i][j]);
            }
        }
    }

    @Test
    public void testMatrixTransposition() throws Exception {
        Matrix4 matrix4 = new Matrix4(new float[][] {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        Matrix4 matrix41= new Matrix4(new float[][] {{1,5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 16}});
        matrix4.transposite();
        for (int i = 0; i < matrix4.getMatrix().length; i++) {
            for (int j = 0; j < matrix4.getMatrix()[i].length; j++) {
                Assertions.assertEquals(matrix4.getMatrix()[i][j], matrix41.getMatrix()[i][j]);
            }
        }
    }

    @Test
    public void testUnitMatrix() throws Exception {
        Matrix4 matrix4 = new Matrix4(new float[4][4]);
        matrix4.unitMatrix();
        for (int i = 0; i < matrix4.getMatrix().length; i++) {
            Assertions.assertEquals(matrix4.getMatrix()[i][i], 1);
        }
    }

    @Test public void testNullMatrix() throws Exception {
        Matrix4 matrix4 = new Matrix4(new float[4][4]);
        for (int i = 0; i < matrix4.getMatrix().length; i++) {
            for (int j = 0; j < matrix4.getMatrix()[i].length; j++) {
                Assertions.assertEquals(matrix4.getMatrix()[i][j], 0);
            }
        }
    }

    @Test
    public void testMatrix3PlusMatrix4() throws Exception {
        Matrix3 matrix3 = new Matrix3(new float[3][3]);
        try {
            matrix3.plus(new Matrix3(new float[4][4]));
        } catch (Exception thrown) {
            Assert.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    public void testMatrixDeterminant() throws Exception {
        Matrix3 matrix3 = new Matrix3(new float[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 19}});
        Assertions.assertEquals(matrix3.determinant(), -30);
    }

    @Test
    public void testMatrix4Determinant() throws Exception {
        Matrix4 matrix4 = new Matrix4(new float[][] {{49, 28, 23, 28}, {11, 35, 23, 6}, {22, 19, 50, 21}, {47, 40, 26, 21}});
        Assertions.assertEquals(matrix4.determinant(), -236987);
    }

    @Test
    public void testAnyMatrixDeterminant() throws Exception {
        Matrix4 matrix4 = new Matrix4(new float[][] {{21, 5, 48, 13},
                                                     {12, 11, 50, 44},
                                                     {15, 11, 11, 45},
                                                     {27, 16, 48, 41}});
        Assertions.assertEquals(matrix4.determinant(), 156498);
    }

    @Test
    public void testReverseMatrix() throws Exception {
        Matrix3 matrix = new Matrix3(new float[][] {{2, 5, 7}, {6, 3, 4}, {5, -2, -3}});
        Matrix3 matrix1 = new Matrix3(new float[][] {{1, -1, 1}, {-38, 41, -34}, {27, -29, 24}});
        Matrix3 resultMatrix = (Matrix3)matrix.reverseMatrix();
        for (int line = 0; line < matrix.getMatrix().length; line++) {
            for (int column = 0; column < matrix.getMatrix()[0].length; column++) {
                Assertions.assertEquals(resultMatrix.getMatrix()[line][column], matrix1.getMatrix()[line][column]);
            }
        }
    }

}