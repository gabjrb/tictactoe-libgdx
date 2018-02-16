package com.tictactoecreator.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gabriel on 3/12/2017.
 */

public class AIPlayer extends Player {

    private IDifficulty difficulty;

    public AIPlayer(String name, PlayerType type, TicTacToeBoard board, Boolean haveToStart, IDifficulty difficulty){
        super(name, type, board, haveToStart);
        this.difficulty = difficulty;
    }

    public Vector2 makeAIMove(){
        Vector2 bestPosition = difficulty.bestMove(getBoard(),this);
        return  bestPosition;
    }
}