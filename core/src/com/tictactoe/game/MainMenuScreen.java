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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
    private TextButton btnPlayOne, btnPlayTwo, btnOptions, btnExit;
    private ImageButton btnShare, btnRate;
    private Label lblTitle;
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

        fontButtonGenerator.scaleForPixelHeight((int)Math.ceil(36));

        NinePatch patch = new NinePatch(atlas.findRegion("button"), 20, 20, 20, 20);

        NinePatchDrawable ninePatchDrawableUP = new NinePatchDrawable(patch);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = fontButtonGenerator.generateFont(fontButtonParameter);
        buttonStyle.up = ninePatchDrawableUP;
        buttonStyle.down = skin.getDrawable("button-pressed");

        //
        // One Player
        //
        btnPlayOne = new TextButton("One Player", buttonStyle);
        btnPlayOne.setColor(new Color(0,0.35f,1,1));
        btnPlayOne.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TicTacToeScreen(game));
            }
        });
        //
        // Two Player
        //
        btnPlayTwo = new TextButton("Two Player", buttonStyle);
        btnPlayTwo.setColor(new Color(0,0.35f,1,1));
        btnPlayTwo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TicTacToeScreen(game));
            }
        });
        //
        // Options
        //
        btnOptions = new TextButton("Options", buttonStyle);
        btnOptions.setColor(new Color(0,0.35f,1,1));
        //
        // Exit
        //
        btnExit = new TextButton("Exit", buttonStyle);
        btnExit.setColor(new Color(0,0.35f,1,1));
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        btnPlayOne.pad(15);
        btnPlayTwo.pad(15);
        btnOptions.pad(15);
        btnExit.pad(15);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Bold.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = (int)Math.ceil(96);

        fontGenerator.scaleForPixelHeight((int)Math.ceil(96));

        fontParameter.minFilter = Texture.TextureFilter.Nearest;

        fontParameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;

        Label.LabelStyle styleLbl = new Label.LabelStyle(fontGenerator.generateFont(fontParameter), Color.BLACK);
        lblTitle = new Label("TicTacToe", styleLbl);


        menuTable = new Table();
        menuTable.add(btnPlayOne).height(100).width(300).padBottom(30);
        menuTable.row();
        menuTable.add(btnPlayTwo).height(100).width(300).padBottom(30);
        menuTable.row();
        menuTable.add(btnOptions).height(100).width(300).padBottom(30);
        menuTable.row();
        menuTable.add(btnExit).height(100).width(300);

        footerButtonsTable = new Table();

        btnRate = new ImageButton(skin.getDrawable("rate-game"));
        btnShare = new ImageButton(skin.getDrawable("share-game"));

        btnRate.setWidth(75);
        btnRate.setHeight(75);
        btnRate.getImageCell().width(75);
        btnRate.getImageCell().height(75);
        btnRate.getImage().setScaling(Scaling.stretch);

        btnShare.setWidth(75);
        btnShare.setHeight(75);
        btnShare.getImageCell().width(75);
        btnShare.getImageCell().height(75);
        btnShare.getImage().setScaling(Scaling.stretch);

        footerButtonsTable.add(btnShare).expand();
        footerButtonsTable.add().width(150);
        footerButtonsTable.add(btnRate).expand();

        rootTable.add(lblTitle).height(200);
        rootTable.row();
        rootTable.add(menuTable).expand();
        rootTable.row();
        rootTable.add(footerButtonsTable);
        rootTable.row();
        rootTable.add().height(150);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        stage.getViewport().apply();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height,true);
//        table.invalidateHierarchy();
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