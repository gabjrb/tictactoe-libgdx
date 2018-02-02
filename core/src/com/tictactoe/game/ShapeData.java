package com.tictactoe.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gabriel on 2/1/2018.
 */

public class ShapeData {
    private Vector2 position;
    private Vector2 boardPosition;
    private String name;
    private int width;
    private int heigth;

    public ShapeData(Vector2 p, String pl) {
        this.position = getOriginCellBoard(p);
        this.boardPosition = p;
        this.name = pl;
        this.width = (int)GameConstants.CELL_SIZE.x - GameConstants.HorizontalShift;
        this.heigth = (int)GameConstants.CELL_SIZE.y - GameConstants.Elevation;
    }

    private Vector2 getOriginCellBoard(Vector2 v){
        return new Vector2(GameConstants.HorizontalShift + (GameConstants.HorizontalShift*0.5f) + (v.x * GameConstants.CELL_SIZE.x),
                GameConstants.Elevation + (GameConstants.Elevation*0.5f) + (v.y * GameConstants.CELL_SIZE.y));
    }

    public String getShapeName() { return name; }

    public int getPositionX(){
        return (int)position.x;
    }

    public int getPositionY(){ return (int)position.y; }

    public int getBoardPositionX(){ return (int)boardPosition.x;}

    public int getBoardPositionY(){ return (int)boardPosition.y;}

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }
}