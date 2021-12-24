package com.vsu.cgcourse.render_engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.DrawTexture;
import com.vsu.cgcourse.model.MeshContext;
import com.vsu.cgcourse.model.Vertexes;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

import javax.vecmath.*;

import com.vsu.cgcourse.model.Mesh;
import javafx.scene.image.Image;

import static com.vsu.cgcourse.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final int width,
            final int height,
            MeshContext meshContext) throws Exception {
        Matrix4 modelMatrix = rotateScaleTranslate(meshContext);
        Matrix4 viewMatrix = camera.getViewMatrix();
        Matrix4 projectionMatrix = camera.getProjectionMatrix();

        Matrix4 modelViewProjectionMatrix = new Matrix4(modelMatrix);
        modelViewProjectionMatrix.multiply(viewMatrix);
        modelViewProjectionMatrix.multiply(projectionMatrix);
        modelViewProjectionMatrix.transposite();
        Collections.synchronizedList(meshContext.getMesh().getPolygons()).parallelStream().
                forEachOrdered(p1 -> {
                    final int nVerticesInPolygon = p1.getPolygonVertexIndices().size();
                    ArrayList<Vertexes> vertexes = new ArrayList<>();
                    for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                        Vector3 vertex = meshContext.getMesh().getVertices().get(p1
                                .getPolygonVertexIndices().get(vertexInPolygonInd));
                        Vector2 textureVertex = meshContext.getMesh().getTextureVertices().get(p1
                                .getPolygonTextureVertexIndices().get(vertexInPolygonInd));
                        Vector3 normalVertex = meshContext.getMesh().getNormals().get(p1
                                .getPolygonNormalIndices().get(vertexInPolygonInd));
                        Vector3 resultPoint = null;
                        try {
                           resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        vertexes.add(new Vertexes(resultPoint, textureVertex, normalVertex));
                    }

                    for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                        graphicsContext.strokeLine(
                                vertexes.get(vertexInPolygonInd - 1).getV().getX(),
                                vertexes.get(vertexInPolygonInd - 1).getV().getY(),
                                vertexes.get(vertexInPolygonInd).getV().getX(),
                                vertexes.get(vertexInPolygonInd).getV().getY());
                    }

                    if (nVerticesInPolygon > 0)
                        graphicsContext.strokeLine(
                                vertexes.get(nVerticesInPolygon - 1).getV().getX(),
                                vertexes.get(nVerticesInPolygon - 1).getV().getY(),
                                vertexes.get(0).getV().getX(),
                                vertexes.get(0).getV().getY());

                    for (int i = 0; i < vertexes.size(); i += 3) {
                        if (meshContext.getTexture() != null) {
                            try {
                                DrawTexture.drawTexture(vertexes.get(i), vertexes.get(i + 1), vertexes.get(i + 2),
                                        meshContext, graphicsContext.getPixelWriter(), meshContext.getTexture().getPixelReader());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                DrawTexture.drawPixels(vertexes.get(i), vertexes.get(i + 1), vertexes.get(i + 2),
                                        graphicsContext.getPixelWriter());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });
//        final int nPolygons = meshContext.getMesh().getPolygons().size();
//        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
//            final int nVerticesInPolygon = meshContext.getMesh().getPolygons().get(polygonInd).getPolygonVertexIndices().size();
//
//            ArrayList<Point2f> resultPoints = new ArrayList<>();
//            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
//                Vector3 vertex = meshContext.getMesh().getVertices().get(meshContext.getMesh().getPolygons().get(polygonInd).
//                        getPolygonVertexIndices().get(vertexInPolygonInd));
//                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
//                resultPoints.add(resultPoint);
//            }
//
//            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
//                graphicsContext.strokeLine(
//                        resultPoints.get(vertexInPolygonInd - 1).x,
//                        resultPoints.get(vertexInPolygonInd - 1).y,
//                        resultPoints.get(vertexInPolygonInd).x,
//                        resultPoints.get(vertexInPolygonInd).y);
//            }
//
//            if (nVerticesInPolygon > 0)
//                graphicsContext.strokeLine(
//                        resultPoints.get(nVerticesInPolygon - 1).x,
//                        resultPoints.get(nVerticesInPolygon - 1).y,
//                        resultPoints.get(0).x,
//                        resultPoints.get(0).y);
//        }
        //DrawTexture.drawPixels(meshContext.getMesh());
    }
}