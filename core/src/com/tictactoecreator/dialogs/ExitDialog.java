package com.tictactoecreator.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.tictactoecreator.game.TictactoeGame;

/**
 * Created by Gabriel on 4/2/2018.
 */

public class ExitDialog extends BaseDialog {
    private Dialog dialog;
    private TictactoeGame game;
    private I18NBundle i18NBundle;
    private ImageButton closeWindowsButton;
    private TextButton btnExit;
    private TextButton btnCancel;
    private Label lblExitMessage;

    public ExitDialog(final TictactoeGame game){
        super(game);
        this.game = game;
        this.i18NBundle = this.game.getAssets().getManager().get("resources/messages", I18NBundle.class);
        dialog = new com.badlogic.gdx.scenes.scene2d.ui.Dialog(i18NBundle.get("ExitTitle"), getSkin()) {
            protected void result(Object object) {

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

        this.dialog.getTitleTable().add(closeWindowsButton);
        lblExitMessage = new Label(i18NBundle.get("ExitMessage"), getSkin());
        lblExitMessage.setWrap(true);
        lblExitMessage.setAlignment(Align.center);
        btnCancel = new TextButton(i18NBundle.get("NoTxt"), getSkin(), "cancel-default");
        btnCancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });
        btnExit = new TextButton(i18NBundle.get("YesTxt"), getSkin());
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        dialog.getContentTable().add(lblExitMessage).width(450).pad(60,30,0,30).row();
        dialog.getButtonTable().padTop(30).padBottom(60);
        dialog.getButtonTable().add(btnCancel).width(100);
        dialog.getButtonTable().add().width(50);
        dialog.getButtonTable().add(btnExit).width(100);
    }

    public void showDialog(Stage stage) {
        this.dialog.show(stage);
    }
}