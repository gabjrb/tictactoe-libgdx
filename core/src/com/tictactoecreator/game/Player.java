package com.tictactoecreator.game;

/**
 * Created by Gabriel on 3/12/2017.
 */

public class Player {

    private String name;
    private PlayerType playerType;
    private TicTacToeBoard board;
    private Boolean haveToStart;

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
        private String name;

        static {
            PLAYER_TYPE_O.opponent = PlayerType.PLAYER_TYPE_X;
            PLAYER_TYPE_O.brand = TicTacToeCell.CellBrand.CIRCLE;
            PLAYER_TYPE_O.name = "O";
            PLAYER_TYPE_X.opponent = PlayerType.PLAYER_TYPE_O;
            PLAYER_TYPE_X.brand = TicTacToeCell.CellBrand.CROSS;
            PLAYER_TYPE_X.name = "X";
        }

        public TicTacToeCell.CellBrand getBrand() {
            return brand;
        }

        public PlayerType getOpponent() {
            return opponent;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public Boolean getHaveToStart() {
        return haveToStart;
    }

    public String getName() {
        return name;
    }

    public TicTacToeBoard getBoard() {
        return board;
    }
}
