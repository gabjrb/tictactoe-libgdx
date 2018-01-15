package com.tictactoe.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.tictactoe.game.GameSettings;
import com.tictactoe.game.Player;
import com.tictactoe.game.TictactoeGame;
import com.tictactoe.screens.TicTacToeScreen;

/**
 * Created by Gabriel on 13/1/2018.
 */

public class SingleplayerDialog extends BaseDialog {
    private Dialog dialog;
    private ImageButton closeWindowsButton;
    private TextButton start;
    //
    // Game modes
    //
    private Label gameModLabel;
    private Label difficultyLabel;
    private SelectBox<GameSettings.GameMod> cboGameMod;
    private SelectBox<GameSettings.Difficulty> cboDifficulty;
    //
    // Player Definition
    //
    private Label lblplayeShape;
    private SelectBox<Player.PlayerType> cboPlayerShape;
    //
    // Game
    //
    private Label lblwhoStarts;
    private Label lblfirstMove;
    private Label lblByRotation;
    private CheckBox chkByRotation;
    private SelectBox<String> cboFirstMovePlayer;
    private TictactoeGame game;
    private GameSettings settings;
    private I18NBundle i18NBundle;

    public SingleplayerDialog(final TictactoeGame game){
        super(game);
        this.game = game;
        this.i18NBundle = this.game.getAssets().getManager().get("resources/messages", I18NBundle.class);
        this.settings = new GameSettings(i18NBundle);
        dialog = new com.badlogic.gdx.scenes.scene2d.ui.Dialog(i18NBundle.get("SinglePlayer"), getSkin()) {
            protected void result(Object object) {
                setParameters();
                game.setScreen(new TicTacToeScreen(game, settings));
            }
        };
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        loadControls();
    }

    public void showDialog(Stage stage) {
        this.dialog.show(stage);
    }

    private void loadControls() {
        closeWindowsButton = new ImageButton(getSkin(), "close");
        closeWindowsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        this.dialog.getTitleTable().add(closeWindowsButton);
        start = new TextButton("Start", getSkin());

        gameModLabel = new Label(i18NBundle.get("GameMod"), getSkin());
        cboGameMod = new SelectBox<GameSettings.GameMod>(getSkin());
        cboGameMod.setItems(GameSettings.GameMod.NORMAL, GameSettings.GameMod.EXPIRING_MOVES);
        difficultyLabel = new Label(i18NBundle.get("GameDifficulty"), getSkin());
        cboDifficulty = new SelectBox<GameSettings.Difficulty>(getSkin());
        cboDifficulty.setItems(GameSettings.Difficulty.EASY, GameSettings.Difficulty.MEDIUM,
                GameSettings.Difficulty.HARD);
        lblplayeShape = new Label("Shape", getSkin());
        cboPlayerShape = new SelectBox<Player.PlayerType>(getSkin());
        cboPlayerShape.setItems(new Player.PlayerType[]{Player.PlayerType.PLAYER_TYPE_X, Player.PlayerType.PLAYER_TYPE_O});
        lblwhoStarts = new Label("Who starts?", getSkin(), "medium-large");
        lblfirstMove = new Label("First move", getSkin());
        lblByRotation = new Label("By rotation?", getSkin());
        chkByRotation = new CheckBox("", getSkin());
        cboFirstMovePlayer = new SelectBox<String>(getSkin());
        cboFirstMovePlayer.setItems(new String[]{i18NBundle.get("PlayerOneDefaultSingle"), i18NBundle.get("PlayerTwoDefaultSingle")});
        cboFirstMovePlayer.setAlignment(Align.left);

        dialog.padTop(30).padBottom(30);
        dialog.getContentTable().add(lblplayeShape).pad(30, 30, 0, 30);
        dialog.getContentTable().add(cboPlayerShape).padRight(30).padTop(30).width(185).row();
        dialog.getContentTable().add(difficultyLabel).pad(30, 30, 0, 30);
        dialog.getContentTable().add(cboDifficulty).padRight(30).padTop(30).width(185).row();
        dialog.getContentTable().add(gameModLabel).pad(30, 30, 0, 30);
        dialog.getContentTable().add(cboGameMod).padRight(30).padTop(30).width(185).row();
        dialog.getContentTable().add(lblwhoStarts).pad(30, 30, 0, 30).row();
        dialog.getContentTable().add(lblfirstMove);
        dialog.getContentTable().add(cboFirstMovePlayer).width(185).left().row();
        dialog.getContentTable().add(lblByRotation);
        dialog.getContentTable().add(chkByRotation).left();
        dialog.getButtonTable().padTop(30);
        dialog.button(start, true);
    }

    private void setParameters(){
        settings.setPlayerOneName(i18NBundle.get("PlayerOneDefaultSingle"));
        settings.setPlayerTwoName(i18NBundle.get("PlayerTwoDefaultSingle"));
        settings.setPlayerTypeOne(cboPlayerShape.getSelected());
        settings.setPlayerTypeTwo(cboPlayerShape.getSelected().getOpponent());
        settings.setModelTypePlayerOne(GameSettings.ModelType.HUMAN);
        settings.setModelTypePlayerTwo(GameSettings.ModelType.COMPUTER);
        settings.setDifficulty(cboDifficulty.getSelected());
        settings.setGameMod(cboGameMod.getSelected());
        if (cboFirstMovePlayer.getSelected() == i18NBundle.get("PlayerOneDefaultSingle")){
            settings.setPlayerOneHasToStart(true);
            settings.setPlayerTwoHasToStart(false);
        }
        else if (cboFirstMovePlayer.getSelected() == i18NBundle.get("PlayerTwoDefaultSingle")){
            settings.setPlayerOneHasToStart(false);
            settings.setPlayerTwoHasToStart(true);
        }
        settings.setPlayedByRotation(chkByRotation.isChecked());
        settings.setGamePlayMode(GameSettings.GamePlayMode.SINGLEPLAYER);
    }
}
