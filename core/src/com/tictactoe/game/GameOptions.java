package com.tictactoe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Gabriel on 26/12/2017.
 */

public class GameOptions {
    private static final GameOptions ourInstance = new GameOptions();

    public static GameOptions getInstance() {
        return ourInstance;
    }

    private Preferences prefs;

    private GameOptions() {
        prefs = Gdx.app.getPreferences("game-options-prefs");
    }

    public void currentLanguage(){
    }

    public void setCurrentLanguaje(String languaje){

    }

    public boolean isMusicEnabled(){
        return getPreferences().getBoolean("music", true);
    }

    public void setMusicEnabled(boolean enabled){
        getPreferences().putBoolean("music", enabled);
        getPreferences().flush();
    }

    public boolean isFxEnabled(){
        return getPreferences().getBoolean("fx", true);
    }

    public void setFxEnabled(boolean enabled){
        getPreferences().putBoolean("fx", enabled);
        getPreferences().flush();
    }

    public boolean isAdsEnabled(){
        return getPreferences().getBoolean("ads", true);
    }

    public void setAdsEnabled(boolean enabled){
        getPreferences().putBoolean("Ads", enabled);
        getPreferences().flush();
    }

    protected Preferences getPreferences() {
        return prefs;
    }
}