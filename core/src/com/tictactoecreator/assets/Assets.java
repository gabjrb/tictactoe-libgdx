package com.tictactoecreator.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.tictactoecreator.game.GameOptions;

/**
 * Created by Gabriel on 12/12/2017.
 */

public class Assets {
    private AssetManager manager;

    public Assets(){
        this.manager = new AssetManager();
    }

    public void load(){
        this.manager.load("ui/tictactoe-game-ui.atlas", TextureAtlas.class);
        this.manager.load("ui/tictactoe-game-ui.json", Skin.class,
                new SkinLoader.SkinParameter("ui/tictactoe-game-ui.atlas"));
        this.manager.load("resources/messages", I18NBundle.class,
                new I18NBundleLoader.I18NBundleParameter(GameOptions.getInstance().getLanguageLocale()));
    }

    public AssetManager getManager() {
        return manager;
    }

    public void dispose() {
        manager.dispose();
    }
}