package com.tictactoe.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import sun.security.provider.SHA;

/**
 * Created by Gabriel on 26/11/2017.
 */

public class TicTacToeBoard {

    private TicTacToeCell[][] cells;

    private Sprite[][] sprites;

    Array<ShapeData> crosses;
    Array<ShapeData> circles;

    public TicTacToeBoard()
    {
        this.cells = new TicTacToeCell[(int)GameConstants.BOARD_DIMENSION.x][(int)GameConstants.BOARD_DIMENSION.y];
        this.sprites = new Sprite[(int)GameConstants.BOARD_DIMENSION.x][(int)GameConstants.BOARD_DIMENSION.y];

        this.crosses = new Array<ShapeData>();
        this.circles = new Array<ShapeData>();

        for (int i = 0; i < (int)GameConstants.BOARD_DIMENSION.x; i++) {

            for (int j = 0; j < (int) GameConstants.BOARD_DIMENSION.y; j++) {
                cells[i][j] = new TicTacToeCell((int)GameConstants.CELL_SIZE.x,
                        (int)GameConstants.CELL_SIZE.y, new Vector2(i*GameConstants.CELL_SIZE.x, j*GameConstants.CELL_SIZE.y),
                        TicTacToeCell.CellType.NOTHING);

                this.sprites[i][j] = new Sprite();
            }
        }
    }

    public void render(float delta, SpriteBatch batch, TextureAtlas atlas)
    {
        for (ShapeData data: crosses) {
            sprites[(int)data.position.x][(int)data.position.y].setRegion(atlas.findRegion(data.player));
            sprites[(int)data.position.x][(int)data.position.y].setSize(cells[(int)data.position.x][(int)data.position.y].getWidth(), cells[(int)data.position.x][(int)data.position.y].getHeigth());
            sprites[(int)data.position.x][(int)data.position.y].setPosition(cells[(int)data.position.x][(int)data.position.y].getPosition().x, cells[(int)data.position.x][(int)data.position.y].getPosition().y);
            sprites[(int)data.position.x][(int)data.position.y].draw(batch);
        }

        for (ShapeData data: circles) {
            sprites[(int)data.position.x][(int)data.position.y].setRegion(atlas.findRegion(data.player));
            sprites[(int)data.position.x][(int)data.position.y].setSize(cells[(int)data.position.x][(int)data.position.y].getWidth(), cells[(int)data.position.x][(int)data.position.y].getHeigth());
            sprites[(int)data.position.x][(int)data.position.y].setPosition(cells[(int)data.position.x][(int)data.position.y].getPosition().x, cells[(int)data.position.x][(int)data.position.y].getPosition().y);
            sprites[(int)data.position.x][(int)data.position.y].draw(batch);
        }
    }

    public void renderPieces(float delta, ShapeRenderer renderer)
    {
        renderer.setColor(Color.BLACK);
        for (ShapeData data: crosses) {
            renderer.rectLine(data.position.x + 1,data.position.y + 1,data.position.x + 3,data.position.y + 3, 0.075f);
            renderer.rectLine(data.position.x + 3,data.position.y + 1,data.position.x + 1,data.position.y + 3, 0.075f);
        }

        for (ShapeData data: circles) {
            renderer.setColor(Color.BLUE);
            renderer.circle(data.position.x + 2, data.position.y + 2, 1.25f, 500);
            renderer.setColor(Color.WHITE);
            renderer.circle(data.position.x + 2, data.position.y + 2, 1.25f - 0.075f, 500);
        }
    }

    public void reset() {
        circles.clear();
        crosses.clear();
    }

    public void handleTouch(Vector2 position, String player)
    {

        if (player == "circle")
        {
            circles.add(new ShapeData(position, player));
        }
        else if (player == "cross")
        {
            crosses.add(new ShapeData(position, player));
        }
    }

    public class ShapeData{
        Vector2 position;
        String player;

        public ShapeData(Vector2 p, String pl){
            this.position = p;
            this.player = pl;
        }

        public String getPlayer() {
            return player;
        }

        public Vector2 getPosition() {
            return position;
        }
    }
}


