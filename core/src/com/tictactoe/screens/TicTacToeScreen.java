package com.tictactoe.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tictactoe.game.AIPlayer;
import com.tictactoe.game.GameConstants;
import com.tictactoe.game.GameSettings;
import com.tictactoe.game.IActivityRequestHandler;
import com.tictactoe.game.MinimaxStrategy;
import com.tictactoe.game.Player;
import com.tictactoe.game.TicTacToeBoard;
import com.tictactoe.game.TictactoeGame;

/**
 * Created by Gabriel on 17/11/2017.
 */

public class TicTacToeScreen extends BaseScreen {
    private Camera cameraGame;
    private Camera cameraWorld;
    private Viewport viewport;
    private Viewport screenViewport;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private ShapeRenderer renderer;
    private SpriteBatch spriteBatch;
    private TicTacToeBoard board;
    private ImageTextButton backButton;
    private ImageButton optionsButton, restartButton;
    private Table rootTable;
    private Table boardHeader;
    private Table boardScore;
    private Table inner;
    private GameSettings settings;
    private Player playerOne;
    private Player playerTwo;
    private Sprite mySprite;

    public TicTacToeScreen(TictactoeGame game, GameSettings settings) {
        super(game);
        this.game = game;
        this.settings = settings;
    }

    @Override
    public void show() {
        cameraGame = new OrthographicCamera();
        cameraWorld = new OrthographicCamera();
        viewport = new FitViewport(14, 14, cameraGame);
        screenViewport = new ExtendViewport(GameConstants.WORLD_SIZE.x, GameConstants.WORLD_SIZE.y, cameraWorld);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screenViewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(screenViewport);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        atlas = game.getAssets().getManager().get("ui/TicTacToe.atlas", TextureAtlas.class);
        skin = game.getAssets().getManager().get("ui/tictactoe-ui.json", Skin.class);
        spriteBatch = new SpriteBatch();

        mySprite = new Sprite();

        mySprite.setRegion(atlas.findRegion("cross-cell"));
        mySprite.setSize(4,4);
        mySprite.setPosition(1,5);

        renderer = new ShapeRenderer();

        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        backButton = new ImageTextButton("Back", skin, "back-button");
        optionsButton = new ImageButton(skin);
        restartButton = new ImageButton(skin);

        boardHeader = new Table();
        boardHeader.add(new ImageButton(skin, "back")).width(160);
        boardHeader.add(backButton).width(160);
//        boardHeader.add(new TextButton("My-Game",skin)).width(160);
        boardHeader.add(new ImageButton(skin, "restart")).width(160);
        boardHeader.add(new ImageButton(skin, "options")).width(160);

        inner = new Table();
        inner.setDebug(true);

        float myscreenHeight = screenViewport.getWorldHeight()/4;

        inner.add(boardHeader).height(myscreenHeight - 100.0f);
        inner.row();
        inner.add().height(100.f);
        inner.row();
        inner.add().height(myscreenHeight*2);
        inner.row();
        inner.add(new ImageButton(skin)).height(myscreenHeight - 100);
        inner.row();
        inner.add().height(100.0f);

        rootTable.add(inner).expand().top();

    }

    private void gamePlayInitializer() {
        board = new TicTacToeBoard();
        switch (settings.getModelTypePlayerOne()) {
            case HUMAN:
                playerOne = new Player(settings.getPlayerOneName(), settings.getPlayerTypeOne(), board, settings.getPlayerOneHasToStart());
            case COMPUTER:
                playerOne = new AIPlayer(settings.getPlayerOneName(), settings.getPlayerTypeOne(), board, settings.getPlayerOneHasToStart(), new MinimaxStrategy());
        }
        switch (settings.getModelTypePlayerTwo()) {
            case HUMAN:
                playerTwo = new Player(settings.getPlayerTwoName(), settings.getPlayerTypeTwo(), board, settings.getPlayerTwoHasToStart());
            case COMPUTER:
                playerTwo = new AIPlayer(settings.getPlayerTwoName(), settings.getPlayerTypeTwo(), board, settings.getPlayerTwoHasToStart(), new MinimaxStrategy());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screenViewport.apply();
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
        float asdas = screenViewport.getWorldHeight();
        float sjada = screenViewport.getWorldWidth();
        viewport.apply();
        renderer.setProjectionMatrix(cameraGame.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        renderer.rectLine(4 + GameConstants.HorizontalShift, 0 + GameConstants.Elevation, 4 + GameConstants.HorizontalShift, 12 + GameConstants.Elevation, 0.075f);
        renderer.rectLine(8 + GameConstants.HorizontalShift, 0 + GameConstants.Elevation, 8 + GameConstants.HorizontalShift, 12 + GameConstants.Elevation, 0.075f);
        renderer.rectLine(0 + GameConstants.HorizontalShift, 8 + GameConstants.Elevation, 12 + GameConstants.HorizontalShift, 8 + GameConstants.Elevation, 0.075f);
        renderer.rectLine(0 + GameConstants.HorizontalShift, 4 + GameConstants.Elevation, 12 + GameConstants.HorizontalShift, 4 + GameConstants.Elevation, 0.075f);
        //board.renderPieces(delta, renderer);
        renderer.end();

        spriteBatch.setProjectionMatrix(cameraGame.combined);
        spriteBatch.begin();
        mySprite.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        Vector2 boardGridVector = getCellOrigin(worldTouch);

        return true;
    }

    private Vector2 getCellOrigin(Vector2 v) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (v.x >= GameConstants.HorizontalShift + (i * 4) && v.x <= (GameConstants.HorizontalShift + GameConstants.CELL_SIZE.x + (i * 4)) && v.y >= GameConstants.Elevation + (j * 4) && v.y <= (GameConstants.Elevation + GameConstants.CELL_SIZE.y + (j * 4))) {
                    return new Vector2(GameConstants.HorizontalShift + (i * 4), GameConstants.Elevation + (j * 4));
                }
            }
        }
        return new Vector2(0, 0);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        screenViewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
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
        renderer.dispose();
        spriteBatch.dispose();
    }
}