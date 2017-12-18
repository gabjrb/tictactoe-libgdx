package com.tictactoe.game;

/**
 * Created by Gabriel on 3/12/2017.
 */

public class Player {

    String name;
    PlayerType playerType;
    TicTacToeBoard board;
    Boolean haveToStart;

    public Player(String name, PlayerType playerType){
        this.name = name;
        this.playerType = playerType;
    }

    public Player(TicTacToeBoard board, PlayerType type, Boolean haveToStart) {
        this.board = board;
        this.playerType = type;
        this.haveToStart = haveToStart;
    }

    public enum PlayerType{
        PLAYER_TYPE_O,
        PLAYER_TYPE_X;

        private PlayerType opponent;
        private TicTacToeCell.CellBrand brand;

        static {
            PLAYER_TYPE_O.opponent = PlayerType.PLAYER_TYPE_X;
            PLAYER_TYPE_O.brand = TicTacToeCell.CellBrand.CIRCLE;
            PLAYER_TYPE_X.opponent = PlayerType.PLAYER_TYPE_O;
            PLAYER_TYPE_X.brand = TicTacToeCell.CellBrand.CROSS;
        }

        public TicTacToeCell.CellBrand getBrand() {
            return brand;
        }

        public PlayerType getOpponent() {
            return opponent;
        }
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public Boolean getHaveToStart() {
        return haveToStart;
    }
}
