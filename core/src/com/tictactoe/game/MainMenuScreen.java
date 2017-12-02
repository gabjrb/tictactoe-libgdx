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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;


/**
 * Created by Gabriel on 11/11/2017.
 */

public class MainMenuScreen extends InputAdapter implements Screen {
    private TictactoeGame game;
    private Stage stage;
    private TextureAtlas atlas;
    private Table table;
    private Skin skin;
    private TextButton buttonPlay, buttonSetting, buttonExit;
    private Label heading;
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
        table = new Table(skin);
        table.setFillParent(true);

        //table.debug();
        stage.addActor(table);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        fontButtonGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Semibold.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter fontButtonParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontButtonParameter.size = (int)Math.ceil(36);

        fontButtonGenerator.scaleForPixelHeight((int)Math.ceil(36));

        NinePatch patch = new NinePatch(atlas.findRegion("button"), 20, 20, 20, 20);

        NinePatchDrawable ninePatchDrawableUP = new NinePatchDrawable(patch);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        //        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = fontButtonGenerator.generateFont(fontButtonParameter);

        buttonStyle.up = ninePatchDrawableUP;//skin.getDrawable("button");
        buttonStyle.down = skin.getDrawable("button-pressed");

        buttonPlay = new TextButton("Play", buttonStyle);
        buttonPlay.setColor(new Color(0,0.35f,1,1));

        //buttonPlay.getLabel().setFontScale(1.5f);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TicTacToeScreen(game));
            }
        });
        buttonSetting = new TextButton("Settings", buttonStyle);
        buttonSetting.setColor(new Color(0,0.35f,1,1));
        //buttonSetting.getLabel().setFontScale(1.5f);
        buttonExit = new TextButton("Exit", buttonStyle);
        buttonExit.setColor(new Color(0,0.35f,1,1));
        //buttonExit.getLabel().setFontScale(1.5f);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(15);
        buttonSetting.pad(15);
        buttonPlay.pad(15);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Bold.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = (int)Math.ceil(96);

        fontGenerator.scaleForPixelHeight((int)Math.ceil(96));

        fontParameter.minFilter = Texture.TextureFilter.Nearest;

        fontParameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;

        Label.LabelStyle styleLbl = new Label.LabelStyle(fontGenerator.generateFont(fontParameter), Color.WHITE);

        heading = new Label("TicTacToe", styleLbl);

                //OpenSans-Light

//        heading = new Label("TicTacToe", skin);
//        heading.setFontScale(3);
        heading.setAlignment(Align.top);
        table.add(heading).height(200).padBottom(30);
        table.row();
        table.add(buttonPlay).width(300).height(100).padBottom(30);
        table.row();
        table.add(buttonSetting).width(300).height(100).padBottom(30);
        table.row();
        table.add(buttonExit).width(300).height(100).padBottom(30);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
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
        table.invalidateHierarchy();
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