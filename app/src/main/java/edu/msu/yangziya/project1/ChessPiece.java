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
    /**
     * delete the opponent piece in target space
     */
    protected boolean deletePieceInTarget;
    protected ChessPiece deletedPiece;
    protected boolean kingCaptured = false;

    /**
     * Index into xPositions array of space to which we're snapping
     */
    protected int snapXIndex;

    /**
     * Index into yPositions array of space to which we're snapping
     */
    protected int snapYIndex;

    /**
     * Code for move violation
     * null = no violation
     * 1 = tried to move opponent's piece
     * 2 = tried to move on an ally-occupied space
     * 3 = tried to move off the board
     * 4 = snapping to same space (i.e. not moving)
     * 5 = trying to move opponent's piece off the board
     * 6 = player already made a move
     * 7 = player already made move and trying to move opponent's piece
     * 8 = invalid Pawn move
     * 9 = invalid Bishop move
     * 10 = invalid King move
     * 11 = invalid Knight move
     * 12 = invalid Queen move
     * 13 = invalid Rook move
     */
    private Integer moveViolation;

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
    public boolean maybeSnap() {
        for (int i =0; i<8;i++){
            for (int j=0; j<8; j++){
                if(Math.abs(x - xPositions[i]) < SNAP_DISTANCE &&
                        Math.abs(y - yPositions[j]) < SNAP_DISTANCE) {
                    snapXIndex = i;
                    snapYIndex = j;
                    return true;
                }
            }
        }
        return false;
    }

    protected Boolean isValidMove (List<List<ChessPiece>> currentBoard, int currentPlayer){
        ChessPiece targetPiece = currentBoard.get(snapYIndex).get(snapXIndex);
        deletePieceInTarget = false;

        if(currentPlayer == 1 && this.color == 'b' ||  // Can't move opponent's piece
                currentPlayer == 2 && this.color == 'w'){
            setViolationCode(1);
            return false;
        }
        else if(targetPiece == null){  // Moving to an empty space
            setViolationCode(null);
            return true;
        }
        else if(targetPiece.id == this.id){  // Moving to same space (i.e. not moving)
            setViolationCode(4);
            return false;
        }
        else if(this.color == targetPiece.color){  // Can't land on ally piece
            setViolationCode(2);
            return false;
        }
        else{
            setViolationCode(null);
            return true;  // Moving to an opponent's space
        }
    }

    protected void toKing(List<List<ChessPiece>> currentBoard){

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

    public void updatePosition(){
        this.movingToRowIndex = snapYIndex;
        this.movingToColumnIndex = snapXIndex;
        x = xPositions[snapXIndex];
        y = yPositions[snapYIndex];
    }

    public void updateBackup(float backupX, float backupY){
        int xIndex = Arrays.binarySearch(xPositions, backupX);
        int yIndex = Arrays.binarySearch(yPositions, backupY);

        this.movingToRowIndex = yIndex;
        this.movingToColumnIndex = xIndex;
        this.x=backupX;
        this.y=backupY;
    }

    public Integer getViolationCode(){
        return this.moveViolation;
    }

    public void setViolationCode(Integer code){
        this.moveViolation = code;
    }

}
