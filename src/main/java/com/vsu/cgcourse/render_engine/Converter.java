package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.Matrix3;
import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector3;


public class Converter {
    private float x;
    private float y;
    private float z;
    private char axis;
    private float angle;
    private Vector3 vectorTranslate;
    private boolean selected = true;

    public Converter(float x, float y, float z, char axis, float angle, Vector3 vectorTranslate) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.axis = axis;
        this.angle = angle;
        this.vectorTranslate = vectorTranslate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public char getAxis() {
        return axis;
    }

    public void setAxis(char axis) {
        this.axis = axis;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Vector3 getVectorTranslate() {
        return vectorTranslate;
    }

    public void setVectorTranslate(Vector3 vectorTranslate) {
        this.vectorTranslate = vectorTranslate;
    }

    public Matrix4 translate() throws Exception {
        if (vectorTranslate.getX() == 0 && vectorTranslate.getY() == 0 && vectorTranslate.getZ() == 0) {
            return new Matrix4(new float[][]{
                    {1, 0, 0, 0},
                    {0, 1, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
            });
        }
        return new Matrix4(new float[][] {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {vectorTranslate.getX(), vectorTranslate.getY(), vectorTranslate.getZ(), 1}
        });
    }

    public Matrix3 scale() throws Exception {
        return new Matrix3(new float[][]{
                    {x, 0, 0},
                    {0, y, 0},
                    {0, 0, z}
        });
    }

    public  Matrix3 rotate() throws Exception {
        float rad = (float) Math.toRadians(angle);
        if(axis == 'x') {
            return new Matrix3(new float[][]{
                    {1, 0, 0},
                    {0, (float) Math.cos(rad), (float) Math.sin(rad)},
                    {0, (float) -Math.sin(rad), (float) Math.cos(rad)}
            });
        } else  if(axis == 'y') {
            return new Matrix3(new float[][]{
                    {(float) Math.cos(rad), 0, (float) Math.sin(rad)},
                    {0, 1, 0},
                    {(float) -Math.sin(rad),0, (float) Math.cos(rad)}
            });
        } else if(axis == 'z') {
            return new Matrix3(new float[][]{
                    {(float) Math.cos(rad), (float) Math.sin(rad), 0},
                    {(float) -Math.sin(rad), (float) Math.cos(rad), 0},
                    {0, 0, 1}
            });
        }
        return new Matrix3(new float[][] {{1, 0, 0},
                                          {0, 1, 0},
                                          {0, 0, 1}});
    }
}
