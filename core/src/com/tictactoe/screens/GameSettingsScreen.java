package com.tictactoe.screens;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tictactoe.game.GameConstants;
import com.tictactoe.game.GameSettings;
import com.tictactoe.game.Player;
import com.tictactoe.game.TictactoeGame;

/**
 * Created by Gabriel on 8/12/2017.
 */

public class GameSettingsScreen extends BaseScreen {

    private Skin skin;
    private TextureAtlas atlas;
    private I18NBundle i18NBundle;
    private Stage stage;
    private Table rootTable;
    private Table inner;
    private Table settingsHeader;
    private Table playerDefinitionTable;
    private Table gameModesTable;
    private Table startingDefinition;
    private Camera camera;
    private Viewport viewport;
    private GameSettings settings;
    private TextField txtplayerOneName;
    private TextField txtplayerTwoName;
    private Sprite circleShape;
    private Sprite crossShape;
    private SpriteBatch batch;
    private ImageTextButton backButton;
    private ImageButton optionsButton;
    private SelectBox<GameSettings.ModelType> cboTypePlayerOne;
    private SelectBox<GameSettings.ModelType> cboTypePlayerTwo;
    private SelectBox<Player.PlayerType> cboShapePlayerOne;
    SelectBox<Player.PlayerType> cboShapePlayerTwo;
    SelectBox<GameSettings.GameMod> cboGameMod;
    SelectBox<GameSettings.Difficulty> cboDifficulty;
    SelectBox<Player.PlayerType> cboWhoStarts;
    private CheckBox chkPlayedByRotation;
    private TextButton btnSetAndGO;
    private Label screenNameLabel;
    private HorizontalGroup shapeGroupPlayerOne;
    private HorizontalGroup shapeGroupPlayerTwo;
    private HorizontalGroup typeGroupPlayerOne;
    private HorizontalGroup typeGroupPlayerTwo;

    public GameSettingsScreen(TictactoeGame game){
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
        Gdx.input.setInputProcessor(stage);
        //
        // Game styles-resources
        //
        atlas = game.getAssets().getManager().get("ui/tictactoe-game-ui.atlas", TextureAtlas.class);
        skin = game.getAssets().getManager().get("ui/tictactoe-game-ui.json", Skin.class);
        i18NBundle = game.getAssets().getManager().get("resources/messages", I18NBundle.class);
        //
        // Settings
        //
        this.settings = new GameSettings(i18NBundle);
        //
        // Game World
        //
        rootTable = new Table();
//        rootTable.setDebug(true);
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        //
        // Settings UI Header
        //
        backButton = new ImageTextButton(i18NBundle.get("GoBack"), skin, "back-button");
        optionsButton = new ImageButton(skin, "options");
        optionsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                //Handle options...
            }
        });
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(Color.GRAY);
        pm1.fill();
        screenNameLabel = new Label(i18NBundle.get("GameSettingsScreenName"), skin, "screen-name");
        screenNameLabel.setAlignment(Align.center);
        settingsHeader = new Table();
        settingsHeader.add(backButton).width(160);
        settingsHeader.add(screenNameLabel).width(380);
        settingsHeader.add(optionsButton).width(100);
        settingsHeader.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
        //
        // Player definition
        //
        txtplayerOneName = new TextField(settings.getPlayerOneName(),skin);
        txtplayerTwoName = new TextField(settings.getPlayerTwoName(),skin);
        cboShapePlayerOne = new SelectBox<Player.PlayerType>(skin);
        cboShapePlayerOne.setItems(new Player.PlayerType[] {Player.PlayerType.PLAYER_TYPE_X, Player.PlayerType.PLAYER_TYPE_O});
        cboShapePlayerTwo = new SelectBox<Player.PlayerType>(skin);
        cboShapePlayerTwo.setItems(new Player.PlayerType[] {Player.PlayerType.PLAYER_TYPE_X, Player.PlayerType.PLAYER_TYPE_O});
        cboTypePlayerOne = new SelectBox<GameSettings.ModelType>(skin);
        cboTypePlayerOne.setItems(new GameSettings.ModelType[] {GameSettings.ModelType.HUMAN, GameSettings.ModelType.COMPUTER});
        cboTypePlayerTwo = new SelectBox<GameSettings.ModelType>(skin);
        cboTypePlayerTwo.setItems(new GameSettings.ModelType[] {GameSettings.ModelType.HUMAN, GameSettings.ModelType.COMPUTER});

        shapeGroupPlayerOne = new HorizontalGroup();
        shapeGroupPlayerOne.addActor(new Label("Shape", skin));
        shapeGroupPlayerOne.addActor(cboShapePlayerOne);
        shapeGroupPlayerTwo = new HorizontalGroup();
        shapeGroupPlayerTwo.addActor(new Label("Shape", skin));
        shapeGroupPlayerTwo.addActor(cboShapePlayerTwo);

        typeGroupPlayerOne = new HorizontalGroup();
        typeGroupPlayerOne.addActor(new Label("Type", skin));
        typeGroupPlayerOne.addActor(cboTypePlayerOne);

        typeGroupPlayerTwo = new HorizontalGroup();
        typeGroupPlayerTwo.addActor(new Label("Type", skin));
        typeGroupPlayerTwo.addActor(cboTypePlayerTwo);

        playerDefinitionTable = new Table();
