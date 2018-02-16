package com.tictactoecreator.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Gabriel on 18/1/2018.
 */

public class RuleBasedStrategy implements IDifficulty {
    public Vector2 bestMove(TicTacToeBoard board, Player forPlayer) {
        // middle
        TicTacToeCell middleCell = board.getCell(new Vector2(1,1));

        // corners
        Array<TicTacToeCell> emptyCornerCells = new Array<TicTacToeCell>();
        if (board.getCell(new Vector2(0, 0)).getCellBrand() == TicTacToeCell.CellBrand.NOTHING) {
            emptyCornerCells.add(board.getCell(new Vector2(0, 0)));
        }
        if (board.getCell(new Vector2(0, 2)).getCellBrand() == TicTacToeCell.CellBrand.NOTHING) {
            emptyCornerCells.add(board.getCell(new Vector2(0, 2)));
        }
        if (board.getCell(new Vector2(2, 0)).getCellBrand() == TicTacToeCell.CellBrand.NOTHING) {
            emptyCornerCells.add(board.getCell(new Vector2(2, 0)));
        }
        if (board.getCell(new Vector2(2, 2)).getCellBrand() == TicTacToeCell.CellBrand.NOTHING) {
            emptyCornerCells.add(board.getCell(new Vector2(2, 2)));
        }

        // edges
        Array<TicTacToeCell> emptyEdgeCells = new Array<TicTacToeCell>();
        if (board.getCell(new Vector2(0, 1)).getCellBrand() == TicTacToeCell.CellBrand.NOTHING) {
            emptyCornerCells.add(board.getCell(new Vector2(0, 1)));
        }
        if (board.getCell(new Vector2(1, 0)).getCellBrand() == TicTacToeCell.CellBrand.NOTHING) {
            emptyCornerCells.add(board.getCell(new Vector2(1, 0)));
        }
        if (board.getCell(new Vector2(1, 2)).getCellBrand() == TicTacToeCell.CellBrand.NOTHING) {
            emptyCornerCells.add(board.getCell(new Vector2(1, 2)));
        }
        if (board.getCell(new Vector2(2, 1)).getCellBrand() == TicTacToeCell.CellBrand.NOTHING) {
            emptyCornerCells.add(board.getCell(new Vector2(2, 1)));
        }

        // try the middle, then the corners, then the edges
        if (middleCell.getCellBrand() == TicTacToeCell.CellBrand.NOTHING) {
            return middleCell.getPosition();
        } else if (emptyCornerCells.size > 0) {
            int randomIndex = MathUtils.random(0, emptyCornerCells.size - 1);
            return emptyCornerCells.get(randomIndex).getPosition();
        } else {
            int randomIndex = MathUtils.random(0, emptyEdgeCells.size - 1);
            return emptyEdgeCells.get(randomIndex).getPosition();
        }
    }
}