package com.tictactoe.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/**
 * Created by Gabriel on 12/12/2017.
 */

public class Assets {
    public AssetManager manager = new AssetManager();
    FileHandleResolver resolver = new InternalFileHandleResolver();

    public void load(){

        manager.load("resources/messages", I18NBundle.class);

        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = "font/OpenSans-Regular.ttf";
        mySmallFont.fontParameters.size = 36;
        mySmallFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        mySmallFont.fontParameters.magFilter = Texture.TextureFilter.Linear;



        manager.load("font/OpenSans-Regular.ttf", BitmapFont.class, mySmallFont);

//        FreetypeFontLoader.FreeTypeFontLoaderParameter myBigFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
//        mySmallFont.fontFileName = "font/OpenSans-Bold.ttf";
//        mySmallFont.fontParameters.size = 96;
//        manager.load("font/OpenSans-Bold.ttf", BitmapFont.class, myBigFont);
    }

    public void dispose() {
        manager.dispose();
    }

}
