package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector2;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class DrawTexture {

    public static void drawPixels(Mesh mesh) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WritableImage writableImage = new WritableImage(screenSize.width, screenSize.height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int i = 0; i < mesh.getPolygons().getPolygonTextureVertexIndices().size(); i++) {
            Vector2 v0 = mesh.getTextureVertices().get(mesh.getPolygons().getPolygonTextureVertexIndices().get(i).get(0));
            Vector2 v1 = mesh.getTextureVertices().get(mesh.getPolygons().getPolygonTextureVertexIndices().get(i).get(1));
            Vector2 v2 = mesh.getTextureVertices().get(mesh.getPolygons().getPolygonTextureVertexIndices().get(i).get(2));
            ArrayList<Vector2> vectors = new ArrayList<>();
            vectors.add(v0);
            vectors.add(v1);
            vectors.add(v2);
            vectors.sort((o1, o2) -> {
                if (Math.abs(o1.getY() - o2.getY()) < 1E-5) {
                    return 0;
                } else if (o1.getY() - o1.getY() < 0) {
                    return -1;
                }
                return 1;
            });
            if (findPointOppositeXAxis(v0, v1, v2) != null) {

            }
        }
    }

    private static Vector2 findPointOppositeXAxis(Vector2 v0, Vector2 v1, Vector2 v2) {
        if (Math.abs(v0.getY() - v1.getY()) < 1E-5) {
            return v2;
        } else if (Math.abs(v0.getY() - v2.getY()) < 1E-5) {
            return v1;
        } else if (Math.abs(v1.getY() - v2.getY()) < 1E-5) {
            return v0;
        }
        return null;
    }

    public static Vector2 findForthPoint(Vector2 v0, Vector2 v1, Vector2 v2) {
        ArrayList<Float> vectors = new ArrayList<>();
        vectors.add(v0.getY());
        vectors.add(v1.getY());
        vectors.add(v2.getY());
        Collections.sort(vectors);
        for (Float vector : vectors) {
            if (Math.abs(vector - v0.getY()) < 1E-5) {
                return getOppositePoint(v1, v2, vectors.get(1));
            } else if (Math.abs(vector - v1.getY()) < 1E-5) {
                return getOppositePoint(v0, v2, vectors.get(1));
            } else if (Math.abs(vector - v2.getY()) < 1E-5) {
                return getOppositePoint(v0, v1, vectors.get(1));
            }
        }
        return null;
    }

    /*private static ArrayList<Float> findTriangleBounds(Vector2 v0, Vector2 v1, Vector2 v2) {
        ArrayList<Float> bound = new ArrayList<>();
        bound.add(maxY(v0.getY(), v1.getY(), v2.getY()));
        bound.add(min(v0.getY(), v1.getY(), v2.getY()));
        bound.add(maxY(v0.getX(), v1.getX(), v2.getX()));
        bound.add(min(v0.getX(), v1.getX(), v2.getX()));
        return bound;
    }

     */

    private static float getXFuncLine(Vector2 v0, Vector2 v1, float y0) {
        return (y0 - v0.getY()) * (v1.getX() - v0.getX()) / (v1.getY() - v0.getY()) - v0.getX();
    }

    private static float getYFuncLine(Vector2 v0, Vector2 v1, float x0) {
        return (x0 - v0.getX()) * (v1.getY() - v0.getY()) / (v1.getX() - v0.getX()) - v0.getY();
    }

    private static Vector2 getOppositePoint(Vector2 v0, Vector2 v1, float y0) {
        return new Vector2(new float[]{(y0 - v0.getY()) * (v1.getX() - v0.getX()) / (v1.getY() - v0.getY()) - v0.getX(), y0});
    }

    private static Vector2 getVectorMinY(Vector2 v0, Vector2 v1, Vector2 v2) {
        if (v0.getY() - v1.getY() < 0 && v0.getY() - v2.getY() < 0) {
            return v0;
        } else if (v1.getY() - v0.getY() < 0 && v1.getY() - v2.getY() < 0) {
            return v1;
        }
        return v2;
    }

    private static Vector2 getVectorMinX(Vector2 v0, Vector2 v1, Vector2 v2) {
        if (v0.getX() - v1.getX() < 0 && v0.getX() - v2.getX() < 0) {
            return v0;
        } else if (v1.getX() - v0.getX() < 0 && v1.getX() - v2.getX() < 0) {
            return v1;
        }
        return v2;
    }

    private static Vector2 getVectorMaxX(Vector2 v0, Vector2 v1, Vector2 v2) {
        if (v0.getX() - v1.getX() > 0 && v0.getX() - v2.getX() > 0) {
            return v0;
        } else if (v1.getX() - v0.getX() > 0 && v1.getX() - v2.getX() > 0) {
            return v1;
        }
        return v2;
    }

    private static Vector2 getVectorMaxY(Vector2 v0, Vector2 v1, Vector2 v2) {
        if (v0.getY() - v1.getY() > 0 && v0.getY() - v2.getY() > 0) {
            return v0;
        } else if (v1.getY() - v0.getY() > 0 && v1.getY() - v2.getY() > 0) {
            return v1;
        }
        return v2;
    }
}