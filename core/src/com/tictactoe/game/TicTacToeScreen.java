package com.tictactoe.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;

/**
 * Created by Gabriel on 17/11/2017.
 */

public class TicTacToeScreen extends InputAdapter implements Screen {

    TictactoeGame game;
    private Camera camera;
    private Viewport viewport;
    
    private Viewport screenViewport;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private ShapeRenderer renderer;
    private SpriteBatch spriteBatch;
    private TicTacToeBoard board;
    private Label scorePlayerOneText;
    private Label scorePlayerTwoText;
    private Label scoreTiesText;
    private Label scorePlayerOneValue;
    private Label scorePlayerTwoValue;
    private Label scoreTiesValue;
    private Label lblTimePassedCount;
    private Label lblTimePassedText;
    private Label lblGameMessages;
    private Label lblGameBoxAdvertising;
    private ImageTextButton backButton;
    private ImageButton pauseButton, restartButton;
    private Table rootTable;
    private Table boardHeader;
    private Table boardScore;
    private VerticalGroup timePassed;
    private VerticalGroup gameScorePlayerOne;
    private VerticalGroup gameScoreTies;
    private VerticalGroup gameScorePlayerTwo;


    public TicTacToeScreen(TictactoeGame game) {
        this.game = game;
    }
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(14,14,camera);
        screenViewport = new ScreenViewport();
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screenViewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(screenViewport);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(rootTable);
        //rootTable.setDebug(true);

        atlas = new TextureAtlas("ui/TicTacToe.atlas");
        skin = new Skin(Gdx.files.internal("ui/tictactoe-ui.json"), atlas);
        spriteBatch = new SpriteBatch();
        board = new TicTacToeBoard();
        renderer = new ShapeRenderer();





        ImageTextButton.ImageTextButtonStyle imageTextButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        imageTextButtonStyle.imageUp = skin.getDrawable("back");
        imageTextButtonStyle.font = GameConstants.getBitmapFont(GameConstants.FontType.Regular, 36);


