package com.vsu.cgcourse.math;

import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector4;
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
        Matrix3 matrix3 = new Matrix3(new float[][] {{5, 2, 7},
                                                     {4, 8, 9},
                                                    {12, 15, 2}});
        Matrix3 matrix31 = new Matrix3(new float[][] {{4, 7, 2},
                                                      {8, 1, 2},
                                                      {9, 15, 11}});
        matrix3.multiply(matrix31);
        Matrix3 matrix32 = new Matrix3(new float[][] {{99, 142, 91},
                                                      {161, 171, 123},
                                                      {186, 129, 76}});
        for (int i = 0; i < matrix3.getMatrix().length; i++) {
            for (int j = 0; j < matrix3.getMatrix()[i].length; j++) {
                Assertions.assertEquals(matrix3.getMatrix()[i][j], matrix32.getMatrix()[i][j]);
            }
        }
    }

}