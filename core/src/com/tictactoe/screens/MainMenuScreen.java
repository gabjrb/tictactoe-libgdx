package com.tictactoe.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tictactoe.assets.Assets;
import com.tictactoe.dialogs.OptionsDialog;
import com.tictactoe.game.TictactoeGame;
import com.tictactoe.screens.GameSettingsScreen;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.text.View;


/**
 * Created by Gabriel on 11/11/2017.
 */

public class MainMenuScreen extends InputAdapter implements Screen {
    TictactoeGame game;
    private Table rootTable;
    private Table menuTable;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private ImageTextButton btnOptions, btnShare, btnAbout;
    private ImageButton btnPlay;
    private Label lblTitle;
    private Label lblGameBoxAdvertising;
    private Camera camera;
    private Viewport viewport;

    public MainMenuScreen(TictactoeGame game) {
        this.game = game;
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();

        I18NBundle i18NBundle = game.getAssets().getManager().get("resources/messages", I18NBundle.class);

//        viewport = new ExtendViewport(1920, 1080, camera);
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
//        viewport = new ExtendViewport(GameConstants.WORLD_SIZE.x, GameConstants.WORLD_SIZE.y, camera);

        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        atlas = game.getAssets().getManager().get("ui/TicTacToe.atlas", TextureAtlas.class);
        skin = game.getAssets().getManager().get("ui/tictactoe-ui.json", Skin.class);
        rootTable = new Table();
        rootTable.setDebug(true);
        rootTable.setFillParent(true);
        rootTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(rootTable);
        //
        // Button PLay
        //
        btnPlay = new ImageButton(skin.getDrawable("play"));
        btnPlay.getImageCell().height(256);
        btnPlay.getImageCell().width(256);
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new GameSettingsScreen(game, skin, atlas));
            }
        });
        //
        // Button Options
        //
        btnOptions = new ImageTextButton("Options", skin);
        btnOptions.getImageCell().width(64);
        btnOptions.getImageCell().height(64);
        btnOptions.getLabelCell().width(200).align(Align.right);
        btnOptions.setColor(new Color(0,0.35f,1,1));
        btnOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OptionsDialog optionsDialog = new OptionsDialog();
                optionsDialog.show(stage);
            }
        });

        btnShare = new ImageTextButton(i18NBundle.get("Share"), skin);
        btnShare.getImageCell().width(64);
        btnShare.getImageCell().height(64);
        btnShare.getLabelCell().width(200).align(Align.right);
        btnShare.setColor(new Color(0,0.35f,1,1));
        btnShare.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new TicTacToeScreen(game));
            }
        });

        btnAbout = new ImageTextButton(i18NBundle.get("About"), skin);
        btnAbout.getImageCell().width(64);
        btnAbout.getImageCell().height(64);
        btnAbout.getLabelCell().width(200).align(Align.right);
        btnAbout.setColor(new Color(0,0.35f,1,1));
        btnAbout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new GameSettingsScreen(game, skin, atlas));
            }
        });

        btnPlay.pad(15);
        btnOptions.pad(15);
        btnShare.pad(15);

        menuTable = new Table();
        menuTable.add(btnPlay).height(256).width(256).padBottom(45);
        menuTable.row();
        menuTable.add(btnOptions).height(100).width(300).padBottom(30);
        menuTable.row();
        menuTable.add(btnShare).padBottom(30);
        menuTable.row();
        menuTable.add(btnAbout).height(100).width(300);

        rootTable.add(lblTitle).height(200);
        rootTable.row();
        rootTable.add(menuTable).expand();
        rootTable.row();
        rootTable.add(lblGameBoxAdvertising).height(150);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        stage.act(delta);
        stage.draw();
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