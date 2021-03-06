package com.tictactoecreator.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tictactoecreator.game.GameConstants;
import com.tictactoecreator.game.TictactoeGame;
import com.tictactoecreator.tween.SpriteAssessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Gabriel on 11/12/2017.
 */

public class SplashScreen extends BaseScreen {

    private Sprite splash;
    private SpriteBatch batch;
    private TweenManager tweenManager;
    private Table rootTable;
    private ExtendViewport viewport;
    private Stage stage;
    private Camera camera;

    public SplashScreen(TictactoeGame game){
        super(game);
    }

    @Override
    public void show() {
        //
        // Screen configuration
        //
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(GameConstants.WORLD_SIZE.x, GameConstants.WORLD_SIZE.y, camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(rootTable);

        Texture logo = new Texture(Gdx.files.internal("resources/tictactoe-logo-256.png"));
        splash = new Sprite(logo);
        Image image = new Image(new SpriteDrawable(splash));
        rootTable.add(image);

        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAssessor());
        Tween.set(splash, SpriteAssessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAssessor.ALPHA, 2f).target(1).repeatYoyo(1,1f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new LoadingScreen(game));
            }
        }).start(tweenManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        tweenManager.update(delta);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        splash.draw(batch);
        batch.end();
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
        batch.dispose();
    }
}