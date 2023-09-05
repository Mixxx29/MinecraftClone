package org.example;

import org.example.engine.Game;
import org.example.engine.GameEngine;

public class Main {
    public static void main(String[] args) {
        Game game = new MinecraftCloneGame("Minecraft Clone");
        GameEngine engine = new GameEngine(
                800,
                600,
                true,
                game
        );
        engine.start();
    }
}