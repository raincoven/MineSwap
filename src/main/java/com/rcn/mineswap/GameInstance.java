package com.rcn.mineswap;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class GameInstance {
    private enum Items {
        EMPTY, COIL, WARN, DYNAMITE, CHEST
    }
    private final Items[][] GameField = new Items[5][5];

    public GameInstance() {
        int[] prizeCoordinates = plantThePrize();
        plantTheDynamites(prizeCoordinates[0], prizeCoordinates[1]);
        plantTheDynamiteMarkers();
    }

    // Plant the prize
    @Contract(" -> new")
    private int @NotNull [] plantThePrize() {
        int prizeXCoordinate = getRandomCoord(0, 4);
        int prizeYCoordinate = getRandomCoord(0, 4);
        GameField[prizeXCoordinate][prizeYCoordinate] = Items.CHEST;
        return new int[]{prizeXCoordinate, prizeYCoordinate};
    }

    //Plant the dynamite for cells in range 3 from the point
    private void plantTheDynamites(int prizeXCoordinate, int prizeYCoordinate) {
        int countDynamite = 0;
        while (countDynamite != 4) {
            int x = getRandomCoord(prizeXCoordinate-3, prizeXCoordinate+3);
            int y = getRandomCoord(prizeYCoordinate-3, prizeYCoordinate+3);
            try {
                if (GameField[x][y] != Items.CHEST && GameField[x][y] != Items.DYNAMITE) {
                    GameField[x][y] = Items.DYNAMITE;
                    countDynamite++;
                }
            }
            // Went out of game field boundaries. Just need to get another set of coordinates.
            catch (Exception ignored) {
            }
        }
    }

    /**
     * Plant EMPTY, COIL and WARN to identify if cell is in dynamite boundaries
     * We are checking only top, left, bottom and right cells
     * EMPTY - no dynamite nearby
     * COIL - 1 cell containing dynamite
     * WARN - more than 1 cell nearby containing the dynamite
     */
    private void plantTheDynamiteMarkers() {
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


    /**
     * Returns random number in boundary
     */
    private int getRandomCoord(int x, int y) {

        Random random = new Random();
        return random.ints(x, y)
                .findFirst()
                .getAsInt();
    }

    // Checking how many top, bottom, left and right bordering cells contains dynamite
    private int CountDynamiteInNeighborCells(int x, int y) {
        int countDynamite = 0;

        // Left cell
        int neighborX = x - 1;
        int neighborY = y;
        countDynamite += CheckCellForTheDynamite(neighborX, neighborY) ? 1 : 0;

        // Right cell
        neighborX = x + 1;
        countDynamite += CheckCellForTheDynamite(neighborX, neighborY) ? 1 : 0;

        // Bottom cell
        neighborX = x;
        neighborY = y - 1;
        countDynamite += CheckCellForTheDynamite(neighborX, neighborY) ? 1 : 0;

        // Top cell
        neighborY = y + 1;
        countDynamite += CheckCellForTheDynamite(neighborX, neighborY) ? 1 : 0;

        return countDynamite;
    }

    private boolean CheckCellForTheDynamite(int x, int y) {
        // Constraints for edge cells
        // TODO: Make universal for any array size
        if(x < 0 || x > 4 || y < 0 || y > 4) {
            return false;
        }
        return GameField[x][y] == Items.DYNAMITE;
    }

    public Items GetCellContent(int x, int y) {
        return GameField[x][y];
    }
}