//        playerDefinitionTable.setDebug(true);
        playerDefinitionTable.add(new Label("1. Player", skin));
        playerDefinitionTable.add(txtplayerOneName);
        playerDefinitionTable.add(shapeGroupPlayerOne);
        playerDefinitionTable.add(typeGroupPlayerOne);

        playerDefinitionTable.row();
        playerDefinitionTable.add().height(40);
        playerDefinitionTable.row();

        playerDefinitionTable.add(new Label("2. Player", skin));
        playerDefinitionTable.add(txtplayerTwoName);
        playerDefinitionTable.add(shapeGroupPlayerTwo);
        playerDefinitionTable.add(typeGroupPlayerTwo);
        //
        // Game modes
        //
        cboGameMod = new SelectBox<GameSettings.GameMod>(skin);
        cboGameMod.setItems(new GameSettings.GameMod[] {GameSettings.GameMod.NORMAL, GameSettings.GameMod.EXPIRING_MOVES} );
        cboDifficulty = new SelectBox<GameSettings.Difficulty>(skin);
        cboDifficulty.setItems(new GameSettings.Difficulty[] {GameSettings.Difficulty.EASY, GameSettings.Difficulty.MEDIUM,
                GameSettings.Difficulty.HARD});
        gameModesTable = new Table();
        gameModesTable.add(new Label("Game Mod", skin));
        gameModesTable.add(cboGameMod);
        gameModesTable.row();
        gameModesTable.add(new Label("Difficulty", skin));
        gameModesTable.add(cboDifficulty);
        //
        // Starting definition
        //
        cboWhoStarts = new SelectBox<Player.PlayerType>(skin);
        cboWhoStarts.setItems(new Player.PlayerType[] {Player.PlayerType.PLAYER_TYPE_X, Player.PlayerType.PLAYER_TYPE_O});
        chkPlayedByRotation = new CheckBox(null, skin);
        chkPlayedByRotation.setChecked(false);
        startingDefinition = new Table();
        startingDefinition.add(new Label("Who starts?", skin));
        startingDefinition.add(cboWhoStarts);
        startingDefinition.add(new Label("By Rotation?", skin));
        startingDefinition.add(chkPlayedByRotation);
        //
        // Set and GO Button
        //
        btnSetAndGO = new TextButton("Set and GO!", skin);
        btnSetAndGO.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TicTacToeScreen(game, settings));
            }
        });
        //
        // Game World
        //
        float myscreenHeight = viewport.getWorldHeight()/4;
        inner = new Table();
        inner.add(settingsHeader).height(myscreenHeight - 130.0f);
        inner.row();
        inner.add(new Label("Players", skin)).height(100);
        inner.row();
        inner.add(playerDefinitionTable);
        inner.row();
        inner.add(new Label("Game", skin)).height(100);
        inner.row();
        inner.add(gameModesTable);
        inner.row();
        inner.add(startingDefinition);
        inner.row();
        inner.add(btnSetAndGO);
        rootTable.add(inner).expand().top();
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
        skin.dispose();
        atlas.dispose();
    }
}
