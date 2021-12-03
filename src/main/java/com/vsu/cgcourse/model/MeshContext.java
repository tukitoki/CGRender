package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.render_engine.Converter;

import java.util.ArrayList;

public class MeshContext {

    private Mesh mesh;
    private Converter converter;
    private boolean changes;
    private ArrayList<Integer> verticesDeleteIndices;

    public MeshContext() {
        mesh = new Mesh();
        converter = new Converter(1, 1, 1, ' ', 0, new Vector3(new float[] {0, 0, 0}));
        verticesDeleteIndices = new ArrayList<>();
    }

    public MeshContext(Mesh mesh) {
        this.mesh = mesh;
        this.converter = new Converter(1, 1, 1, ' ', 0, new Vector3(new float[] {0, 0, 0}));
        this.changes = false;
        verticesDeleteIndices = new ArrayList<>();
    }

    public void setNewMeshConverter() {
        this.converter = new Converter(1, 1, 1, ' ', 0, new Vector3(new float[] {0, 0, 0}));
    }

    public boolean isChanges() {
        return changes;
    }

    public void setChanges(boolean changes) {
        this.changes = changes;
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

    public ArrayList<Integer> getVerticesDeleteIndices() {
        return verticesDeleteIndices;
    }

    public void setVerticesDeleteIndices(ArrayList<Integer> verticesDeleteIndices) {
        this.verticesDeleteIndices = verticesDeleteIndices;
    }
}
