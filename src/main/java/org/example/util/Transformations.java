package org.example.util;

import org.example.camera.Camera;
import org.example.engine.GameObject;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformations {
    private static final Matrix4f projectionMatrix;
    private static final Matrix4f worldMatrix;
    private static final Matrix4f viewMatrix;
    private static final Matrix4f modelViewMatrix;

    static {
        projectionMatrix = new Matrix4f();
        worldMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        modelViewMatrix = new Matrix4f();
    }

    public static Matrix4f getProjectionMatrix(
            float fov,
            float width,
            float height,
            float zNear,
            float zFar
    ) {
        float aspectRation = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(
                fov,
                aspectRation,
                zNear,
                zFar
        );
        return projectionMatrix;
    }

    public static Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, Vector3f scale) {
        worldMatrix.identity()
                .translate(offset)
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateY((float) Math.toRadians(rotation.y))
                .rotateZ((float) Math.toRadians(rotation.z))
                .scale(scale);
        return worldMatrix;
    }

    public static Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPosition = camera.getPosition();
        Vector3f cameraRotation = camera.getRotation();

        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians(cameraRotation.x), new Vector3f(1.0f, 0.0f, 0.0f));
        viewMatrix.rotate((float) Math.toRadians(cameraRotation.y), new Vector3f(0.0f, 1.0f, 0.0f));
        viewMatrix.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

        return viewMatrix;
    }

    public static Matrix4f getModelViewMatrix(GameObject gameObject, Matrix4f viewMatrix) {
        Vector3f rotation = gameObject.getRotation();
        modelViewMatrix.identity()
                .translate(gameObject.getPosition())
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateY((float) Math.toRadians(rotation.y))
                .rotateZ((float) Math.toRadians(rotation.z))
                .scale(gameObject.getScale());
        Matrix4f viewCurrent = new Matrix4f(viewMatrix);
        return viewCurrent.mul(modelViewMatrix);
    }
}
