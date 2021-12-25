package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;

import java.util.Objects;

public class Vertexes {

    protected Vector3 v;
    protected Vector2 vt;
    protected Vector3 vn;

    public Vertexes(Vector3 v, Vector2 vt, Vector3 vn) {
        this.v = v;
        this.vt = vt;
        this.vn = vn;
    }

    public Vector3 getV() {
        return v;
    }

    public void setV(Vector3 v) {
        this.v = v;
    }

    public Vector2 getVt() {
        return vt;
    }

    public void setVt(Vector2 vt) {
        this.vt = vt;
    }

    public Vector3 getVn() {
        return vn;
    }

    public void setVn(Vector3 vn) {
        this.vn = vn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertexes vertexes = (Vertexes) o;
        return Objects.equals(v, vertexes.v) && Objects.equals(vt, vertexes.vt) && Objects.equals(vn, vertexes.vn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(v, vt, vn);
    }
}
