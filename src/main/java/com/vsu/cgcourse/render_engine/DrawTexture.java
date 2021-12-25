package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.MeshContext;
import com.vsu.cgcourse.model.Vertexes;
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

    private float[][] zBuffer;

    public void setZBuffer(float[][] zBuffer) {
        this.zBuffer = zBuffer;
    }

    private static Color fillingColor = Color.rgb(255, 111, 255, 1.0);

    public static void drawTexture(Vertexes ver0, Vertexes ver1, Vertexes ver2,
                                   MeshContext meshContext, PixelWriter pw) throws Exception {
        Image texture = meshContext.getTexture();
        ArrayList<Vertexes> sortedVectors = getSortedVectors(ver0, ver1, ver2);
        draw(sortedVectors, texture, pw);
    }

    public static void drawPixels(Vertexes ver0, Vertexes ver1, Vertexes ver2,
                                  PixelWriter pw) throws Exception {

        ArrayList<Vertexes> sortedVectors = getSortedVectors(ver0, ver1, ver2);
        draw(sortedVectors, null, pw);
    }

    private static void putPixel(Vector2 o, Vertexes ver0, Vertexes ver1, Vertexes ver2,
                                 Image texture, PixelWriter pw) throws Exception {

        Vertexes vec1 = new Vertexes(new Vector3(ver1.getV().subtraction(ver0.getV())), ver1.getVt(), ver1.getVn()) ;
        Vertexes vec2 = new Vertexes(new Vector3(ver2.getV().subtraction(ver0.getV())), ver1.getVt(), ver1.getVn()) ;
        Vector2 vecO = new Vector2(o.subtraction(ver0.getV().getVector2()));
        float alpha = findAlpha(vecO, vec1.getV().getVector2(), vec2.getV().getVector2());
        float beta = findBeta(vecO, vec1.getV().getVector2(), vec2.getV().getVector2());
        float vtResultX = ver0.getVt().getX() * (1 - alpha - beta) + vec1.getVt().getX() * beta + vec2.getVt().getX() * alpha;
        float vtResultY = ver0.getVt().getY() * (1 - alpha - beta) + vec1.getVt().getY() * beta + vec2.getVt().getY() * alpha;
        vtResultX *= (float) texture.getWidth();
        vtResultY = (float) texture.getHeight() * (1 - vtResultY);
        Color color = texture.getPixelReader().getColor((int) Math.floor(vtResultX), (int) Math.floor(vtResultY));
        pw.setColor((int) Math.floor(o.getX()), (int) Math.floor(o.getY()), color);
    }

    private static void draw(ArrayList<Vertexes> sortedVectors,
                             Image texture, PixelWriter pw) throws Exception {
        Vertexes ver0 = sortedVectors.get(0);
        Vertexes ver1 = sortedVectors.get(1);
        Vertexes ver2 = sortedVectors.get(2);
        if (sortedVectors.size() == 3) {
            float n;
            if (ver0.getV().getY() > ver1.getV().getY()) {
                n = -1;
            } else {
                n = 1;
            }
            for (float y = ver0.getV().getY(); y * n < ver1.getV().getY(); y += n) {
                if (Math.floor(y) == Math.floor(ver1.getV().getY())) {
                    break;
                }
                for (float x = getXFuncLine(ver0, ver1, y); x < getXFuncLine(ver0, ver2, y); x++) {
                    if (texture != null) {
                        putPixel(new Vector2(new float[] {x, y}), ver0, ver1, ver2, texture, pw);
                    } else {
                        pw.setColor((int) x, (int) y, fillingColor);
                    }
                }
            }
        } else if (sortedVectors.size() == 4) {
            Vertexes ver3 = sortedVectors.get(3);
            for (float y = ver0.getV().getY(); y > ver2.getV().getY(); y--) { //down
                for (float x = getXFuncLine(ver0, ver2, y); x < getXFuncLine(ver0, ver3, y); x++) {
                    if (texture != null) {
                        putPixel(new Vector2(new float[] {x, y}), ver0, ver2, ver3, texture, pw);
                    } else {
                    pw.setColor((int) x, (int) y, fillingColor);
                    }
                }
            }
            for (float y = ver1.getV().getY(); y < ver2.getV().getY(); y++) { //up
                for (float x = getXFuncLine(ver1, ver2, y); x < getXFuncLine(ver1, ver3, y); x++) {
                    if (texture != null) {
                        putPixel(new Vector2(new float[] {x, y}), ver1, ver2, ver3, texture, pw);
                    } else {
                        pw.setColor((int) x, (int) y, fillingColor);
                    }
                }
            }
        }
    }

    private static ArrayList<Vertexes> getSortedVectors(Vertexes ver0, Vertexes ver1, Vertexes ver2) throws Exception {
        ArrayList<Vertexes> sortedVectors = new ArrayList<>();
        if (Math.abs(ver0.getV().getY() - ver1.getV().getY()) < 1E-6) {
            sortedVectors.add(ver2);
            if (ver0.getV().getX() - ver1.getV().getX() < 0) {
                sortedVectors.add(ver0);
                sortedVectors.add(ver1);
            } else {
                sortedVectors.add(ver1);
                sortedVectors.add(ver0);
            }
            return sortedVectors;
        } else if (Math.abs(ver0.getV().getY() - ver2.getV().getY()) < 1E-6) {
            sortedVectors.add(ver1);
            if (ver0.getV().getX() - ver2.getV().getX() < 0) {
                sortedVectors.add(ver0);
                sortedVectors.add(ver2);
            } else {
                sortedVectors.add(ver2);
                sortedVectors.add(ver0);
            }
            return sortedVectors;
        } else if (Math.abs(ver1.getV().getY() - ver2.getV().getY()) < 1E-6) {
            sortedVectors.add(ver0);
            if (ver1.getV().getX() - ver2.getV().getX() < 0) {
                sortedVectors.add(ver1);
                sortedVectors.add(ver2);
            } else {
                sortedVectors.add(ver2);
                sortedVectors.add(ver1);
            }
            return sortedVectors;
        }
        Vertexes max = getVectorMaxY(ver0, ver1, ver2);
        Vertexes min = getVectorMinY(ver0, ver1, ver2);
        Vertexes mid = getVectorMidY(ver0, ver1, ver2);
        Vertexes opposite = getOppositeVector(max, min, mid.getV().getY());
        sortedVectors.add(max);
        sortedVectors.add(min);
        if (mid.getV().getX() - opposite.getV().getX() < 0) {
            sortedVectors.add(mid);
            sortedVectors.add(opposite);
        } else {
            sortedVectors.add(opposite);
            sortedVectors.add(mid);
        }
        return sortedVectors;
    }

    private static float getXFuncLine(Vertexes ver0, Vertexes ver1, float y0) {
        return (y0 - ver0.getV().getY()) * (ver1.getV().getX() - ver0.getV().getX()) / (ver1.getV().getY() - ver0.getV().getY()) + ver0.getV().getX();
    }

    private static float getYFuncLine(Vector2 v0, Vector2 v1, float x0) {
        return (x0 - v0.getX()) * (v1.getY() - v0.getY()) / (v1.getX() - v0.getX()) + v0.getY();
    }

    private static Vertexes getOppositeVector(Vertexes ver0, Vertexes ver1, float y0) throws Exception {
        Vector3 ver3 = new Vector3(new float[]{(y0 - ver0.getV().getY()) * (ver1.getV().getX() - ver0.getV().getX()) / (ver1.getV().getY() - ver0.getV().getY()) + ver0.getV().getX(), y0, 0});
        Vector3 verMax = new Vector3(ver1.getV().subtraction(ver0.getV()));
        Vector3 verMin = new Vector3(ver3.subtraction(ver0.getV()));
        float prop = verMin.length() / verMax.length();
        Vector2 verTexture = new Vector2(ver1.getVt().subtraction(ver0.getVt()));
        verTexture.multiply(prop);
        verTexture.plus(ver0.getVt());
        return new Vertexes(ver3, verTexture, new Vector3(new float[]{1, 2, 3}));
    }

    private static Vertexes getVectorMinY(Vertexes ver0, Vertexes ver1, Vertexes ver2) {
        if (ver0.getV().getY() - ver1.getV().getY() < 0 && ver0.getV().getY() - ver2.getV().getY() < 0) {
            return ver0;
        } else if (ver1.getV().getY() - ver0.getV().getY() < 0 && ver1.getV().getY() - ver2.getV().getY() < 0) {
            return ver1;
        }
        return ver2;
    }

    private static Vertexes getVectorMidY(Vertexes ver0, Vertexes ver1, Vertexes ver2) {
        if (ver0.getV().getY() - ver1.getV().getY() < 0 && ver0.getV().getY() - ver2.getV().getY() > 0 ||
                ver0.getV().getY() - ver1.getV().getY() > 0 && ver0.getV().getY() - ver2.getV().getY() < 0) {
            return ver0;
        } else if (ver1.getV().getY() - ver0.getV().getY() < 0 && ver1.getV().getY() - ver2.getV().getY() > 0 ||
                ver1.getV().getY() - ver0.getV().getY() > 0 && ver1.getV().getY() - ver2.getV().getY() < 0)  {
            return ver1;
        }
        return ver2;
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
        if (ver0.getV().getY() - ver1.getV().getY() > 0 && ver0.getV().getY() - ver2.getV().getY() > 0) {
            return ver0;
        } else if (ver1.getV().getY() - ver0.getV().getY() > 0 && ver1.getV().getY() - ver2.getV().getY() > 0) {
            return ver1;
        }
        return ver2;
    }

    private static float findAlpha(Vector2 v0, Vector2 v1, Vector2 v2) {
        return (v2.getX() * v0.getY() - v2.getY() * v0.getX()) / (v1.getY() * v2.getX() - v2.getY() * v1.getX());
    }

    private static float findBeta(Vector2 v0, Vector2 v1, Vector2 v2) {
        float beta = (v1.getY() * v0.getX() - v1.getX() * v0.getY()) / (v1.getY() * v2.getX() - v2.getY() * v1.getX());
        if (beta < 0) {
            return Math.abs(beta);
        }
        return beta;
    }
}