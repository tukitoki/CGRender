package com.vsu.cgcourse;

import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.MeshContext;
import com.vsu.cgcourse.obj_writer.ObjWriter;
import com.vsu.cgcourse.render_engine.Converter;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;

import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.obj_reader.ObjReader;
import com.vsu.cgcourse.render_engine.Camera;
import com.vsu.cgcourse.render_engine.RenderEngine;

public class GuiController {

    final private float TRANSLATION = 2F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Mesh mesh;

    private Camera camera = new Camera(
            new Vector3(new float[] {0, 00, 100}),
            new Vector3(new float[] {0, 0, 0}),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));
            if (mesh != null) {
                try {
                    RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height,
                            new Converter(1, 1, 1, 'f', 0, new Vector3(new float[] {0, 0, 0})));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        fileChooser.setInitialDirectory(new File("src/main/resources/com/vsu/cgcourse/models"));
        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    private void onSaveModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        fileChooser.setInitialDirectory(new File("src/main/resources/com/vsu/cgcourse/models"));

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            ObjWriter.write(mesh, file);
            // todo: обработка ошибок
        } catch (Exception exception) {

        }
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
        TextField textFieldX = new TextField();
        textFieldX.setLayoutX(textX.getX());
        textFieldX.setLayoutY(textX.getY() + 7);
        textFieldX.setPrefSize(40, 20);

        Text textY = new Text("Y :");
        textY.setX(textFieldX.getLayoutX());
        textY.setY(textFieldX.getLayoutY() + textFieldX.getPrefHeight() + 20);
        TextField textFieldY = new TextField();
        textFieldY.setLayoutX(textX.getX());
        textFieldY.setLayoutY(textY.getY() + 7);
        textFieldY.setPrefSize(textFieldX.getPrefWidth(), textFieldX.getPrefHeight());

        Text textZ = new Text("Z :");
        textZ.setX(textFieldY.getLayoutX());
        textZ.setY(textFieldY.getLayoutY() + textFieldY.getPrefHeight() + 20);
        TextField textFieldZ = new TextField();
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
            anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
            anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

            KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
                double width = canvas.getWidth();
                double height = canvas.getHeight();

                canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
                camera.setAspectRatio((float) (width / height));
                if (mesh != null) {
                    try {
                        RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh,
                                (int) canvas.getWidth(), (int) canvas.getHeight(),
                                new Converter(x, y, z, ' ', 0, new Vector3(new float[] {0, 0, 0})));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                event.consume();
            });
            timeline.getKeyFrames().add(frame);
            timeline.play();
            actionEvent.consume();
        });
        group.getChildren().addAll(textX,textFieldX, textY, textFieldY, textZ, textFieldZ, buttonAccept);
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
            anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
            anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

            KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
                double width = canvas.getWidth();
                double height = canvas.getHeight();

                canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
                camera.setAspectRatio((float) (width / height));
                if (mesh != null) {
                    try {
                        RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh,
                                (int) canvas.getWidth(), (int) canvas.getHeight(),
                                new Converter(1, 1, 1, axis, angle, new Vector3(new float[] {0, 0, 0})));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                event.consume();
            });
            timeline.getKeyFrames().add(frame);
            timeline.play();
            actionEvent.consume();
        });
        group.getChildren().addAll(textAxis, textFieldAxis, textAngle, textFieldAngle, buttonAccept);
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
        TextField textFieldX = new TextField();
        textFieldX.setLayoutX(textX.getX());
        textFieldX.setLayoutY(textX.getY() + 7);
        textFieldX.setPrefSize(40, 20);

        Text textY = new Text("Y :");
        textY.setX(textFieldX.getLayoutX());
        textY.setY(textFieldX.getLayoutY() + textFieldX.getPrefHeight() + 20);
        TextField textFieldY = new TextField();
        textFieldY.setLayoutX(textX.getX());
        textFieldY.setLayoutY(textY.getY() + 7);
        textFieldY.setPrefSize(textFieldX.getPrefWidth(), textFieldX.getPrefHeight());

        Text textZ = new Text("Z :");
        textZ.setX(textFieldY.getLayoutX());
        textZ.setY(textFieldY.getLayoutY() + textFieldY.getPrefHeight() + 20);
        TextField textFieldZ = new TextField();
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
            anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
            anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

            KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
                double width = canvas.getWidth();
                double height = canvas.getHeight();

                canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
                camera.setAspectRatio((float) (width / height));
                if (mesh != null) {
                    try {
                        RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh,
                                (int) canvas.getWidth(), (int) canvas.getHeight(), );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                event.consume();
            });
            timeline.getKeyFrames().add(frame);
            timeline.play();
            actionEvent.consume();
        });
        group.getChildren().addAll(textVectorCoords, textFieldX, textFieldY, textFieldZ, textX, textY, textZ, buttonAccept);
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
        camera.movePosition(new Vector3(new float[] {0, 0, -TRANSLATION}));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[] {0, 0, TRANSLATION}));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[] {TRANSLATION, 0, 0}));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[] {-TRANSLATION, 0, 0}));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[] {0, TRANSLATION, 0}));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) throws Exception {
        camera.movePosition(new Vector3(new float[] {0, -TRANSLATION, 0}));
    }

}