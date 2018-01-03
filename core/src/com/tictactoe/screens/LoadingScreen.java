package com.tictactoe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tictactoe.game.GameSettings;
import com.tictactoe.game.TictactoeGame;

/**
 * Created by Gabriel on 12/12/2017.
 */

public class LoadingScreen extends BaseScreen {

    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
    private Label loading;
    private ProgressBar progressBar;
    private Table rootTable;

    public LoadingScreen(TictactoeGame game){
        super(game);
    }

    @Override
    public void show() {

        game.getAssets().load();
        game.getAssets().getManager().finishLoading();

        stage = new Stage(new FitViewport(640, 360));

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setBounds(0,0,640,360);
        stage.addActor(rootTable);

//        atlas = game.getAssets().getManager().get("ui/TicTacToe.atlas", TextureAtlas.class);
//        skin = game.getAssets().getManager().get("ui/tictactoe-ui.json", Skin.class);

        atlas = game.getAssets().getManager().get("ui/tictactoe-game-ui.atlas", TextureAtlas.class);
        skin = game.getAssets().getManager().get("ui/tictactoe-game-ui.json", Skin.class);

        loading = new Label("Loading...", skin);

        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();

        progressBarStyle.background = getColoredDrawable(400,40,Color.RED);
        progressBarStyle.knob = getColoredDrawable(0, 40, Color.GREEN);
        progressBarStyle.knobBefore = getColoredDrawable(400,40, Color.GREEN);

        progressBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);
        progressBar.setValue(0f);
        progressBar.setAnimateDuration(2f);
        progressBar.setBounds(0, 0, 400, 40);

        rootTable.add(loading);
        rootTable.row();
        rootTable.add(progressBar).width(400);
    }

    public Drawable getColoredDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        return drawable;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        progressBar.setValue(game.getAssets().getManager().getProgress());

        int progress = (int) (progressBar.getVisualValue() * 100) == 99 ? 100 : (int) (progressBar.getVisualValue() * 100);

        loading.setText("Loading... " + progress + "%");

        if (game.getAssets().getManager().update() && progressBar.getVisualValue() == 1f) {
//            game.setScreen(new MainMenuScreen(game));
            game.setScreen(new TicTacToeScreen(game, new GameSettings()));
        }

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
