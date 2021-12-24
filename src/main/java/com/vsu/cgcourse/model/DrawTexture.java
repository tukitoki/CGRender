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
import java.util.Comparator;

public class DrawTexture {

    public static void drawTexture(Vertexes ver0, Vertexes ver1, Vertexes ver2,
                                   MeshContext meshContext, PixelWriter pw,
                                   PixelReader pr) throws Exception {
        Image texture = meshContext.getTexture();
        ArrayList<Vertexes> sortedVectors = getSortedVectors(ver0, ver1, ver2);
        draw(sortedVectors, texture, pw);
    }

    public static void drawPixels(Vertexes ver0, Vertexes ver1, Vertexes ver2,
                                  PixelWriter pw) throws Exception {

        ArrayList<Vertexes> sortedVectors = getSortedVectors(ver0, ver1, ver2);
        draw(sortedVectors, null, pw);
    }

    /*private static void putPixel(Vertexes ver0, Vertexes ver1, Vertexes ver2,
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
        pw.setColor((int) vtResultX, (int) vtResultY, texture.getPixelReader().getColor((int) vtResultX, (int) vtResultY));
    }

     */

    private static void draw(ArrayList<Vertexes> sortedVectors,
                             Image texture, PixelWriter pw) throws Exception {
        Vertexes ver0 = sortedVectors.get(0);
        Vertexes ver1 = sortedVectors.get(1);
        Vertexes ver2 = sortedVectors.get(2);
        if (ver1.v.getY() == ver2.v.getY()) {
            float n;
            if (ver0.v.getY() > ver1.v.getY()) {
                n = -1;
            } else {
                n = 1;
            }
            for (float y = ver0.v.getY(); y * n < ver1.v.getY(); y += n) {
                for (float x = getXFuncLine(ver0, ver1, y); x < getXFuncLine(ver0, ver2, y); x++) {
                    pw.setColor((int) x,(int) y, Color.PURPLE);
                }
            }
        } else {
            Vertexes ver3 = getOppositeVector(ver0, ver1, ver1.v.getY());
            for (float y = ver0.v.getY(); y > ver1.v.getY(); y--) {
                for (float x = getXFuncLine(ver0, ver1, y); x < getXFuncLine(ver0, ver3, y); x++) {
                    //if (texture != null) {
                    //    putPixel(new Vector2(new float[]{(int) x, (int) y}), ver0, ver1,
                    //            ver2, texture, pw);
                    //} else {
                        pw.setColor((int) x, (int) y, Color.PURPLE);
                    //}
                }
            }
            for (float y = ver1.v.getY(); y < ver2.v.getY(); y++) {
                for (float x = getXFuncLine(ver1, ver1, y); x < getXFuncLine(ver1, ver3, y); x++) {
                    pw.setColor((int) x, (int) y, Color.GREEN);
//                    if (texture != null) {
//                        putPixel(new Vector2(new float[]{(int) x, (int) y}), sortedVectors.get(0), sortedVectors.get(1),
//                                sortedVectors.get(2), vt0, vt1, vt2, texture, pw);
//                    } else {
//
//                    }
                }
            }
        }
    }

    private static ArrayList<Vertexes> getSortedVectors(Vertexes ver0, Vertexes ver1, Vertexes ver2) {
        ArrayList<Vertexes> sortedVectors = new ArrayList<>();
        sortedVectors.add(ver0);
        sortedVectors.add(ver1);
        sortedVectors.add(ver2);
        sortedVectors.sort((o1, o2) -> {
            int res = Integer.compare((int) Math.floor(o1.v.getY()), (int) Math.floor(o2.v.getY()));
            if (res != 0) {
                return res;
            } else {
                return Float.compare(o1.v.getX(), o2.v.getX());
            }
        });
        return sortedVectors;
    }

