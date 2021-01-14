package com.company;

import java.awt.*;

public class Game extends Canvas{

    public static final int width=500;
    public static final int height=500;

    public Game() {
            Window window = new Window(width, height, "Wumpus", this);
            window.putCharacters();
            window.drawMap();
            window.startTheGame();
    }

    public static void main(String[] args) {
        new Game();
    }
}
