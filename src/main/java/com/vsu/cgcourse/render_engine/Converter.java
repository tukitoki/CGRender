package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.Matrix3;
import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector3;


public class Converter {
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private float angleX;
    private float angleY;
    private float angleZ;
    private Vector3 vectorTranslate;

    public Converter(float scaleX, float scaleY, float scaleZ, float angleX, float angleY, float angleZ, Vector3 vectorTranslate) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
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

    public float getAngleX() {
        return angleX;
    }

    public void setAngleX(float angleX) {
        this.angleX = angleX;
    }

    public float getAngleY() {
        return angleY;
    }

    public void setAngleY(float angleY) {
        this.angleY = angleY;
    }

    public float getAngleZ() {
        return angleZ;
    }

    public void setAngleZ(float angleZ) {
        this.angleZ = angleZ;
    }

    public void setAngle(float angleX, float angleY, float angleZ) {
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
    }

    public Vector3 getVectorTranslate() {
        return vectorTranslate;
    }

    public void setVectorTranslate(Vector3 vectorTranslate) {
        this.vectorTranslate = vectorTranslate;
    }
}
