package com.tictactoe.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Game;
import com.tictactoe.assets.Assets;
import com.tictactoe.screens.SplashScreen;

public class TictactoeGame extends Game {

	private Assets assets;
	private IActivityRequestHandler myRequestHandler;

	public TictactoeGame(IActivityRequestHandler handler) {
		myRequestHandler = handler;
	}
	
	@Override
	public void create()
	{
		this.assets = new Assets();
		myRequestHandler.showAds(true);
	    setScreen(new SplashScreen(this));
	}

    public Assets getAssets() {
        return assets;
    }
}
