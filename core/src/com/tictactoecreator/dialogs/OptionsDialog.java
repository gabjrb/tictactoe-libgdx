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
import com.tictactoecreator.assets.Assets;
import com.tictactoecreator.game.GameOptions;
import com.tictactoecreator.game.TictactoeGame;
import com.tictactoecreator.screens.SplashScreen;

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
    private SelectBox<GameOptions.Languages> cboLanguage;

    public OptionsDialog(TictactoeGame game) {
        super(game);
        this.game = game;
        this.i18NBundle = this.game.getAssets().getManager().get("resources/messages", I18NBundle.class);
        dialog = new com.badlogic.gdx.scenes.scene2d.ui.Dialog(i18NBundle.get("Options"), getSkin()) {
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
        fxLabel = new Label(i18NBundle.get("Effects"), getSkin());
        fxButton = new TextButton(i18NBundle.get("YesTxt"), getSkin(), "toggle");
        fxButton.setChecked(!GameOptions.getInstance().isFxEnabled());
        fxButton.setText(!fxButton.isChecked() ? i18NBundle.get("YesTxt") : i18NBundle.get("NoTxt"));
        fxButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fxButton.setText(!fxButton.isChecked() ? i18NBundle.get("YesTxt") : i18NBundle.get("NoTxt"));
                GameOptions.getInstance().setFxEnabled(!fxButton.isChecked());
            }
        });
        languageLabel = new Label(i18NBundle.get("Language"), getSkin());
        cboLanguage = new SelectBox<GameOptions.Languages>(getSkin());
        cboLanguage.setItems(GameOptions.Languages.ENGLISH, GameOptions.Languages.SPANISH);
        cboLanguage.setSelected(GameOptions.getInstance().getLanguage());
        cboLanguage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameOptions.getInstance().setLanguage(cboLanguage.getSelected());
                game.getAdsRequestHandler().showVideoAd();
                game.setAssets(new Assets());
                game.setScreen(new SplashScreen(game));
            }
        });
        dialog.getContentTable().add(fxLabel).pad(60, 60, 0, 60).row();
        dialog.getContentTable().add(fxButton).pad(15, 60, 0, 60).width(185).row();
        dialog.getContentTable().add(languageLabel).pad(30, 30, 0, 30).row();
        dialog.getContentTable().add(cboLanguage).pad(15, 30, 60, 30).width(185).row();
    }

    public void showDialog(Stage stage){
        this.dialog.show(stage);
    }
}