package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.Matrix3;
import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.math.Vector4;

import javax.vecmath.Point2f;

public class GraphicConveyor {

    public static Matrix4 rotateScaleTranslate(Converter converter) throws Exception {
        Matrix4 matrix = new Matrix4 (new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
        Matrix3 matrix3 = converter.scale();
        return new Matrix4(new float[][]{
                {matrix3.getMatrix()[0][0], matrix3.getMatrix()[0][1], matrix3.getMatrix()[0][2], 0},
                {matrix3.getMatrix()[1][0], matrix3.getMatrix()[1][1], matrix3.getMatrix()[1][2], 0},
                {matrix3.getMatrix()[2][0], matrix3.getMatrix()[2][1], matrix3.getMatrix()[2][2], 0},
                {0, 0, 0, 1}
        });
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

    public static Vector3 multiplyMatrix4ByVector3(final Matrix4 matrix, final Vector3 vertex) throws Exception {
        Vector4 vertex4 = new Vector4(new float[] {vertex.getX(), vertex.getY(), vertex.getZ(), 1});
        vertex4.multiply(matrix);
        return new Vector3(new float[] {vertex4.getX() / vertex4.getW(), vertex4.getY() / vertex4.getW(),
                                                                        vertex4.getZ() / vertex4.getW()});
    }

    public static Point2f vertexToPoint(final Vector3 vertex, final int width, final int height) {
        return new Point2f(vertex.getVectorCoords()[0] * width + width / 2.0F, -vertex.getVectorCoords()[1] * height + height / 2.0F);
    }
}
