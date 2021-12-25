package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.obj_reader.ObjReaderException;

import java.util.*;

public class Mesh {

    private ArrayList<Vector3> vertices;
    private ArrayList<Vector2> textureVertices;
    private ArrayList<Vector3> normals;
    private ArrayList<Polygon> polygons;

    public Mesh() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new ArrayList<>();
    }

    public ArrayList<Vector3> getVertices() {
        return vertices;
    }

    public ArrayList<Vector2> getTextureVertices() {
        return textureVertices;
    }

    public ArrayList<Vector3> getNormals() {
        return normals;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(ArrayList polygons) {
        this.polygons = polygons;
    }

    public void recheckModel() {
        for (int index = 0; index < polygons.size(); index++) {
            checkOnCorrectPolygonFill(index);
            recheckOnRightIndices(index);
        }
    }


    public void recheckOnCorrect(int index) {
        checkOnSize(index);
        checkOnCorrectPolygonFill(index);
        /*if (!polygonTextureVertexIndices.get(index).isEmpty() && !polygonNormalIndices.get(index).isEmpty()) {
            if (polygonTextureVertexIndices.get(index).size() != polygonNormalIndices.get(index).size()) {
                throw new ObjReaderException("Different size between TextureVertexIndices and NormalIndices");
            }
        }*/
    }

    private void checkOnSize(int index) {
        if (!polygons.isEmpty() && !polygons.get(index).getPolygonTextureVertexIndices().isEmpty()) {
            if (polygons.get(index).getPolygonVertexIndices().size() != polygons.get(index).getPolygonTextureVertexIndices().size()) {
                throw new ObjReaderException("Different size between VertexIndices and TextureVertexIndices");
            }
        }
        if (!polygons.isEmpty() && !polygons.get(index).getPolygonNormalIndices().isEmpty()) {
            if (polygons.get(index).getPolygonVertexIndices().size() != polygons.get(index).getPolygonNormalIndices().size()) {
                throw new ObjReaderException("Different size between VertexIndices and NormalIndices");
            }
        }
    }

    private void recheckOnRightIndices(int index) {
        for (Integer v : polygons.get(index).getPolygonVertexIndices()) {
            if (v > vertices.size() - 1) {
                v += 1;
                throw new ObjReaderException("Error of getting wrong VertexIndex: " + v + ". Maximum: " + vertices.size());
            }
        }
        for (Integer vt : polygons.get(index).getPolygonTextureVertexIndices()) {
            if (vt > textureVertices.size() - 1) {
                vt += 1;
                throw new ObjReaderException("Error of getting wrong TextureIndex: " + vt + ". Maximum: " + textureVertices.size());
            }
        }
        for (Integer vn : polygons.get(index).getPolygonNormalIndices()) {
            if (vn > normals.size() - 1) {
                vn += 1;
                throw new ObjReaderException("Error of getting wrong NormalIndex: " + vn + ". Maximum: " + normals.size());
            }
        }
    }
    private void checkOnCorrectPolygonFill(int index) {
        if (index != 0) {
            if (polygons.get(0).getPolygonTextureVertexIndices().isEmpty() && !polygons.get(index).getPolygonTextureVertexIndices().isEmpty()) {
                throw new ObjReaderException("Unexpected TextureVertexIndices");
            }
            if (!polygons.get(0).getPolygonTextureVertexIndices().isEmpty() && polygons.get(index).getPolygonTextureVertexIndices().isEmpty()) {
                throw new ObjReaderException("Can't find TextureVertexIndices");
            }
            if (polygons.get(0).getPolygonNormalIndices().isEmpty() && !polygons.get(index).getPolygonNormalIndices().isEmpty()) {
                throw new ObjReaderException("Unexpected NormalIndices");
            }
            if (!polygons.get(0).getPolygonNormalIndices().isEmpty() && polygons.get(index).getPolygonNormalIndices().isEmpty()) {
                throw new ObjReaderException("Can't find NormalIndices");
            }
        }
    }

}
