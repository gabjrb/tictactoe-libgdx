package com.tictactoe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by Gabriel on 22/11/2017.
 */

public class GameConstants {
    public static final Vector2 WORLD_SIZE = new Vector2(640f, 360f);
    public static final Vector2 BOARD_SIZE = new Vector2(120,120);
    public static final Vector2 BOARD_DIMENSION = new Vector2(3,3);
    public static final int Elevation = 10;
    public static final int HorizontalShift = 10;
    public static final Vector2 CELL_SIZE = new Vector2( BOARD_SIZE.x/BOARD_DIMENSION.x, BOARD_SIZE.y/BOARD_DIMENSION.y);


    public static final Vector2[][] WINNING_PATTERNS = new Vector2[][]{
            { new Vector2(0, 0), new Vector2(0, 1), new Vector2(0, 2) },
            { new Vector2(1, 0), new Vector2(1, 1), new Vector2(1, 2) },
            { new Vector2(2, 0), new Vector2(2, 1), new Vector2(2, 2) },
            { new Vector2(0, 0), new Vector2(1, 0), new Vector2(2, 0) },
            { new Vector2(0, 1), new Vector2(1, 1), new Vector2(2, 1) },
            { new Vector2(0, 2), new Vector2(1, 2), new Vector2(2, 2) },
            { new Vector2(0, 0), new Vector2(1, 1), new Vector2(2, 2) },
            { new Vector2(2, 0), new Vector2(1, 1), new Vector2(0, 2) }
    };

    public static final int THREE_CROSS_BONUS = 100;
    public static final int TWO_CROSS_BONUS = 10;
    public static final int ONE_CROSS_BONUS = 1;

    public static final int THREE_NOUGHT_BONUS = -100;
    public static final int TWO_NOUGHT_BONUS = -10;
    public static final int ONE_NOUGHT_BONUS = -1;
}