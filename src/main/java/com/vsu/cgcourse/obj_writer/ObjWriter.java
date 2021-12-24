package com.vsu.cgcourse.obj_writer;

import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.model.MeshContext;
import com.vsu.cgcourse.model.Polygons;
import com.vsu.cgcourse.render_engine.Converter;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vsu.cgcourse.render_engine.GraphicConveyor.multiplyMatrix4ByVector3;
import static com.vsu.cgcourse.render_engine.GraphicConveyor.rotateScaleTranslate;

public class ObjWriter {

    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";

    private ObjWriter() {

    }

    protected static void writeAllVertices(
            FileWriter writer,
            final ArrayList<Vector3> vertices,
            final ArrayList<Vector2> textureVertices,
            final ArrayList<Vector3> normals,
            final MeshContext meshContext) throws Exception {
        if (meshContext.getStatus().isChanges()) {
            Matrix4 modelMatrix = rotateScaleTranslate(meshContext);
            modelMatrix.transposite();
            for (int i = 0; i < vertices.size(); i++) {
                vertices.set(i, multiplyMatrix4ByVector3(modelMatrix, vertices.get(i)));
            }
        }
        List<String> listVertices = vertices.stream().map(Vector3::toString).collect(Collectors.toList());
        List<String> listTextureVertices = textureVertices.stream().map(Vector2::toString).collect(Collectors.toList());
        List<String> listNormals = normals.stream().map(Vector3::toString).collect(Collectors.toList());
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
            final ArrayList<Polygons> polygons) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < polygons.size(); i++) {
            str.append(OBJ_FACE_TOKEN);
            for (int j = 0; j < polygons.get(i).getPolygonVertexIndices().size(); j++) {
                str.append(" ").append(polygons.get(i).getPolygonVertexIndices().get(j) + 1);

                if (!polygons.get(i).getPolygonTextureVertexIndices().isEmpty()) {
                    str.append("/");
                    str.append(polygons.get(i).getPolygonTextureVertexIndices().get(j) + 1);
                }

                if (!polygons.get(i).getPolygonNormalIndices().isEmpty()) {
                    str.append("/");
                    if (polygons.get(i).getPolygonTextureVertexIndices().isEmpty()) {
                        str.append("/");
                    }
                    str.append(polygons.get(i).getPolygonNormalIndices().get(j) + 1);
                }
            }
            str.append("\n");
        }
        return str.toString();
    }

    protected static void isModelReadyForRecording(final Mesh model) throws Exception {
        model.recheckModel();
    }

    public static void write(final File file, MeshContext meshContext) throws Exception {
        FileWriter writer = new FileWriter(file, false);
        Mesh model;
        if (meshContext.getStatus().isChanges()) {
            model = meshContext.getMesh();
        } else {
            model = meshContext.getOldMesh();
        }
        isModelReadyForRecording(model);
        writeAllVertices(writer, model.getVertices(), model.getTextureVertices(), model.getNormals(), meshContext);
        writer.flush();
        writer.write("\n");
        try {
            writer.write(writeFace(
                    model.getPolygons()));
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
