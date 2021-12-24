package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class DeleteFace {

    public static void deleteFace(Mesh model, ArrayList<Integer> indices, boolean deleteVertexes) throws IOException {
//        if (indices.isEmpty()) {
//            return;
//        }
//        indices.sort(Comparator.comparingInt(o -> o));
//        int indexOfDelete;
//        int changeSize = 0;
//        for (int i = 0; i < indices.size(); i++) {
//            indexOfDelete = indices.get(i);
//            if (indexOfDelete >= model.getPolygons().getPolygonTextureVertexIndices().size()) {
//                indexOfDelete -= changeSize;
//            }
//            for (int j = 0; j < model.getPolygons().getPolygonVertexIndices().size(); j++) {
//                if (indexOfDelete == j) {
//                    model.getPolygons().getPolygonVertexIndices().remove(j);
//                    model.getPolygons().getPolygonNormalIndices().remove(j);
//                    model.getPolygons().getPolygonTextureVertexIndices().remove(j);
//                    changeSize++;
//                    break;
//                }
//            }
//        }
//        if (deleteVertexes) {
//            deleteFreeVertices(model.getPolygons().getPolygonVertexIndices(), model.getVertices());
//            deleteTextureVertices(model.getPolygons().getPolygonTextureVertexIndices(), model.getTextureVertices());
//            deleteFreeVertices(model.getPolygons().getPolygonNormalIndices(), model.getNormals());
//        }
    }

    private static void deleteTextureVertices(ArrayList<ArrayList<Integer>> polygonDeleteIndices, ArrayList<Vector2> deleteVertex) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < polygonDeleteIndices.size(); i++) {
            set.addAll(polygonDeleteIndices.get(i));
        }
        int buffer = 0;
        for (int i = 0; i < deleteVertex.size(); i++) {
            if (!set.contains(i + buffer)) {
                deleteVertex.remove(i);
                overrideIndices(polygonDeleteIndices, i);
                i--;
                buffer++;
            }
        }
    }

    private static void deleteFreeVertices(ArrayList<ArrayList<Integer>> polygonDeleteIndices, ArrayList<Vector3> vertices) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < polygonDeleteIndices.size(); i++) {
            set.addAll(polygonDeleteIndices.get(i));
        }
        int buffer = 0;
        for (int vertexInd = 0; vertexInd < vertices.size(); vertexInd++) {
            if (!set.contains(vertexInd + buffer)) {
                vertices.remove(vertexInd);
                overrideIndices(polygonDeleteIndices, vertexInd);
                vertexInd--;
                buffer++;
            }
        }
    }

    private static void overrideIndices(ArrayList<ArrayList<Integer>> listForDelete, int deletedIndex) {
        int n;
        for (int i = 0; i < listForDelete.size(); i++) {
            for (int j = 0; j < listForDelete.get(i).size(); j++) {
                if (listForDelete.get(i).get(j) > deletedIndex) {
                    n = listForDelete.get(i).get(j);
                    n--;
                    listForDelete.get(i).set(j, n);
                }
            }
        }
    }

//    private static void writeIntoFile(Model model, Path fileName) throws IOException {
//        FileWriter writer = new FileWriter(fileName.toString(), false);
//        if (model.vertices.size() != 0) {
//            for (int i = 0; i < model.vertices.size(); i++) {
//                writer.write("v " + model.vertices.get(i).x + " "
//                        + model.vertices.get(i).y + " " + model.vertices.get(i).z + "\n");
//            }
//        }
//        if (model.textureVertices.size() != 0) {
//            for (int i = 0; i < model.textureVertices.size(); i++) {
//                writer.write("vt " + model.textureVertices.get(i).x + " "
//                        + model.textureVertices.get(i).y + "\n");
//            }
//        }
//        if (model.normals.size() != 0) {
//            for (int i = 0; i < model.normals.size(); i++) {
//                writer.write("vn " + model.normals.get(i).x + " "
//                        + model.normals.get(i).y + " " + model.normals.get(i).z + "\n");
//            }
//        }
//        for (int i = 0; i < model.polygonVertexIndices.size(); i++) {
//            writer.write("f ");
//            for (int j = 0; j < model.polygonVertexIndices.get(i).size(); j++) {
//                writer.write(Integer.toString(model.polygonVertexIndices.get(i).get(j) + 1));
//                if (model.polygonTextureVertexIndices.get(i).size() > 0) {
//                    writer.write( "/" + (model.polygonTextureVertexIndices.get(i).get(j) + 1));
//                }
//                if (model.polygonNormalIndices.get(i).size() > 0) {
//                    writer.write("/" + (model.polygonNormalIndices.get(i).get(j) + 1) + " ");
//                } else {
//                    writer.write(" ");
//                }
//            }
//            writer.write("\n");
//        }
//        writer.flush();
//    }
//
//
//
//    private static String chooseDelete(String deleteFileName) throws IOException {
//        FileReader reader = new FileReader(deleteFileName);
//        BufferedReader bufferedReader = new BufferedReader(reader);
//        return bufferedReader.readLine();
//    }

}
