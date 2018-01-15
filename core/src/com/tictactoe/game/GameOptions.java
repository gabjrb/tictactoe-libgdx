package com.tictactoe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Locale;

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

    private Preferences getPreferences() {
        return prefs;
    }

    public void setLanguage(String locale){
        getPreferences().putString("language", locale);
        getPreferences().flush();
    }

    public String getLanguage(){
        return getPreferences().getString("language", "root");
    }

//    public boolean isMusicEnabled(){
//        return getPreferences().getBoolean("music", true);
//    }
//
//    public void setMusicEnabled(boolean enabled){
//        getPreferences().putBoolean("music", enabled);
//        getPreferences().flush();
//    }

    public boolean isFxEnabled(){
        return getPreferences().getBoolean("fx", true);
    }

    public void setFxEnabled(boolean enabled){
        getPreferences().putBoolean("fx", enabled);
        getPreferences().flush();
    }
}