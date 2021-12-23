package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector2;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DrawTexture {

    public static void drawPixels(Mesh mesh) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WritableImage writableImage = new WritableImage(screenSize.width, screenSize.height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int i = 0; i < mesh.getPolygons().getPolygonTextureVertexIndices().size(); i++) {
            Vector2 v0 = mesh.getTextureVertices().get(mesh.getPolygons().getPolygonTextureVertexIndices().get(i).get(0));
            Vector2 v1 = mesh.getTextureVertices().get(mesh.getPolygons().getPolygonTextureVertexIndices().get(i).get(1));
            Vector2 v2 = mesh.getTextureVertices().get(mesh.getPolygons().getPolygonTextureVertexIndices().get(i).get(2));
            findForthPoint(v0, v1, v2);
            if (findPointOppositeX(v0, v1, v2) == null) {

            }
        }

    }

    private static Vector2 findPointOppositeX(Vector2 v0, Vector2 v1, Vector2 v2) {
        if (Math.abs(v0.getY() - v1.getY()) < 1E-5) {
            return v2;
        } else if (Math.abs(v0.getY() - v2.getY()) < 1E-5) {
            return v1;
        } else if (Math.abs(v1.getY() - v2.getY()) < 1E-5) {
            return v0;
        }
        return null;
    }

    private static Vector2 findForthPoint(Vector2 v0, Vector2 v1, Vector2 v2) {
        ArrayList<Float> vectorsY = new ArrayList<>();
        vectorsY.add(v0.getY());
        vectorsY.add(v2.getY());
        vectorsY.add(v1.getY());
        Collections.sort(vectorsY);
        return (getXFuncLine())
    }

    private static ArrayList<Float> findTriangleBounds(Vector2 v0, Vector2 v1, Vector2 v2) {
        ArrayList<Float> bound = new ArrayList<>();
        bound.add(max(v0.getY(), v1.getY(), v2.getY()));
        bound.add(min(v0.getY(), v1.getY(), v2.getY()));
        bound.add(max(v0.getX(), v1.getX(), v2.getX()));
        bound.add(min(v0.getX(), v1.getX(), v2.getX()));
        return bound;
    }

    private static float getXFuncLine(Vector2 v0, Vector2 v1, float y) {
        return (y - v0.getY()) * (v1.getX() - v0.getX()) / (v1.getY() - v0.getY()) - v0.getX();
    }

    private static float min(float n0, float n1, float n2) {
        if (Math.abs(n0 - n1) < 1E-5 && Math.abs(n0 - n2) < 1E-5) {
            return n0;
        } else if (Math.abs(n1 - n0) < 1E-5 && Math.abs(n1 - n2) < 1E-5) {
            return n1;
        }
        return n2;
    }

    private static float max(float n0, float n1, float n2) {
        if (Math.abs(n0 - n1) > 1E-5 && Math.abs(n0 - n2) > 1E-5) {
            return n0;
        } else if (Math.abs(n1 - n0) > 1E-5 && Math.abs(n1 - n2) > 1E-5) {
            return n1;
        }
        return n2;
    }

}
