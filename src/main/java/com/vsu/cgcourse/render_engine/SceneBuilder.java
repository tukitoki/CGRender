package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.model.MeshContext;

import java.util.ArrayList;

public class SceneBuilder {

    private ArrayList<MeshContext> meshContexts;

    public SceneBuilder() {
        meshContexts = new ArrayList<>();
    }

    public SceneBuilder(ArrayList<MeshContext> meshContexts) {
        this.meshContexts = meshContexts;
    }

    public ArrayList<MeshContext> getMeshContexts() {
        return meshContexts;
    }

    public void setMeshContexts(ArrayList<MeshContext> meshContexts) {
        this.meshContexts = meshContexts;
    }
}
