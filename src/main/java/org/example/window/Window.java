package org.example.window;

import org.example.util.FloatColor;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {

    private final boolean vsync;
    private long handle;
    private String title;
    private int width;
    private int height;
    private boolean resized;

    public Window(String title, int width, int height, boolean vsync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vsync = vsync;
    }

    public void init() {
        // Set error callback to print in console
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        configure();

        handle = glfwCreateWindow(width, height, title, 0, 0);
        if (handle == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        glfwSetFramebufferSizeCallback(handle, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.resized = true;
        });

        glfwSetKeyCallback(handle, (windowHandle, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE) {
                glfwSetWindowShouldClose(windowHandle, true);
            } else if (key == GLFW_KEY_SPACE) {
                System.out.println();
            }
        });

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(handle, pWidth, pHeight);

            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if (videoMode == null) {
                throw new RuntimeException("Failed to get monitor data");
            }

            glfwSetWindowPos(
                    handle,
                    (videoMode.width() - pWidth.get(0)) / 2,
                    (videoMode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(handle);
        glfwSwapInterval(vsync ? 1 : 0); // Enable v-sync

        glfwShowWindow(handle);

        GL.createCapabilities(); // Enable OpenGL use
        // printOpenGLVersion();

        glEnable(GL_DEPTH_TEST);
        // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }

    private void configure() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Hide window
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Enable resize
    }

    public void render() {
        if (resized) {
            glViewport(0, 0, width, height);
            clear();
            resized = false;
        }

        glfwSwapBuffers(handle);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void close() {
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);

        glfwTerminate();
        freeErrorCallback();
    }

    private void freeErrorCallback() {
        GLFWErrorCallback errorCallback = glfwSetErrorCallback(null);
        if (errorCallback != null) errorCallback.free();
    }

    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(handle, this.title);
    }

    public void setBackground(Color color) {
        FloatColor floatColor = new FloatColor(color);
        glClearColor(
                floatColor.getRed(),
                floatColor.getGreen(),
                floatColor.getBlue(),
                floatColor.getAlpha()
        );
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(handle);
    }

    public boolean hasResized() {
        return resized;
    }

    public void updateFPS(int fps) {
        glfwSetWindowTitle(handle, title + " FPS: " + fps);
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(handle, keyCode) == GLFW_PRESS;
    }

    public boolean isKeyReleased(int keyCode) {
        return glfwGetKey(handle, keyCode) == GLFW_RELEASE;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void printOpenGLVersion() {
        System.out.println(glGetString(GL_VERSION));
    }

    public long getHandle() {
        return handle;
    }
}
