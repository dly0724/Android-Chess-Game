package edu.msu.yangziya.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Game {

    /**
     * Percentage of the display width or height that
     * is occupied by the game.
     */
    final static float SCALE_IN_VIEW = 0.9f;

    /**
     * Paint for filling the area the game is in
     */
    private Paint fillPaint;

    /**
     * Paint for outlining the area the game is in
     */
    private Paint outlinePaint;


    private Paint paint1;


    public Game(Context context) {

        // Create paint for filling the area the game will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xff000000);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(0xff00ff00);

    }

    public void draw(Canvas canvas) {

        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        int gameSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the game
        int marginX = (wid - gameSize) / 2;
        int marginY = (hit - gameSize) / 2;

        //
        // Draw the border of the game area
        //
        canvas.drawRect(marginX - 4, marginY - 4,
                marginX + gameSize + 4, marginY + gameSize + 4, outlinePaint);

        //
        // Draw the game checkerboard
        //
        //canvas.drawRect(marginX / 8, marginY / 8, (marginX + gameSize) / 8,
                //(marginY + gameSize) / 8, paint1);

        //
        // Draw the outline of the game area
        //
        canvas.drawRect(marginX, marginY, marginX + gameSize,
                marginY + gameSize, fillPaint);
        
    }
}
