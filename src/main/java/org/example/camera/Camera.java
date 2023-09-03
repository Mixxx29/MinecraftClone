package org.example.camera;

import org.joml.Vector3d;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private Vector3f rotation;

    public Camera() {
        position = new Vector3f(0.0f, 0.0f, 0.0f);
        rotation = new Vector3f(0.0f, 0.0f, 0.0f);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void move(Vector3d offset) {
        if (offset.z != 0) {
            position.x -= (float) Math.sin(Math.toRadians(rotation.y)) * offset.z;
            position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offset.z;
        }

        if (offset.x != 0) {
            position.x -= (float) Math.sin(Math.toRadians(rotation.y - 90)) * offset.x;
            position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offset.x;
        }

        position.y += offset.y;
    }

    public void rotate(Vector3f offset) {
        rotation.x += offset.x;
        rotation.y += offset.y;
        rotation.z += offset.z;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
