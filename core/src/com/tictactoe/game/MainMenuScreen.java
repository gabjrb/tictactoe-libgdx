package com.tictactoe.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tictactoe.assets.Assets;

import javax.swing.text.View;


/**
 * Created by Gabriel on 11/11/2017.
 */

public class MainMenuScreen extends InputAdapter implements Screen {
    TictactoeGame game;
    private Stage stage;
    private TextureAtlas atlas;
    private Table rootTable;
    private Table menuTable;
    private Table footerButtonsTable;
    private Skin skin;
    private ImageTextButton btnOptions, btnShare, btnAbout;
    private ImageButton btnPlay;
    private Label lblTitle;
    private Label lblGameBoxAdvertising;
    private FreeTypeFontGenerator fontGenerator;
    private Camera camera;
    private Viewport viewport;
    private FreeTypeFontGenerator fontButtonGenerator;

    public MainMenuScreen(TictactoeGame game) {
        this.game = game;
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();

        viewport = new ExtendViewport(GameConstants.WORLD_SIZE.x, GameConstants.WORLD_SIZE.y, camera);

        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/TicTacToe.atlas");
        skin = new Skin(Gdx.files.internal("ui/tictactoe-ui.json"), atlas);
        rootTable = new Table();
        rootTable.setDebug(true);
        rootTable.setFillParent(true);
        rootTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(rootTable);

        fontButtonGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Semibold.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter fontButtonParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontButtonParameter.size = (int)Math.ceil(36);

        fontButtonParameter.minFilter = Texture.TextureFilter.Linear;

        fontButtonParameter.magFilter = Texture.TextureFilter.Linear;

        fontButtonGenerator.scaleForPixelHeight((int)Math.ceil(36));

//        BitmapFont font = fontButtonGenerator.generateFont(fontButtonParameter);

        //
        // Button PLay
        //
        btnPlay = new ImageButton(skin.getDrawable("play"));
        btnPlay.getImageCell().height(256);
        btnPlay.getImageCell().width(256);

        ImageTextButton.ImageTextButtonStyle imgTextButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        imgTextButtonStyle.up = skin.getDrawable("button");
        imgTextButtonStyle.down = skin.newDrawable("round-green");
        imgTextButtonStyle.imageUp = skin.newDrawable("options-white", Color.WHITE);
        imgTextButtonStyle.font = game.assets.manager.get("font/OpenSans-Regular.ttf", BitmapFont.class);

        ImageTextButton.ImageTextButtonStyle imgTextButtonStyle2 = new ImageTextButton.ImageTextButtonStyle();
        imgTextButtonStyle2.up = skin.getDrawable("button");
        imgTextButtonStyle2.down = skin.newDrawable("round-green");
        imgTextButtonStyle2.imageUp = skin.newDrawable("share-white", Color.WHITE);
        imgTextButtonStyle2.font = game.assets.manager.get("font/OpenSans-Regular.ttf", BitmapFont.class);

        ImageTextButton.ImageTextButtonStyle imgTextButtonStyle3 = new ImageTextButton.ImageTextButtonStyle();
        imgTextButtonStyle3.up = skin.getDrawable("button");
        imgTextButtonStyle3.down = skin.newDrawable("round-green");
        imgTextButtonStyle3.imageUp = skin.newDrawable("about-white", Color.WHITE);
        imgTextButtonStyle3.font = game.assets.manager.get("font/OpenSans-Regular.ttf", BitmapFont.class);

        //
        // Button Options
        //
        btnOptions = new ImageTextButton("Options", imgTextButtonStyle);
        btnOptions.getImageCell().width(64);
        btnOptions.getImageCell().height(64);
        btnOptions.getLabelCell().width(200).align(Align.right);
//        btnOptions.getStyle().down.tint(Color.WHITE);
        btnOptions.setColor(new Color(0,0.35f,1,1));

        btnOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TicTacToeScreen(game));
            }
        });
        //
        // Button Share
        //
        btnShare = new ImageTextButton("Share", imgTextButtonStyle2);
        btnShare.getImageCell().width(64);
        btnShare.getImageCell().height(64);
        btnShare.getLabelCell().width(200).align(Align.right);
        btnShare.setColor(new Color(0,0.35f,1,1));
        btnShare.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TicTacToeScreen(game));
            }
        });
        //
        // Button Share
        //
        btnAbout = new ImageTextButton("About", imgTextButtonStyle3);
//        btnAbout.getStyle().imageUp = skin.getDrawable("about-white");
        btnAbout.getImageCell().width(64);
        btnAbout.getImageCell().height(64);
        btnAbout.getLabelCell().width(200).align(Align.right);
        btnAbout.setColor(new Color(0,0.35f,1,1));
        btnAbout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TicTacToeScreen(game));
            }
        });

        btnPlay.pad(15);
        btnOptions.pad(15);
        btnShare.pad(15);
        btnAbout.pad(15);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Bold.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = (int)Math.ceil(96);

        fontGenerator.scaleForPixelHeight((int)Math.ceil(96));

        fontParameter.minFilter = Texture.TextureFilter.Linear;

        fontParameter.magFilter = Texture.TextureFilter.Linear;

        Label.LabelStyle styleLbl = new Label.LabelStyle(game.assets.manager.get("font/OpenSans-Regular.ttf", BitmapFont.class), Color.BLACK);
        lblTitle = new Label("TicTacToe", styleLbl);

        fontParameter.size = (int)Math.ceil(24);

        fontGenerator.scaleForPixelHeight((int)Math.ceil(24));
        styleLbl = new Label.LabelStyle(game.assets.manager.get("font/OpenSans-Regular.ttf", BitmapFont.class), Color.BLACK);
        lblGameBoxAdvertising = new Label("Box for advertising", styleLbl);


        menuTable = new Table();
        menuTable.add(btnPlay).height(256).width(256).padBottom(45);
        menuTable.row();
        menuTable.add(btnOptions).height(100).width(300).padBottom(30);
        menuTable.row();
        menuTable.add(btnShare).height(100).width(300).padBottom(30);
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); viewport.apply();
        stage.getViewport().apply(); stage.act(delta); stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height,true);
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
        fontGenerator.dispose();
        fontButtonGenerator.dispose();
    }
}