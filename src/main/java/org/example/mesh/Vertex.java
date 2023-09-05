package org.example.mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;

public class Vertex {
    public static final int NO_VALUE = -1;
    private final int index;
    private final int textureCoordsIndex;
    private final int normalIndex;

    public Vertex(int index, int textureCoordsIndex, int normalIndex) {
        this.index = index;
        this.textureCoordsIndex = textureCoordsIndex;
        this.normalIndex = normalIndex;
    }

    public void process(
            List<Vector2f> textures,
            List<Vector3f> normals,
            List<Integer> indices,
            float[] textureArray,
            float[] normalArray
    ) {
        indices.add(index);
        setTextureCoordsValue(textures, textureArray, index);
        setNormalValue(normals, normalArray, index);
    }

    public void setTextureCoordsValue(List<Vector2f> textureCoordsList, float[] textureArray, int index) {
        if (textureCoordsIndex == Vertex.NO_VALUE) return;
        Vector2f textureCoords = textureCoordsList.get(textureCoordsIndex);
        textureArray[index * 2] = textureCoords.x;
        textureArray[index * 2 + 1] = 1.0f - textureCoords.y;
    }

    public void setNormalValue(List<Vector3f> normalsList, float[] normalArray, int index) {
        if (normalIndex == Vertex.NO_VALUE) return;
        Vector3f normal = normalsList.get(normalIndex);
        normalArray[index * 3] = normal.x;
        normalArray[index * 3 + 1] = normal.y;
        normalArray[index * 3 + 2] = normal.z;
    }
}
