package com.vsu.cgcourse.model;

import com.vsu.cgcourse.obj_reader.ObjReaderException;

import java.util.*;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector2f;

public class Mesh {

    private ArrayList<Vector3f> vertices;
    private ArrayList<Vector2f> textureVertices;
    private ArrayList<Vector3f> normals;
    private Polygons polygons;

    public Mesh() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new Polygons();
    }

    public ArrayList<Vector3f> getVertices() {
        return vertices;
    }

    public ArrayList<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public ArrayList<Vector3f> getNormals() {
        return normals;
    }

    public Polygons getPolygons() {
        return polygons;
    }

    public void setPolygons(Polygons polygons) {
        this.polygons = polygons;
    }

    public void recheckModel() {
        for (int index = 0; index < polygons.getPolygonNormalIndices().size(); index++) {
            polygons.recheckOnCorrect(index);
            recheckOnRightIndices(index);
        }
    }

    private void recheckOnRightIndices(int index) {
        for (Integer v : polygons.getPolygonVertexIndices().get(index)) {
            if (v > vertices.size() - 1) {
                v += 1;
                throw new ObjReaderException("Error of getting wrong VertexIndex: " + v + ". Maximum: " + vertices.size());
            }
        }
        for (Integer vt : polygons.getPolygonTextureVertexIndices().get(index)) {
            if (vt > textureVertices.size() - 1) {
                vt += 1;
                throw new ObjReaderException("Error of getting wrong TextureIndex: " + vt + ". Maximum: " + textureVertices.size());
            }
        }
        for (Integer vn : polygons.getPolygonNormalIndices().get(index)) {
            if (vn > normals.size() - 1) {
                vn += 1;
                throw new ObjReaderException("Error of getting wrong NormalIndex: " + vn + ". Maximum: " + normals.size());
            }
        }
    }

}
