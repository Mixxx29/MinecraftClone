package org.example.mesh;

import org.example.material.Material;
import org.example.util.FileLoader;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class OBJLoader {
    public static Mesh loadMesh(String filepath) {
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        List<String> lines = FileLoader.readLines(filepath);
        if (lines == null) throw new RuntimeException("Failed to load file: " + filepath);
        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v" -> {
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    vertices.add(vertex);
                }

                case "vt" -> {
                    Vector2f texture = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2])
                    );
                    textures.add(texture);
                }

                case "vn" -> {
                    Vector3f normal = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    normals.add(normal);
                }

                case "f" -> {
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                }
            }
        }

        return convert(vertices, textures, normals, faces);
    }

    private static Mesh convert(
            List<Vector3f> vertices,
            List<Vector2f> textures,
            List<Vector3f> normals,
            List<Face> faces
    ) {
        List<Integer> indices = new ArrayList<>();

        float[] vertexArray = new float[vertices.size() * 3];
        int i = 0;
        for (Vector3f vertex : vertices) {
            vertexArray[i * 3] = vertex.x;
            vertexArray[i * 3 + 1] = vertex.y;
            vertexArray[i * 3 + 2] = vertex.z;
            i++;
        }

        float[] textureArray = new float[vertices.size() * 2];
        float[] normalArray = new float[vertices.size() * 3];

        for (Face face : faces) {
            Vertex[] vertexGroup = face.getVertices();
            for (Vertex vertex : vertexGroup) {
                vertex.process(
                        textures,
                        normals,
                        indices,
                        textureArray,
                        normalArray
                );
            }
        }

        int[] indexArray = indices.stream().mapToInt((Integer v) -> v).toArray();

        Material material = new Material(
                new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
                0.0f
        );
        return new Mesh(vertexArray, indexArray, textureArray, normalArray, material);
    }
}
