package com.rcn.mineswap;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class GameInstance {
    private final GameObjects[][] GameField = new GameObjects[5][5];

    public GameInstance() {
        Cell prizeCoordinates = plantThePrize();
        plantTheDynamites(prizeCoordinates);
        plantTheDynamiteMarkers();
    }

    // Plant the prize
    private Cell plantThePrize() {
        int prizeXCoordinate = getRandomCoord(0, 4);
        int prizeYCoordinate = getRandomCoord(0, 4);
        GameField[prizeXCoordinate][prizeYCoordinate] = GameObjects.CHEST;
        return new Cell(prizeXCoordinate, prizeYCoordinate, GameObjects.CHEST);
    }

    //Plant the dynamite for cells in range 3 from the point
    private void plantTheDynamites(Cell cell) {
        int countDynamite = 0;
        while (countDynamite != 4) {
            int x = getRandomCoord(cell.getX()-3, cell.getX()+3);
            int y = getRandomCoord(cell.getY()-3, cell.getY()+3);
            try {
                if (GameField[x][y] != GameObjects.CHEST && GameField[x][y] != GameObjects.DYNAMITE) {
                    GameField[x][y] = GameObjects.DYNAMITE;
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
                if (GameField[x][y] == GameObjects.CHEST || GameField[x][y] == GameObjects.DYNAMITE) {
                    continue;
                }
                int count = CountDynamiteInNeighborCells(x,y);
                if(count > 0) {
                    GameField[x][y] = count == 1 ? GameObjects.COIL : GameObjects.WARN;
                } else {
                    GameField[x][y] = GameObjects.EMPTY;
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
        Cell[] Boundaries = new Cell[] {
            new Cell(x-1, y),
            new Cell(x+1, y),
            new Cell(x, y-1),
            new Cell(x, y+1),
        };

        for (Cell cell : Boundaries) {
            System.out.println(cell.getX());
            countDynamite += CheckCellForTheDynamite(cell) ? 1 : 0;
        }

        return countDynamite;
    }

    private boolean CheckCellForTheDynamite(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        // Constraints for edge cells
        // TODO: Make universal for any array size
        if(x < 0 || x > 4 || y < 0 || y > 4) {
            return false;
        }
        return GameField[x][y] == GameObjects.DYNAMITE;
    }

    public GameObjects GetCellContent(int x, int y) {
        return GameField[x][y];
    }
}
