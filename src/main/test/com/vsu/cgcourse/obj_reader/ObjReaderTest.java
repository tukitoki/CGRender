package com.vsu.cgcourse.obj_reader;

import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.model.Polygons;
import com.vsu.cgcourse.obj_reader.ObjReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class ObjReaderTest {

    @Test
    public void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3 result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3 expectedResult = new Vector3(new float[] {1.01f, 1.02f, 1.03f});
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3 result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3 expectedResult = new Vector3(new float[]{1.01f, 1.02f, 1.10f});
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex05() {
        // АГААА! Вот тест, который говорит, что у метода нет проверки на более, чем 3 числа
        // А такой случай лучше не игнорировать, а сообщать пользователю, что у него что-то не так
        // ассерт, чтобы не забыть про тест:
        //Assertions.assertTrue(false);


        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        Vector2 result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2 expectedResult = new Vector2(new float[] {1.01f, 1.02f});
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseTextureVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormalVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("12", "12", "12"));
        Vector3 result = ObjReader.parseNormal(wordsInLineWithoutToken, 5);
        Vector3 expectedResult = new Vector3(new float[]{12f, 12f, 12f});
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseNormalVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "22"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormalVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "sad"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormalVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "3"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFace01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/2", "1/2/3", "2/3/2"));
        ArrayList<Polygons> polygons = new ArrayList<>();
        polygons.add(ObjReader.parseFace(wordsInLineWithoutToken, 10));
        ArrayList<Integer> expVertexes = new ArrayList<>(Arrays.asList(0, 0, 1));
        ArrayList<Integer> expVertTextures = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> expNormals = new ArrayList<>(Arrays.asList(1, 2, 1));
        Assertions.assertEquals(polygons.get(0).getPolygonVertexIndices(), expVertexes);
        Assertions.assertEquals(polygons.get(0).getPolygonTextureVertexIndices(), expVertTextures);
        Assertions.assertEquals(polygons.get(0).getPolygonNormalIndices(), expNormals);
    }

    @Test
    public void testParseFace02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1", "1/2", "2/3"));
        ArrayList<Polygons> polygons = new ArrayList<>();
        polygons.add(ObjReader.parseFace(wordsInLineWithoutToken, 10));
        ArrayList<Integer> expVertexes = new ArrayList<>(Arrays.asList(0, 0, 1));
        ArrayList<Integer> expVertTextures = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> expNormals = new ArrayList<>();
        Assertions.assertEquals(polygons.get(0).getPolygonVertexIndices(), expVertexes);
        Assertions.assertEquals(polygons.get(0).getPolygonTextureVertexIndices(), expVertTextures);
        Assertions.assertEquals(polygons.get(0).getPolygonNormalIndices(), expNormals);
    }

    @Test
    public void testParseFace03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "1//3", "2//2"));
        ArrayList<Polygons> polygons = new ArrayList<>();
        polygons.add(ObjReader.parseFace(wordsInLineWithoutToken, 10));
        ArrayList<Integer> expVertexes = new ArrayList<>(Arrays.asList(0, 0, 1));
        ArrayList<Integer> expVertTextures = new ArrayList<>();
        ArrayList<Integer> expNormals = new ArrayList<>(Arrays.asList(1, 2, 1));
        Assertions.assertEquals(polygons.get(0).getPolygonVertexIndices(), expVertexes);
        Assertions.assertEquals(polygons.get(0).getPolygonTextureVertexIndices(), expVertTextures);
        Assertions.assertEquals(polygons.get(0).getPolygonNormalIndices(), expNormals);
    }

    @Test
    public void testParseFace04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/2", "1//3", "2//2"));
        ArrayList<Polygons> polygons = new ArrayList<>();
        polygons.add(ObjReader.parseFace(wordsInLineWithoutToken, 10));
        Mesh mesh = new Mesh();
        mesh.setPolygons(polygons);
        try {
            mesh.recheckOnCorrect(0);
        } catch (ObjReaderException e) {
            String expectedError = "Error parsing OBJ file. Different size between VertexIndices and TextureVertexIndices";
            Assertions.assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    public void testParseFace05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "1//3", "2"));
        ArrayList<Polygons> polygons = new ArrayList<>();
        polygons.add(ObjReader.parseFace(wordsInLineWithoutToken, 10));
        Mesh mesh = new Mesh();
        mesh.setPolygons(polygons);
        try {
            mesh.recheckOnCorrect(0);
        } catch (ObjReaderException e) {
            String expectedError = "Error parsing OBJ file. Different size between VertexIndices and NormalIndices";
            Assertions.assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    public void testParseFace06() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/2/2", "1/2/3", "1/2"));
        ArrayList<Polygons> polygons = new ArrayList<>();
        polygons.add(ObjReader.parseFace(wordsInLineWithoutToken, 10));
        Mesh mesh = new Mesh();
        mesh.setPolygons(polygons);
        try {
            mesh.recheckOnCorrect(0);
        } catch (ObjReaderException e) {
            String expectedError = "Error parsing OBJ file. Different size between VertexIndices and NormalIndices";
            Assertions.assertEquals(expectedError, e.getMessage());
        }
    }

}
