package com.vsu.cgcourse.obj_writer;

import com.vsu.cgcourse.model.Mesh;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ObjWriter {

    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";

    private ObjWriter() {

    }

    protected static void writeAllVertices(
            FileWriter writer,
            final ArrayList<Vector3f> vertices,
            final ArrayList<Vector2f> textureVertices,
            final ArrayList<Vector3f> normals) throws Exception{
        List<String> listVertices = vertices.stream().map(Vector3f::toString).collect(Collectors.toList());
        List<String> listTextureVertices = textureVertices.stream().map(Vector2f::toString).collect(Collectors.toList());
        List<String> listNormals = normals.stream().map(Vector3f::toString).collect(Collectors.toList());
        writer.write(writeTheDesiredVertices(listVertices, OBJ_VERTEX_TOKEN));
        writer.write("\n");
        writer.flush();
        writer.write(writeTheDesiredVertices(listTextureVertices, OBJ_TEXTURE_TOKEN));
        writer.write("\n");
        writer.flush();
        writer.write(writeTheDesiredVertices(listNormals, OBJ_NORMAL_TOKEN));
        writer.write("\n");
        writer.flush();
//        return writeTheDesiredVertices(listVertices, OBJ_VERTEX_TOKEN) +
//                writeTheDesiredVertices(listTextureVertices, OBJ_TEXTURE_TOKEN) +
//                writeTheDesiredVertices(listNormals, OBJ_NORMAL_TOKEN);
    }


    protected static String writeTheDesiredVertices(final List<String> vertices, final String token) {
        StringBuilder str = new StringBuilder();
        for (String vector : vertices) {
            str.append(token).append(" ").append(vector.replaceAll("[(,)]", "")).append("\n");
        }
        return str.toString();
    }


    protected static String writeFace(
            final ArrayList<ArrayList<Integer>> polygonVertexIndices,
            final ArrayList<ArrayList<Integer>> polygonTextureVertexIndices,
            final ArrayList<ArrayList<Integer>> polygonNormalIndices) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < polygonVertexIndices.size(); i++) {
            str.append(OBJ_FACE_TOKEN);
            for (int j = 0; j < polygonVertexIndices.get(i).size(); j++) {
                str.append(" ").append(polygonVertexIndices.get(i).get(j) + 1);

                if (!polygonTextureVertexIndices.get(i).isEmpty()) {
                    str.append("/");
                    str.append(polygonTextureVertexIndices.get(i).get(j) + 1);
                }

                if (!polygonNormalIndices.get(i).isEmpty()) {
                    str.append("/");
                    str.append(polygonNormalIndices.get(i).get(j) + 1);
                }
            }
            str.append("\n");
        }
        return str.toString();
    }

    protected static void isModelReadyForRecording(final Mesh model) throws Exception {
        model.recheckModel();
    }

    public static void write(final Mesh model, final File file) throws Exception {
        isModelReadyForRecording(model);
        FileWriter writer = new FileWriter(file, false);

        writeAllVertices(writer, model.getVertices(), model.getTextureVertices(), model.getNormals());
        writer.flush();
        writer.write("\n");
        //writer.write(writeAllVertices(model.getVertices(), model.getTextureVertices(), model.getNormals()));
        try {
            writer.write(writeFace(
                    model.getPolygons().getPolygonVertexIndices(),
                    model.getPolygons().getPolygonTextureVertexIndices(),
                    model.getPolygons().getPolygonNormalIndices())
            );
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
