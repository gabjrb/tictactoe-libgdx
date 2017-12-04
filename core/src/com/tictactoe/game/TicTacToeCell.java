package com.tictactoe.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gabriel on 26/11/2017.
 */

public class TicTacToeCell {

    Vector2 position;
    CellBrand cellBrand;

    public TicTacToeCell(TicTacToeCell cell){
        this.position = cell.position;
        this.cellBrand = cell.cellBrand;
    }

    public TicTacToeCell(Vector2 position){
        this.position = position;
        this.cellBrand = CellBrand.NOTHING;
    }

    public enum CellBrand{
        CROSS,
        CIRCLE,
        NOTHING
    }
}
