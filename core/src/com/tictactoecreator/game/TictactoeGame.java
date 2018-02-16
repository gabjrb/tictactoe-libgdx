package com.tictactoecreator.game;

import com.badlogic.gdx.Game;
import com.tictactoecreator.assets.Assets;
import com.tictactoecreator.screens.SplashScreen;

public class TictactoeGame extends Game {

	private Assets assets;
	private IActivityRequestHandler adsRequestHandler;

	public TictactoeGame(IActivityRequestHandler handler) {
		adsRequestHandler = handler;
	}
	
	@Override
	public void create()
	{
		this.assets = new Assets();
	    setScreen(new SplashScreen(this));
	}

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public Assets getAssets() {
        return assets;
    }

	public IActivityRequestHandler getAdsRequestHandler() {
		return adsRequestHandler;
	}
}
