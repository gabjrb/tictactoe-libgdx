package com.tictactoe.dialogs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.tictactoe.game.GameSettings;
import com.tictactoe.game.Player;
import com.tictactoe.game.TictactoeGame;
import com.tictactoe.screens.TicTacToeScreen;

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
    private SelectBox<Player.PlayerType> cboPlayerOneShape;
    //
    // Player Two
    //
    private Label playerTwoTitle;
    private Label lblplayerTwoName;
    private TextField txtplayerTwoName;
    private Label lblplayerTwoShape;
    private SelectBox<Player.PlayerType> cboPlayerTwoShape;
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

    public MultiplayerDialog(final TictactoeGame game) {
        super(game);
        this.game = game;
        this.i18NBundle = this.game.getAssets().getManager().get("resources/messages", I18NBundle.class);
        this.settings = new GameSettings(i18NBundle);
        dialog = new com.badlogic.gdx.scenes.scene2d.ui.Dialog("", getSkin()) {
            protected void result(Object object) {
                setParameters();
                game.setScreen(new TicTacToeScreen(game, settings));
//                callBack.setAndGo();
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

    private void loadControls(){
        closeWindowsButton = new ImageButton(getSkin(), "close");
        closeWindowsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        this.dialog.getTitleTable().add(closeWindowsButton);
        start = new TextButton("Start", getSkin());
        playerOneTitle = new Label(i18NBundle.get("PlayerOneDefault"), getSkin(), "medium-large");
        playerOneTitle.setAlignment(Align.left);
        lblplayerOneName = new Label("Name", getSkin());
        txtplayerOneName = new TextField("Player1", getSkin());
        lblplayerOneShape = new Label("Shape",getSkin());
        cboPlayerOneShape = new SelectBox<Player.PlayerType>(getSkin());
        cboPlayerOneShape.setItems(new Player.PlayerType[] {Player.PlayerType.PLAYER_TYPE_X, Player.PlayerType.PLAYER_TYPE_O});
        cboPlayerOneShape.setSelected(settings.getPlayerTypeOne());
        cboPlayerOneShape.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cboPlayerTwoShape.setSelected(cboPlayerOneShape.getSelected().getOpponent());
            }
        });

        playerTwoTitle = new Label(i18NBundle.get("PlayerTwoDefault"), getSkin(), "medium-large");
        playerTwoTitle.setAlignment(Align.left);
        lblplayerTwoName = new Label("Name", getSkin());
        txtplayerTwoName = new TextField("Player2", getSkin());
        lblplayerTwoShape = new Label("Shape", getSkin());
        cboPlayerTwoShape = new SelectBox<Player.PlayerType>(getSkin());
        cboPlayerTwoShape.setItems(new Player.PlayerType[] {Player.PlayerType.PLAYER_TYPE_X, Player.PlayerType.PLAYER_TYPE_O});
        cboPlayerTwoShape.setSelected(settings.getPlayerTypeTwo());
        cboPlayerTwoShape.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cboPlayerOneShape.setSelected(cboPlayerTwoShape.getSelected().getOpponent());
            }
        });

        lblwhoStarts = new Label("Who starts?", getSkin(), "medium-large");
        lblfirstMove = new Label("First move", getSkin());
        lblByRotation = new Label("By rotation?", getSkin());
        chkByRotation = new CheckBox("", getSkin());
        cboFirstMovePlayer = new SelectBox<String>(getSkin());
        cboFirstMovePlayer.setItems(new String[]{i18NBundle.get("PlayerOneDefault"), i18NBundle.get("PlayerTwoDefault")});
        cboFirstMovePlayer.setAlignment(Align.left);

        dialog.padTop(30).padBottom(30);
        dialog.getContentTable().add(playerOneTitle).pad(30,30,0,30).row();
        dialog.getContentTable().add(lblplayerOneName);
        dialog.getContentTable().add(txtplayerOneName).padRight(30).width(185).row();
        dialog.getContentTable().add(lblplayerOneShape);
        dialog.getContentTable().add(cboPlayerOneShape).padRight(30).width(185).row();

        dialog.getContentTable().add(playerTwoTitle).pad(30,30,0,30).row();
        dialog.getContentTable().add(lblplayerTwoName);
        dialog.getContentTable().add(txtplayerTwoName).padRight(30).width(185).row();
        dialog.getContentTable().add(lblplayerTwoShape);
        dialog.getContentTable().add(cboPlayerTwoShape).padRight(30).width(185).row();

        dialog.getContentTable().add(lblwhoStarts).pad(30,30,0,30).row();
        dialog.getContentTable().add(lblfirstMove);
        dialog.getContentTable().add(cboFirstMovePlayer).width(185).left().row();
        dialog.getContentTable().add(lblByRotation);
        dialog.getContentTable().add(chkByRotation).left();

        dialog.getButtonTable().padTop(30);
        //medium-large-font
        dialog.button(start, true);
    }

    public void showDialog(Stage stage) {
        this.dialog.show(stage);
    }

    private void setParameters(){
        settings.setPlayerOneName(txtplayerOneName.getText());
        settings.setPlayerTwoName(txtplayerTwoName.getText());
        settings.setPlayerTypeOne(cboPlayerOneShape.getSelected());
        settings.setPlayerTypeTwo(cboPlayerTwoShape.getSelected());
        settings.setModelTypePlayerOne(GameSettings.ModelType.HUMAN);
        settings.setModelTypePlayerTwo(GameSettings.ModelType.HUMAN);
        if (cboFirstMovePlayer.getSelected() == i18NBundle.get("PlayerOneDefault")){
            settings.setPlayerOneHasToStart(true);
            settings.setPlayerTwoHasToStart(false);
        }
        else if (cboFirstMovePlayer.getSelected() == i18NBundle.get("PlayerTwoDefault")){
            settings.setPlayerOneHasToStart(false);
            settings.setPlayerTwoHasToStart(true);
        }
        settings.setPlayedByRotation(chkByRotation.isChecked());
        settings.setGamePlayMode(GameSettings.GamePlayMode.MULTIPLAYER);
    }
}