package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawTexture {

    public static void drawTexture(Vector2 p0, Vector2 p1, Vector2 p2,
                                   Vector2 vt0, Vector2 vt1, Vector2 vt2,
                                   MeshContext meshContext, PixelWriter pw,
                                   PixelReader pr) throws Exception {
        Image texture = meshContext.getTexture();
        ArrayList<Vector2> sortedVectors = getSortedVectors(p0, p1, p2, vt0, vt1, vt2);
        draw(sortedVectors, vt0, vt1, vt2, texture, pw);
    }

    public static void drawPixels(Vector2 p0, Vector2 p1, Vector2 p2,
                                   Vector2 vt0, Vector2 vt1, Vector2 vt2,
                                   PixelWriter pw) throws Exception {

        ArrayList<Vector2> sortedVectors = getSortedVectors(p0, p1, p2, vt0, vt1, vt2);
        draw(sortedVectors, vt0, vt1, vt2, null, pw);
    }

    private static void putPixel(Vector2 o, Vector2 p0, Vector2 p1, Vector2 p2,
                                 Vector2 vt0, Vector2 vt1, Vector2 vt2,
                                 Image texture, PixelWriter pw) throws Exception {
        p1.sub(p0);
        p2.sub(p0);
        o.sub(p0);
        float alpha = findAlpha(o, p1, p2);
        float beta = findBeta(o, p1, p2);
        float vtResultX = vt0.getX() * (1 - alpha - beta) + vt1.getX() * beta + vt2.getX() * alpha;
        float vtResultY = vt0.getY() * (1 - alpha - beta) + vt1.getY() * beta + vt2.getY() * alpha;
        vtResultX *= (float) texture.getWidth();
        vtResultY *= (float) texture.getHeight();
        pw.setColor((int) vtResultX,(int) vtResultY, texture.getPixelReader().getColor((int) vtResultX,(int) vtResultY));
    }

    private static void draw(ArrayList<Vector2> sortedVectors, Vector2 vt0, Vector2 vt1, Vector2 vt2,
                             Image texture, PixelWriter pw) throws Exception {
        if (sortedVectors.size() == 3) {
            float n;
            if (sortedVectors.get(0).getY() - sortedVectors.get(1).getY() > 0) {
                n = -1;
            } else {
                n = 1;
            }
            for (float y = sortedVectors.get(0).getY(); y < sortedVectors.get(1).getY(); y += n) {
                for (float x = getXFuncLine(sortedVectors.get(0), sortedVectors.get(1), y); x < getXFuncLine(sortedVectors.get(0), sortedVectors.get(2), y); x++) {
                    if (texture != null) {
                        putPixel(new Vector2(new float[]{(int) x, (int) y}), sortedVectors.get(0), sortedVectors.get(1),
                                sortedVectors.get(2), vt0, vt1, vt2, texture, pw);
                    } else {
                        pw.setColor((int) x, (int) y, Color.BLUE);
                    }
                }
            }
        } else if (sortedVectors.size() == 4) {
            drawUpTriangle(sortedVectors, vt0, vt1, vt2, pw, texture);
            drawDownTriangle(sortedVectors, vt0, vt1, vt2, pw, texture);
        }
    }

    private static void drawUpTriangle(ArrayList<Vector2> sortedVectors,
                                       Vector2 vt0, Vector2 vt1, Vector2 vt2,
                                       PixelWriter pw, Image texture) throws Exception {
        for (float y = sortedVectors.get(0).getY(); y > sortedVectors.get(2).getY(); y--) {
            for (float x = getXFuncLine(sortedVectors.get(0), sortedVectors.get(2), y); x < getXFuncLine(sortedVectors.get(0), sortedVectors.get(3), y); x++) {
                if (texture != null) {
                    putPixel(new Vector2(new float[]{(int) x, (int) y}), sortedVectors.get(0), sortedVectors.get(1),
                            sortedVectors.get(2), vt0, vt1, vt2, texture, pw);
                } else {
                    pw.setColor((int) x, (int) y, Color.PURPLE);
                }
            }
        }
    }

    private static void drawDownTriangle(ArrayList<Vector2> sortedVectors,
                                         Vector2 vt0, Vector2 vt1, Vector2 vt2,
                                         PixelWriter pw, Image texture) throws Exception {
        for (float y = sortedVectors.get(1).getY(); y < sortedVectors.get(3).getY(); y++) {
            for (float x = getXFuncLine(sortedVectors.get(1), sortedVectors.get(2), y); x < getXFuncLine(sortedVectors.get(1), sortedVectors.get(3), y); x++) {
                if (texture != null) {
                    putPixel(new Vector2(new float[]{(int) x, (int) y}), sortedVectors.get(0), sortedVectors.get(1),
                            sortedVectors.get(2), vt0, vt1, vt2, texture, pw);
                } else {
                    pw.setColor((int) x, (int) y, Color.GREEN);
                }
            }
        }
    }

    private static ArrayList<Vector2> getSortedVectors(Vector2 v0, Vector2 v1, Vector2 v2, Vector2 vt0, Vector2 vt1, Vector2 vt2) {
        ArrayList<Vector2> sortedVectors = new ArrayList<>();
        if (Math.abs(v0.getY() - v1.getY()) < 1E-5) {
            sortedVectors.add(v2);
            sortedVectors.add(vt2);
            if (v0.getX() - v1.getX() < 0) {
                sortedVectors.add(v0);
                sortedVectors.add(v1);
                sortedVectors.add(vt0);
                sortedVectors.add(vt1);
            } else {
                sortedVectors.add(v1);
                sortedVectors.add(v0);
                sortedVectors.add(vt1);
                sortedVectors.add(vt0);
            }
            return sortedVectors;
        } else if (Math.abs(v0.getY() - v2.getY()) < 1E-5) {
            sortedVectors.add(v1);
            sortedVectors.add(vt1);
            if (v0.getX() - v2.getX() < 0) {
                sortedVectors.add(v0);
                sortedVectors.add(v2);
                sortedVectors.add(vt0);
                sortedVectors.add(vt2);
            } else {
                sortedVectors.add(v2);
                sortedVectors.add(v0);
                sortedVectors.add(vt2);
                sortedVectors.add(vt0);
            }
            return sortedVectors;
        } else if (Math.abs(v1.getY() - v2.getY()) < 1E-5) {
            sortedVectors.add(v0);
            sortedVectors.add(vt0);
            if (v1.getX() - v2.getX() < 0) {
                sortedVectors.add(v1);
                sortedVectors.add(v2);
                sortedVectors.add(vt1);
                sortedVectors.add(vt2);
            } else {
                sortedVectors.add(v2);
                sortedVectors.add(v1);
                sortedVectors.add(vt2);
                sortedVectors.add(vt1);
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

    private static float findAlpha(Vector2 v0, Vector2 v1, Vector2 v2) {
        return (v2.getX() * v0.getY() - v2.getY() * v0.getX()) / (v1.getY() * v2.getX() - v2.getY() * v1.getX());
    }

    private static float findBeta(Vector2 v0, Vector2 v1, Vector2 v2) {
        return (v1.getY() * v0.getX() - v1.getX() * v0.getY()) / (v1.getY() * v2.getX() - v2.getY() * v1.getX());
    }
}