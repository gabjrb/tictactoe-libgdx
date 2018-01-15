package com.tictactoe.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.tictactoe.game.TictactoeGame;

/**
 * Created by Gabriel on 13/12/2017.
 */

public class OptionsDialog extends BaseDialog {
    private Dialog dialog;
    private ImageButton closeWindowsButton;
    private I18NBundle i18NBundle;
    private TictactoeGame game;
    //
    // Options
    //
    private Label fxLabel;
    private TextButton fxButton;
    private Label languageLabel;
    private SelectBox<String> cboLanguage;

    public OptionsDialog(TictactoeGame game) {
        super(game);
        this.game = game;
        this.i18NBundle = this.game.getAssets().getManager().get("resources/messages", I18NBundle.class);
        dialog = new com.badlogic.gdx.scenes.scene2d.ui.Dialog("Options", getSkin()) {
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

    private void loadControls(){
        closeWindowsButton = new ImageButton(getSkin(), "close");
        closeWindowsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        this.dialog.getTitleTable().add(closeWindowsButton);
        fxLabel = new Label("Effects", getSkin());
        fxButton = new TextButton("Yes", getSkin(), "toggle");
        languageLabel = new Label("Language", getSkin());
        cboLanguage = new SelectBox<String>(getSkin());
        cboLanguage.setItems(new String[]{"English", "Spanish", "Portuguese"});
        dialog.getContentTable().setWidth(260);
        dialog.padTop(30).padBottom(30);
        dialog.getContentTable().add(fxLabel).pad(30, 30, 0, 30).row();
        dialog.getContentTable().add(fxButton).padRight(30).padTop(30).width(185).row();
        dialog.getContentTable().add(languageLabel).pad(30, 30, 0, 30).row();
        dialog.getContentTable().add(cboLanguage).padRight(30).padTop(30).width(185).row();
    }

    public void showDialog(Stage stage){
        this.dialog.show(stage);
    }
}