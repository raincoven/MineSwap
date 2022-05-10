package com.rcn.mineswap;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class GameInstance {
    private final Cell[][] GameField = new Cell[5][5];

    public GameInstance() {
        Cell prize = plantThePrize();
        plantTheDynamites(prize);
        plantTheDynamiteMarkers();
    }

    // Plant the prize
    private Cell plantThePrize() {
        int x = getRandomNum(0, 4);
        int y = getRandomNum(0, 4);
        GameField[x][y] = new Cell(x, y, GameObjects.CHEST);
        return GameField[x][y];
    }

    //Plant the dynamite for cells in range 3 from the point
    private void plantTheDynamites(@NotNull Cell cell) {
        int countDynamite = 0;
        while (countDynamite != 4) {
            int x = getRandomNum(cell.getX()-3, cell.getX()+3);
            int y = getRandomNum(cell.getY()-3, cell.getY()+3);
            try {
                if(GameField[x][y] == null) {
                    GameField[x][y] = new Cell(x, y, GameObjects.DYNAMITE);
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
                if (GameField[x][y] != null) {
                    continue;
                }
                Cell markerCell = new Cell(x, y);
                int count = CountDynamiteInNeighborCells(markerCell);
                switch (count){
                    case 0: markerCell.setValue(GameObjects.EMPTY);
                    case 1 : markerCell.setValue(GameObjects.COIL);
                    default: markerCell.setValue(GameObjects.WARN);
                }

                GameField[x][y] = markerCell;
            }
        }
    }

    /**
     * Returns random number in boundary
     */
    private int getRandomNum(int a, int b) {

        Random random = new Random();
        return random.ints(a, b)
                .findFirst()
                .getAsInt();
    }

    // Checking how many top, bottom, left and right bordering cells contains dynamite
    // TODO: Cell as input argument
    private int CountDynamiteInNeighborCells(@NotNull Cell cell) {
        int countDynamite = 0;
        int x = cell.getX();
        int y = cell.getY();
        Cell[] Boundaries = new Cell[] {
            new Cell(x-1, y),
            new Cell(x+1, y),
            new Cell(x, y-1),
            new Cell(x, y+1),
        };

        for (Cell boundaryCell : Boundaries) {
            countDynamite += CheckCellForTheDynamite(boundaryCell) ? 1 : 0;
        }

        return countDynamite;
    }

    private boolean CheckCellForTheDynamite(@NotNull Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        // Constraints for edge cells
        // TODO: Make universal for any array size
        if(x < 0 || x > 4 || y < 0 || y > 4) {
            return false;
        }
        return GameField[x][y] != null && GameField[x][y].getValue() == GameObjects.DYNAMITE;
    }

    public GameObjects GetCellContent(int x, int y) {
        return GameField[x][y].getValue();
    }
}
