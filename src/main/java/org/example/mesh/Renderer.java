package org.example.mesh;

import org.example.camera.Camera;
import org.example.engine.GameObject;
import org.example.light.PointLight;
import org.example.shader.ShaderProgram;
import org.example.util.FileLoader;
import org.example.util.Transformations;
import org.example.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Renderer {
    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.0f;
    private final Window window;
    private final ShaderProgram shaderProgram;

    public Renderer(Window window) {
        this.window = window;

        // Create shader program
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(FileLoader.loadContent("shaders/vertex.vert"));
        shaderProgram.createFragmentShader(FileLoader.loadContent("shaders/fragment.frag"));
        shaderProgram.link();

        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("modelViewMatrix");
        shaderProgram.createUniform("textureSampler");
        shaderProgram.createUniform("ambientColor");
        shaderProgram.createUniform("specularPower");
        shaderProgram.createPointLightUniform("pointLight");
        shaderProgram.createMaterialUniform("material");
    }

    public void render(GameObject[] gameObjects, Camera camera, PointLight pointLight) {
        shaderProgram.bind();

        Matrix4f projectionMatrix = Transformations.getProjectionMatrix(
                FOV,
                window.getWidth(),
                window.getHeight(),
                Z_NEAR,
                Z_FAR
        );
        Matrix4f viewMatrix = camera.getViewMatrix();

        shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        shaderProgram.setUniform("textureSampler", 0);
        shaderProgram.setUniform("ambientColor", new Vector3f(1.0f, 1.0f, 1.0f));
        shaderProgram.setUniform("specularPower", 1.0f);
        shaderProgram.setUniform("pointLight", pointLight, viewMatrix);

        for (GameObject gameObject : gameObjects) {
            Matrix4f modelViewMatrix = gameObject.getModelViewMatrix(viewMatrix);
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);

            gameObject.setShaderMaterial(shaderProgram);

            gameObject.render();
        }

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }

    public void clear() {

    }
}