    /*private static void getFourSortedVectors(Vertexes ver0, Vertexes ver1, Vertexes ver2, ArrayList<Vertexes> sortedVectors) {
        if (ver0.v.getY() - ver1.v.getY() < 0 && ver0.v.getY() - ver2.v.getY() > 0 || ver0.v.getY() - ver1.v.getY() > 0 && ver0.v.getY() - ver2.v.getY() < 0) {
            Vector2 v3 = getOppositeVector(sortedVectors.get(0), sortedVectors.get(1), ver0.v.getY());
            if (ver0.v.getX() - v3.getX() < 0) {
                sortedVectors.add(ver0);
                sortedVectors.add(v3);
            } else {
                sortedVectors.add(v3);
                sortedVectors.add(ver0);
            }
        } else if (ver1.v.getY() - ver0.v.getY() < 0 && ver1.v.getY() - ver2.v.getY() > 0 || ver1.v.getY() - ver0.v.getY() > 0 && ver1.v.getY() - ver2.v.getY() < 0) {
            Vector2 v3 = getOppositeVector(sortedVectors.get(0), sortedVectors.get(1), ver1.v.getY());
            if (ver1.v.getX() - v3.getX() < 0) {
                sortedVectors.add(ver1);
                sortedVectors.add(v3);
            } else {
                sortedVectors.add(v3);
                sortedVectors.add(ver1);
            }
        } else if (ver2.v.getY() - ver0.v.getY() > 0 && ver2.v.getY() - ver1.v.getY() < 0 || ver2.v.getY() - ver0.v.getY() < 0 && ver2.v.getY() - ver1.v.getY() > 0) {
            Vector2 v3 = getOppositeVector(sortedVectors.get(0), sortedVectors.get(1), ver2.v.getY());
            if (ver2.v.getX() - v3.getX() < 0) {
                sortedVectors.add(ver2);
                sortedVectors.add(v3);
            } else {
                sortedVectors.add(v3);
                sortedVectors.add(ver2);
            }
        }
    }*/

    private static float getXFuncLine(Vertexes ver0, Vertexes ver1, float y0) {
        float x = (y0 - ver0.v.getY()) * (ver1.v.getX() - ver0.v.getX()) / (ver1.v.getY() - ver0.v.getY()) + ver0.v.getX();
        return x;
    }

    private static float getYFuncLine(Vector2 v0, Vector2 v1, float x0) {
        return (x0 - v0.getX()) * (v1.getY() - v0.getY()) / (v1.getX() - v0.getX()) + v0.getY();
    }

    private static Vertexes getOppositeVector(Vertexes ver0, Vertexes ver1, float y0) throws Exception {
        Vector3 ver3 = new Vector3(new float[]{(y0 - ver0.v.getY()) * (ver1.v.getX() - ver0.v.getX()) / (ver1.v.getY() - ver0.v.getY()) + ver0.v.getX(), y0});
        /*Vector3 verMax = new Vector3(ver0.v.subtraction(ver1.v));
        Vector3 verMin = new Vector3(ver0.v.subtraction(v3));
        float prop = verMin.getX() / verMax.getX();
        Vector2 verTexture = new Vector2(ver0.vt.subtraction(ver1.vt));

         */
        return new Vertexes(ver3, new Vector2(new float[] {1, 2}), new Vector3(new float[] {1, 2, 3}));
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

    private static Vertexes getVectorMaxY(Vertexes ver0, Vertexes ver1, Vertexes ver2) {
        if (ver0.v.getY() - ver1.v.getY() > 0 && ver0.v.getY() - ver2.v.getY() > 0) {
            return ver0;
        } else if (ver1.v.getY() - ver0.v.getY() > 0 && ver1.v.getY() - ver2.v.getY() > 0) {
            return ver1;
        }
        return ver2;
    }

    private static float findAlpha(Vector2 v0, Vector2 v1, Vector2 v2) {
        return (v2.getX() * v0.getY() - v2.getY() * v0.getX()) / (v1.getY() * v2.getX() - v2.getY() * v1.getX());
    }

    private static float findBeta(Vector2 v0, Vector2 v1, Vector2 v2) {
        return (v1.getY() * v0.getX() - v1.getX() * v0.getY()) / (v1.getY() * v2.getX() - v2.getY() * v1.getX());
    }
}