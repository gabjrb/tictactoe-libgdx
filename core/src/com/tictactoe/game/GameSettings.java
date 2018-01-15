package com.tictactoe.game;

import com.badlogic.gdx.utils.I18NBundle;

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
    private GamePlayMode gamePlayMode;
    private static I18NBundle nBundle;

    public GameSettings(I18NBundle nBundle){
        this.nBundle = nBundle;
        this.playerOneName = nBundle.get("PlayerOneDefaultName");
        this.playerTwoName = nBundle.get("PlayerModelComputer");
        this.playerTypeOne = Player.PlayerType.PLAYER_TYPE_X;
        this.playerTypeTwo = Player.PlayerType.PLAYER_TYPE_O;
        this.modelTypePlayerOne = ModelType.HUMAN;
        this.modelTypePlayerTwo = ModelType.COMPUTER;
        this.gameMod = GameMod.NORMAL;
        this.difficulty = Difficulty.MEDIUM;
        this.playedByRotation = false;
        this.playerOneHasToStart = true;
        this.playerTwoHasToStart = false;
        this.gamePlayMode = GamePlayMode.SINGLEPLAYER;
    }

    public static I18NBundle getnBundle() {
        return nBundle;
    }

    public enum GamePlayMode{
        SINGLEPLAYER,
        MULTIPLAYER
    }

    public enum ModelType{
        HUMAN,
        COMPUTER;

        private String name;

        static {
            HUMAN.name = GameSettings.getnBundle().get("PlayerModelHuman");
            COMPUTER.name = GameSettings.getnBundle().get("PlayerModelComputer");
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum GameMod{
        NORMAL,
        EXPIRING_MOVES;

        private String name;

        static {
            NORMAL.name = GameSettings.getnBundle().get("GameModNormal");
            EXPIRING_MOVES.name = GameSettings.getnBundle().get("GameModExpiration");
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Difficulty{
        EASY,
        MEDIUM,
        HARD;

        private  String name;

        static {
            EASY.name = GameSettings.getnBundle().get("GameDifficultyEasy");
            MEDIUM.name = GameSettings.getnBundle().get("GameDifficultyMedium");
            HARD.name = GameSettings.getnBundle().get("GameDifficultyHard");
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public void setGamePlayMode(GamePlayMode gamePlayMode) {
        this.gamePlayMode = gamePlayMode;
    }

    public GamePlayMode getGamePlayMode() {
        return gamePlayMode;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public GameMod getGameMod() {
        return gameMod;
    }

    public void setGameMod(GameMod gameMod) {
        this.gameMod = gameMod;
    }

    public Boolean getPlayedByRotation() {
        return playedByRotation;
    }

    public void setPlayedByRotation(Boolean playedByRotation) {
        this.playedByRotation = playedByRotation;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public void setPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public void setPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName;
    }

    public Player.PlayerType getPlayerTypeOne() {
        return playerTypeOne;
    }

    public void setPlayerTypeOne(Player.PlayerType playerTypeOne) {
        this.playerTypeOne = playerTypeOne;
    }

    public Player.PlayerType getPlayerTypeTwo() {
        return playerTypeTwo;
    }

    public void setPlayerTypeTwo(Player.PlayerType playerTypeTwo) {
        this.playerTypeTwo = playerTypeTwo;
    }

    public ModelType getModelTypePlayerOne() {
        return modelTypePlayerOne;
    }

    public void setModelTypePlayerOne(ModelType modelTypePlayerOne) {
        this.modelTypePlayerOne = modelTypePlayerOne;
    }

    public ModelType getModelTypePlayerTwo() {
        return modelTypePlayerTwo;
    }

    public void setModelTypePlayerTwo(ModelType modelTypePlayerTwo) {
        this.modelTypePlayerTwo = modelTypePlayerTwo;
    }

    public Boolean getPlayerOneHasToStart() {
        return playerOneHasToStart;
    }

    public void setPlayerOneHasToStart(Boolean playerOneHasToStart) {
        this.playerOneHasToStart = playerOneHasToStart;
    }

    public Boolean getPlayerTwoHasToStart() {
        return playerTwoHasToStart;
    }

    public void setPlayerTwoHasToStart(Boolean playerTwoHasToStart) {
        this.playerTwoHasToStart = playerTwoHasToStart;
    }
}
