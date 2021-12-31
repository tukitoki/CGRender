package com.vsu.cgcourse.math;

public class Quaternion {

    protected float s;
    protected Vector3 v;

    public Quaternion() {
    }

    public Quaternion(float s, Vector3 v) {
        this.s = s;
        this.v = v;
    }

    public float getS() {
        return s;
    }

    public void setS(float s) {
        this.s = s;
    }

    public Vector3 getV() {
        return v;
    }

    public void setV(Vector3 v) {
        this.v = v;
    }

    public void sum(Quaternion second) throws Exception {
        this.s += second.s;
        this.v.sum(second.v);
    }

    public Quaternion summation(Quaternion second) throws Exception {
        return new Quaternion(this.s + second.s, new Vector3(this.v.summation(second.v)));
    }

    public void sub(Quaternion second) throws Exception {
        this.s -= second.s;
        this.v.sub(second.v);
    }

    public Quaternion subtract(Quaternion second) throws Exception {
        return new Quaternion(this.s - second.s, new Vector3(this.v.subtract(second.v)));
    }

    public Quaternion multiply(Quaternion second) throws Exception {
        Vector3 v1 = new Vector3(second.v.multiplyScal(this.s));
        Vector3 v2 = new Vector3(this.v.multiplyScal(second.s));
        Vector3 v3 = new Vector3(v1.summation(v2));
        return new Quaternion(this.s * second.s - this.v.scalarMultiply(second.v),
                new Vector3(v3.summation(this.v.vectorMultiply(second.v))));
    }
}
