package org.example.mesh;

import org.example.material.Material;
import org.example.texture.Texture;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private final int vaoId;
    private final int vertexVboId;
    private final int indexVboId;
    private final int vertexCount;
    private Texture texture;
    private int normalVboId = -1;
    private int textureVboId;
    private Vector3f color = new Vector3f(1.0f, 1.0f, 1.0f);
    private boolean isWireframe;

    private Material material;

    public Mesh(float[] vertices, int[] indices, float[] textureCoords, float[] normals, Material material) {
        this.material = material;
        vertexCount = indices.length;

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Vertices
        vertexVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexVboId);
        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexBuffer.put(vertices).flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        MemoryUtil.memFree(vertexBuffer);

        // Indices
        indexVboId = glGenBuffers();
        IntBuffer indexBuffer = MemoryUtil.memAllocInt(indices.length);
        indexBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(indexBuffer);

        // Texture
        textureVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textureVboId);
        FloatBuffer textureCoordsBuffer = MemoryUtil.memAllocFloat(textureCoords.length);
        textureCoordsBuffer.put(textureCoords).flip();
        glBufferData(GL_ARRAY_BUFFER, textureCoordsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        if (normals != null) {
            // Normals
            normalVboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, normalVboId);
            FloatBuffer normalsBuffer = MemoryUtil.memAllocFloat(normals.length);
            normalsBuffer.put(normals).flip();
            glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void render() {
        if (texture != null) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture.getId());
        }

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        if (normalVboId != -1) glEnableVertexAttribArray(2);

        if (isWireframe) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        if (normalVboId != -1) glDisableVertexAttribArray(2);

        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void cleanup() {
        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vertexVboId);
        glDeleteBuffers(indexVboId);
        glDeleteBuffers(textureVboId);
        glDeleteBuffers(normalVboId);

        // Delete the VAO
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
        glDeleteVertexArrays(vaoId);
    }

    public Vector3f getColor() {
        return color;
    }

    public boolean hasTexture() {
        return texture != null;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.material.setHasTexture(hasTexture());
    }

    public void setWireframe(boolean wireframe) {
        isWireframe = wireframe;
    }

    public Material getMaterial() {
        return material;
    }
}
