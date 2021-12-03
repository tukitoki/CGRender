package com.vsu.cgcourse.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractVectorTest {

    @Test
    public void multiplyMatrixByVector() throws Exception {
        Matrix4 matrix4 = new Matrix4(new float[][] {{5, 1, 3, 8},
                                                    {1, 23, 6, 2},
                                                    {5,  3, 4, 5},
                                                    {8,  7, 4, 6}});
        Vector4 vector4 = new Vector4(new float[] {4, 1, 3, 7});
        vector4.multiply(matrix4);
        Assertions.assertEquals(vector4.getX(), 86);
        Assertions.assertEquals(vector4.getY(), 59);
        Assertions.assertEquals(vector4.getZ(), 70);
        Assertions.assertEquals(vector4.getW(), 93);
    }

    @Test
    public void multiplyMatrixByMatrix() throws Exception {
        Matrix4 matrix4 = new Matrix4(new float[][] {{5, 2, 7, 8},
                                                     {4, 8, 9, 8},
                                                    {12, 15, 2, 8},
                                                     {4, 9, 9, 8}});
        Matrix4 matrix41 = new Matrix4(new float[][] {{4, 7, 2, 8},
                                                      {8, 1, 2, 8},
                                                      {9, 15, 11, 8},
                                                      {5, 4, 4, 2}});
        matrix4.multiply(matrix41);
        Matrix4 matrix42 = new Matrix4(new float[][] {{139, 174, 123, 128},
                                                      {201 ,203, 155, 184},
                                                      {226, 161, 108, 248},
                                                      {209, 204, 157, 192}});
        for (int i = 0; i < matrix4.getMatrix().length; i++) {
            for (int j = 0; j < matrix4.getMatrix()[i].length; j++) {
                Assertions.assertEquals(matrix4.getMatrix()[i][j], matrix42.getMatrix()[i][j]);
            }
        }
    }

}