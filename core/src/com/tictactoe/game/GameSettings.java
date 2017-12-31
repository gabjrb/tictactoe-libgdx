package com.tictactoe.game;

/**
 * Created by Gabriel on 17/12/2017.
 */

public class GameSettings {

    private String playerOneName;
    private String playerTwoName;
    private Player.PlayerType playerTypeOne;
    private Player.PlayerType playerTypeTwo;
    private ModelType modelTypePlayerOne;
    private ModelType modelTypePlayerTwo;
    private GameMod gameMod;
    private Difficulty difficulty;
    private Boolean playedByRotation;
    private Boolean playerOneHasToStart;
    private Boolean playerTwoHasToStart;

    public GameSettings(){
        this.playerOneName = "You";
        this.playerTwoName = "CPU";
        this.playerTypeOne = Player.PlayerType.PLAYER_TYPE_X;
        this.playerTypeTwo = Player.PlayerType.PLAYER_TYPE_O;
        this.modelTypePlayerOne = ModelType.HUMAN;
        this.modelTypePlayerTwo = ModelType.COMPUTER;
        this.gameMod = GameMod.NORMAL;
        this.difficulty = Difficulty.MEDIUM;
        this.playedByRotation = false;
        this.playerOneHasToStart = true;
        this.playerTwoHasToStart = false;
    }

    public enum ModelType{
        HUMAN,
        COMPUTER
    }

    public enum GameMod{
        NORMAL,
        EXPIRING_MOVES
    }

    public enum Difficulty{
        EASY,
        MEDIUM,
        HARD
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public GameMod getGameMod() {
        return gameMod;
    }

    public Boolean getPlayedByRotation() {
        return playedByRotation;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public Player.PlayerType getPlayerTypeOne() {
        return playerTypeOne;
    }

    public Player.PlayerType getPlayerTypeTwo() {
        return playerTypeTwo;
    }

    public ModelType getModelTypePlayerOne() {
        return modelTypePlayerOne;
    }

    public ModelType getModelTypePlayerTwo() {
        return modelTypePlayerTwo;
    }

    public Boolean getPlayerOneHasToStart() {
        return playerOneHasToStart;
    }

    public Boolean getPlayerTwoHasToStart() {
        return playerTwoHasToStart;
    }
}
