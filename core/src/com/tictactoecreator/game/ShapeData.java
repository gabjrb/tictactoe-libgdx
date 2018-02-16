package com.tictactoecreator.game;

import com.badlogic.gdx.math.Vector2;

import java.awt.Color;

/**
 * Created by Gabriel on 2/1/2018.
 */

public class ShapeData {
    private Vector2 position;
    private Vector2 boardPosition;
    private String name;
    private int width;
    private int heigth;
    private com.badlogic.gdx.graphics.Color color;

    public ShapeData(Vector2 p, String pl) {
        this.position = getOriginCellBoard(p);
        this.boardPosition = p;
        this.name = pl;
        this.width = (int)GameConstants.CELL_SIZE.x - GameConstants.HorizontalShift;
        this.heigth = (int)GameConstants.CELL_SIZE.y - GameConstants.Elevation;
        this.color = new com.badlogic.gdx.graphics.Color(0.3372549f, 0.8f,0.44705883f,1);
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

    public com.badlogic.gdx.graphics.Color getColor() {
        return color;
    }
}