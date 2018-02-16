package com.tictactoecreator.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tictactoecreator.game.GameConstants;
import com.tictactoecreator.game.TictactoeGame;

/**
 * Created by Gabriel on 12/12/2017.
 */

public class LoadingScreen extends BaseScreen {
    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
//    private Label loading;
    private ProgressBar progressBar;
    private Table rootTable;
    private Camera camera;
    private ExtendViewport viewport;
    private Sprite logo;
    private SpriteBatch batch;

    public LoadingScreen(TictactoeGame game){
        super(game);
    }

    @Override
    public void show() {
        //
        // Load styles-resources
        //
        game.getAssets().load();
        game.getAssets().getManager().finishLoading();
        //
        // Screen configuration
        //
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(GameConstants.WORLD_SIZE.x, GameConstants.WORLD_SIZE.y, camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        //
        // Game styles-resources
        //
        atlas = game.getAssets().getManager().get("ui/tictactoe-game-ui.atlas", TextureAtlas.class);
        skin = game.getAssets().getManager().get("ui/tictactoe-game-ui.json", Skin.class);
        //
        // Game World
        //
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        //
        // Logo Texture
        //
        Texture texture = new Texture(Gdx.files.internal("resources/tictactoe-logo-256.png"));
        logo = new Sprite(texture);
        Image image = new Image(new SpriteDrawable(logo));
        batch = new SpriteBatch();

//        loading = new Label("Loading...", skin);

        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();

        progressBarStyle.background = getColoredDrawable(400,40,Color.RED);
        progressBarStyle.knob = getColoredDrawable(0, 40, Color.GREEN);
        progressBarStyle.knobBefore = getColoredDrawable(400,40, Color.GREEN);

        progressBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);
        progressBar.setValue(0f);
        progressBar.setAnimateDuration(2f);
        progressBar.setBounds(0, 0, 400, 40);

        rootTable.add(image).padBottom(30);
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
        viewport.apply();
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        logo.draw(batch);
        batch.end();

        progressBar.setValue(game.getAssets().getManager().getProgress());

        if (game.getAssets().getManager().update() && progressBar.getVisualValue() == 1f) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
