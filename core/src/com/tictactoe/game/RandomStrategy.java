package com.tictactoe.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

/**
 * Created by Gabriel on 28/1/2018.
 */

public class RandomStrategy implements IDifficulty {

    @Override
    public Vector2 bestMove(TicTacToeBoard board, Player forPlayer) {
        List<Vector2> availableCells = board.getEmptyCells();
        int randomIndex = MathUtils.random(0, availableCells.size() - 1);
        return availableCells.get(randomIndex);
    }
}