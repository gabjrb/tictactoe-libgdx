package com.tictactoecreator.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tictactoecreator.dialogs.ExitDialog;
import com.tictactoecreator.dialogs.MultiplayerDialog;
import com.tictactoecreator.dialogs.OptionsDialog;
import com.tictactoecreator.dialogs.SingleplayerDialog;
import com.tictactoecreator.game.GameConstants;
import com.tictactoecreator.game.TictactoeGame;


/**
 * Created by Gabriel on 11/11/2017.
 */

public class MainMenuScreen extends BaseScreen {
    private Table rootTable;
    private Table innerTable;
    private Table menuTable;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private I18NBundle i18NBundle;
    private TextButton btnSinglePlayer, btnMultiPlayer, btnOptions, btnAbout;
    private Camera camera;
    private Viewport viewport;
    private Sprite logo;
    private SpriteBatch batch;

    public MainMenuScreen(TictactoeGame game) {
        super(game);
    }

    @Override
    public void show() {
        //
        // Screen configuration
        //
        game.getAdsRequestHandler().showAds(true);
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(GameConstants.WORLD_SIZE.x, GameConstants.WORLD_SIZE.y, camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        //Gdx.input.setInputProcessor(stage);
        //
        // Game styles-resources
        //
        atlas = game.getAssets().getManager().get("ui/tictactoe-game-ui.atlas", TextureAtlas.class);
        skin = game.getAssets().getManager().get("ui/tictactoe-game-ui.json", Skin.class);
        i18NBundle = game.getAssets().getManager().get("resources/messages", I18NBundle.class);
        //
        // Game World
        //
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        //
        // Game UI
        //
        Gdx.input.setCatchBackKey(true);
        btnSinglePlayer = new TextButton(i18NBundle.get("SinglePlayer"), skin, "menu-button");
        btnSinglePlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SingleplayerDialog singleplayerDialog = new SingleplayerDialog(game);
                singleplayerDialog.showDialog(stage);
            }
        });
        btnSinglePlayer.pad(5,5,5,5);
        btnMultiPlayer = new TextButton(i18NBundle.get("MultiPlayer"), skin, "menu-button");
        btnMultiPlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MultiplayerDialog multiplayerDialog = new MultiplayerDialog(game);
                multiplayerDialog.showDialog(stage);
            }
        });
        btnMultiPlayer.pad(5,5,5,5);
        btnOptions = new TextButton(i18NBundle.get("Options"), skin, "menu-button");
        btnOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OptionsDialog optionsDialog = new OptionsDialog(game);
                optionsDialog.showDialog(stage);
            }
        });
        btnOptions.pad(5,5,5,5);
        btnAbout = new TextButton(i18NBundle.get("About"), skin, "menu-button");
        btnAbout.pad(5,5,5,5);
        menuTable = new Table();
        menuTable.add(btnSinglePlayer).width(360).padBottom(20);
        menuTable.row();
        menuTable.add(btnMultiPlayer).width(360).padBottom(20);
        menuTable.row();
        menuTable.add(btnOptions).width(360).padBottom(20);
        //
        // Logo Texture
        //
        Texture texture = new Texture(Gdx.files.internal("resources/tictactoe-logo-512.png"));
        logo = new Sprite(texture);
        Image image = new Image(new SpriteDrawable(logo));
        batch = new SpriteBatch();
        //
        // Game World
        //
        innerTable = new Table();
        innerTable.add().height(50).padBottom(50).row();
        innerTable.add(image).padBottom(50);
        innerTable.row();
        innerTable.add(menuTable);
        rootTable.add(innerTable).expand().top();
        rootTable.setBackground(new TextureRegionDrawable(atlas.findRegion("game-background")));
    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0.5f,0.8f,0.44705883f,1);
        Gdx.gl.glClearColor(1f,1f,1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        logo.draw(batch);
        batch.end();
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.BACK) {
            ExitDialog exitDialog = new ExitDialog(game);
            exitDialog.showDialog(stage);
        }
        return false;
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