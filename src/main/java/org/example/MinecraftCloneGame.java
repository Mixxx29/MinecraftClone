package org.example;

import org.example.camera.Camera;
import org.example.engine.Game;
import org.example.engine.GameObject;
import org.example.input.MouseInput;
import org.example.mesh.Mesh;
import org.example.mesh.OBJLoader;
import org.example.mesh.Renderer;
import org.example.texture.Texture;
import org.example.window.Window;
import org.joml.Vector2f;
import org.joml.Vector3i;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class MinecraftCloneGame implements Game {
    private final String title;
    private final Camera camera = new Camera();
    private final float speed = 1.0f;
    private final float rotationSpeed = 10.0f;
    private final Vector3i direction = new Vector3i(0, 0, 0);
    private Renderer renderer;
    private GameObject[] gameObjects;

    public MinecraftCloneGame(String title) {
        this.title = title;
    }

    @Override
    public void init(Window window) {
        renderer = new Renderer(window);
        window.setBackground(Color.BLACK);

        float[] vertices = new float[]{
                // Front face
                -0.5f, 0.5f, 0.5f,   // Top left
                -0.5f, -0.5f, 0.5f,  // Bottom left
                0.5f, -0.5f, 0.5f,   // Bottom right
                0.5f, 0.5f, 0.5f,    // Top right

                // Back face
                0.5f, 0.5f, -0.5f,   // Top left
                0.5f, -0.5f, -0.5f,  // Bottom left
                -0.5f, -0.5f, -0.5f, // Bottom right
                -0.5f, 0.5f, -0.5f,  // Top right

                // Left face
                -0.5f, 0.5f, -0.5f,  // Top left
                -0.5f, -0.5f, -0.5f, // Bottom left
                -0.5f, -0.5f, 0.5f,  // Bottom right
                -0.5f, 0.5f, 0.5f,   // Top right

                // Right face
                0.5f, 0.5f, 0.5f,    // Top left
                0.5f, -0.5f, 0.5f,   // Bottom left
                0.5f, -0.5f, -0.5f,  // Bottom right
                0.5f, 0.5f, -0.5f,   // Top right

                // Top face
                -0.5f, 0.5f, -0.5f,  // Top left
                -0.5f, 0.5f, 0.5f,   // Bottom left
                0.5f, 0.5f, 0.5f,    // Bottom right
                0.5f, 0.5f, -0.5f,   // Top right

                // Bottom face
                -0.5f, -0.5f, 0.5f,  // Top left
                -0.5f, -0.5f, -0.5f, // Bottom left
                0.5f, -0.5f, -0.5f,  // Bottom right
                0.5f, -0.5f, 0.5f    // Top right
        };
        int[] indices = new int[]{
                // Front face
                0, 1, 2,  // Triangle 1
                2, 3, 0,  // Triangle 2

                // Back face
                4, 5, 6,  // Triangle 1
                6, 7, 4,  // Triangle 2

                // Left face
                8, 9, 10, // Triangle 1
                10, 11, 8, // Triangle 2

                // Right face
                12, 13, 14, // Triangle 1
                14, 15, 12, // Triangle 2

                // Top face
                16, 17, 18, // Triangle 1
                18, 19, 16, // Triangle 2

                // Bottom face
                20, 21, 22, // Triangle 1
                22, 23, 20  // Triangle 2
        };
        float[] textureCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
                1.0f, 0.0f,

        };

        Texture texture = new Texture("/textures/texture.png");
        Mesh mesh = new Mesh(vertices, indices, textureCoords, null);
        mesh.setTexture(texture);

        GameObject gameObject1 = new GameObject(mesh);
        gameObject1.setPosition(0, 0, -2.0f);
        gameObject1.setScale(0.5f, 0.5f, 0.5f);

        GameObject gameObject2 = new GameObject(mesh);
        gameObject2.setPosition(0.5f, 0.5f, -2.0f);
        gameObject2.setScale(0.5f, 0.5f, 0.5f);

        GameObject gameObject3 = new GameObject(mesh);
        gameObject3.setPosition(0, 0, -2.5f);
        gameObject3.setScale(0.5f, 0.5f, 0.5f);

        GameObject gameObject4 = new GameObject(mesh);
        gameObject4.setPosition(0.5f, 0, -2.5f);
        gameObject4.setScale(0.5f, 0.5f, 0.5f);

        Mesh bunnyMesh = OBJLoader.loadMesh("models/bunny.obj");
        GameObject gameObject5 = new GameObject(bunnyMesh);
        gameObject5.setPosition(0.5f, 0.78f, -2.0f);
        gameObject5.setScale(0.1f, 0.1f, 0.1f);

        Texture planeTexture = new Texture("/textures/F16s.png");
        Mesh planeMesh = OBJLoader.loadMesh("models/f16.obj");
        planeMesh.setTexture(planeTexture);
        GameObject gameObject6 = new GameObject(planeMesh);
        gameObject6.setPosition(0.5f, 1.75f, -2.0f);
        gameObject6.setScale(1.0f, 1.0f, 1.0f);

        gameObjects = new GameObject[]{
                gameObject1,
                gameObject2,
                gameObject3,
                gameObject4,
                gameObject5,
        };
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        direction.x = 0;
        direction.y = 0;
        direction.z = 0;

        if (window.isKeyPressed(GLFW.GLFW_KEY_W)) {
            direction.z = -1;
        } else if (window.isKeyPressed(GLFW.GLFW_KEY_S)) {
            direction.z = 1;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_D)) {
            direction.x = 1;
        } else if (window.isKeyPressed(GLFW.GLFW_KEY_A)) {
            direction.x = -1;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            direction.y = 1;
        } else if (window.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            direction.y = -1;
        }
    }

    @Override
    public void update(float deltaTime, MouseInput mouseInput) {
        camera.move(
                direction.x * speed * deltaTime,
                direction.y * speed * deltaTime,
                direction.z * speed * deltaTime
        );

        Vector2f deltaRotation = new Vector2f(
                rotationSpeed * deltaTime,
                rotationSpeed * deltaTime
        );
        mouseInput.rotateCamera(camera, deltaRotation);
    }

    @Override
    public void render(Window window) {
        window.clear();
        renderer.render(gameObjects, camera);
        window.render();
    }

    @Override
    public String getTitle() {
        return title;
    }
}
