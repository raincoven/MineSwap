package com.rcn.mineswap;

import org.jetbrains.annotations.NotNull;

public class Cell {
    private final int x;
    private final int y;
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

    public boolean setValue(@NotNull GameObjects value) {
        if (this.value == null) {
            this.value = value;
            return true;
        } else {
            return false;
        }
    }
}
