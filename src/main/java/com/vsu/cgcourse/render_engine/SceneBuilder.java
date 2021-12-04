package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.model.MeshContext;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class SceneBuilder {

    private ArrayList<MeshContext> meshContexts;
    private boolean replaceable = false;
    private Stage sceneStage;

    public SceneBuilder() {
        meshContexts = new ArrayList<>();
        sceneStage = new Stage();
    }

    public SceneBuilder(ArrayList<MeshContext> meshContexts) {
        this.meshContexts = meshContexts;
        initStage();
    }

    public void initStage() {
        sceneStage = new Stage();
        sceneStage.initStyle(StageStyle.UTILITY);
        sceneStage.setTitle("Models controller");
    }

    public ArrayList<MeshContext> getMeshContexts() {
        return meshContexts;
    }

    public void setMeshContexts(ArrayList<MeshContext> meshContexts) {
        this.meshContexts = meshContexts;
    }

    public boolean isReplaceable() {
        return replaceable;
    }

    public void setReplaceable(boolean replaceable) {
        this.replaceable = replaceable;
    }

    public Stage getSceneStage() {
        return sceneStage;
    }

    public void setSceneStage(Stage sceneStage) {
        this.sceneStage = sceneStage;
    }
}
