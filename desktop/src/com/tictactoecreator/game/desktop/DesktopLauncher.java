package com.tictactoecreator.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tictactoecreator.game.IActivityRequestHandler;
import com.tictactoecreator.game.TictactoeGame;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new TictactoeGame(new IActivityRequestHandler() {
			@Override
			public void showAds(boolean show) {

			}

			@Override
			public void showVideoAd() {

			}

			@Override
			public void showInterstitial() {

			}
		}), config);
	}
}