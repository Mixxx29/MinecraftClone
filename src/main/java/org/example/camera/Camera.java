package org.example.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private final Vector3f position;
    private final Vector3f rotation;
    private final Matrix4f viewMatrix;

    public Camera() {
        position = new Vector3f(0.0f, 0.0f, 0.0f);
        rotation = new Vector3f(0.0f, 0.0f, 0.0f);
        viewMatrix = new Matrix4f();
    }

    public void move(float deltaX, float deltaY, float deltaZ) {
        moveX(deltaX);
        moveY(deltaY);
        moveZ(deltaZ);
    }

    private void moveX(double deltaX) {
        if (deltaX == 0) return;
        position.x -= (float) Math.sin(Math.toRadians(rotation.y - 90)) * deltaX;
        position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * deltaX;
    }

    private void moveY(double deltaY) {
        position.y += deltaY;
    }

    private void moveZ(double deltaZ) {
        if (deltaZ == 0) return;
        position.x -= (float) Math.sin(Math.toRadians(rotation.y)) * deltaZ;
        position.z += (float) Math.cos(Math.toRadians(rotation.y)) * deltaZ;
    }

    public void rotate(float deltaX, float deltaY, float deltaZ) {
        rotation.x = (rotation.x + deltaX + 360) % 360;
        rotation.y = (rotation.y + deltaY + 360) % 360;
        rotation.z = (rotation.z + deltaZ + 360) % 360;
    }

    public Matrix4f getViewMatrix() {
        viewMatrix.identity()
                .rotate((float) Math.toRadians(rotation.x), new Vector3f(1.0f, 0.0f, 0.0f))
                .rotate((float) Math.toRadians(rotation.y), new Vector3f(0.0f, 1.0f, 0.0f))
                .translate(-position.x, -position.y, -position.z);
        return new Matrix4f(viewMatrix);
    }

    public Vector3f getPosition() {
        return position;
    }
}
