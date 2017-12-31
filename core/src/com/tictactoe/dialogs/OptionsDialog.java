package com.tictactoe.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.tictactoe.game.GameOptions;


/**
 * Created by Gabriel on 13/12/2017.
 */

public class OptionsDialog {

    com.badlogic.gdx.scenes.scene2d.ui.Dialog dialog;
    TextureAtlas atlas;
    Skin skin;
    CheckBox chkSound;
    CheckBox chkFX;
    CheckBox chkAds;
    SelectBox<String> cboLanguage;
    String[] languages;
    TextButton btnQuitGame;
    ImageButton btnCloseWindows;

    public OptionsDialog() {
        atlas = new TextureAtlas("ui/TicTacToe.atlas");
        skin = new Skin(Gdx.files.internal("ui/tictactoe-ui.json"), atlas);
        dialog = new com.badlogic.gdx.scenes.scene2d.ui.Dialog("Options", skin){
            protected void result(Object object){

            }
        };
        loadControls();
    }

    private void loadControls(){
        languages = new String[] {"Spanish, English, Deutch"};
        cboLanguage = new SelectBox<String>(skin);
        cboLanguage.setItems("English", "Spanish", "Portuguese");
        btnCloseWindows = new ImageButton(skin.getDrawable("button-close"));
        btnCloseWindows.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               dialog.hide();
            }
        });
        this.dialog.getTitleTable().add(btnCloseWindows);
        chkSound = new CheckBox("Sound", skin);
        chkSound.setChecked(GameOptions.getInstance().isMusicEnabled());
        chkSound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameOptions.getInstance().setMusicEnabled(chkSound.isChecked());
            }
        });
        chkFX = new CheckBox("FX", skin);
        chkAds = new CheckBox("ADS", skin);
        btnQuitGame = new TextButton("Quit", skin);
        this.dialog.getButtonTable().add(cboLanguage);
        this.dialog.getButtonTable().row();
        this.dialog.getButtonTable().add(chkSound);
        this.dialog.getButtonTable().row();
        this.dialog.getButtonTable().add(chkFX);
        this.dialog.getButtonTable().row();
        this.dialog.getButtonTable().add(chkSound);
        this.dialog.getButtonTable().row();
        this.dialog.getButtonTable().add(chkAds);
        this.dialog.getButtonTable().row();
        this.dialog.getButtonTable().add(btnQuitGame);
    }

    public void show(Stage stage){
        this.dialog.show(stage);
    }
}
