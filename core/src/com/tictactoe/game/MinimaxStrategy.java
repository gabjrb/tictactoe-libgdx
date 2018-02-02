package com.tictactoe.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Gabriel on 3/12/2017.
 */

public class MinimaxStrategy implements IDifficulty {

    private Array<Results> availableActions;

    public MinimaxStrategy(){
        availableActions = new Array<Results>();
    }

    private class Results{
        int score;
        Vector2 bestPosition;

        public Results(Results results){
            this.score = results.score;
            this.bestPosition = results.bestPosition;
        }

        public Results(int score, Vector2 bestPosition){
            this.score = score;
            this.bestPosition = bestPosition;
        }
    }

    public Vector2 bestMove(TicTacToeBoard board, Player player){
        Results results = miniMax(board,2, Integer.MIN_VALUE, Integer.MAX_VALUE, player.getPlayerType());
        return results.bestPosition;
    }

    public Vector2 bestMove(TicTacToeBoard board, Player player, int rate){
        Results results = miniMax(board,2, Integer.MIN_VALUE, Integer.MAX_VALUE, player.getPlayerType());
        if ((MathUtils.random()*100) <= rate){
            return results.bestPosition;
        }
        for (Results r: availableActions) {
            if (board.getEmptyCells().contains(r.bestPosition)){
                return r.bestPosition;
            }
        }
        return results.bestPosition;
    }

    private Results miniMax(TicTacToeBoard board, int depth, int alpha, int beta, Player.PlayerType playerType){
        if (board.gameOver() || depth == 0) {
            return new Results(board.getScore(), new Vector2(-1, -1));
        }
        int bestScore = (playerType == Player.PlayerType.PLAYER_TYPE_X) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Vector2 bestPosition = new Vector2(-1,-1);
        for (Vector2 v : board.getEmptyCells()) {
            TicTacToeBoard nextBoard = new TicTacToeBoard(board.boardAfterMove(v, playerType.getBrand()));
            if (playerType == Player.PlayerType.PLAYER_TYPE_X) {
                Results result = new Results(miniMax(nextBoard, depth -1, alpha, beta, playerType.getOpponent()));
                if (result.score > bestScore) {
                    bestScore = result.score;
                    bestPosition = new TicTacToeCell(v).getPosition();
                }
                alpha = Math.max(alpha, result.score);
                if (beta <= alpha) {
                    break;
                }
            } else {
                Results result = new Results(miniMax(nextBoard, depth-1, alpha, beta, playerType.getOpponent()));
                if (result.score < bestScore) {
                    bestScore = result.score;
                    bestPosition = new TicTacToeCell(v).getPosition();
                }
                beta = Math.min(beta, result.score);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        availableActions.add(new Results(bestScore, bestPosition));
        return availableActions.get(availableActions.size - 1);
    }
}