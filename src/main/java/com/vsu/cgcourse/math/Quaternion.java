package com.vsu.cgcourse.math;

public class Quaternions{

    protected float s;
    protected Vector3 v;

    public Quaternions() {}

    public Quaternions(float s, Vector3 v) {
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

    public void sum(Quaternions second) throws Exception {
        this.s += second.s;
        this.v.sum(second.v);
    }

    public Quaternions summation(Quaternions second) throws Exception {
        return new Quaternions(this.s + second.s, new Vector3(this.v.summation(second.v)));
    }

    public void sub(Quaternions second) throws Exception {
        this.s -= second.s;
        this.v.sub(second.v);
    }

    public Quaternions subtract(Quaternions second) throws Exception {
        return new Quaternions(this.s - second.s, new Vector3(this.v.subtract(second.v)));
    }

    public Quaternions multiply(Quaternions second) throws Exception {
        Vector3 v1 = new Vector3(second.v.multiplyScal(this.s));
        Vector3 v2 = new Vector3(this.v.multiplyScal(second.s));
        Vector3 v3 = new Vector3(v1.summation(v2));
        return new Quaternions(this.s * second.s - this.v.scalarMultiply(second.v),
                new Vector3(v3.summation(this.v.vectorMultiply(second.v))));
    }
