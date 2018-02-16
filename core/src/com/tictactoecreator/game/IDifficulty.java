package com.tictactoecreator.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gabriel on 18/1/2018.
 */

public interface IDifficulty {
    Vector2 bestMove(TicTacToeBoard board, Player forPlayer);
}
