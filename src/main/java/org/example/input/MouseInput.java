package org.example.input;

import org.example.camera.Camera;
import org.example.window.Window;
import org.joml.Vector2f;
import org.joml.Vector2i;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {
    private final Vector2i position;
    private final Vector2i newPosition;
    private final Vector2i moved;

    private boolean inWindow;
    private boolean leftButtonPressed;
    private boolean rightButtonPressed;

    public MouseInput() {
        position = new Vector2i(0, 0);
        newPosition = new Vector2i(0, 0);
        moved = new Vector2i(0, 0);
    }

    public void init(Window window) {
        addPositionListener(window);
        addEnterListener(window);
        addButtonListener(window);
    }

    private void addPositionListener(Window window) {
        glfwSetCursorPosCallback(window.getHandle(), (windowHandle, x, y) -> {
            newPosition.x = (int) x;
            newPosition.y = (int) y;
        });
    }

    private void addEnterListener(Window window) {
        glfwSetCursorEnterCallback(window.getHandle(), (windowHandle, entered) -> {
            inWindow = entered;
        });
    }

    private void addButtonListener(Window window) {
        glfwSetMouseButtonCallback(window.getHandle(), (windowHandle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }

    public void update() {
        reset();
        calculateMovement();
        updatePosition();
    }

    private void reset() {
        moved.x = 0;
        moved.y = 0;
    }

    private void calculateMovement() {
        if (!inWindow) return;
        moved.x = newPosition.x - position.x;
        moved.y = newPosition.y - position.y;
    }

    private void updatePosition() {
        position.x = newPosition.x;
        position.y = newPosition.y;
    }

    public void rotateCamera(Camera camera, Vector2f deltaRotation) {
        if (rightButtonPressed && hasMoved()) {
            camera.rotate(
                    moved.y * deltaRotation.x,
                    moved.x * deltaRotation.y,
                    0.0f
            );
        }
    }

    private boolean hasMoved() {
        return moved.x != 0 || moved.y != 0;
    }
}
