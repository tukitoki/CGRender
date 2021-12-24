package com.vsu.cgcourse.model;

import com.vsu.cgcourse.obj_reader.ObjReaderException;

import java.util.ArrayList;

public class Polygons {

    private ArrayList<Integer> polygonVertexIndices;
    private ArrayList<Integer> polygonTextureVertexIndices;
    private ArrayList<Integer> polygonNormalIndices;

    public Polygons() {
        polygonVertexIndices = new ArrayList<>();
        polygonTextureVertexIndices = new ArrayList<>();
        polygonNormalIndices = new ArrayList<>();
    }

    public ArrayList<Integer> getPolygonVertexIndices() {
        return polygonVertexIndices;
    }

    public ArrayList<Integer> getPolygonTextureVertexIndices() {
        return polygonTextureVertexIndices;
    }

    public ArrayList<Integer> getPolygonNormalIndices() {
        return polygonNormalIndices;
    }

//    public void recheckOnCorrect(int index) {
//        checkOnSize(index);
//        checkOnCorrectPolygonFill(index);
//        /*if (!polygonTextureVertexIndices.get(index).isEmpty() && !polygonNormalIndices.get(index).isEmpty()) {
//            if (polygonTextureVertexIndices.get(index).size() != polygonNormalIndices.get(index).size()) {
//                throw new ObjReaderException("Different size between TextureVertexIndices and NormalIndices");
//            }
//        }*/
//    }

    private void checkOnSize(int index) {
        if (!polygonVertexIndices.isEmpty() && !polygonTextureVertexIndices.isEmpty()) {
            if (polygonVertexIndices.size() != polygonTextureVertexIndices.size()) {
                throw new ObjReaderException("Different size between VertexIndices and TextureVertexIndices");
            }
        }
        if (!polygonVertexIndices.isEmpty() && !polygonNormalIndices.isEmpty()) {
            if (polygonVertexIndices.size() != polygonNormalIndices.size()) {
                throw new ObjReaderException("Different size between VertexIndices and NormalIndices");
            }
        }
    }

    public void triangulation() {

    }

//    private void checkOnCorrectPolygonFill(int index) {
//        if (index != 0) {
//            if (polygonTextureVertexIndices.get(0).isEmpty() && !polygonTextureVertexIndices.get(index).isEmpty()) {
//                throw new ObjReaderException("Unexpected TextureVertexIndices");
//            }
//            if (!polygonTextureVertexIndices.get(0).isEmpty() && polygonTextureVertexIndices.get(index).isEmpty()) {
//                throw new ObjReaderException("Can't find TextureVertexIndices");
//            }
//            if (polygonNormalIndices.get(0).isEmpty() && !polygonNormalIndices.get(index).isEmpty()) {
//                throw new ObjReaderException("Unexpected NormalIndices");
//            }
//            if (!polygonNormalIndices.get(0).isEmpty() && polygonNormalIndices.get(index).isEmpty()) {
//                throw new ObjReaderException("Can't find NormalIndices");
//            }
//        }
//    }
}
