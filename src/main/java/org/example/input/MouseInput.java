package org.example.input;

import org.example.window.Window;
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
        glfwSetCursorPosCallback(window.getHandle(), (windowHandle, x, y) -> {
            newPosition.x = (int) x;
            newPosition.y = (int) y;
        });

        glfwSetCursorEnterCallback(window.getHandle(), (windowHandle, entered) -> {
            inWindow = entered;
        });

        glfwSetMouseButtonCallback(window.getHandle(), (windowHandle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });

        //glfwSetInputMode(window.getHandle(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    public void input(Window window) {
        moved.x = 0;
        moved.y = 0;
        if (inWindow) {
            moved.x = newPosition.x - position.x;
            moved.y = newPosition.y - position.y;
        }
        position.x = newPosition.x;
        position.y = newPosition.y;
    }

    public Vector2i getMoved() {
        return moved;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
}
