package com.tictactoecreator.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;
import com.tictactoecreator.dialogs.EndGameDialog;

/**
 * Created by Gabriel on 14/1/2018.
 */

public class GamePlayHandler {
    private TicTacToeBoard board;
    private Player playerOne;
    private Player playerTwo;
    private Boolean turn;
    private Player winnerPlayer;
    private Integer playerOneScore;
    private Integer playerTwoScore;
    private GameSettings settings;
    private Boolean openDialog;
    private String gameScore;
    private Timer.Task playerAIMovementTask;
    private TictactoeGame game;
    private I18NBundle i18NBundle;

    public GamePlayHandler(TictactoeGame game, GameSettings settings) {
        this.settings = settings;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.game = game;
        this.i18NBundle = game.getAssets().getManager().get("resources/messages", I18NBundle.class);
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
                    Timer.schedule(playerAIMovementTask, 0.666f);
                }
            } else if (settings.getGamePlayMode() == GameSettings.GamePlayMode.MULTIPLAYER) {
                if (!board.setCell(v, turn ? playerOne.getPlayerType().getBrand() : playerTwo.getPlayerType().getBrand()))
                    return false;
                turn = !turn;
            }
            if (settings.getGameMod() == GameSettings.GameMod.EXPIRING_MOVES)
                board.setCellLive(board.getCellQueue().first());
            return true;
        }
        return false;
    }

    public TicTacToeCell getCelltobeRemoved(){
        if ((settings.getGameMod() == GameSettings.GameMod.EXPIRING_MOVES) && (board.getCellQueue().size > 0))
            return board.getCellQueue().first();
        return new TicTacToeCell(new Vector2());
    }

    public void verifier(Stage stage) {
        if (board.gameOver() && openDialog) {
            EndGameDialog endGameDialog = new EndGameDialog(game, settings, hasWinner(),
                    winnerPlayer);
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

    private IDifficulty getDifficultyLevel(){
        IDifficulty level = new MinimaxStrategy();
        switch (settings.getDifficulty()){
            case EASY:
                level = new RandomStrategy();
                break;
            case MEDIUM:
                level = new RuleBasedStrategy();
                break;
            case HARD:
                level = new SuccessRateBasedStrategy(35);
                break;
            case UNBEATABLE:
                level = new MinimaxStrategy();
                break;
        }
        return level;
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
                playerOne = new AIPlayer(settings.getPlayerOneName(), settings.getPlayerTypeOne(), board, settings.getPlayerOneHasToStart(), getDifficultyLevel());
                break;
        }
        switch (settings.getModelTypePlayerTwo()) {
            case HUMAN:
                playerTwo = new Player(settings.getPlayerTwoName(), settings.getPlayerTypeTwo(), board, settings.getPlayerTwoHasToStart());
                break;
            case COMPUTER:
                playerTwo = new AIPlayer(settings.getPlayerTwoName(), settings.getPlayerTypeTwo(), board, settings.getPlayerTwoHasToStart(), getDifficultyLevel());
                break;
        }
        playerAIMovementTask = new Timer.Task() {
            @Override
            public void run() {
                if (board.setCell(turn ? ((AIPlayer) playerTwo).makeAIMove() : ((AIPlayer) playerOne).makeAIMove(),
                        turn ? playerTwo.getPlayerType().getBrand() : playerOne.getPlayerType().getBrand()) && settings.getGameMod() == GameSettings.GameMod.EXPIRING_MOVES)
                    board.setCellLive(board.getCellQueue().first());
            }
        };
        if (settings.getGamePlayMode() == GameSettings.GamePlayMode.SINGLEPLAYER && settings.getPlayerTwoHasToStart()) {
            if (!board.gameOver())
                Timer.schedule(playerAIMovementTask, 0.666f);
            settings.setPlayerOneHasToStart(true);
            turn = true;
        }
        gameScore = String.format("%d : %d", playerOneScore, playerTwoScore);
    }

    private Boolean hasWinner() {
        if (board.getResults().getWinner() == Player.PlayerType.PLAYER_TYPE_X) {
            if (playerOne.getPlayerType() == Player.PlayerType.PLAYER_TYPE_X) {
                winnerPlayer = playerOne;
                playerOneScore += 1;
            } else {
                winnerPlayer = playerTwo;
                playerTwoScore += 1;
            }
            return true;
        } else if (board.getResults().getWinner() == Player.PlayerType.PLAYER_TYPE_O) {
            if (playerOne.getPlayerType() == Player.PlayerType.PLAYER_TYPE_O) {
                winnerPlayer = playerOne;
                playerOneScore += 1;
            } else {
                winnerPlayer = playerTwo;
                playerTwoScore += 1;
            }
            return true;
        }
        return false;
    }

    public Array<ShapeData> getCircles() {
        Array<ShapeData> shapes = new Array<ShapeData>();
        if (settings.getGameMod() == GameSettings.GameMod.NORMAL)
            return board.getCircles();
        for (TicTacToeCell cell: board.getCellQueue()) {
            if (cell.getCellBrand() == TicTacToeCell.CellBrand.CIRCLE)
                shapes.add(cell.getShapeData());
        }
        return shapes;
    }

    public Array<ShapeData> getCrosses() {
        Array<ShapeData> shapes = new Array<ShapeData>();
        if (settings.getGameMod() == GameSettings.GameMod.NORMAL)
            return board.getCrosses();
        for (TicTacToeCell cell: board.getCellQueue()) {
            if (cell.getCellBrand() == TicTacToeCell.CellBrand.CROSS)
                shapes.add(cell.getShapeData());
        }
        return shapes;
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
            return i18NBundle.get("GameOver");
        if (playerAIMovementTask.isScheduled())
            return i18NBundle.get("Thinking");
        if (GameOptions.getInstance().getLanguage().toString() == "Español")
        {
            return (settings.getGamePlayMode() == GameSettings.GamePlayMode.SINGLEPLAYER) ?
                    i18NBundle.get("YourTurn") : String.format("%s %s", i18NBundle.get("GameTurn"),
                    turn ? settings.getPlayerOneName() : settings.getPlayerTwoName());
        }
        else if (GameOptions.getInstance().getLanguage().toString() == "English"){
            return (settings.getGamePlayMode() == GameSettings.GamePlayMode.SINGLEPLAYER) ?
                    i18NBundle.get("YourTurn") : String.format("%s %s", turn ?
                    settings.getPlayerOneName() : settings.getPlayerTwoName(), i18NBundle.get("GameTurn"));
        }
        return "";
    }

    public String getGameScore() {
        return gameScore;
    }
}