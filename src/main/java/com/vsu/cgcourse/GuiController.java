package com.vsu.cgcourse;

import com.vsu.cgcourse.math.Matrix3;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.DeleteFace;
import com.vsu.cgcourse.model.MeshContext;
import com.vsu.cgcourse.obj_writer.ObjWriter;
import com.vsu.cgcourse.render_engine.SceneBuilder;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;

import com.vsu.cgcourse.obj_reader.ObjReader;
import com.vsu.cgcourse.render_engine.Camera;
import com.vsu.cgcourse.render_engine.RenderEngine;

public class GuiController {

    final private float TRANSLATION = 2F;

    @FXML
    AnchorPane anchorPane;

    RenderEngine renderEngine = new RenderEngine();


    @FXML
    private Canvas canvas;

    private SceneBuilder sceneBuilder = new SceneBuilder();

    private Camera camera = new Camera(
            new Vector3(new float[]{0, 00, 100}),
            new Vector3(new float[]{0, 0, 0}),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        sceneBuilder.getMeshContexts().add(new MeshContext(null));

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));
            for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
                if (sceneBuilder.getMeshContexts().get(i).getMesh() != null) {
                    try {
                        renderEngine.render(canvas.getGraphicsContext2D(), camera, (int) width, (int) height,
                                sceneBuilder.getMeshContexts().get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenFacesMenu() {
//        Stage stageFaces = new Stage(StageStyle.UTILITY);
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        Group group = new Group();
//        VBox vBox = new VBox();
//        ScrollPane scrollPane = new ScrollPane(vBox);
//        scrollPane.setPrefWidth(200);
//        scrollPane.setPrefHeight(300);
//        stageFaces.setX(screenSize.getWidth() - 10 - scrollPane.getPrefWidth());
//        stageFaces.setY(screenSize.getHeight() / 3);
//        int index = 0;
//        for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
//            if (sceneBuilder.getMeshContexts().get(i).getStatus().isSelected()) {
//                index = i;
//            }
//        }
//        Scene sceneFaces = new Scene(group, scrollPane.getPrefWidth(), scrollPane.getPrefHeight() + 50);
//        scrollPane.setPrefViewportHeight(sceneBuilder.getMeshContexts().get(index).getMesh().getPolygons().
//                getPolygonVertexIndices().size() * 10);
//        for (int i = 0; i < sceneBuilder.getMeshContexts().get(index).getMesh().getPolygons().getPolygonVertexIndices().size(); i++) {
//            CheckBox checkBox = new CheckBox();
//            checkBox.setText(" " + (i + 1) + "f");
//            vBox.getChildren().add(checkBox);
//            int finalIndex = index;
//            int finalI = i;
//            checkBox.setOnAction(actionEvent -> {
//                if (checkBox.isSelected()) {
//                    sceneBuilder.getMeshContexts().get(finalIndex).getVerticesDeleteIndices().add(finalI);
//                }
//            });
//        }
//        Button buttonCheck = new Button("Accept");
//        buttonCheck.setLayoutX(50);
//        buttonCheck.setLayoutY(scrollPane.getLayoutX() + scrollPane.getPrefHeight() + 10);
//        buttonCheck.setPrefSize(100, 30);
//        int finalIndex1 = index;
//        buttonCheck.setOnAction(actionEvent -> {
//            try {
//                DeleteFace.deleteFace(sceneBuilder.getMeshContexts().get(finalIndex1).getMesh(),
//                        sceneBuilder.getMeshContexts().get(finalIndex1).getVerticesDeleteIndices(), true);
//                sceneBuilder.getMeshContexts().get(finalIndex1).getVerticesDeleteIndices().clear();
//                sceneBuilder.getMeshContexts().get(finalIndex1).getStatus().setSelected(true);
//                stageFaces.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        group.getChildren().add(scrollPane);
//        group.getChildren().add(buttonCheck);
//        stageFaces.setScene(sceneFaces);
//        stageFaces.setResizable(false);
//        stageFaces.show();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        fileChooser.setInitialDirectory(new File("src/main/resources/com/vsu/cgcourse/models"));
        File file = fileChooser.showOpenDialog( canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            sceneBuilder.getMeshContexts().add(new MeshContext(null));
            if (sceneBuilder.getMeshContexts().size() == 2 && sceneBuilder.getMeshContexts().get(0).getMesh() == null) {
                sceneBuilder.getMeshContexts().remove(0);
            }
            modelPane.setVisible(true);
            sceneBuilder.getMeshContexts().get(sceneBuilder.getMeshContexts().size() - 1).setOldMesh(ObjReader.read(fileContent));
            sceneBuilder.getMeshContexts().get(sceneBuilder.getMeshContexts().size() - 1).setMesh(ObjReader.read(fileContent));
            sceneBuilder.getMeshContexts().get(sceneBuilder.getMeshContexts().size() - 1).setNewMeshConverter();
            int counter = 0;
            for (Node node : modelGridPane.getChildren()) {
                if (node != null) {
                    counter++;
                }
            }
            ToggleButton tb = new ToggleButton(fileName.getFileName().toString());
            tb.setOnAction(actionEvent -> {
                int index = 0;
                for (Node node : modelGridPane.getChildren()) {
                    if (node != null) {
                        ToggleButton tb2 = (ToggleButton) node;
                        if (tb2 != tb) {
                            sceneBuilder.getMeshContexts().get(index).getStatus().setSelected(false);
                            tb2.setSelected(false);
                        } else {
                            sceneBuilder.getMeshContexts().get(index).getStatus().setSelected(true);
                            tb2.setSelected(true);
                        }
                    }
                    index++;
                }
            });
            modelGridPane.add(tb, 0, counter);
        } catch (Exception exception) {
            StackPane stackPane = new StackPane();
            Scene scene = new Scene(stackPane, 600, 120);
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setTitle("Cannot read model");
            stage.centerOnScreen();
            Label label = new Label(exception.getMessage());
            label.setFont(new javafx.scene.text.Font(15));
            label.setAlignment(Pos.CENTER);
            stackPane.getChildren().add(label);
            stackPane.setAlignment(label, Pos.CENTER);
            stage.setScene(scene);
            stage.show();
            exception.printStackTrace();
        }
    }

    @FXML
    private void onSaveModelMenuItemClick() {
        if (sceneBuilder.getMeshContexts().get(0).getMesh() != null) {
            StackPane stackPane1 = new StackPane();
            Scene scene1 = new Scene(stackPane1, 600, 120);
            Stage stage1 = new Stage(StageStyle.UTILITY);
            stage1.setTitle("Cannot write model");
            stage1.centerOnScreen();
            Label label1 = new Label("Do you agree with changes?");
            label1.setFont(new Font(15));
            label1.setAlignment(Pos.CENTER);
            Button buttonAccept = new Button("Yes");
            buttonAccept.setOnAction(actionEvent -> {
                sceneBuilder.getMeshContexts().get(0).getStatus().setChanges(true);
                stage1.close();
            });
            Button buttonDecline = new Button("No");
            buttonDecline.setOnAction(actionEvent -> {
                sceneBuilder.getMeshContexts().get(0).getStatus().setChanges(false);
                stage1.close();
            });
            stackPane1.getChildren().addAll(label1, buttonDecline, buttonAccept);
            stackPane1.setAlignment(label1, Pos.CENTER);
            stackPane1.setAlignment(buttonAccept, Pos.BOTTOM_RIGHT);
            buttonAccept.setPrefSize(60, 40);
            stackPane1.setAlignment(buttonDecline, Pos.BOTTOM_LEFT);
            buttonDecline.setPrefSize(60, 40);
            stage1.setScene(scene1);
            //stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.showAndWait();
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        fileChooser.setInitialDirectory(new File("src/main/resources/com/vsu/cgcourse/models"));

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        try {
            for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
                if (sceneBuilder.getMeshContexts().get(i).getStatus().isSelected()) {
                    ObjWriter.write(file, sceneBuilder.getMeshContexts().get(i));
                }
            }
        } catch (Exception exception) {
            StackPane stackPane = new StackPane();
            Scene scene = new Scene(stackPane, 600, 120);
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setTitle("Cannot write model");
            stage.centerOnScreen();
            Label label = new Label(exception.getMessage());
            label.setFont(new Font(15));
            label.setAlignment(Pos.CENTER);
            stackPane.getChildren().add(label);
            stackPane.setAlignment(label, Pos.CENTER);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    TitledPane modelPane;
    @FXML
    GridPane modelGridPane;

    @FXML
    public void testAct(ActionEvent actionEvent) throws Exception {
        modelGridPane.add(new Label("sdada"), 0, 0);
    }
    @FXML
    AnchorPane rotateScaleTranslatePane;
    @FXML
    TextField scaleX;
    @FXML
    TextField scaleY;
    @FXML
    TextField scaleZ;
    @FXML
    TextField translationX;
    @FXML
    TextField translationY;
    @FXML
    TextField translationZ;
    @FXML
    TextField rotateX;
    @FXML
    TextField rotateY;
    @FXML
    TextField rotateZ;
    @FXML
    Button acceptButton;
    @FXML
    Button closeButton;

    @FXML
    public void openRotateScaleTranslate(ActionEvent actionEvent) throws Exception {
        rotateScaleTranslatePane.setVisible(true);
    }

    @FXML
    public void cancelRotateScaleTranslate(ActionEvent actionEvent) throws Exception {
        rotateScaleTranslatePane.setVisible(false);
    }

    private void scaleModel() {
        float x, y, z;
        if (scaleX.getText().length() != 0) {
            x = Float.parseFloat(scaleX.getText());
        } else {
            x = 1;
        }
        if (scaleY.getText().length() != 0) {
            y = Float.parseFloat(scaleY.getText());
        } else {
            y = 1;
        }
        if (scaleZ.getText().length() != 0) {
            z = Float.parseFloat(scaleZ.getText());
        } else {
            z = 1;
        }
        for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
            if (sceneBuilder.getMeshContexts().get(i).getStatus().isSelected()) {
                sceneBuilder.getMeshContexts().get(i).getConverter().setScaleX(x);
                sceneBuilder.getMeshContexts().get(i).getConverter().setScaleY(y);
                sceneBuilder.getMeshContexts().get(i).getConverter().setScaleZ(z);
                break;
            }
        }
    }

    private void rotateModel() {
        float angleX = Float.parseFloat(rotateX.getText());
        float angleY = Float.parseFloat(rotateY.getText());
        float angleZ = Float.parseFloat(rotateZ.getText());
        for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
            if (sceneBuilder.getMeshContexts().get(i).getStatus().isSelected()) {
                sceneBuilder.getMeshContexts().get(i).getConverter().setAngle(angleX, angleY, angleZ);
                break;
            }
        }
    }

    private void translateModel() {
        float x, y, z;
        if (translationX.getText().length() != 0) {
            x = Float.parseFloat(translationX.getText());
        } else {
            x = 0;
        }
        if (translationY.getText().length() != 0) {
            y = Float.parseFloat(translationY.getText());
        } else {
            y = 0;
        }
        if (translationZ.getText().length() != 0) {
            z = Float.parseFloat(translationZ.getText());
        } else {
            z = 0;
        }
        for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
            if (sceneBuilder.getMeshContexts().get(i).getStatus().isSelected()) {
                sceneBuilder.getMeshContexts().get(i).getConverter().setVectorTranslate(new Vector3(new float[] {x, y, z}));
                break;
            }
        }
    }
    @FXML
    public void acceptRotateScaleTranslate(ActionEvent actionEvent) throws Exception {
        scaleModel();
        translateModel();
        rotateModel();
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[]{0, 0, -TRANSLATION}));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[]{0, 0, TRANSLATION}));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[]{TRANSLATION, 0, 0}));
        camera.moveTarget(new Vector3(new float[] {TRANSLATION, 0, 0}));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[]{-TRANSLATION, 0, 0}));
        camera.moveTarget(new Vector3(new float[] {-TRANSLATION, 0, 0}));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[]{0, TRANSLATION, 0}));
        camera.moveTarget(new Vector3(new float[] {0, TRANSLATION, 0}));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[]{0, -TRANSLATION, 0}));
        camera.moveTarget(new Vector3(new float[] {0, -TRANSLATION, 0}));
    }

    @FXML
    public void handleCameraRotateLeft(ActionEvent actionEvent) throws Exception {
        float rad = (float) Math.toRadians(1);
        camera.rotate(new Matrix3(new float[][]{
                {(float) Math.cos(rad), 0, (float) Math.sin(rad)},
                {0, 1, 0},
                {(float) -Math.sin(rad),0, (float) Math.cos(rad)}
        }));
    }

    @FXML
    public void handleCameraRotateRight(ActionEvent actionEvent) throws Exception {
        float rad = (float) Math.toRadians(-1);
        camera.rotate(new Matrix3(new float[][]{
                {(float) Math.cos(rad), 0, (float) Math.sin(rad)},
                {0, 1, 0},
                {(float) -Math.sin(rad),0, (float) Math.cos(rad)}
        }));
    }

    @FXML
    public void handleCameraRotateUp(ActionEvent actionEvent) throws Exception {
        float rad = (float) Math.toRadians(1);
        camera.rotate(new Matrix3(new float[][]{
                {1, 0, 0},
                {0, (float) Math.cos(rad), (float) Math.sin(rad)},
                {0, (float) -Math.sin(rad), (float) Math.cos(rad)}
        }));
    }

    @FXML
    public void handleCameraRotateDown(ActionEvent actionEvent) throws Exception {
        float rad = (float) Math.toRadians(-1);
        camera.rotate(new Matrix3(new float[][]{
                {1, 0, 0},
                {0, (float) Math.cos(rad), (float) Math.sin(rad)},
                {0, (float) -Math.sin(rad), (float) Math.cos(rad)}
        }));
    }
}