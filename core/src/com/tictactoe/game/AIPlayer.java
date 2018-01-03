package com.tictactoe.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gabriel on 3/12/2017.
 */

public class AIPlayer extends Player {

    MinimaxStrategy strategy;

    public AIPlayer(String name, PlayerType type, TicTacToeBoard board, Boolean haveToStart, MinimaxStrategy strategy){
        super(name, type, board, haveToStart);
        this.strategy = strategy;
    }

    public Vector2 makeAIMove(){
        Vector2 bestPosition = strategy.BestMove(getBoard(),this);
        return  bestPosition;
    }
}