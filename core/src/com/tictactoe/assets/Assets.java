package com.tictactoe.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/**
 * Created by Gabriel on 12/12/2017.
 */

public class Assets {

    private AssetManager manager;

    public Assets(){
        this.manager = new AssetManager();
    }

    public void load(){
        this.manager.load("ui/TicTacToe.atlas", TextureAtlas.class);
        this.manager.load("ui/tictactoe-ui.json", Skin.class,
                new SkinLoader.SkinParameter("ui/TicTacToe.atlas"));
        this.manager.load("resources/messages", I18NBundle.class, new I18NBundleLoader.I18NBundleParameter(Locale.ROOT));
    }

    public AssetManager getManager() {
        return manager;
    }

    public void dispose() {
        manager.dispose();
    }

}
