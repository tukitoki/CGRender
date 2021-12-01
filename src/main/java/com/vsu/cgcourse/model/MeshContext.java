package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.render_engine.Converter;

public class MeshContext {

    private Mesh mesh;
    private Converter converter;

    public MeshContext() {
        mesh = new Mesh();
        converter = new Converter(0, 0, 0, ' ', 0, new Vector3(new float[] {0, 0, 0}));
    }

    public MeshContext(Mesh mesh, Converter converter) {
        this.mesh = mesh;
        this.converter = converter;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }
}
