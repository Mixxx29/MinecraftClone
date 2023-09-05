package org.example.engine;

import org.example.mesh.Mesh;
import org.example.shader.ShaderProgram;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GameObject {
    private final Matrix4f modelViewMatrix;

    private final Mesh mesh;
    private final Vector3f position;
    private final Vector3f rotation;
    private final Vector3f scale;

    public GameObject(Mesh mesh) {
        this.mesh = mesh;
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);
        this.scale = new Vector3f(0.5f, 0.5f, 0.5f);
        this.modelViewMatrix = new Matrix4f();
    }

    public void render() {
        mesh.render();
    }

    public void setShaderMaterial(ShaderProgram shaderProgram) {
        shaderProgram.setUniform("material", mesh.getMaterial());
    }

    public Matrix4f getModelViewMatrix(Matrix4f viewMatrix) {
        calculateWorldMatrix();
        Matrix4f viewCurrent = new Matrix4f(viewMatrix);
        return viewCurrent.mul(modelViewMatrix);
    }

    private void calculateWorldMatrix() {
        modelViewMatrix.identity()
                .translate(position)
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateY((float) Math.toRadians(rotation.y))
                .rotateZ((float) Math.toRadians(rotation.z))
                .scale(scale);
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }


    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void setScale(float x, float y, float z) {
        this.scale.x = x;
        this.scale.y = y;
        this.scale.z = z;
    }
}
