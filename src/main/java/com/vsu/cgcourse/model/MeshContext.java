package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.render_engine.Converter;

import java.util.ArrayList;

public class MeshContext {

    private Mesh oldMesh;
    private Mesh mesh;
    private Converter converter;
    private ArrayList<Integer> verticesDeleteIndices;
    private MeshStatus status;


    public MeshContext(float x, float y, float z, char axis, float angle) {}

    public MeshContext(float scaleX, float scaleY, float scaleZ, float angleX, float angleY, float angleZ) {

        mesh = new Mesh();
        converter = new Converter(scaleX, scaleY, scaleZ, angleX, angleY, angleZ, new Vector3(new float[] {0, 0, 0}));
    }

    public MeshContext(Mesh mesh) {
        this.mesh = mesh;
        this.converter = new Converter(1, 1, 1, 0, 0, 0, new Vector3(new float[] {0, 0, 0}));
        verticesDeleteIndices = new ArrayList<>();
        status = new MeshStatus();
    }

    public void setNewMeshConverter() {
        this.converter = new Converter(1, 1, 1, 0, 0, 0,  new Vector3(new float[] {0, 0, 0}));
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

    public MeshStatus getStatus() {
        return status;
    }

    public void setStatus(MeshStatus status) {
        this.status = status;
    }
}
