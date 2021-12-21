package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.Matrix3;
import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector3;


public class Converter {
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private char axis;
    private float angle;
    private Vector3 vectorTranslate;

    public Converter(float scaleX, float scaleY, float scaleZ, char axis, float angle, Vector3 vectorTranslate) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
        this.axis = axis;
        this.angle = angle;
        this.vectorTranslate = vectorTranslate;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public void setScaleZ(float scaleZ) {
        this.scaleZ = scaleZ;
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
}
