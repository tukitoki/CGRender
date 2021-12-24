package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector2;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawTexture {

    public static void drawTexture(MeshContext meshContext, PixelWriter pw, PixelReader pr) {
        Mesh mesh = meshContext.getMesh();
        Image texture = meshContext.getTexture();
        for (int i = 0; i < mesh.getPolygons().size(); i++) {
            Vector2 vt0 = mesh.getTextureVertices().get(mesh.getPolygons().get(i).getPolygonTextureVertexIndices().get(0));
            Vector2 vt1 = mesh.getTextureVertices().get(mesh.getPolygons().get(i).getPolygonTextureVertexIndices().get(1));
            Vector2 vt2 = mesh.getTextureVertices().get(mesh.getPolygons().get(i).getPolygonTextureVertexIndices().get(2));
            ArrayList<Vector2> sortedVectors = getSortedVectors(vt0, vt1, vt2);
            draw(sortedVectors, texture, pw, pr);
        }
    }

    public static void drawPixels(ArrayList<Vector2> resultPoints, PixelWriter pw) {
        for (int i = 0; i < resultPoints.size(); i += 3) {
            Vector2 v0 = resultPoints.get(i);
            Vector2 v1 = resultPoints.get(i + 1);
            Vector2 v2 = resultPoints.get(i + 2);
            ArrayList<Vector2> sortedVectors = getSortedVectors(v0, v1, v2);
            draw(sortedVectors, null, pw, null);
        }
    }

    private static void draw(ArrayList<Vector2> sortedVectors, Image texture, PixelWriter pw, PixelReader pr) {
        if (sortedVectors.size() == 3) {
            float n;
            if (sortedVectors.get(0).getY() - sortedVectors.get(1).getY() > 0) {
                n = -1;
            } else {
                n = 1;
            }
            for (float y = sortedVectors.get(0).getY(); y  < sortedVectors.get(1).getY(); y += n) {
                for (float x = getXFuncLine(sortedVectors.get(0), sortedVectors.get(1), y); x < getXFuncLine(sortedVectors.get(0), sortedVectors.get(2), y); x++) {
                    pw.setColor((int) x, (int) y, Color.BLUE);
                    if (texture != null) {
                        // сюда ебать
                    }
                }
            }
        } else if (sortedVectors.size() == 4) {
            for (float y = sortedVectors.get(0).getY(); y > sortedVectors.get(2).getY(); y--) {
                for (float x = getXFuncLine(sortedVectors.get(0), sortedVectors.get(2), y); x < getXFuncLine(sortedVectors.get(0), sortedVectors.get(3), y); x++) {
                    pw.setColor((int) x, (int) y, Color.PURPLE);
                    if (texture != null) {
                        // сюда ебать
                    }
                }
            }
            for (float y = sortedVectors.get(1).getY(); y < sortedVectors.get(3).getY(); y++) {
                for (float x = getXFuncLine(sortedVectors.get(1), sortedVectors.get(2), y); x < getXFuncLine(sortedVectors.get(1), sortedVectors.get(3), y); x++) {
                    pw.setColor((int) x, (int) y, Color.GREEN);
                    if (texture != null) {
                        // сюда ебать
                    }
                }
            }
        }
    }

    private static ArrayList<Vector2> getSortedVectors(Vector2 v0, Vector2 v1, Vector2 v2) {
        ArrayList<Vector2> sortedVectors = new ArrayList<>();
        if (Math.abs(v0.getY() - v1.getY()) < 1E-5) {
            sortedVectors.add(v2);
            if (v0.getX() - v1.getX() < 0) {
                sortedVectors.add(v0);
                sortedVectors.add(v1);
            } else {
                sortedVectors.add(v1);
                sortedVectors.add(v0);
            }
            return sortedVectors;
        } else if (Math.abs(v0.getY() - v2.getY()) < 1E-5) {
            sortedVectors.add(v1);
            if (v0.getX() - v2.getX() < 0) {
                sortedVectors.add(v0);
                sortedVectors.add(v2);
            } else {
                sortedVectors.add(v2);
                sortedVectors.add(v0);
            }
            return sortedVectors;
        } else if (Math.abs(v1.getY() - v2.getY()) < 1E-5) {
            sortedVectors.add(v0);
            if (v1.getX() - v2.getX() < 0) {
                sortedVectors.add(v1);
                sortedVectors.add(v2);
            } else {
                sortedVectors.add(v2);
                sortedVectors.add(v1);
            }
            return sortedVectors;
        }
        sortedVectors.add(getVectorMaxY(v0, v1, v2));
        sortedVectors.add(getVectorMinY(v0, v1, v2));
        getFourSortedVectors(v0, v1, v2, sortedVectors);
        return sortedVectors;
    }

    private static void getFourSortedVectors(Vector2 v0, Vector2 v1, Vector2 v2, ArrayList<Vector2> sortedVectors) {
        if (v0.getY() - v1.getY() < 0 && v0.getY() - v2.getY() > 0 || v0.getY() - v1.getY() > 0 && v0.getY() - v2.getY() < 0) {
            Vector2 v3 = getOppositeVector(sortedVectors.get(0), sortedVectors.get(1), v0.getY());
            if (v0.getX() - v3.getX() < 0) {
                sortedVectors.add(v0);
                sortedVectors.add(v3);
            } else {
                sortedVectors.add(v3);
                sortedVectors.add(v0);
            }
        } else if (v1.getY() - v0.getY() < 0 && v1.getY() - v2.getY() > 0 || v1.getY() - v0.getY() > 0 && v1.getY() - v2.getY() < 0) {
            Vector2 v3 = getOppositeVector(sortedVectors.get(0), sortedVectors.get(1), v1.getY());
            if (v1.getX() - v3.getX() < 0) {
                sortedVectors.add(v1);
                sortedVectors.add(v3);
            } else {
                sortedVectors.add(v3);
                sortedVectors.add(v1);
            }
        } else if (v2.getY() - v0.getY() > 0 && v2.getY() - v1.getY() < 0 || v2.getY() - v0.getY() < 0 && v2.getY() - v1.getY() > 0) {
            Vector2 v3 = getOppositeVector(sortedVectors.get(0), sortedVectors.get(1), v2.getY());
            if (v2.getX() - v3.getX() < 0) {
                sortedVectors.add(v2);
                sortedVectors.add(v3);
            } else {
                sortedVectors.add(v3);
                sortedVectors.add(v2);
            }
        }
    }

    private static float getXFuncLine(Vector2 v0, Vector2 v1, float y0) {
        float x = (y0 - v0.getY()) * (v1.getX() - v0.getX()) / (v1.getY() - v0.getY()) + v0.getX();
        return x;
    }

    private static float getYFuncLine(Vector2 v0, Vector2 v1, float x0) {
        return (x0 - v0.getX()) * (v1.getY() - v0.getY()) / (v1.getX() - v0.getX()) + v0.getY();
    }

    private static Vector2 getOppositeVector(Vector2 v0, Vector2 v1, float y0) {
        return new Vector2(new float[]{(y0 - v0.getY()) * (v1.getX() - v0.getX()) / (v1.getY() - v0.getY()) + v0.getX(), y0});
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