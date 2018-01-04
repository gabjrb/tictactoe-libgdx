package com.tictactoe.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.tictactoe.game.EndGameDialogCallback;
import com.tictactoe.game.TictactoeGame;

/**
 * Created by Gabriel on 3/1/2018.
 */

public class EndGameDialog extends BaseDialog {
    private Dialog dialog;
    private ImageButton closeWindowsButton;
    private EndGameDialogCallback callBack;
    private ImageButton restartGameButton;
    private Label message;
    private String nameOfWinner;
    private Boolean hasWinner;

    public EndGameDialog(TictactoeGame game, Boolean hasWinner, String nameOfWinner) {
        super(game);
        this.nameOfWinner = nameOfWinner;
        this.hasWinner = hasWinner;
        dialog = new com.badlogic.gdx.scenes.scene2d.ui.Dialog("", getSkin()) {
            protected void result(Object object) {
                callBack.restartGame();
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

    public void loadControls() {
        closeWindowsButton = new ImageButton(getSkin(), "close");
        closeWindowsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        this.dialog.getTitleTable().add(closeWindowsButton);
        restartGameButton = new ImageButton(getSkin(), "restart");
        message = new Label(hasWinner ? "Congratulations " + this.nameOfWinner + " you won the game!" : "It's a tie!", getSkin());
        message.setWrap(true);
        message.setAlignment(Align.center);
        dialog.padTop(30).padBottom(30);
        dialog.getContentTable().add(message).width(450).pad(30,30,0,30).row();
        dialog.getButtonTable().padTop(30);
        dialog.button(restartGameButton, true);
    }

    public void showDialog(Stage stage, EndGameDialogCallback callBack) {
        this.callBack = callBack;
        this.dialog.show(stage);
    }
}