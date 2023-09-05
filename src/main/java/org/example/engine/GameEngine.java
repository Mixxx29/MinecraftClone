package org.example.engine;

import org.example.input.MouseInput;
import org.example.window.Window;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class GameEngine implements Runnable {
    private final Thread gameThread;
    private final Game game;
    private final Window window;
    private final MouseInput mouseInput;
    private int fps = 0;
    private float deltaTime;
    private double elapsedTime = 0.0f;
    private double previousTime;

    public GameEngine(int width, int height, boolean vsync, Game game) {
        this.game = game;
        this.gameThread = new Thread(this, "GAME_LOOP_THREAD");
        this.window = new Window(game.getTitle(), width, height, vsync);
        this.mouseInput = new MouseInput();
    }

    public void start() {
        gameThread.start();
    }

    @Override
    public void run() {
        init();
        startLoop();
    }

    private void init() {
        window.init();
        mouseInput.init(window);
        game.init(window);
    }

    private void startLoop() {
        previousTime = glfwGetTime();
        loop();
    }

    private void loop() {
        while (isRunning()) {
            processTime();
            processInput();
            processGame();
        }
        close();
    }

    private boolean isRunning() {
        return !window.shouldClose();
    }

    private void processTime() {
        calculateDeltaTime();
        calculateFPS();
    }

    private void calculateDeltaTime() {
        double currentTime = glfwGetTime();
        deltaTime = (float) (currentTime - previousTime);
        previousTime = currentTime;
    }

    private void calculateFPS() {
        elapsedTime += deltaTime;
        fps++;
        updateFPS();
    }

    private void updateFPS() {
        if (elapsedTime >= 1.0f) {
            window.updateFPS(fps);
            elapsedTime = 0.0f;
            fps = 0;
        }
    }

    private void processInput() {
        glfwPollEvents(); // Process events
        mouseInput.update();
    }

    private void processGame() {
        game.input(window, mouseInput);
        game.update(deltaTime, mouseInput);
        game.render(window);
    }

    private void close() {
        window.close();
    }
}
