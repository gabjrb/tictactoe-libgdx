package com.tictactoe.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tictactoe.game.IActivityRequestHandler;
import com.tictactoe.game.TictactoeGame;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new TictactoeGame(new IActivityRequestHandler() {
			@Override
			public void showAds(boolean show) {

			}
		}), config);
	}
}