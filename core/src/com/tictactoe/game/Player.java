package com.tictactoe.game;

/**
 * Created by Gabriel on 3/12/2017.
 */

public class Player {

    String name;
    PlayerType playerType;
    TicTacToeBoard board;
    Boolean haveToStart;

    public Player(String name, PlayerType playerType, Boolean haveToStart){
        this.name = name;
        this.playerType = playerType;
        this.haveToStart = haveToStart;
    }

    public Player(String name, PlayerType type, TicTacToeBoard board, Boolean haveToStart) {
        this.name = name;
        this.playerType = type;
        this.board = board;
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
