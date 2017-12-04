package com.tictactoe.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gabriel on 3/12/2017.
 */

public class AIPlayer extends Player {

    MinimaxStrategy strategy;

    public AIPlayer(TicTacToeBoard board, PlayerType playerType, MinimaxStrategy strategy){
        super(board,playerType);
        this.strategy = strategy;
    }

    public Vector2 makeAIMove(){
        Vector2 bestPosition = strategy.BestMove(board,this);
        return  bestPosition;
    }
}