        backButton = new ImageTextButton("Back", imageTextButtonStyle);
        pauseButton = new ImageButton(skin.getDrawable("pause-game"));
        restartButton = new ImageButton(skin.getDrawable("restart-game"));
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                board.reset();
            }
        });
        backButton.setWidth(75);
        backButton.setHeight(75);
        backButton.getImageCell().width(75);
        backButton.getImageCell().height(75);
        backButton.getImage().setScaling(Scaling.stretch);
        pauseButton.setWidth(75);
        pauseButton.setHeight(75);
        pauseButton.getImageCell().width(75);
        pauseButton.getImageCell().height(75);
        pauseButton.getImage().setScaling(Scaling.stretch);
        restartButton.setWidth(75);
        restartButton.setHeight(75);
        restartButton.getImageCell().width(75);
        restartButton.getImageCell().height(75);
        restartButton.getImage().setScaling(Scaling.stretch);




        lblTimePassedCount = GameConstants.CreateLabel("1:23", GameConstants.FontType.Regular, 48);
        lblGameMessages = GameConstants.CreateLabel("Thinking", GameConstants.FontType.Regular, 48);
        lblGameBoxAdvertising = GameConstants.CreateLabel("Box for advertising", GameConstants.FontType.Bold, 24);
        lblTimePassedText = GameConstants.CreateLabel("TIME PASSED", GameConstants.FontType.Bold, 24);


        scorePlayerOneText = GameConstants.CreateLabel("Computer", GameConstants.FontType.Bold, 28);
        scorePlayerTwoText = GameConstants.CreateLabel("Gabriel", GameConstants.FontType.Bold, 28);
        scoreTiesText = GameConstants.CreateLabel("Ties", GameConstants.FontType.Bold, 28);

        scorePlayerOneValue = GameConstants.CreateLabel("1", GameConstants.FontType.Regular, 48);
        scoreTiesValue = GameConstants.CreateLabel("2", GameConstants.FontType.Regular, 48);
        scorePlayerTwoValue = GameConstants.CreateLabel("3", GameConstants.FontType.Regular, 48);


        timePassed = new VerticalGroup();
        timePassed.addActor(lblTimePassedCount);
        timePassed.addActor(lblTimePassedText);



        boardHeader = new Table();

        boardHeader.add(backButton).width(150);
        boardHeader.add(timePassed).expand();
        boardHeader.add(pauseButton).width(100);
        boardHeader.add(restartButton).width(100);

        gameScorePlayerOne = new VerticalGroup();
        gameScorePlayerOne.addActor(scorePlayerOneText);
        gameScorePlayerOne.addActor(scorePlayerOneValue);

        gameScorePlayerTwo = new VerticalGroup();
        gameScorePlayerTwo.addActor(scorePlayerTwoText);
        gameScorePlayerTwo.addActor(scorePlayerTwoValue);

        gameScoreTies = new VerticalGroup();
        gameScoreTies.addActor(scoreTiesText);
        gameScoreTies.addActor(scoreTiesValue);

        boardScore = new Table();
        //boardScore.setDebug(true);
        boardScore.add(gameScorePlayerOne).width(200);
        boardScore.add(gameScoreTies).expand();
        boardScore.add(gameScorePlayerTwo).width(200);


        rootTable.add(boardHeader).width(screenViewport.getScreenWidth()).height(200);
        rootTable.row();
        rootTable.add(lblGameMessages).height(100);
        rootTable.row();
        rootTable.add().expand();
        rootTable.row();
        rootTable.add(boardScore).width(screenViewport.getScreenWidth()).height(150);
        rootTable.row();
        rootTable.add(lblGameBoxAdvertising).height(150);
        board.handleTouch(new Vector2(GameConstants.HorizontalShift, GameConstants.Elevation), "circle");
        board.handleTouch(new Vector2(4 + GameConstants.HorizontalShift, GameConstants.Elevation), "cross");
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();

        stage.act(delta);
        stage.draw();

//        // Draw shapes
//        spriteBatch.setProjectionMatrix(camera.combined);
//        spriteBatch.begin();

//        spriteBatch.end();
//
//        // Draw board delimiters
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        renderer.rectLine(4 + GameConstants.HorizontalShift,0 + GameConstants.Elevation,4 + GameConstants.HorizontalShift,12 + GameConstants.Elevation, 0.075f);
        renderer.rectLine(8 + GameConstants.HorizontalShift,0 + GameConstants.Elevation,8 + GameConstants.HorizontalShift,12 + GameConstants.Elevation, 0.075f);
        renderer.rectLine(0 + GameConstants.HorizontalShift,8 + GameConstants.Elevation,12 + GameConstants.HorizontalShift,8 + GameConstants.Elevation, 0.075f);
        renderer.rectLine(0 + GameConstants.HorizontalShift,4 + GameConstants.Elevation,12 + GameConstants.HorizontalShift,4 + GameConstants.Elevation, 0.075f);

        board.renderPieces(delta, renderer);

        renderer.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        Vector2 boardGridVector = getCellOrigin(worldTouch);

        if (boardGridVector.x != 0 && boardGridVector.y !=0){
            board.handleTouch(boardGridVector, "circle");
        }
        return true;
    }

    private Vector2 getCellOrigin(Vector2 v){

        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3 ; j++) {
                if (v.x >= GameConstants.HorizontalShift + (i*4) && v.x <= (GameConstants.HorizontalShift + GameConstants.CELL_SIZE.x + (i*4)) && v.y >= GameConstants.Elevation + (j*4) && v.y <= (GameConstants.Elevation + GameConstants.CELL_SIZE.y + (j*4))){
                    return new Vector2(GameConstants.HorizontalShift + (i*4), GameConstants.Elevation + (j*4));
                }
            }
        }

        return new Vector2(0,0);
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
        renderer.dispose();
        spriteBatch.dispose();
    }
}
