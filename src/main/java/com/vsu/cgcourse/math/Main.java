package com.vsu.cgcourse.math;

public class Main {

    public static void main(String[] args) throws Exception {
        Matrix4 matrix4 = new Matrix4(new float[][] {{1, 4, 2, 3}, {5 , 8, 6, 7}, {9, 12, 10, 11}, {13, 16, 14, 15}});
        System.out.println(matrix4.determinant());
        matrix4.multiply(new Vector4(new float[] {1, 2, 3, 4}));
    }
}
