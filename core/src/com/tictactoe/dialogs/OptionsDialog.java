package com.tictactoe.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


/**
 * Created by Gabriel on 13/12/2017.
 */

public class OptionsDialog {

    com.badlogic.gdx.scenes.scene2d.ui.Dialog dialog;
    TextureAtlas atlas;
    Skin skin;

    public OptionsDialog() {
        atlas = new TextureAtlas("ui/TicTacToe.atlas");
        skin = new Skin(Gdx.files.internal("ui/tictactoe-ui.json"), atlas);
        dialog = new com.badlogic.gdx.scenes.scene2d.ui.Dialog("Options", skin);
    }

}
