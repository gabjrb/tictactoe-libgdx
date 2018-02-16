package com.tictactoecreator.dialogs;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tictactoecreator.game.TictactoeGame;

/**
 * Created by Gabriel on 13/12/2017.
 */

public abstract class BaseDialog {
    private TextureAtlas atlas;
    private Skin skin;

    public BaseDialog(TictactoeGame game){
        this.atlas = game.getAssets().getManager().get("ui/tictactoe-game-ui.atlas", TextureAtlas.class);
        this.skin = game.getAssets().getManager().get("ui/tictactoe-game-ui.json", Skin.class);
    }

    public Skin getSkin() {
        return skin;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }
}