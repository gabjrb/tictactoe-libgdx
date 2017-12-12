package com.tictactoe.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

/**
 * Created by Gabriel on 12/12/2017.
 */

public class Assets {
    public AssetManager manager = new AssetManager();
    FileHandleResolver resolver = new InternalFileHandleResolver();

    public void load(){
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = "font/OpenSans-Regular.ttf";
        mySmallFont.fontParameters.size = 36;
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
