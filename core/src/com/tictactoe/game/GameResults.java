package com.tictactoe.game;

/**
 * Created by Gabriel on 3/12/2017.
 */

public class GameResults {

    boolean hasWinner;
    Player.PlayerType winplayerType;

    public GameResults(){
        this.hasWinner = false;
    }

    public Player.PlayerType getWinner(){
        return winplayerType;
    }

    public boolean isHasWinner() {
        return hasWinner;
    }

    public void setWinner(Player.PlayerType playerType){
        this.winplayerType = playerType;
    }

    public void setHasWinner(boolean hasWinner) {
        this.hasWinner = hasWinner;
    }
}
