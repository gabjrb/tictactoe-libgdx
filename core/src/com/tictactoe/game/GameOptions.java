package com.tictactoe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Locale;

/**
 * Created by Gabriel on 26/12/2017.
 */

public class GameOptions {
    private static final GameOptions ourInstance = new GameOptions();
    private Preferences prefs;
    private Languages language;

    public GameOptions() {
        prefs = Gdx.app.getPreferences("game-options-prefs");
    }

    private Preferences getPreferences() {
        return prefs;
    }

    public static GameOptions getInstance() {
        return ourInstance;
    }

    public void setLanguage(Languages language){
        this.language = language;
        switch (language){
            case ENGLISH:
                getPreferences().putString("language", Languages.ENGLISH.name);
                break;
            case SPANISH:
                getPreferences().putString("language", Languages.SPANISH.name);
                break;
            case PORTUGUESE:
                getPreferences().putString("language", Languages.PORTUGUESE.name);
                break;
        }
        getPreferences().flush();
    }

    public Locale getLanguageLocale(){
        String localeLang = getPreferences().getString("language", "English").toString();
        if (localeLang.equals("English")){
            this.language = Languages.ENGLISH;
            return Languages.ENGLISH.getLocale();
        }
        if ((localeLang.equals("Español"))){
            this.language = Languages.SPANISH;
            return Languages.SPANISH.getLocale();
        }
        if ((localeLang.equals("Portugues"))){
            this.language = Languages.PORTUGUESE;
            return Languages.PORTUGUESE.getLocale();
        }
        return Locale.ROOT;
    }

    public Languages getLanguage(){
        return language;
    }

    public boolean isFxEnabled(){
        return getPreferences().getBoolean("fx", true);
    }

    public void setFxEnabled(boolean enabled){
        getPreferences().putBoolean("fx", enabled);
        getPreferences().flush();
    }

    public enum Languages{
        ENGLISH,
        SPANISH,
        PORTUGUESE;

        private  String name;
        private Locale locale;

        static {
            ENGLISH.name = "English";
            ENGLISH.locale = Locale.ROOT;
            SPANISH.name = "Español";
            SPANISH.locale = new Locale("es");
            PORTUGUESE.name = "Portugues";
            PORTUGUESE.locale = new Locale("pt");
        }

        public Locale getLocale() {
            return locale;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}