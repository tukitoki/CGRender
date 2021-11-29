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

    public Converter(float x, float y, float z, char axis, float angle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.axis = axis;
        this.angle = angle;
    }

    public Matrix4 translate(Vector3 vector3) throws Exception {
        return new Matrix4(new float[][]{
                    {1, 0, 0, vector3.getX()},
                    {0, 1, 0, vector3.getY()},
                    {0, 0, 1, vector3.getZ()},
                    {0, 0, 0, 1}});
    }

    public Matrix3 scale() throws Exception {
        return new Matrix3(new float[][]{
                    {x, 0, 0},
                    {0, y, 0},
                    {0, 0, z}
        });
    }

    public  Matrix3 rotate()
            throws Exception {
        if(axis == 'x') {
            return new Matrix3(new float[][]{
                    {1, 0, 0},
                    {0, (float) Math.cos(angle), (float) Math.sin(angle)},
                    {0, (float) -Math.sin(angle), (float) Math.cos(angle)}
            });
        }
        if(axis == 'y') {
            return new Matrix3(new float[][]{
                    {(float) Math.cos(angle), 0, (float) Math.sin(angle)},
                    {0, 1, 0},
                    {(float) -Math.sin(angle),0, (float) Math.cos(angle)}
            });
        }
        if(axis == 'z') {
            return new Matrix3(new float[][]{
                    {(float) Math.cos(angle), (float) Math.sin(angle), 0},
                    {(float) -Math.sin(angle), (float) Math.cos(angle), 0},
                    {0, 0, 1}
            });
        }
        return null;
    }
}
