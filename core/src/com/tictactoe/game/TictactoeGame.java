package com.tictactoe.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Game;
import com.tictactoe.screens.SplashScreen;

public class TictactoeGame extends Game {

	public AssetManager manager = new AssetManager();
	
	@Override
	public void create()
	{
		setScreen(new SplashScreen(this));
	}
}
