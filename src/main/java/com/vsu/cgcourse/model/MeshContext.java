package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.render_engine.Converter;

import java.util.ArrayList;

public class MeshContext {

    private Mesh oldMesh;
    private Mesh mesh;
    private Converter converter;
    private ArrayList<Integer> verticesDeleteIndices;
    private ModeStatus status;

    public MeshContext(float scaleX, float scaleY, float scaleZ, char axis, float angle) {
        mesh = new Mesh();
        converter = new Converter(scaleX, scaleY, scaleZ, axis, angle, new Vector3(new float[] {0, 0, 0}));
    }

    public MeshContext(Mesh mesh) {
        this.mesh = mesh;
        this.converter = new Converter(1, 1, 1, ' ', 0, new Vector3(new float[] {0, 0, 0}));
        verticesDeleteIndices = new ArrayList<>();
        status = new ModeStatus();
    }

    public void setNewMeshConverter() {
        this.converter = new Converter(1, 1, 1, ' ', 0, new Vector3(new float[] {0, 0, 0}));
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

    public Mesh getOldMesh() {
        return oldMesh;
    }

    public void setOldMesh(Mesh oldMesh) {
        this.oldMesh = oldMesh;
    }

    public ModeStatus getStatus() {
        return status;
    }

    public void setStatus(ModeStatus status) {
        this.status = status;
    }
}
