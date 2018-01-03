package com.tictactoe.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gabriel on 26/11/2017.
 */

public class TicTacToeBoard {
    private TicTacToeCell[][] cells;
    public TicTacToeBoard()
    {
        this.cells = new TicTacToeCell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.cells[i][j] = new TicTacToeCell(new Vector2(i,j));
            }
        }
    }

    public TicTacToeBoard(TicTacToeBoard board){
        this.cells = new TicTacToeCell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.cells[i][j] = new TicTacToeCell(board.cells[i][j]);
            }
        }
    }

    public TicTacToeBoard boardAfterMove(Vector2 position, TicTacToeCell.CellBrand newValue) {
        TicTacToeBoard nextBoard = new TicTacToeBoard(this);
        nextBoard.cells[(int)position.x][(int)position.y].setPosition(position);
        nextBoard.cells[(int)position.x][(int)position.y].setCellBrand(newValue);
        return nextBoard;
    }

    public Array<ShapeData> getCrosses() {
        Array<ShapeData> shapes = new Array<ShapeData>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.cells[i][j].getCellBrand() == TicTacToeCell.CellBrand.CROSS)
                    shapes.add(this.cells[i][j].getShapeData());
            }
        }
        return shapes;
    }
    public Array<ShapeData> getCircles() {
        Array<ShapeData> shapes = new Array<ShapeData>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.cells[i][j].getCellBrand() == TicTacToeCell.CellBrand.CIRCLE)
                    shapes.add(this.cells[i][j].getShapeData());
            }
        }
        return shapes;
    }

    public List<Vector2> getEmptyCells(){
        List<Vector2> emptyCells = new ArrayList<Vector2>();
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3 ; j++) {
                if (this.cells[i][j].getCellBrand() == TicTacToeCell.CellBrand.NOTHING)
                emptyCells.add(new Vector2(i,j));
            }
        }
        return emptyCells;
    }

    public void handleTouch(Vector2 position, Player.PlayerType playerType)
    {

    }

    public boolean setCell(Vector2 position, TicTacToeCell.CellBrand brand) {
        if (cells[(int)position.x][(int)position.y].getCellBrand() == TicTacToeCell.CellBrand.NOTHING) {
            cells[(int)position.x][(int)position.y].setCellBrand(brand);
        } else {
            return false;
        }
        return true;
    }

    public static Boolean getCellOrigin(Vector2 v){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (v.x >= GameConstants.HorizontalShift + (i * GameConstants.CELL_SIZE.x) &&
                        v.x <= (GameConstants.HorizontalShift + GameConstants.CELL_SIZE.x + (i * GameConstants.CELL_SIZE.x)) &&
                        v.y >= GameConstants.Elevation + (j * GameConstants.CELL_SIZE.y) &&
                        v.y <= (GameConstants.Elevation + GameConstants.CELL_SIZE.y + (j * GameConstants.CELL_SIZE.y))) {
                    v.x = i;
                    v.y = j;
                    return true;
                }
            }
        }
        return false;
    }

//    public static Vector2 getCellOriginWorld(Vector2 v){
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (v.x >= GameConstants.HorizontalShift + (i * GameConstants.CELL_SIZE.x) &&
//                        v.x <= (GameConstants.HorizontalShift + GameConstants.CELL_SIZE.x + (i * GameConstants.CELL_SIZE.x)) &&
//                        v.y >= GameConstants.Elevation + (j * GameConstants.CELL_SIZE.y) &&
//                        v.y <= (GameConstants.Elevation + GameConstants.CELL_SIZE.y + (j * GameConstants.CELL_SIZE.y))) {
//
//                    return  new Vector2(GameConstants.HorizontalShift + (i * GameConstants.CELL_SIZE.x),
//                            GameConstants.Elevation + (j * GameConstants.CELL_SIZE.y));
//                }
//            }
//        }
//        return new Vector2(0,0);
//    }

    public GameResults getResults(){
        GameResults results = new GameResults();
        for (Vector2[] pattern:
             GameConstants.WINNING_PATTERNS) {
            TicTacToeCell cell = cells[(int)pattern[0].x][(int)pattern[0].y];
            if (cell.getCellBrand() == TicTacToeCell.CellBrand.NOTHING){
                continue;
            }
            if (cells[(int)pattern[0].x][(int)pattern[0].y].getCellBrand() == cells[(int)pattern[1].x][(int)pattern[1].y].getCellBrand() &&
                    cells[(int)pattern[1].x][(int)pattern[1].y].getCellBrand() == cells[(int)pattern[2].x][(int)pattern[2].y].getCellBrand()) {
                results.setHasWinner(true);
                break;
            }
        }
        return results;
    }

    public Boolean gameOver() {
        return getResults().isHasWinner() == true || getEmptyCells().size() == 0;
    }

    public int getScore() {
        int totalScore = 0;
        for (Vector2[] pattern: GameConstants.WINNING_PATTERNS) {
            // count the number of Empty, Cross, and Nought in a row
            HashMap<TicTacToeCell.CellBrand, Integer> counts = new HashMap<TicTacToeCell.CellBrand, Integer>();
            int emptyCells = 0;
            int crossCells = 0;
            int noughtCells = 0;
            for (Vector2 position: pattern) {
                switch(cells[(int)position.x][(int)position.y].getCellBrand()) {
                    case NOTHING:
                        emptyCells++;
                        break;
                    case CROSS:
                        crossCells++;
                        break;
                    case CIRCLE:
                        noughtCells++;
                        break;
                }
            }
            counts.put(TicTacToeCell.CellBrand.NOTHING, emptyCells);
            counts.put(TicTacToeCell.CellBrand.CROSS, crossCells);
            counts.put(TicTacToeCell.CellBrand.CIRCLE, noughtCells);
            // calculate score based on counts
            totalScore += calculateScoreForRow(counts);
        }
        return totalScore;
    }

    private int calculateScoreForRow(HashMap<TicTacToeCell.CellBrand, Integer> counts) {
        int score = 0;
        int emptyCount = counts.get(TicTacToeCell.CellBrand.NOTHING);
        int crossCount = counts.get(TicTacToeCell.CellBrand.CROSS);
        int noughtCount = counts.get(TicTacToeCell.CellBrand.CIRCLE);
        if (crossCount == 3 && emptyCount == 0) {
            score += GameConstants.THREE_CROSS_BONUS;
        }
        if (crossCount == 2 && emptyCount == 1) {
            score += GameConstants.TWO_CROSS_BONUS;
        }
        if (crossCount == 1 && emptyCount == 2) {
            score += GameConstants.ONE_CROSS_BONUS;
        }
        if (noughtCount == 3 && emptyCount == 0) {
            score += GameConstants.THREE_NOUGHT_BONUS;
        }
        if (noughtCount == 2 && emptyCount == 1) {
            score += GameConstants.TWO_NOUGHT_BONUS;
        }
        if (noughtCount == 1 && emptyCount == 2) {
            score += GameConstants.ONE_NOUGHT_BONUS;
        }
        return score;
    }
}