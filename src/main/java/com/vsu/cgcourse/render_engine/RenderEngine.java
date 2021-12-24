package com.vsu.cgcourse.render_engine;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.DrawTexture;
import com.vsu.cgcourse.model.MeshContext;
import javafx.scene.canvas.GraphicsContext;

import javax.vecmath.*;

import com.vsu.cgcourse.model.Mesh;

import static com.vsu.cgcourse.render_engine.GraphicConveyor.*;

public class RenderEngine {

    private <T, E extends Exception> Consumer<T> consume(ConsumerWithException<T, E> ce) {
        return arg -> {
            try {
                ce.accept(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

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
        meshContext.getMesh().getPolygons().parallelStream().
                forEachOrdered(p1 -> {
                    final int nVerticesInPolygon = p1.getPolygonVertexIndices().size();
                    ArrayList<Point2f> resultPoints = new ArrayList<>();
                    for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                        Vector3 vertex = meshContext.getMesh().getVertices().get(p1.
                                getPolygonVertexIndices().get(vertexInPolygonInd));
                        Point2f resultPoint = null;
                        try {
                            resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        resultPoints.add(resultPoint);
                    }

                    for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                        graphicsContext.strokeLine(
                                resultPoints.get(vertexInPolygonInd - 1).x,
                                resultPoints.get(vertexInPolygonInd - 1).y,
                                resultPoints.get(vertexInPolygonInd).x,
                                resultPoints.get(vertexInPolygonInd).y);
                    }

                    if (nVerticesInPolygon > 0)
                        graphicsContext.strokeLine(
                                resultPoints.get(nVerticesInPolygon - 1).x,
                                resultPoints.get(nVerticesInPolygon - 1).y,
                                resultPoints.get(0).x,
                                resultPoints.get(0).y);
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