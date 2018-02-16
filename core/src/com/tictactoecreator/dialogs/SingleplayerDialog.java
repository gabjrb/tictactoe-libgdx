package com.tictactoecreator.dialogs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.tictactoecreator.game.GameSettings;
import com.tictactoecreator.game.Player;
import com.tictactoecreator.game.TictactoeGame;
import com.tictactoecreator.screens.TicTacToeScreen;

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
    private TextButton btnPlayerShape;
    //
    // Game
    //
    private Label lblwhoStarts;
    private Label lblfirstMove;
    private Label lblByRotation;
    private TextButton btnByRotation;
    private TextButton btnFirstMovePlayer;
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
        start = new TextButton(i18NBundle.get("StartGame"), getSkin());
        gameModLabel = new Label(i18NBundle.get("GameMod"), getSkin());
        cboGameMod = new SelectBox<GameSettings.GameMod>(getSkin());
        cboGameMod.setItems(GameSettings.GameMod.NORMAL, GameSettings.GameMod.EXPIRING_MOVES);
        cboGameMod.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (cboGameMod.getSelected() == GameSettings.GameMod.EXPIRING_MOVES)
                    game.getAdsRequestHandler().showVideoAd();
            }
        });
        difficultyLabel = new Label(i18NBundle.get("GameDifficulty"), getSkin());
        cboDifficulty = new SelectBox<GameSettings.Difficulty>(getSkin());
        cboDifficulty.setItems(GameSettings.Difficulty.EASY, GameSettings.Difficulty.MEDIUM,
                GameSettings.Difficulty.HARD, GameSettings.Difficulty.UNBEATABLE);
        lblplayeShape = new Label(i18NBundle.get("Shape"), getSkin());
        btnPlayerShape = new TextButton("X", getSkin(), "toggle");
        btnPlayerShape.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnPlayerShape.setText(!btnPlayerShape.isChecked() ? "X" : "O");
            }
        });
        lblwhoStarts = new Label(i18NBundle.get("WhoStarts"), getSkin(), "medium-large");
        lblfirstMove = new Label(i18NBundle.get("FirstMove"), getSkin());
        lblByRotation = new Label(i18NBundle.get("ByRotation"), getSkin());
        btnByRotation = new TextButton(i18NBundle.get("NoTxt"), getSkin(), "toggle");
        btnByRotation.setChecked(true);
        btnByRotation.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnByRotation.setText(btnByRotation.isChecked() ? i18NBundle.get("NoTxt") : i18NBundle.get("YesTxt"));
            }
        });
        btnFirstMovePlayer = new TextButton(i18NBundle.get("PlayerOneDefaultSingle"), getSkin(), "toggle");
        btnFirstMovePlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnFirstMovePlayer.setText(!btnFirstMovePlayer.isChecked() ? i18NBundle.get("PlayerOneDefaultSingle") :
                        i18NBundle.get("PlayerTwoDefaultSingle"));
            }
        });
        dialog.padTop(30).padBottom(30);
        dialog.getContentTable().add(lblplayeShape).pad(30, 30, 0, 30);
        dialog.getContentTable().add(btnPlayerShape).padRight(30).padTop(30).width(220).row();
        dialog.getContentTable().add(difficultyLabel).pad(30, 30, 0, 30);
        dialog.getContentTable().add(cboDifficulty).padRight(30).padTop(30).width(220).row();
        dialog.getContentTable().add(gameModLabel).pad(30, 30, 0, 30);
        dialog.getContentTable().add(cboGameMod).padRight(30).padTop(30).width(220).row();
        dialog.getContentTable().add(lblwhoStarts).pad(30, 30, 0, 30).row();
        dialog.getContentTable().add(lblfirstMove);
        dialog.getContentTable().add(btnFirstMovePlayer).width(220).left().row();
        dialog.getContentTable().add(lblByRotation);
        dialog.getContentTable().add(btnByRotation).width(220).left();
        dialog.getButtonTable().padTop(30);
        dialog.button(start, true);
    }

    private void setParameters(){
        settings.setPlayerOneName(i18NBundle.get("PlayerOneDefaultSingle"));
        settings.setPlayerTwoName(i18NBundle.get("PlayerTwoDefaultSingle"));
        settings.setPlayerTypeOne(btnPlayerShape.isChecked() ? Player.PlayerType.PLAYER_TYPE_O :
                Player.PlayerType.PLAYER_TYPE_X);
        settings.setPlayerTypeTwo(settings.getPlayerTypeOne().getOpponent());
        settings.setModelTypePlayerOne(GameSettings.ModelType.HUMAN);
        settings.setModelTypePlayerTwo(GameSettings.ModelType.COMPUTER);
        settings.setDifficulty(cboDifficulty.getSelected());
        settings.setGameMod(cboGameMod.getSelected());
        settings.setPlayerOneHasToStart(!btnFirstMovePlayer.isChecked());
        settings.setPlayerTwoHasToStart(btnFirstMovePlayer.isChecked());
        settings.setPlayedByRotation(!btnByRotation.isChecked());
        settings.setGamePlayMode(GameSettings.GamePlayMode.SINGLEPLAYER);
    }
}