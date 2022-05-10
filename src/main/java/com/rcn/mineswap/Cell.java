package com.rcn.mineswap;

public class Cell {
    private int x;
    private int y;
    private GameObjects value;

    public Cell(int x, int y, GameObjects value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
    public Cell(int x, int y) {
        this(x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameObjects getValue() {
        return value;
    }
}
