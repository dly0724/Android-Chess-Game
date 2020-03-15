package edu.msu.yangziya.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

/**
 * This class represents one piece of our chess game.
 */
public class ChessPiece {
    /**
     * The image for the actual piece.
     */
    private Bitmap piece;

    /**
     * x location.
     * We use relative x locations in the range 0-1 for the center
     * of the chess piece.
     */
    private float x;

    /**
     * y location
     */
    private float y;

    /**
     * The chess piece ID
     */
    private int id;

    private float testingX_1;
    private float testingX_2;

    private float testingY_1;
    private float testingY_2;

     /* We consider a piece to be in the right location if within
     * this distance.
     */
    final static float SNAP_DISTANCE = 0.2f;

//    /**
//     * We consider a piece to be in the right location if within
//     * this distance.
//     */
//    final static float SNAP_DISTANCE = 0.05f;

    public ChessPiece(Context context, int id, float x, float y) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.testingX_1 = 0.059f;
        this.testingX_2 = 0.9f;
        this.testingY_1 = 0.812f;
        this.testingY_2 = 0.9f;

        piece = BitmapFactory.decodeResource(context.getResources(), id);
    }

    /**
     * Draw the chess piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param boardSize Size we draw the board in pixels
     * @param scaleFactor Amount we scale the chess pieces when we draw them
     */
    public void draw(Canvas canvas, int marginX, int marginY, int boardSize, float scaleFactor) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * boardSize, marginY + y * boardSize);

        // Scale it to the right size
        canvas.scale(scaleFactor, scaleFactor);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-piece.getWidth() / 2f , -piece.getHeight() / 2f );

        // Draw the bitmap
        canvas.drawBitmap(piece, 0, 0, null);
        canvas.restore();
    }

    /**
     * Test to see if we have touched a chess piece
     * @param testX X location as a normalized coordinate (0 to 1)
     * @param testY Y location as a normalized coordinate (0 to 1)
     * @param gameSize the size of the game in pixels
     * @param scaleFactor the amount to scale a piece by
     * @return true if we hit the piece
     */
    public boolean hit(float testX, float testY,
                       int gameSize, float scaleFactor) {

        // Make relative to the location and size to the piece size
        int pX = (int)((testX - x) * gameSize / scaleFactor) +
                piece.getWidth() / 2;
        int pY = (int)((testY - y) * gameSize / scaleFactor) +
                piece.getHeight() / 2;

        if(pX < 0 || pX >= piece.getWidth() ||
                pY < 0 || pY >= piece.getHeight()) {
            return false;
        }


        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }

    /**
     * Move the chess piece by dx, dy
     * @param dx x amount to move
     * @param dy y amount to move
     */
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    /**
     * If we are within SNAP_DISTANCE of the correct
     * answer, snap to the correct answer exactly.
     * @return TRUE OF false
     */
    public boolean maybeSnap() {
        if(Math.abs(x - testingX_1) < SNAP_DISTANCE &&
                Math.abs(y - testingY_1) < SNAP_DISTANCE) {

            x = testingX_1;
            y = testingY_1;
            return true;
        }
        else if(Math.abs(x - testingX_2) < SNAP_DISTANCE &&
                Math.abs(y - testingY_2) < SNAP_DISTANCE) {

            x = testingX_2;
            y = testingY_2;
            return true;
        }

        return false;
    }

    /**
     * Determine if this piece is snapped in place
     * @return true if snapped into place
     */
    public boolean isSnapped() {
        return (x == testingX_1 && y == testingY_1) ||(x == testingX_2 && y == testingY_2) ;
    }


    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public float getTestingX_1() {
        return testingX_1;
    }

    public float getTestingX_2(){
        return testingX_2;
    }

    public float getTestingY_1() {
        return testingY_1;
    }

    public float getTestingY_2() {
        return testingY_2;
    }

    public void setTestingX_1(float testingX_1) {
        this.testingX_1 = testingX_1;
    }

    public void setTestingX_2(float testingX_2) {
        this.testingX_2 = testingX_2;
    }

    public void setTestingY_1(float testingY_1) {
        this.testingY_1 = testingY_1;
    }

    public void setTestingY_2(float testingY_2) {
        this.testingY_2 = testingY_2;
    }
}