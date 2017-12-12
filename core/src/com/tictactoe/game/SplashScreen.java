package com.tictactoe.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tictactoe.tween.SpriteAssessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Gabriel on 11/12/2017.
 */

public class SplashScreen implements Screen {

    private Sprite splash;
    private SpriteBatch batch;
    private TweenManager tweenManager;
    TictactoeGame game;

    public SplashScreen(TictactoeGame game){
        this.game = game;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAssessor());
        Texture logo = new Texture(Gdx.files.internal("logo.png"));
        splash = new Sprite(logo);

        Tween.set(splash, SpriteAssessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAssessor.ALPHA, 2f).target(1).repeatYoyo(1,1f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreen(game));
            }
        }).start(tweenManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batch.begin();
        splash.draw(batch);
        batch.end();
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
