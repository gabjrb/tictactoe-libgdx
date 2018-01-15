package com.tictactoe.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.tictactoe.dialogs.EndGameDialog;

/**
 * Created by Gabriel on 14/1/2018.
 */

public class GamePlayHandler {
    private TicTacToeBoard board;
    private Player playerOne;
    private Player playerTwo;
    private Boolean turn;
    private String winnerName;
    private Integer playerOneScore;
    private Integer playerTwoScore;
    private GameSettings settings;
    private Boolean openDialog;
    private String gameScore;
    private Timer.Task playerAIMovementTask;
    private TictactoeGame game;

    public GamePlayHandler(TictactoeGame game, GameSettings settings) {
        this.settings = settings;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.game = game;
    }

    public TicTacToeBoard getBoard() {
        return board;
    }

    public Boolean handleTouch(Vector2 v) {
        if (board.gameOver())
            return false;
        if (playerAIMovementTask.isScheduled())
            return false;
        if (board.getCellOrigin(v)) {
            if (settings.getGamePlayMode() == GameSettings.GamePlayMode.SINGLEPLAYER) {
                if (!board.setCell(v, turn ? playerOne.getPlayerType().getBrand() : playerTwo.getPlayerType().getBrand()))
                    return false;
                if (!board.gameOver()) {
                    Timer.schedule(playerAIMovementTask, 1.0f);
                }
            } else if (settings.getGamePlayMode() == GameSettings.GamePlayMode.MULTIPLAYER) {
                if (!board.setCell(v, turn ? playerOne.getPlayerType().getBrand() : playerTwo.getPlayerType().getBrand()))
                    return false;
                turn = !turn;
            }
            return true;
        }
        return false;
    }

    public void verifier(Stage stage) {
        if (board.gameOver() && openDialog) {
            EndGameDialog endGameDialog = new EndGameDialog(game, hasWinner(),
                    winnerName);
            endGameDialog.showDialog(stage, new EndGameDialogCallback() {
                @Override
                public void restartGame() {
                    playerAIMovementTask.cancel();
                    turnExchanger();
                    gamePlayInitializer();
                }
            });
            openDialog = false;
            gameScore = String.format("%d : %d", playerOneScore, playerTwoScore);
        }
    }

    private void turnExchanger() {
        if (settings.getPlayedByRotation()) {
            if (settings.getPlayerOneHasToStart())
                turn = true;
            if (settings.getPlayerTwoHasToStart())
                turn = false;
            settings.setPlayerOneHasToStart(!turn);
            settings.setPlayerTwoHasToStart(turn);
        } else {
            this.turn = settings.getPlayerOneHasToStart();
        }
    }

    public void gamePlayInitializer() {
        this.board = new TicTacToeBoard();
        if (settings.getPlayerOneHasToStart())
            turn = true;
        if (settings.getPlayerTwoHasToStart())
            turn = false;
        this.openDialog = true;
        switch (settings.getModelTypePlayerOne()) {
            case HUMAN:
                playerOne = new Player(settings.getPlayerOneName(), settings.getPlayerTypeOne(), board, settings.getPlayerOneHasToStart());
                break;
            case COMPUTER:
                playerOne = new AIPlayer(settings.getPlayerOneName(), settings.getPlayerTypeOne(), board, settings.getPlayerOneHasToStart(), new MinimaxStrategy());
                break;
        }
        switch (settings.getModelTypePlayerTwo()) {
            case HUMAN:
                playerTwo = new Player(settings.getPlayerTwoName(), settings.getPlayerTypeTwo(), board, settings.getPlayerTwoHasToStart());
                break;
            case COMPUTER:
                playerTwo = new AIPlayer(settings.getPlayerTwoName(), settings.getPlayerTypeTwo(), board, settings.getPlayerTwoHasToStart(), new MinimaxStrategy());
                break;
        }

        playerAIMovementTask = new Timer.Task() {
            @Override
            public void run() {
                board.setCell(turn ? ((AIPlayer) playerTwo).makeAIMove() : ((AIPlayer) playerOne).makeAIMove(),
                        turn ? playerTwo.getPlayerType().getBrand() : playerOne.getPlayerType().getBrand());
            }
        };

        if (settings.getGamePlayMode() == GameSettings.GamePlayMode.SINGLEPLAYER && settings.getPlayerTwoHasToStart()) {
            if (!board.gameOver())
                Timer.schedule(playerAIMovementTask, 1.0f);
            settings.setPlayerOneHasToStart(true);
            turn = true;
        }

        gameScore = String.format("%d : %d", playerOneScore, playerTwoScore);
    }

    private Boolean hasWinner() {
        if (board.getResults().getWinner() == Player.PlayerType.PLAYER_TYPE_X) {
            if (playerOne.getPlayerType() == Player.PlayerType.PLAYER_TYPE_X) {
                winnerName = playerOne.getName();
                playerOneScore += 1;
            } else {
                winnerName = playerTwo.getName();
                playerTwoScore += 1;
            }
            return true;
        } else if (board.getResults().getWinner() == Player.PlayerType.PLAYER_TYPE_O) {
            if (playerOne.getPlayerType() == Player.PlayerType.PLAYER_TYPE_O) {
                winnerName = playerOne.getName();
                playerOneScore += 1;
            } else {
                winnerName = playerTwo.getName();
                playerTwoScore += 1;
            }
            return true;
        }
        return false;
    }

    public Timer.Task getPlayerAIMovementTask() {
        return playerAIMovementTask;
    }

    public GameSettings.GamePlayMode getGamePlayMode() {
        return settings.getGamePlayMode();
    }

    public GameSettings.Difficulty getDifficulty() {
        return settings.getDifficulty();
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public String messages() {
        if (board.gameOver())
            return "Game Over!";
        if (playerAIMovementTask.isScheduled())
            return "Think...";
        return String.format("Turn of %s!", turn ? settings.getPlayerOneName() : settings.getPlayerTwoName());
    }

    public String getGameScore() {
        return gameScore;
    }
}