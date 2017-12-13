package com.tictactoe.screens;

import com.badlogic.gdx.Screen;
import com.tictactoe.game.TictactoeGame;

/**
 * Created by Gabriel on 12/12/2017.
 */

public abstract class BaseScreen implements Screen {

    protected TictactoeGame game;

    public BaseScreen(TictactoeGame game){
        this.game = game;
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
}
