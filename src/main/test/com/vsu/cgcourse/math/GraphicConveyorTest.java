package com.vsu.cgcourse.math;

import com.vsu.cgcourse.model.MeshContext;
import com.vsu.cgcourse.render_engine.GraphicConveyor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.testng.Assert.assertTrue;

public class GraphicConveyorTest {

    @Test
    public void checkScaleForMatrix3() throws Exception {
        MeshContext meshContext = new MeshContext(3, 2, 2, ' ', 0);
        Matrix3 matrix3 = meshContext.getConverter().scale();
        Matrix3 matrix3Ans = new Matrix3(new float[][]{
                {3, 0, 0},
                {0, 2, 0},
                {0, 0, 2}
        });
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assertions.assertEquals(matrix3.getMatrix()[i][j], matrix3Ans.getMatrix()[i][j]);
            }
        }
    }

    @Test
    public void checkScaleForVector() throws Exception {
        MeshContext meshContext = new MeshContext(2, 1, 1, ' ', 0);
        Matrix3 matrix3 = meshContext.getConverter().scale();
        Matrix4 matrix4 = new Matrix4(new float[][]{
                {matrix3.getElement(0, 0), matrix3.getElement(0, 1), matrix3.getElement(0, 2), 0},
                {matrix3.getElement(1, 0), matrix3.getElement(1, 1), matrix3.getElement(1, 2), 0},
                {matrix3.getElement(2, 0), matrix3.getElement(2, 1), matrix3.getElement(2, 2), 0},
                {0, 0, 0, 1}
        });

        Vector3 vector3 = GraphicConveyor.multiplyMatrix4ByVector3(matrix4, new Vector3(new float[] {3, 3, 3}));
        Vector3 vector3Ans = new Vector3(new float[] {6, 3, 3});
            Assertions.assertEquals(vector3.getX(), vector3Ans.getX());
            Assertions.assertEquals(vector3.getY(), vector3Ans.getY());
            Assertions.assertEquals(vector3.getZ(), vector3Ans.getZ());
    }

    @Test
    public void checkRotateAxisX() throws Exception {
        MeshContext meshContext = new MeshContext(null, 'x', 30);
        Matrix3 matrix3 = meshContext.getConverter().rotate();
        float rad = (float) Math.toRadians(30);
        Matrix3 matrix3Ans = new Matrix3(new float[][]{
                {1, 0, 0},
                {0, (float) Math.cos(rad), (float) Math.sin(rad)},
                {0, (float) -Math.sin(rad), (float) Math.cos(rad)}
        });
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assertions.assertEquals(matrix3.getMatrix()[i][j], matrix3Ans.getMatrix()[i][j]);
            }
        }
    }

    @Test
    public void checkRotateAxisY() throws Exception {
        MeshContext meshContext = new MeshContext(null, 'y', 45);
        Matrix3 matrix3 = meshContext.getConverter().rotate();
        float rad = (float) Math.toRadians(45);
        Matrix3 matrix3Ans = new Matrix3(new float[][]{
                {(float) Math.cos(rad), 0, (float) Math.sin(rad)},
                {0, 1, 0},
                {(float) -Math.sin(rad), 0, (float) Math.cos(rad)}
        });
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assertions.assertEquals(matrix3.getMatrix()[i][j], matrix3Ans.getMatrix()[i][j]);
            }
        }
    }

    @Test
    public void checkRotateAxisZ() throws Exception {
        MeshContext meshContext = new MeshContext(null, 'z', 90);
        Matrix3 matrix3 = meshContext.getConverter().rotate();
        float rad = (float) Math.toRadians(90);
        Matrix3 matrix3Ans = new Matrix3(new float[][]{
                {(float) Math.cos(rad), (float) Math.sin(rad), 0},
                {(float) -Math.sin(rad), (float) Math.cos(rad), 0},
                {0, 0, 1}
        });
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assertions.assertEquals(matrix3.getMatrix()[i][j], matrix3Ans.getMatrix()[i][j]);
            }
        }
    }

    @Test
    public void checkRotateXForVector() throws Exception {
        MeshContext meshContext = new MeshContext(null, 'x', 60);
        Matrix3 matrix3 = meshContext.getConverter().rotate();
        Matrix4 matrix4 = new Matrix4(new float[][]{
                {matrix3.getElement(0, 0), matrix3.getElement(0, 1), matrix3.getElement(0, 2), 0},
                {matrix3.getElement(1, 0), matrix3.getElement(1, 1), matrix3.getElement(1, 2), 0},
                {matrix3.getElement(2, 0), matrix3.getElement(2, 1), matrix3.getElement(2, 2), 0},
                {0, 0, 0, 1}
        });
        Vector3 vector3 = GraphicConveyor.multiplyMatrix4ByVector3(matrix4, new Vector3(new float[] {3, 2, 2}));
        Vector3 vector3Ans = new Vector3(new float[] {3.0F, (float) 2.732051, (float) -0.73205096});
        Assertions.assertEquals(vector3.getX(), vector3Ans.getX());
        Assertions.assertEquals(vector3.getY(), vector3Ans.getY());
        Assertions.assertEquals(vector3.getZ(), vector3Ans.getZ());
    }
}
