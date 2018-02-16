package com.tictactoecreator.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.tictactoecreator.game.EndGameDialogCallback;
import com.tictactoecreator.game.GameSettings;
import com.tictactoecreator.game.Player;
import com.tictactoecreator.game.TictactoeGame;

/**
 * Created by Gabriel on 3/1/2018.
 */

public class EndGameDialog extends BaseDialog {
    private Dialog dialog;
    private ImageButton closeWindowsButton;
    private EndGameDialogCallback callBack;
    private ImageButton restartGameButton;
    private Label message;
    private Player winner;
    private Boolean hasWinner;
    private I18NBundle i18NBundle;
    private GameSettings settings;

    public EndGameDialog(TictactoeGame game, GameSettings settings, Boolean hasWinner, Player winner) {
        super(game);
        this.i18NBundle = game.getAssets().getManager().get("resources/messages", I18NBundle.class);
        this.winner = winner;
        this.hasWinner = hasWinner;
        this.settings = settings;
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
        message = new Label(hasWinner ? endGameMessage() : i18NBundle.get("DrawMessage"), getSkin());
        message.setWrap(true);
        message.setAlignment(Align.center);
        dialog.padTop(30).padBottom(30);
        dialog.getContentTable().add(message).width(450).pad(30,30,0,30).row();
        dialog.getButtonTable().padTop(30);
        dialog.button(restartGameButton, true);
    }

    private String endGameMessage(){
        if (settings.getGamePlayMode() == GameSettings.GamePlayMode.SINGLEPLAYER){
            if (settings.getPlayerTypeOne() == winner.getPlayerType()){
                if (settings.getModelTypePlayerOne() == GameSettings.ModelType.COMPUTER)
                    return i18NBundle.get("WonMessageCPU");
                else if (settings.getModelTypePlayerOne() == GameSettings.ModelType.HUMAN)
                    return i18NBundle.get("WonMessageHuman");
            }
            else if (settings.getPlayerTypeTwo() == winner.getPlayerType()){
                if (settings.getModelTypePlayerTwo() == GameSettings.ModelType.COMPUTER)
                    return i18NBundle.get("WonMessageCPU");
                else if (settings.getModelTypePlayerTwo() == GameSettings.ModelType.HUMAN)
                    return i18NBundle.get("WonMessageHuman");
            }
        }
        else
        {
            return String.format("%s %s", winner.getName(), i18NBundle.get("WonMessage"));
        }
        return "";
    }

    public void showDialog(Stage stage, EndGameDialogCallback callBack) {
        this.callBack = callBack;
        this.dialog.show(stage);
    }
}