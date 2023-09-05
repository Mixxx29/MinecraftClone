package org.example.engine;

import org.example.input.MouseInput;
import org.example.window.Window;

public interface Game {
    void init(Window window);

    void input(Window window, MouseInput mouseInput);

    void update(float deltaTime, MouseInput mouseInput);

    void render(Window window);

    String getTitle();
}
