package com.rcn.mineswap;

import java.util.Random;

public final class GameInstance {
    private static GameInstance INSTANCE;
    private enum Items {
        EMPTY, COIL, WARN, DYNAMITE, CHEST
    }
    private Items[][] GameField = new Items[5][5];

    public GameInstance() {
        BuildGameFiled();
    }

    public static GameInstance getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameInstance();
        }
        return INSTANCE;
    }

    private void BuildGameFiled() {
        // Plant the prize
        int chestXCord = getRandomCord(0, 4);
        int chestYCord = getRandomCord(0, 4);
        GameField[chestXCord][chestYCord] = Items.CHEST;

        //Plant the dynamite
        // @TODO: improve logic to place dynamite near the chest
        int countDynamite = 0;
        while (countDynamite != 3) {
            int x = getRandomCord(0, 4);
            int y = getRandomCord(0, 4);
            if (GameField[x][y] != Items.CHEST || GameField[x][y] != Items.EMPTY) {
                GameField[x][y] = Items.DYNAMITE;
                countDynamite++;
            }
        }

        //Plant EMPTY, COIL and WARN
        for(int x = 0; x < 5; x++) {
            for(int y=0; y < 5; y++) {
                if (GameField[x][y] == Items.CHEST || GameField[x][y] == Items.DYNAMITE) {
                    continue;
                }
                int count = CountDynamiteInNeighborCells(x,y);
                if(count > 0) {
                    GameField[x][y] = count == 1 ? Items.COIL : Items.WARN;
                } else {
                    GameField[x][y] = Items.EMPTY;
                }

            }
        }


    }

    private int getRandomCord(int x, int y) {
        Random random = new Random();
        return random.ints(x, y)
                .findFirst()
                .getAsInt();
    }

    // We need to check 4 cells (2 vertical and 2 horizontal)
    private int CountDynamiteInNeighborCells(int x, int y) {
        int countDynamite = 0;

        int neighborX = x - 1;
        int neighborY = y;
        countDynamite += CheckCellForTheDynamite(neighborX, neighborY) ? 1 : 0;

        neighborX = x + 1;
        countDynamite += CheckCellForTheDynamite(neighborX, neighborY) ? 1 : 0;

        neighborX = x;
        neighborY = y - 1;
        countDynamite += CheckCellForTheDynamite(neighborX, neighborY) ? 1 : 0;

        neighborY = y + 1;
        countDynamite += CheckCellForTheDynamite(neighborX, neighborY) ? 1 : 0;

        return countDynamite;
    }

    private boolean CheckCellForTheDynamite(int x, int y) {
        if(x < 0 || x > 4 || y < 0 || y > 4) {
            return false;
        }
        return GameField[x][y] == Items.DYNAMITE;
    }

    public Items GetCellContent(int x, int y) {
        return GameField[x][y];
    }

    // Refresh current state
    public void Restart() {
        this.GameField = new Items[5][5];
        BuildGameFiled();
    }


}
