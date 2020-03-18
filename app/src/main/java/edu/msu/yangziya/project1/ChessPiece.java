package edu.msu.yangziya.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Arrays;
import java.util.List;
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
    protected float x;

    /**
     * y location
     */
    protected float y;

    /**
     * The chess piece ID
     */
    protected int id;

    protected Game game = new Game();
    /**
     * Map positions
     */
    protected float[] xPositions = game.xPositions;
    protected float[] yPositions = game.yPositions;

    /**
     * We consider a piece to be in the right location if within
     * this distance.
     */
    final static float SNAP_DISTANCE = 0.07f;

    /**
     * The index in the board array of the row to which this piece is moving
     */
    protected Integer movingToRowIndex;

    /**
     * The index in the board array of the column to which this piece is moving
     */
    protected Integer movingToColumnIndex;

    /**
     * Index of the row in board array at which this piece currently resides
     */
    protected int rowIndex;

    /**
     * Index of the column in board array at which this piece currently resides
     */
    protected int columnIndex;

    /**
     * 'b' if Black, 'w' if White
     */
    protected char color;

    public ChessPiece(Context context, int id, int drawableId, float x, float y, char color) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.color = color;

        piece = BitmapFactory.decodeResource(context.getResources(), drawableId);
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
    public boolean maybeSnap(float backupX, float backupY, List<List<ChessPiece>> currentBoard) {
        for (int i =0; i<8;i++){
            for (int j=0; j<8; j++){
                if(Math.abs(x - xPositions[i]) < SNAP_DISTANCE &&
                        Math.abs(y - yPositions[j]) < SNAP_DISTANCE) {
                    if(isValidMove(i, j, currentBoard)){
                        this.movingToRowIndex = j;
                        this.movingToColumnIndex = i;
                        x = xPositions[i];
                        y = yPositions[j];
                        return true;
                    }
                    else{
                        x=backupX;
                        y=backupY;
                    }
//                    return true;
                }
            }
        }
        return false;
    }

    protected Boolean isValidMove (int TpX, int TpY, List<List<ChessPiece>> currentBoard){
        ChessPiece targetPiece = currentBoard.get(TpY).get(TpX);

        if (targetPiece == null){  // Empty space, ok to move
            return true;
        }
        else if(this.color == 'b' && targetPiece.color == 'b'){  // Don't move on fellow black piece
            return false;
        }
        else if(this.color == 'w' && targetPiece.color == 'w'){  // Don't move on fellow white piece
            return false;
        }
        else{
            return true;
        }

    }

    /**
     * Update the indices at which this piece resides in the board array
     * @param row row index
     * @param col col index
     */
    public void setBoardPosition(int row, int col){
        this.rowIndex = row;
        this.columnIndex = col;
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

    public float[] getXPositions() {
        return xPositions;
    }

    public void setxPositions(float[] newXPositions) {
        xPositions = newXPositions;
    }

    public float[] getYPositions() {
        return yPositions;
    }

    public void setYPositions(float[] newYPositions) {
        yPositions = newYPositions;
    }

    public List<List<ChessPiece>> getCurrentBoard() {
        return game.getCurrentBoardArray();
    }


}
