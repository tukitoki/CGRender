package com.vsu.cgcourse;

import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.DeleteFace;
import com.vsu.cgcourse.model.MeshContext;
import com.vsu.cgcourse.obj_writer.ObjWriter;
import com.vsu.cgcourse.render_engine.SceneBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.util.ArrayList;

import com.vsu.cgcourse.obj_reader.ObjReader;
import com.vsu.cgcourse.render_engine.Camera;
import com.vsu.cgcourse.render_engine.RenderEngine;

public class GuiController {

    final private float TRANSLATION = 2F;

    @FXML
    AnchorPane anchorPane;

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
                        RenderEngine.render(canvas.getGraphicsContext2D(), camera, (int) width, (int) height,
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
        Stage stageFaces = new Stage(StageStyle.UTILITY);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stageFaces.setX(screenSize.getWidth() - 310);
        stageFaces.setY(screenSize.getHeight() / 3);
        Group group = new Group();
        VBox vBox = new VBox();
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPrefWidth(300);
        scrollPane.setPrefHeight(300);
        int index = 0;
        for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
            if (sceneBuilder.getMeshContexts().get(i).getConverter().isTransform()) {
                index = i;
            }
        }
        Scene sceneFaces = new Scene(group, scrollPane.getPrefWidth(), scrollPane.getPrefHeight() + 50);
        scrollPane.setPrefViewportHeight(sceneBuilder.getMeshContexts().get(index).getMesh().getPolygons().
                getPolygonVertexIndices().size() * 10);
        for (int i = 0; i < sceneBuilder.getMeshContexts().get(index).getMesh().getPolygons().getPolygonVertexIndices().size(); i++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(" " + (i + 1) + "f");
            vBox.getChildren().add(checkBox);
            int finalIndex = index;
            int finalI = i;
            checkBox.setOnAction(actionEvent -> {
                if (checkBox.isSelected()) {
                    sceneBuilder.getMeshContexts().get(finalIndex).getVerticesDeleteIndices().add(finalI);
                }
            });
        }
        Button buttonCheck = new Button("Accept");
        buttonCheck.setLayoutX(80);
        buttonCheck.setLayoutY(scrollPane.getLayoutX() + scrollPane.getPrefHeight() + 10);
        buttonCheck.setPrefSize(150, 30);
        int finalIndex1 = index;
        buttonCheck.setOnAction(actionEvent -> {
            try {
                DeleteFace.deleteFace(sceneBuilder.getMeshContexts().get(finalIndex1).getMesh(),
                        sceneBuilder.getMeshContexts().get(finalIndex1).getVerticesDeleteIndices(), true);
                sceneBuilder.getMeshContexts().get(finalIndex1).getVerticesDeleteIndices().clear();
                sceneBuilder.getMeshContexts().get(finalIndex1).getConverter().setTransform(true);
                stageFaces.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        group.getChildren().add(scrollPane);
        group.getChildren().add(buttonCheck);
        stageFaces.setScene(sceneFaces);
        stageFaces.setResizable(false);
        stageFaces.show();
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

        if (sceneBuilder.getMeshContexts().get(0).getMesh() != null) {
            StackPane stackPane1 = new StackPane();
            Scene scene1 = new Scene(stackPane1, 600, 120);
            Stage stage1 = new Stage(StageStyle.UTILITY);
            stage1.setTitle("Load model");
            stage1.centerOnScreen();
            Label label1 = new Label("Do you want add new model or replace exist model?");
            label1.setFont(new Font(15));
            label1.setAlignment(Pos.CENTER);
            Button buttonAccept = new Button("New");
            buttonAccept.setOnAction(actionEvent -> {
                sceneBuilder.setReplaceable(false);
                stage1.close();
            });
            Button buttonDecline = new Button("Replace");
            buttonDecline.setOnAction(actionEvent -> {
                sceneBuilder.setReplaceable(true);
                stage1.close();
            });
            stackPane1.getChildren().addAll(label1, buttonDecline, buttonAccept);
            stackPane1.setAlignment(label1, Pos.CENTER);
            stackPane1.setAlignment(buttonAccept, Pos.BOTTOM_RIGHT);
            buttonAccept.setPrefSize(60, 40);
            stackPane1.setAlignment(buttonDecline, Pos.BOTTOM_LEFT);
            buttonDecline.setPrefSize(60, 40);
            stage1.setScene(scene1);
            stage1.showAndWait();
        }
        try {
            String fileContent = Files.readString(fileName);
            if (sceneBuilder.isReplaceable()) {
                StackPane newStackPane = new StackPane();
                Scene newScene = new Scene(newStackPane, 600, 120);
                Stage newStage = new Stage(StageStyle.UTILITY);
                newStage.setTitle("Number of model");
                newStage.centerOnScreen();
                Label replaceLabel = new Label("Type number of model that you want to replace");
                replaceLabel.setFont(new Font(15));
                replaceLabel.setAlignment(Pos.CENTER);
                TextField numberTextField = new TextField();
                numberTextField.setPrefWidth(200);
                Button buttonReadNumber = new Button("New");
                buttonReadNumber.setOnAction(actionEvent -> {
                    newStage.close();
                    sceneBuilder.getMeshContexts().get(Integer.parseInt(numberTextField.getText())).setMesh(ObjReader.read(fileContent));
                    sceneBuilder.getMeshContexts().get(Integer.parseInt(numberTextField.getText())).setNewMeshConverter();
                    sceneBuilder.getMeshContexts().get(Integer.parseInt(numberTextField.getText())).setVerticesDeleteIndices(new ArrayList<>());
                    if (sceneBuilder.getMeshContexts().size() > 1) {
                        drawRadioButtons(sceneBuilder);
                    }
                });
                newStackPane.setAlignment(numberTextField, Pos.CENTER);
                newStackPane.setAlignment(buttonReadNumber, Pos.BOTTOM_CENTER);
                newStackPane.getChildren().addAll(replaceLabel, numberTextField, buttonReadNumber);
                newStage.setScene(newScene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.showAndWait();
            } else {
                sceneBuilder.getMeshContexts().add(new MeshContext(null));
                if (sceneBuilder.getMeshContexts().size() == 2 && sceneBuilder.getMeshContexts().get(0).getMesh() == null) {
                    sceneBuilder.getMeshContexts().remove(0);
                }
                if (sceneBuilder.getMeshContexts().size() > 1) {
                    drawRadioButtons(sceneBuilder);
                }
                sceneBuilder.getMeshContexts().get(sceneBuilder.getMeshContexts().size() - 1).setMesh(ObjReader.read(fileContent));
                sceneBuilder.getMeshContexts().get(sceneBuilder.getMeshContexts().size() - 1).setNewMeshConverter();
            }
            // todo: обработка ошибок
        } catch (Exception exception) {
            StackPane stackPane = new StackPane();
            Scene scene = new Scene(stackPane, 600, 120);
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setTitle("Cannot read model");
            stage.centerOnScreen();
            javafx.scene.control.Label label = new javafx.scene.control.Label(exception.getMessage());
            label.setFont(new javafx.scene.text.Font(15));
            label.setAlignment(Pos.CENTER);
            stackPane.getChildren().add(label);
            stackPane.setAlignment(label, Pos.CENTER);
            stage.setScene(scene);
            stage.show();
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
                sceneBuilder.getMeshContexts().get(0).setChanges(true);
                stage1.close();
            });
            Button buttonDecline = new Button("No");
            buttonDecline.setOnAction(actionEvent -> {
                sceneBuilder.getMeshContexts().get(0).setChanges(false);
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
            ObjWriter.write(sceneBuilder.getMeshContexts().get(0).getMesh(), file, sceneBuilder.getMeshContexts().get(0));
            // todo: обработка ошибок
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

    private void drawRadioButtons(SceneBuilder sceneBuilder) {
        sceneBuilder.getSceneStage().close();
        Group group = new Group();
        ToggleGroup toggleGroup = new ToggleGroup();
        Scene scene = new Scene(group, 500, 50);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
            RadioButton radioButton = new RadioButton();
            radioButton.setText("Model" + i);
            if (i != 0) {
                radioButton.setLayoutX(20 * (i + 4));
            } else {
                radioButton.setLayoutX(20 * (i + 1));
            }
            radioButton.setLayoutY(25);
            radioButton.setToggleGroup(toggleGroup);
            group.getChildren().add(radioButton);
        }
        toggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
                sceneBuilder.getMeshContexts().get(i).getConverter().setTransform(false);
            }
            sceneBuilder.getMeshContexts().get(group.getChildren().indexOf(toggleGroup.getSelectedToggle())).
                    getConverter().setTransform(true);
        });
        sceneBuilder.getSceneStage().setScene(scene);
        sceneBuilder.getSceneStage().setX(screenSize.getWidth() / 2);
        sceneBuilder.getSceneStage().setY(30);
        sceneBuilder.getSceneStage().setTitle("Models controller");
        sceneBuilder.getSceneStage().setAlwaysOnTop(true);
        sceneBuilder.getSceneStage().setResizable(false);
        sceneBuilder.getSceneStage().initStyle(StageStyle.UTILITY);
        sceneBuilder.getSceneStage().show();
    }

    private void drawScaleMenu() {
        Group group = new Group();
        Scene scene = new Scene(group, 120, 200);
        Stage stage = new Stage(StageStyle.UTILITY);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setTitle("Scale");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setX(0);
        stage.setY(screenSize.getHeight() / 3);

        Text textX = new Text("X :");
        textX.setX(40);
        textX.setY(20);
        TextField textFieldX = new TextField("1");
        textFieldX.setLayoutX(textX.getX());
        textFieldX.setLayoutY(textX.getY() + 7);
        textFieldX.setPrefSize(40, 20);

        Text textY = new Text("Y :");
        textY.setX(textFieldX.getLayoutX());
        textY.setY(textFieldX.getLayoutY() + textFieldX.getPrefHeight() + 20);
        TextField textFieldY = new TextField("1");
        textFieldY.setLayoutX(textX.getX());
        textFieldY.setLayoutY(textY.getY() + 7);
        textFieldY.setPrefSize(textFieldX.getPrefWidth(), textFieldX.getPrefHeight());

        Text textZ = new Text("Z :");
        textZ.setX(textFieldY.getLayoutX());
        textZ.setY(textFieldY.getLayoutY() + textFieldY.getPrefHeight() + 20);
        TextField textFieldZ = new TextField("1");
        textFieldZ.setLayoutX(textZ.getX());
        textFieldZ.setLayoutY(textZ.getY() + 7);
        textFieldZ.setPrefSize(textFieldY.getPrefWidth(), textFieldY.getPrefHeight());

        Button buttonAccept = new Button("Accept");
        buttonAccept.setLayoutX(30);
        buttonAccept.setLayoutY(textFieldZ.getLayoutY() + textFieldZ.getPrefHeight() + 10);
        buttonAccept.setPrefSize(60, 20);
        buttonAccept.setOnAction(actionEvent -> {
            float x, y, z;
            if (textFieldX.getText().length() != 0) {
                x = Float.parseFloat(textFieldX.getText());
            } else {
                x = 1;
            }
            if (textFieldY.getText().length() != 0) {
                y = Float.parseFloat(textFieldY.getText());
            } else {
                y = 1;
            }
            if (textFieldZ.getText().length() != 0) {
                z = Float.parseFloat(textFieldZ.getText());
            } else {
                z = 1;
            }
            for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
                if (sceneBuilder.getMeshContexts().get(i).getConverter().isTransform()) {
                    sceneBuilder.getMeshContexts().get(i).getConverter().setX(x);
                    sceneBuilder.getMeshContexts().get(i).getConverter().setY(y);
                    sceneBuilder.getMeshContexts().get(i).getConverter().setZ(z);
                    break;
                }
            }
        });
        group.getChildren().addAll(textX, textFieldX, textY, textFieldY, textZ, textFieldZ, buttonAccept);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    private void drawRotateMenu() {
        Group group = new Group();
        Scene scene = new Scene(group, 120, 130);
        Stage stage = new Stage(StageStyle.UTILITY);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setTitle("Rotate");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setX(0);
        stage.setY(screenSize.getHeight() / 3);

        Text textAxis = new Text("Axis : ");
        textAxis.setX(40);
        textAxis.setY(10);
        TextField textFieldAxis = new TextField();
        textFieldAxis.setLayoutX(textAxis.getX());
        textFieldAxis.setLayoutY(textAxis.getY() + 7);
        textFieldAxis.setPrefSize(40, 20);

        Text textAngle = new Text("Angle : ");
        textAngle.setX(textFieldAxis.getLayoutX());
        textAngle.setY(textFieldAxis.getLayoutY() + textFieldAxis.getPrefHeight() + 20);
        TextField textFieldAngle = new TextField();
        textFieldAngle.setLayoutX(textAngle.getX());
        textFieldAngle.setLayoutY(textAngle.getY() + 7);
        textFieldAngle.setPrefSize(textFieldAxis.getPrefWidth(), textFieldAxis.getPrefHeight());

        Button buttonAccept = new Button("Accept");
        buttonAccept.setLayoutX(30);
        buttonAccept.setLayoutY(textFieldAngle.getLayoutY() + textFieldAngle.getPrefHeight() + 10);
        buttonAccept.setPrefSize(60, 20);
        buttonAccept.setOnAction(actionEvent -> {
            char axis = textFieldAxis.getText().charAt(0);
            float angle = Float.parseFloat(textFieldAngle.getText());
            for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
                if (sceneBuilder.getMeshContexts().get(i).getConverter().isTransform()) {
                    sceneBuilder.getMeshContexts().get(i).getConverter().setAxis(axis);
                    sceneBuilder.getMeshContexts().get(i).getConverter().setAngle(angle);
                    break;
                }
            }
        });
        group.getChildren().addAll(textAxis, textFieldAxis, textAngle, textFieldAngle, buttonAccept);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    private void drawTranslateMenu() {
        Group group = new Group();
        Scene scene = new Scene(group, 130, 200);
        Stage stage = new Stage(StageStyle.UTILITY);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setTitle("Translate");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setX(0);
        stage.setY(screenSize.getHeight() / 3);

        Text textVectorCoords = new Text("Координаты вектора :");
        textVectorCoords.setX(5);
        textVectorCoords.setY(13);

        Text textX = new Text("X :");
        textX.setX(45);
        textX.setY(textVectorCoords.getY() + 17);
        TextField textFieldX = new TextField("1");
        textFieldX.setLayoutX(textX.getX());
        textFieldX.setLayoutY(textX.getY() + 7);
        textFieldX.setPrefSize(40, 20);

        Text textY = new Text("Y :");
        textY.setX(textFieldX.getLayoutX());
        textY.setY(textFieldX.getLayoutY() + textFieldX.getPrefHeight() + 20);
        TextField textFieldY = new TextField("1");
        textFieldY.setLayoutX(textX.getX());
        textFieldY.setLayoutY(textY.getY() + 7);
        textFieldY.setPrefSize(textFieldX.getPrefWidth(), textFieldX.getPrefHeight());

        Text textZ = new Text("Z :");
        textZ.setX(textFieldY.getLayoutX());
        textZ.setY(textFieldY.getLayoutY() + textFieldY.getPrefHeight() + 20);
        TextField textFieldZ = new TextField("1");
        textFieldZ.setLayoutX(textZ.getX());
        textFieldZ.setLayoutY(textZ.getY() + 7);
        textFieldZ.setPrefSize(textFieldY.getPrefWidth(), textFieldY.getPrefHeight());

        Button buttonAccept = new Button("Accept");
        buttonAccept.setLayoutX(35);
        buttonAccept.setLayoutY(textFieldZ.getLayoutY() + textFieldZ.getPrefHeight() + 10);
        buttonAccept.setPrefSize(60, 20);
        buttonAccept.setOnAction(actionEvent -> {
            float x, y, z;
            if (textFieldX.getText().length() != 0) {
                x = Float.parseFloat(textFieldX.getText());
            } else {
                x = 0;
            }
            if (textFieldY.getText().length() != 0) {
                y = Float.parseFloat(textFieldY.getText());
            } else {
                y = 0;
            }
            if (textFieldZ.getText().length() != 0) {
                z = Float.parseFloat(textFieldZ.getText());
            } else {
                z = 0;
            }
            for (int i = 0; i < sceneBuilder.getMeshContexts().size(); i++) {
                if (sceneBuilder.getMeshContexts().get(i).getConverter().isTransform()) {
                    sceneBuilder.getMeshContexts().get(i).getConverter().setVectorTranslate(new Vector3(new float[] {x, y, z}));
                    break;
                }
            }
        });
        group.getChildren().addAll(textVectorCoords, textFieldX, textFieldY, textFieldZ, textX, textY, textZ, buttonAccept);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    public void onOpenTranslateMenu(ActionEvent actionEvent) {
        drawTranslateMenu();
    }

    @FXML
    public void onOpenRotateMenu(ActionEvent actionEvent) {
        drawRotateMenu();
    }

    @FXML
    public void onOpenScaleMenu(ActionEvent actionEvent) throws Exception {
        drawScaleMenu();
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
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[]{-TRANSLATION, 0, 0}));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[]{0, TRANSLATION, 0}));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[]{0, -TRANSLATION, 0}));
    }

}