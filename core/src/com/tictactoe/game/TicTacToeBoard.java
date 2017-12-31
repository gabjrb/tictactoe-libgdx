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
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gabriel on 26/11/2017.
 */

public class TicTacToeBoard {

    public static final String TAG = TicTacToeBoard.class.getName();

    private TicTacToeCell[][] cells;

    Array<ShapeData> crosses;
    Array<ShapeData> circles;

    public TicTacToeBoard()
    {
        this.crosses = new Array<ShapeData>();
        this.circles = new Array<ShapeData>();

        this.cells = new TicTacToeCell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.cells[i][j] = new TicTacToeCell(new Vector2(i,j));
            }
        }
    }

    public TicTacToeBoard(TicTacToeBoard board){
        this.cells = new TicTacToeCell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.cells[i][j] = new TicTacToeCell(board.cells[i][j]);
            }
        }
    }

    public TicTacToeBoard boardAfterMove(Vector2 position, TicTacToeCell.CellBrand newValue) {
        TicTacToeBoard nextBoard = new TicTacToeBoard(this);
        nextBoard.cells[(int)position.x][(int)position.y].position = position;
        nextBoard.cells[(int)position.x][(int)position.y].cellBrand = newValue;
        return nextBoard;
    }



    public void render(float delta, SpriteBatch batch, TextureAtlas atlas) {
//        for (ShapeData data : crosses) {
//            sprites[(int) data.position.x][(int) data.position.y].setRegion(atlas.findRegion(data.player));
//            sprites[(int) data.position.x][(int) data.position.y].setSize(cells[(int) data.position.x][(int) data.position.y].getWidth(), cells[(int) data.position.x][(int) data.position.y].getHeigth());
//            sprites[(int) data.position.x][(int) data.position.y].setPosition(cells[(int) data.position.x][(int) data.position.y].getPosition().x, cells[(int) data.position.x][(int) data.position.y].getPosition().y);
//            sprites[(int) data.position.x][(int) data.position.y].draw(batch);
//        }
//
//        for (ShapeData data : circles) {
//            sprites[(int) data.position.x][(int) data.position.y].setRegion(atlas.findRegion(data.player));
//            sprites[(int) data.position.x][(int) data.position.y].setSize(cells[(int) data.position.x][(int) data.position.y].getWidth(), cells[(int) data.position.x][(int) data.position.y].getHeigth());
//            sprites[(int) data.position.x][(int) data.position.y].setPosition(cells[(int) data.position.x][(int) data.position.y].getPosition().x, cells[(int) data.position.x][(int) data.position.y].getPosition().y);
//            sprites[(int) data.position.x][(int) data.position.y].draw(batch);
//        }
    }

    public List<Vector2> getEmptyCells(){
        List<Vector2> emptyCells = new ArrayList<Vector2>();
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3 ; j++) {
                if (this.cells[i][j].cellBrand == TicTacToeCell.CellBrand.NOTHING)
                emptyCells.add(new Vector2(i,j));
            }
        }
        return emptyCells;
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

    }

    public void handleTouch(Vector2 position, Player.PlayerType playerType)
    {

        if (playerType == Player.PlayerType.PLAYER_TYPE_O)
        {
            circles.add(new ShapeData(position));
        }
        else if (playerType == Player.PlayerType.PLAYER_TYPE_X)
        {
            crosses.add(new ShapeData(position));
        }
    }

    public boolean setCell(Vector2 position, TicTacToeCell.CellBrand brand) {
        if (cells[(int)position.x][(int)position.y].cellBrand == TicTacToeCell.CellBrand.NOTHING) {
            cells[(int)position.x][(int)position.y].cellBrand = brand;
        } else {
            Gdx.app.log(TAG, "cell already taken");
            return false;
        }
        return true;
    }

    public class ShapeData{
        Vector2 position;

        public ShapeData(Vector2 p){
            this.position = p;
        }
        public Vector2 getPosition() {
            return position;
        }
    }

    public GameResults getResults(){
        GameResults results = new GameResults();

        for (Vector2[] pattern:
             GameConstants.WINNING_PATTERNS) {
            TicTacToeCell cell = cells[(int)pattern[0].x][(int)pattern[0].y];
            if (cell.cellBrand == TicTacToeCell.CellBrand.NOTHING){
                continue;
            }

            if (cells[(int)pattern[0].x][(int)pattern[0].y].cellBrand == cells[(int)pattern[1].x][(int)pattern[1].y].cellBrand &&
                    cells[(int)pattern[1].x][(int)pattern[1].y].cellBrand == cells[(int)pattern[2].x][(int)pattern[2].y].cellBrand) {
//                results.setWinner(cells[(int)pattern[0].x][(int)pattern[0].y].);
                results.setHasWinner(true);
                break;
            }
        }

        return results;
    }

    public Boolean gameOver() {
        return getResults().isHasWinner() == true || getEmptyCells().size() == 0;
    }

    public int getScore() {
        int totalScore = 0;
        for (Vector2[] pattern: GameConstants.WINNING_PATTERNS) {
            // count the number of Empty, Cross, and Nought in a row
            HashMap<TicTacToeCell.CellBrand, Integer> counts = new HashMap<TicTacToeCell.CellBrand, Integer>();
            int emptyCells = 0;
            int crossCells = 0;
            int noughtCells = 0;
            for (Vector2 position: pattern) {
                switch(cells[(int)position.x][(int)position.y].cellBrand) {
                    case NOTHING:
                        emptyCells++;
                        break;
                    case CROSS:
                        crossCells++;
                        break;
                    case CIRCLE:
                        noughtCells++;
                        break;
                }
            }
            counts.put(TicTacToeCell.CellBrand.NOTHING, emptyCells);
            counts.put(TicTacToeCell.CellBrand.CROSS, crossCells);
            counts.put(TicTacToeCell.CellBrand.CIRCLE, noughtCells);
            // calculate score based on counts
            totalScore += calculateScoreForRow(counts);
        }
        return totalScore;
    }

    private int calculateScoreForRow(HashMap<TicTacToeCell.CellBrand, Integer> counts) {
        int score = 0;
        int emptyCount = counts.get(TicTacToeCell.CellBrand.NOTHING);
        int crossCount = counts.get(TicTacToeCell.CellBrand.CROSS);
        int noughtCount = counts.get(TicTacToeCell.CellBrand.CIRCLE);
        if (crossCount == 3 && emptyCount == 0) {
            score += GameConstants.THREE_CROSS_BONUS;
        }
        if (crossCount == 2 && emptyCount == 1) {
            score += GameConstants.TWO_CROSS_BONUS;
        }
        if (crossCount == 1 && emptyCount == 2) {
            score += GameConstants.ONE_CROSS_BONUS;
        }
        if (noughtCount == 3 && emptyCount == 0) {
            score += GameConstants.THREE_NOUGHT_BONUS;
        }
        if (noughtCount == 2 && emptyCount == 1) {
            score += GameConstants.TWO_NOUGHT_BONUS;
        }
        if (noughtCount == 1 && emptyCount == 2) {
            score += GameConstants.ONE_NOUGHT_BONUS;
        }
        return score;
    }
}