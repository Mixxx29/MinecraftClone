package org.example.light;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PointLight {
    private final Matrix4f modelViewMatrix;
    private Vector3f color;
    private Vector3f position;
    private float intensity;
    private Attenuation attenuation;

    public PointLight(Vector3f color, Vector3f position, float intensity, Attenuation attenuation) {
        this.color = color;
        this.position = position;
        this.intensity = intensity;
        this.attenuation = attenuation;
        this.modelViewMatrix = new Matrix4f();
    }

    public Vector3f getCameraViewPosition(Matrix4f viewMatrix) {
        calculateWorldMatrix();
        Matrix4f viewCurrent = new Matrix4f(viewMatrix);
        viewCurrent = viewCurrent.mul(modelViewMatrix);
        return viewCurrent.transformPosition(new Vector3f(position));
    }

    private void calculateWorldMatrix() {
        modelViewMatrix.identity()
                .translate(position);
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getIntensity() {
        return intensity;
    }

    public Attenuation getAttenuation() {
        return attenuation;
    }
}

