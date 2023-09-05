package org.example.util;

import org.joml.Matrix4f;

public class Transformations {
    private static final Matrix4f projectionMatrix;

    static {
        projectionMatrix = new Matrix4f();
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
}
