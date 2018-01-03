package com.tictactoe.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gabriel on 26/11/2017.
 */

public class TicTacToeCell {

    private CellBrand cellBrand;
    private Vector2 position;
    private ShapeData shapeData;

    public TicTacToeCell(TicTacToeCell cell){
        this.cellBrand = cell.cellBrand;
        this.shapeData = cell.shapeData;
        this.position = cell.position;
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

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public CellBrand getCellBrand() {
        return cellBrand;
    }

    public void setCellBrand(CellBrand cellBrand) {
        this.cellBrand = cellBrand;

        if (cellBrand == CellBrand.CIRCLE)
        {
            this.shapeData = new ShapeData(getPosition(), "circle-shape");
        }
        else if (cellBrand == CellBrand.CROSS)
        {
            this.shapeData = new ShapeData(getPosition(), "cross-shape");
        }
    }

    public ShapeData getShapeData() {
        return shapeData;
    }
}