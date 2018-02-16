package com.tictactoecreator.screens;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tictactoecreator.game.TictactoeGame;

/**
 * Created by Gabriel on 12/12/2017.
 */

public abstract class BaseScreen extends InputAdapter implements Screen {

    protected TictactoeGame game;
    protected Skin skin;
    protected TextureAtlas atlas;

    public BaseScreen(TictactoeGame game){
        this.game = game;
    }
    public BaseScreen(TictactoeGame game, Skin skin, TextureAtlas atlas){
        this.game = game;
        this.skin = skin;
        this.atlas = atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Skin getSkin() {
        return skin;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TictactoeGame getGame() {
        return game;
    }
}