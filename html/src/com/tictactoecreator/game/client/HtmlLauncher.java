package com.tictactoecreator.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.tictactoecreator.game.IActivityRequestHandler;
import com.tictactoecreator.game.TictactoeGame;

public class HtmlLauncher extends GwtApplication implements IActivityRequestHandler {
        @Override
        public GwtApplicationConfiguration getConfig() {
                return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener createApplicationListener() {
                return new TictactoeGame(this);
        }

        @Override
        public void showInterstitial() {

        }

        @Override
        public void showVideoAd() {

        }

        @Override
        public void showAds(boolean show) {

        }
}