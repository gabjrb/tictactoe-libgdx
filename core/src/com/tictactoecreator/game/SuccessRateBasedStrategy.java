package com.tictactoecreator.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gabriel on 27/1/2018.
 */

public class SuccessRateBasedStrategy extends MinimaxStrategy {

    private int rate;
    public SuccessRateBasedStrategy(int rate){
        super();
        this.rate = rate;
    }
    @Override
    public Vector2 bestMove(TicTacToeBoard board, Player player) {
        return super.bestMove(board,player,this.rate);
    }
}