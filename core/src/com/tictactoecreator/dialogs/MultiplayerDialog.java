package com.tictactoecreator.dialogs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.tictactoecreator.game.GameSettings;
import com.tictactoecreator.game.Player;
import com.tictactoecreator.game.TictactoeGame;
import com.tictactoecreator.screens.TicTacToeScreen;

/**
 * Created by Gabriel on 8/1/2018.
 */

public class MultiplayerDialog extends BaseDialog {
    private Dialog dialog;
    private ImageButton closeWindowsButton;
    private TextButton start;
    //
    // Player one
    //
    private Label playerOneTitle;
    private Label lblplayerOneName;
    private TextField txtplayerOneName;
    private Label lblplayerOneShape;
    private TextButton btnPlayerOneShape;
    //
    // Player Two
    //
    private Label playerTwoTitle;
    private Label lblplayerTwoName;
    private TextField txtplayerTwoName;
    private Label lblplayerTwoShape;
    private TextButton btnPlayerTwoShape;
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

    public MultiplayerDialog(final TictactoeGame game) {
        super(game);
        this.game = game;
        this.i18NBundle = this.game.getAssets().getManager().get("resources/messages", I18NBundle.class);
        this.settings = new GameSettings(i18NBundle);
        dialog = new com.badlogic.gdx.scenes.scene2d.ui.Dialog(i18NBundle.get("MultiPlayer"), getSkin()) {
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

    private void loadControls() {
        closeWindowsButton = new ImageButton(getSkin(), "close");
        closeWindowsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        this.dialog.getTitleTable().add(closeWindowsButton);//i18NBundle.get("")
        start = new TextButton(i18NBundle.get("StartGame"), getSkin());
        playerOneTitle = new Label(i18NBundle.get("PlayerOneDefault"), getSkin(), "medium-large");
        playerOneTitle.setAlignment(Align.left);
        lblplayerOneName = new Label(i18NBundle.get("PlayerName"), getSkin());
        txtplayerOneName = new TextField(i18NBundle.get("PlayerOneDefault"), getSkin());
        lblplayerOneShape = new Label(i18NBundle.get("Shape"), getSkin());
        btnPlayerOneShape = new TextButton("X", getSkin(), "toggle");
        btnPlayerOneShape.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                btnPlayerOneShape.setText(!btnPlayerOneShape.isChecked() ? "X" : "O");
                btnPlayerTwoShape.setChecked(!btnPlayerOneShape.isChecked());
            }
        });
        playerTwoTitle = new Label(i18NBundle.get("PlayerTwoDefault"), getSkin(), "medium-large");
        playerTwoTitle.setAlignment(Align.left);
        lblplayerTwoName = new Label(i18NBundle.get("PlayerName"), getSkin());
        txtplayerTwoName = new TextField(i18NBundle.get("PlayerTwoDefault"), getSkin());
        lblplayerTwoShape = new Label(i18NBundle.get("Shape"), getSkin());
        btnPlayerTwoShape = new TextButton("O", getSkin(), "toggle");
        btnPlayerTwoShape.setChecked(true);
        btnPlayerTwoShape.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                btnPlayerTwoShape.setText(!btnPlayerTwoShape.isChecked() ? "X" : "O");
                btnPlayerOneShape.setChecked(!btnPlayerTwoShape.isChecked());
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
        btnFirstMovePlayer = new TextButton(i18NBundle.get("PlayerOneDefault"), getSkin(), "toggle");
        btnFirstMovePlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnFirstMovePlayer.setText(!btnFirstMovePlayer.isChecked() ? i18NBundle.get("PlayerOneDefault") :
                        i18NBundle.get("PlayerTwoDefault"));
            }
        });
        dialog.padTop(30).padBottom(30);
        dialog.getContentTable().add(playerOneTitle).pad(30, 30, 0, 30).row();
        dialog.getContentTable().add(lblplayerOneName);
        dialog.getContentTable().add(txtplayerOneName).padRight(30).width(220).row();
        dialog.getContentTable().add(lblplayerOneShape);
        dialog.getContentTable().add(btnPlayerOneShape).padRight(30).width(220).row();

        dialog.getContentTable().add(playerTwoTitle).pad(30, 30, 0, 30).row();
        dialog.getContentTable().add(lblplayerTwoName);
        dialog.getContentTable().add(txtplayerTwoName).padRight(30).width(220).row();
        dialog.getContentTable().add(lblplayerTwoShape);
        dialog.getContentTable().add(btnPlayerTwoShape).padRight(30).width(220).row();

        dialog.getContentTable().add(lblwhoStarts).pad(30, 30, 0, 30).row();
        dialog.getContentTable().add(lblfirstMove);
        dialog.getContentTable().add(btnFirstMovePlayer).width(220).left().row();
        dialog.getContentTable().add(lblByRotation);
        dialog.getContentTable().add(btnByRotation).width(220).left();

        dialog.getButtonTable().padTop(30);
        dialog.button(start, true);
    }

    public void showDialog(Stage stage) {
        this.dialog.show(stage);
    }

    private void setParameters(){
        settings.setPlayerOneName(txtplayerOneName.getText());
        settings.setPlayerTwoName(txtplayerTwoName.getText());
        settings.setPlayerTypeOne(btnPlayerOneShape.isChecked() ? Player.PlayerType.PLAYER_TYPE_O :
                Player.PlayerType.PLAYER_TYPE_X);
        settings.setPlayerTypeTwo(settings.getPlayerTypeOne().getOpponent());
        settings.setModelTypePlayerOne(GameSettings.ModelType.HUMAN);
        settings.setModelTypePlayerTwo(GameSettings.ModelType.HUMAN);
        settings.setPlayerOneHasToStart(!btnFirstMovePlayer.isChecked());
        settings.setPlayerTwoHasToStart(btnFirstMovePlayer.isChecked());
        settings.setPlayedByRotation(!btnByRotation.isChecked());
        settings.setGamePlayMode(GameSettings.GamePlayMode.MULTIPLAYER);
    }
}