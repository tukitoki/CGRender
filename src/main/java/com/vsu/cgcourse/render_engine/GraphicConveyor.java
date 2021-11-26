package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.Matrix3;
import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.math.Vector4;

import javax.vecmath.Point2f;

public class GraphicConveyor {

    public static Matrix4 rotateScaleTranslate() throws Exception {
        Matrix4 matrix = new Matrix4(new float[][]{
                {(float)Math.cos(Math.PI / 6), 0, (float) Math.sin(Math.PI / 6), 0},
                {0, 1, 0, 0},
                {(float) -Math.sin(Math.PI / 6), 0, (float) Math.cos(Math.PI / 6), 0},
                {0,                                                              0, 0, 1}
        });
        return new Matrix4(matrix);
    }

    public static Matrix4 lookAt(Vector3 eye, Vector3 target) throws Exception {
        return lookAt(eye, target, new Vector3(new float[]{0F, 1.0F, 0F}));
    }

    public static Matrix4 lookAt(Vector3 eye, Vector3 target, Vector3 up) throws Exception {
        Vector3 resultX = new Vector3(up);
        Vector3 resultZ = new Vector3(target);

        resultZ.sub(eye);
        resultX.vectorMultiply(resultZ);

        Vector3 resultY = new Vector3(resultZ);
        resultY.vectorMultiply(resultX);

        resultX.normal();
        resultY.normal();
        resultZ.normal();

        float[][] matrix = new float[][]{
                {resultX.getX(), resultY.getX(), resultZ.getX(), 0},
                {resultX.getY(), resultY.getY(), resultZ.getY(), 0},
                {resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0},
                {-resultX.scalarMultiply(eye), -resultY.scalarMultiply(eye), -resultZ.scalarMultiply(eye), 1}};
        return new Matrix4(matrix);
    }

    public static Matrix4 perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4 result = new Matrix4();
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.setElement(0, 0, tangentMinusOnDegree / aspectRatio);
        result.setElement(1, 1, tangentMinusOnDegree);
        result.setElement(2, 2, (farPlane + nearPlane) / (farPlane - nearPlane));
        result.setElement(2, 3, 1.0F);
        result.setElement(3, 2, 2 * (nearPlane * farPlane) / (nearPlane - farPlane));
        return result;
    }

    public static Vector3 multiplyMatrix4ByVector3(final Matrix4 matrix, final Vector3 vertex) {
        final float x = (vertex.getX() * matrix.getElement(0, 0)) +
                (vertex.getY() * matrix.getElement(1, 0)) +
                (vertex.getZ() * matrix.getElement(2, 0)) + matrix.getElement(3, 0);
        final float y = (vertex.getX() * matrix.getElement(0, 1)) +
                (vertex.getY() * matrix.getElement(1, 1)) +
                (vertex.getZ() * matrix.getElement(2, 1)) + matrix.getElement(3, 1);
        final float z = (vertex.getX() * matrix.getElement(0, 2)) +
                (vertex.getY() * matrix.getElement(1, 2)) +
                (vertex.getZ() * matrix.getElement(2, 2)) + matrix.getElement(3, 2);
        final float w = (vertex.getX() * matrix.getElement(0, 3)) +
                (vertex.getY() * matrix.getElement(1, 3)) +
                (vertex.getZ() * matrix.getElement(2, 3)) + matrix.getElement(3, 3);
        return new Vector3(new float[]{x / w, y / w, z / w});
    }

    public static Point2f vertexToPoint(final Vector3 vertex, final int width, final int height) {
        return new Point2f(vertex.getVectorCoords()[0] * width + width / 2.0F, -vertex.getVectorCoords()[1] * height + height / 2.0F);
    }
}
