package com.tictactoe.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gabriel on 26/11/2017.
 */

public class TicTacToeCell {

    private int width;
    private int heigth;
    private Vector2 position;
    private CellType cellType;

    public enum CellType{
        CROSS,
        CIRCLE,
        NOTHING
    }

    public TicTacToeCell(int width, int heigth, Vector2 position, CellType cellType)
    {
        this.width = width;
        this.heigth = heigth;
        this.position = position;
        this.cellType = cellType;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getWidth() {
        return width;
    }
}
