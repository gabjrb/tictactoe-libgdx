package com.tictactoe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tictactoe.game.AIPlayer;
import com.tictactoe.game.GameConstants;
import com.tictactoe.game.GameSettings;
import com.tictactoe.game.MinimaxStrategy;
import com.tictactoe.game.Player;
import com.tictactoe.game.ShapeData;
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
    private I18NBundle i18NBundle;
    private ShapeRenderer renderer;
    private SpriteBatch spriteBatch;
    private ImageTextButton backButton;
    private ImageButton optionsButton, restartButton;
    private Table rootTable;
    private Table boardHeader;
    private Table boardScore;
    private Table inner;
    private VerticalGroup playerOneGroup;
    private VerticalGroup playerTwoGroup;
    private Label gameScore;
    private Sprite[][] sprites;
    private gameHandler gamePlayHandler;

    public TicTacToeScreen(TictactoeGame game, GameSettings settings) {
        super(game);
        this.game = game;
        this.gamePlayHandler = new gameHandler(settings);
    }

    private class gameHandler{
        private TicTacToeBoard board;
        private Player playerOne;
        private Player playerTwo;
        private Boolean turn;
        private GameSettings settings;

        public gameHandler(GameSettings settings){
            this.settings = settings;
            gamePlayInitializer();
        }

        public void handleTouch(Vector2 v){
            if(turn && board.getCellOrigin(v)){
                if (settings.getModelTypePlayerOne() == GameSettings.ModelType.HUMAN
                        && settings.getModelTypePlayerTwo() == GameSettings.ModelType.COMPUTER){
                    board.setCell(v, playerOne.getPlayerType().getBrand());
                    board.setCell(((AIPlayer)playerTwo).makeAIMove(), playerTwo.getPlayerType().getBrand());
                    // Make delay
                }
                else if (settings.getModelTypePlayerOne() == GameSettings.ModelType.HUMAN
                        && settings.getModelTypePlayerTwo() == GameSettings.ModelType.HUMAN){
                    board.setCell(v, playerOne.getPlayerType().getBrand());
                    turn = !turn;
                }
            }
            else if (board.getCellOrigin(v)){
                if (settings.getModelTypePlayerTwo() == GameSettings.ModelType.HUMAN
                        && settings.getModelTypePlayerOne() == GameSettings.ModelType.COMPUTER){
                    board.setCell(v, playerTwo.getPlayerType().getBrand());
                    board.setCell(((AIPlayer)playerOne).makeAIMove(), playerOne.getPlayerType().getBrand());
                    // Make delay
                }
                else if (settings.getModelTypePlayerTwo() == GameSettings.ModelType.HUMAN
                        && settings.getModelTypePlayerOne() == GameSettings.ModelType.HUMAN){
                    board.setCell(v, playerTwo.getPlayerType().getBrand());
                    turn = !turn;
                }
            }
        }

        public void gamePlayInitializer(){
            this.board = new TicTacToeBoard();
            this.turn = settings.getPlayerOneHasToStart();
            switch (settings.getModelTypePlayerOne()) {
                case HUMAN:
                    playerOne = new Player(settings.getPlayerOneName(), settings.getPlayerTypeOne(), board, settings.getPlayerOneHasToStart());
                    break;
                case COMPUTER:
                    playerOne = new AIPlayer(settings.getPlayerOneName(), settings.getPlayerTypeOne(), board, settings.getPlayerOneHasToStart(), new MinimaxStrategy());
                    break;
            }
            switch (settings.getModelTypePlayerTwo()) {
                case HUMAN:
                    playerTwo = new Player(settings.getPlayerTwoName(), settings.getPlayerTypeTwo(), board, settings.getPlayerTwoHasToStart());
                    break;
                case COMPUTER:
                    playerTwo = new AIPlayer(settings.getPlayerTwoName(), settings.getPlayerTypeTwo(), board, settings.getPlayerTwoHasToStart(), new MinimaxStrategy());
                    break;
            }
        }

        public TicTacToeBoard getBoard() {
            return board;
        }

        public GameSettings.Difficulty getDifficulty(){
            return settings.getDifficulty();
        }

        public Player getPlayerOne() {
            return playerOne;
        }

        public Player getPlayerTwo() {
            return playerTwo;
        }
    }

    private void drawBoard(ShapeRenderer renderer){
        renderer.setColor(Color.WHITE);
        renderer.rectLine(GameConstants.CELL_SIZE.x + GameConstants.HorizontalShift,
                0 + GameConstants.Elevation,
                GameConstants.CELL_SIZE.x + GameConstants.HorizontalShift,
                GameConstants.BOARD_SIZE.y + GameConstants.Elevation, 0.075f);
        renderer.rectLine((2*GameConstants.CELL_SIZE.x) + GameConstants.HorizontalShift,
                0 + GameConstants.Elevation,
                (2*GameConstants.CELL_SIZE.x) + GameConstants.HorizontalShift,
                GameConstants.BOARD_SIZE.y + GameConstants.Elevation, 0.075f);
        renderer.rectLine(0 + GameConstants.HorizontalShift,
                (2*GameConstants.CELL_SIZE.y) + GameConstants.Elevation,
                GameConstants.BOARD_SIZE.x + GameConstants.HorizontalShift,
                (2*GameConstants.CELL_SIZE.y) + GameConstants.Elevation, 0.075f);
        renderer.rectLine(0 + GameConstants.HorizontalShift,
                GameConstants.CELL_SIZE.y + GameConstants.Elevation,
                GameConstants.BOARD_SIZE.x + GameConstants.HorizontalShift,
                GameConstants.CELL_SIZE.y + GameConstants.Elevation, 0.075f);
    }

    private void renderPieces(SpriteBatch batch, TextureAtlas atlas) {
        for (ShapeData data : gamePlayHandler.getBoard().getCrosses()) {
            sprites[data.getBoardPositionX()][data.getBoardPositionY()].setRegion(atlas.findRegion(data.getShapeName()));
            sprites[data.getBoardPositionX()][data.getBoardPositionY()].setSize(data.getWidth(), data.getHeigth());
            sprites[data.getBoardPositionX()][data.getBoardPositionY()].setPosition(data.getPositionX(), data.getPositionY());
            sprites[data.getBoardPositionX()][data.getBoardPositionY()].draw(batch);
        }

        for (ShapeData data : gamePlayHandler.getBoard().getCircles()) {
            sprites[data.getBoardPositionX()][data.getBoardPositionY()].setRegion(atlas.findRegion(data.getShapeName()));
            sprites[data.getBoardPositionX()][data.getBoardPositionY()].setSize(data.getWidth(), data.getHeigth());
            sprites[data.getBoardPositionX()][data.getBoardPositionY()].setPosition(data.getPositionX(), data.getPositionY());
            sprites[data.getBoardPositionX()][data.getBoardPositionY()].draw(batch);
        }
    }

    @Override
    public void show() {
        //
        // Screen configuration
        //
        game.getAdsRequestHandler().showAds(true);
        cameraGame = new OrthographicCamera();
        cameraWorld = new OrthographicCamera();
        viewport = new FitViewport(GameConstants.BOARD_SIZE.x + (2*GameConstants.HorizontalShift),
                GameConstants.BOARD_SIZE.y + (2*GameConstants.Elevation), cameraGame);
        screenViewport = new ExtendViewport(GameConstants.WORLD_SIZE.x, GameConstants.WORLD_SIZE.y, cameraWorld);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screenViewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(screenViewport);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        //
        // Game styles-resources
        //
        atlas = game.getAssets().getManager().get("ui/tictactoe-game-ui.atlas", TextureAtlas.class);
        skin = game.getAssets().getManager().get("ui/tictactoe-game-ui.json", Skin.class);
        i18NBundle = game.getAssets().getManager().get("resources/messages", I18NBundle.class);
        //
        // Game World
        //
        renderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        //
        // Sprites Initializer
        //
        this.sprites = new Sprite[(int)GameConstants.BOARD_DIMENSION.x][(int)GameConstants.BOARD_DIMENSION.y];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.sprites[i][j] = new Sprite();
            }
        }
        //
        // Game UI Header
        //
        backButton = new ImageTextButton(i18NBundle.get("GoBack"), skin, "back-button");
        restartButton = new ImageButton(skin, "restart");
        optionsButton = new ImageButton(skin, "options");
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(Color.BLUE);
        pm1.fill();
        boardHeader = new Table();
        boardHeader.add(backButton).width(160);
        boardHeader.add(new TextButton(gamePlayHandler.getDifficulty().toString(),skin)).width(280);
        boardHeader.add(restartButton).width(100);
        boardHeader.add(optionsButton).width(100);
        boardHeader.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
        //
        // Game Score
        //
        playerOneGroup = new VerticalGroup();
        playerOneGroup.addActor(new ImageButton(skin, (gamePlayHandler.getPlayerOne().getPlayerType() ==
                Player.PlayerType.PLAYER_TYPE_X) ? "player-cross" : "player-circle").padBottom(10));
        playerOneGroup.addActor(new Label(gamePlayHandler.getPlayerOne().getName(), skin, "player-name"));
        playerTwoGroup = new VerticalGroup();
        playerTwoGroup.addActor(new ImageButton(skin, (gamePlayHandler.getPlayerTwo().getPlayerType() ==
                Player.PlayerType.PLAYER_TYPE_O) ? "player-circle" : "player-cross").padBottom(10));
        playerTwoGroup.addActor(new Label(gamePlayHandler.getPlayerTwo().getName(), skin, "player-name"));
        gameScore = new Label("0:0", skin, "big");
        gameScore.setAlignment(Align.center);
        boardScore = new Table();
        boardScore.add(playerOneGroup).width(240);
        boardScore.add(gameScore).width(160);
        boardScore.add(playerTwoGroup).width(240);
        //
        // Game World
        //
        float myscreenHeight = screenViewport.getWorldHeight()/4;
        inner = new Table();
        inner.add(boardHeader).height(myscreenHeight - 130.0f);
        inner.row();
        inner.add(new Label("It's your turn", skin)).height(130.f);
        inner.row();
        inner.add().height(myscreenHeight*2);
        inner.row();
        inner.add(boardScore).height(myscreenHeight - 100);
        inner.row();
        inner.add().height(100.0f);
        rootTable.add(inner).expand().top();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screenViewport.apply();
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
        viewport.apply();
        renderer.setProjectionMatrix(cameraGame.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        drawBoard(renderer);
        renderer.end();
        // Draw shapes
        spriteBatch.setProjectionMatrix(cameraGame.combined);
        spriteBatch.begin();
        renderPieces(spriteBatch, atlas);
        spriteBatch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        gamePlayHandler.handleTouch(viewport.unproject(new Vector2(screenX, screenY)));
        return true;
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