package org.example;

import org.example.engine.BaseGame;
import org.example.engine.GameEngine;

public class Main {
    public static void main(String[] args) {
        BaseGame game = new MinecraftCloneGame();
        GameEngine engine = new GameEngine(
                "Minecraft Clone",
                800,
                600,
                false,
                game
        );
        engine.start();
    }
}