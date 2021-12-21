package com.vsu.cgcourse.math;

import com.vsu.cgcourse.model.MeshContext;
import com.vsu.cgcourse.render_engine.GraphicConveyor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class GraphicConveyorTest {

    @Test
    public void checkScaleForMatrix3() throws Exception {
        MeshContext meshContext = new MeshContext(3, 2, 2, ' ', 0);
        Matrix3 matrix3 = GraphicConveyor.scale(meshContext.getConverter());
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
        Matrix3 matrix3 = GraphicConveyor.scale(meshContext.getConverter());
        Matrix4 matrix4 = new Matrix4(new float[][]{
                {matrix3.getElement(0, 0), matrix3.getElement(0, 1), matrix3.getElement(0, 2), 0},
                {matrix3.getElement(1, 0), matrix3.getElement(1, 1), matrix3.getElement(1, 2), 0},
                {matrix3.getElement(2, 0), matrix3.getElement(2, 1), matrix3.getElement(2, 2), 0},
                {0, 0, 0, 1}
        });
        matrix4.transposite();

        Vector3 vector3 = GraphicConveyor.multiplyMatrix4ByVector3(matrix4, new Vector3(new float[] {3, 3, 3}));
        Vector3 vector3Ans = new Vector3(new float[] {6, 3, 3});
            Assertions.assertEquals(vector3.getX(), vector3Ans.getX());
            Assertions.assertEquals(vector3.getY(), vector3Ans.getY());
            Assertions.assertEquals(vector3.getZ(), vector3Ans.getZ());
    }

    @Test
    public void checkRotateAxisX() throws Exception {
        MeshContext meshContext = new MeshContext(null);
        meshContext.getConverter().setAxis('x');
        meshContext.getConverter().setAngle(30);
        Matrix3 matrix3 = GraphicConveyor.rotate(meshContext.getConverter());
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
        MeshContext meshContext = new MeshContext(null);
        meshContext.getConverter().setAxis('y');
        meshContext.getConverter().setAngle(45);
        Matrix3 matrix3 = GraphicConveyor.rotate(meshContext.getConverter());
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
        MeshContext meshContext = new MeshContext(null);
        meshContext.getConverter().setAxis('z');
        meshContext.getConverter().setAngle(90);
        Matrix3 matrix3 = GraphicConveyor.rotate(meshContext.getConverter());
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
        MeshContext meshContext = new MeshContext(null);
        meshContext.getConverter().setAxis('x');
        meshContext.getConverter().setAngle(60);
        Matrix3 matrix3 = GraphicConveyor.rotate(meshContext.getConverter());
        Matrix4 matrix4 = new Matrix4(new float[][]{
                {matrix3.getElement(0, 0), matrix3.getElement(0, 1), matrix3.getElement(0, 2), 0},
                {matrix3.getElement(1, 0), matrix3.getElement(1, 1), matrix3.getElement(1, 2), 0},
                {matrix3.getElement(2, 0), matrix3.getElement(2, 1), matrix3.getElement(2, 2), 0},
                {0, 0, 0, 1}
        });
        matrix4.transposite();
        Vector3 vector3 = GraphicConveyor.multiplyMatrix4ByVector3(matrix4, new Vector3(new float[] {3, 2, 2}));
        Vector3 vector3Ans = new Vector3(new float[] {3.0F, (float) 2.732, (float) -0.732});
        if (vector3.getX()  - vector3Ans.getX() < 1e-6f) {
            assertTrue(true);
        } else {
            fail();
        }

    }

    @Test
    public void checkTranslateForMatrix() throws Exception {
        MeshContext meshContext = new MeshContext( 1, 1, 1, ' ', 0);
        meshContext.getConverter().setVectorTranslate(new Vector3(new float[]{1, 7, 5}));
        Matrix4 matrix4 = GraphicConveyor.translate(meshContext.getConverter());
        Matrix4 matrix4Ans = new Matrix4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {1, 7, 5, 1}
        });
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Assertions.assertEquals(matrix4.getMatrix()[i][j], matrix4Ans.getMatrix()[i][j]);
            }
        }
    }

    @Test
    public void checkTranslateForVector() throws Exception {
        MeshContext meshContext = new MeshContext(2, 4, 3, ' ', 0);
        Matrix4 matrix4 = GraphicConveyor.translate(meshContext.getConverter());
        matrix4.transposite();
        Vector3 vector3 = GraphicConveyor.multiplyMatrix4ByVector3(matrix4, new Vector3(new float[] {3, 7, 2}));
        Vector3 vector3Ans = new Vector3(new float[] {3, 7, 2});
        Assertions.assertEquals(vector3.getX(), vector3Ans.getX());
        Assertions.assertEquals(vector3.getY(), vector3Ans.getY());
        Assertions.assertEquals(vector3.getZ(), vector3Ans.getZ());
    }

    @Test
    public void checkFuncLookAt() throws Exception {
        Vector3 eye = new Vector3(new float[]{3, 2, 2});
        Vector3 target = new Vector3(new float[] {0, 0, 0});
        Matrix4 matrix4 = GraphicConveyor.lookAt(eye, target);
        Matrix4 matrix4Ans = new Matrix4(new float[][]{
                {(float) -0.5547, (float) -0.4036, (float) -0.727, 0},
                {0, (float) 0.874474, (float) -0.4850712, 0},
                {(float) 0.83205, (float) -0.269069, (float) -0.485, 0},
                {0, (float) -5.9606E-8, (float) 4.123105, 1}
        });
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix4.getMatrix()[i][j] - matrix4Ans.getMatrix()[i][j] < 1e-6f) {
                    assertTrue(true);
                } else {
                    fail();
                }
            }
        }
    }

    @Test
    public void checkPerspective() throws Exception {
        Matrix4 matrix4 = GraphicConveyor.perspective(45, 2, 3, 4);
        Matrix4 matrix4Ans = new Matrix4(new float[][]{
                {(float) 0.896295, 0, 0, 0},
                {0, (float) 1.79259, 0, 0},
                {0, 0, 7, 1},
                {0, 0, (float) -24.0, 0}
        });
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix4.getMatrix()[i][j] - matrix4Ans.getMatrix()[i][j] < 1e-6f) {
                    assertTrue(true);
                } else {
                    fail();
                }
            }
        }
    }
}
