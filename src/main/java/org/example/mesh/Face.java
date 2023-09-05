package org.example.mesh;

public class Face {
    private final Vertex[] vertices;

    public Face(String... tokens) {
        vertices = new Vertex[3];
        vertices[0] = parseVertex(tokens[0]);
        vertices[1] = parseVertex(tokens[1]);
        vertices[2] = parseVertex(tokens[2]);
    }

    private Vertex parseVertex(String token) {
        String[] tokenParts = token.split("/");
        int vertexIndex = Integer.parseInt(tokenParts[0]) - 1;

        int textureCoordsIndex = Vertex.NO_VALUE;
        if (tokenParts.length > 1 && tokenParts[1].length() > 0) {
            textureCoordsIndex = Integer.parseInt(tokenParts[0]) - 1;
        }

        int normalIndex = Vertex.NO_VALUE;
        if (tokenParts.length > 2 && tokenParts[2].length() > 0) {
            normalIndex = Integer.parseInt(tokenParts[2]) - 1;
        }

        return new Vertex(vertexIndex, textureCoordsIndex, normalIndex);
    }

    public Vertex[] getVertices() {
        return vertices;
    }
}
