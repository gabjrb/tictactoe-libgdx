package com.tictactoe.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tictactoe.assets.Assets;
import com.tictactoe.game.MainMenuScreen;
import com.tictactoe.game.TictactoeGame;

/**
 * Created by Gabriel on 12/12/2017.
 */

public class LoadingScreen extends BaseScreen {

    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
    private Label loading;

    public LoadingScreen(TictactoeGame game){
        super(game);
    }

    @Override
    public void show() {
        Assets assets = new Assets();
        assets.load();
        assets.manager.finishLoading();

        // Set up the stage and the skin. See GameOverScreen for more comments on this.
        stage = new Stage(new FitViewport(640, 360));
        atlas = new TextureAtlas("ui/TicTacToe.atlas");
        skin = new Skin(Gdx.files.internal("ui/tictactoe-ui.json"), atlas);

        // Create some loading text using this skin file and position it on screen.
        loading = new Label("Loading...", skin);
        loading.setPosition(320 - loading.getWidth() / 2, 180 - loading.getHeight() / 2);
        stage.addActor(loading);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (game.manager.update()) { // Load some, will return true if done loading
            if (Gdx.input.isTouched()) { // If the screen is touched after the game is done loading, go to the main menu screen
                game.setScreen(new MainMenuScreen(game));
            }
        }

        int progress = (int) (game.manager.getProgress() * 100);
        loading.setText("Loading... " + progress + "%");

        stage.act();
        stage.draw();
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
        stage.dispose();
        atlas.dispose();
        skin.dispose();
    }
}
