package edu.msu.yangziya.project1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import static java.lang.System.in;


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
    private Paint Dark;
    private Paint Light;

    /**
     * Paint for outlining the area the game is in
     */
    private Paint outlinePaint;

    private float scaleFactor = 0.8f;

    /**
     * The image for the game board.
     */
    private Bitmap gameBoard;

    /**
     * Collection of chess pieces
     */
    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    private int marginX;
    private int marginY;
    private int gameSize;

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private ChessPiece dragging = null;

    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    private float lastRelXind;
    private float lastRelYind;

    /**
     * The name of the bundle keys to save the game
     */
    private final static String LOCATIONS = "Chess.locations";
    private final static String IDS = "Chess.ids";

    public float[] xPositions = {0.059f,0.189f,0.319f,0.439f,0.569f,0.689f,0.819f,0.939f};
    public float[] yPositions = {0.06f,0.185f,0.31f,0.435f,0.56f,0.685f,0.812f,0.94f};
    public Integer[] pieceIDs = {
            R.drawable.chess_rdt45, R.drawable.chess_ndt45, R.drawable.chess_bdt45, // Rook, Knight, Bishop (Black)
            R.drawable.chess_qdt45, R.drawable.chess_kdt45, R.drawable.chess_pdt45, // Queen, King, Pawn (Black)
            R.drawable.chess_rlt45, R.drawable.chess_nlt45, R.drawable.chess_blt45, // Rook, Knight, Bishop (White)
            R.drawable.chess_qlt45, R.drawable.chess_klt45, R.drawable.chess_plt45  // Queen, King, Pawn (White)
    };

    /**
     * A Java Pair Class
     * Source: https://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples
     * @param <L> Left Value
     * @param <R> Right Value
     */
    public class Pair<L,R> {

        private final L left;
        private final R right;

        public Pair(L left, R right) {
            assert left != null;
            assert right != null;

            this.left = left;
            this.right = right;
        }

        public L getLeft() { return left; }
        public R getRight() { return right; }

//        public L setLeft(L left) { this.left = left; }
//        public R setRight(R right) { this.right = right; }

        @Override
        public int hashCode() { return left.hashCode() ^ right.hashCode(); }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Pair)) return false;
            Pair pairo = (Pair) o;
            return this.left.equals(pairo.getLeft()) &&
                    this.right.equals(pairo.getRight());
        }

    }

    Map<Integer, Pair<Integer, Pair<Float, Float>>> initialPieceMap =
            new HashMap<Integer, Pair<Integer, Pair<Float, Float>>>() {
                {
                    // Black Pieces (Leftmost to Rightmost)
                    put(0, new Pair(R.drawable.chess_rdt45, new Pair(0.059f, 0.06f)));  // Rook
                    put(1, new Pair(R.drawable.chess_ndt45, new Pair(0.189f, 0.06f)));  // Knight
                    put(2, new Pair(R.drawable.chess_bdt45, new Pair(0.319f, 0.06f)));  // Bishop
                    put(3, new Pair(R.drawable.chess_qdt45, new Pair(0.439f, 0.06f)));  // Queen
                    put(4, new Pair(R.drawable.chess_kdt45, new Pair(0.569f, 0.06f)));  // King
                    put(5, new Pair(R.drawable.chess_bdt45, new Pair(0.689f, 0.06f)));  // Bishop
                    put(6, new Pair(R.drawable.chess_ndt45, new Pair(0.819f, 0.06f)));  // Knight
                    put(7, new Pair(R.drawable.chess_rdt45, new Pair(0.939f, 0.06f)));  // Rook
                    put(8, new Pair(R.drawable.chess_pdt45, new Pair(0.059f, 0.185f))); // Pawns
                    put(9, new Pair(R.drawable.chess_pdt45, new Pair(0.189f, 0.185f)));
                    put(10, new Pair(R.drawable.chess_pdt45, new Pair(0.319f, 0.185f)));
                    put(11, new Pair(R.drawable.chess_pdt45, new Pair(0.439f, 0.185f)));
                    put(12, new Pair(R.drawable.chess_pdt45, new Pair(0.569f, 0.185f)));
                    put(13, new Pair(R.drawable.chess_pdt45, new Pair(0.689f, 0.185f)));
                    put(14, new Pair(R.drawable.chess_pdt45, new Pair(0.819f, 0.185f)));
                    put(15, new Pair(R.drawable.chess_pdt45, new Pair(0.939f, 0.185f)));

                    // White Pieces (Leftmost to Rightmost)
                    put(16, new Pair(R.drawable.chess_plt45, new Pair(0.059f, 0.812f))); // Pawns
                    put(17, new Pair(R.drawable.chess_plt45, new Pair(0.189f, 0.812f)));
                    put(18, new Pair(R.drawable.chess_plt45, new Pair(0.319f, 0.812f)));
                    put(19, new Pair(R.drawable.chess_plt45, new Pair(0.439f, 0.812f)));
                    put(20, new Pair(R.drawable.chess_plt45, new Pair(0.569f, 0.812f)));
                    put(21, new Pair(R.drawable.chess_plt45, new Pair(0.689f, 0.812f)));
                    put(22, new Pair(R.drawable.chess_plt45, new Pair(0.819f, 0.812f)));
                    put(23, new Pair(R.drawable.chess_plt45, new Pair(0.939f, 0.812f)));
                    put(24, new Pair(R.drawable.chess_rlt45, new Pair(0.059f, 0.94f)));  // Rook
                    put(25, new Pair(R.drawable.chess_nlt45, new Pair(0.189f, 0.94f)));  // Knight
                    put(26, new Pair(R.drawable.chess_blt45, new Pair(0.319f, 0.94f)));  // Bishop
                    put(27, new Pair(R.drawable.chess_qlt45, new Pair(0.439f, 0.94f)));  // Queen
                    put(28, new Pair(R.drawable.chess_klt45, new Pair(0.569f, 0.94f)));  // King
                    put(29, new Pair(R.drawable.chess_blt45, new Pair(0.689f, 0.94f)));  // Bishop
                    put(30, new Pair(R.drawable.chess_nlt45, new Pair(0.819f, 0.94f)));  // Knight
                    put(31, new Pair(R.drawable.chess_rlt45, new Pair(0.939f, 0.94f)));  // Rook

                }
            };


    //1x: Black --- 11:BRooks 12:BKnights 13:BBishops 14:BQueen 15:BKing 16:BPawn
    //2x: White --- 21:WRooks 22:WKnights 23:BBishops 24:WQueen 25:WKing 26:WPawn
    private Integer[] iniMapLine1 = {11,12,13,14,15,13,12,11};
    private Integer[] iniMapLine2 = {16,16,16,16,16,16,16,16};
    private Integer[] iniMapEmptyLine = {0,0,0,0,0,0,0,0};
    private Integer[] iniMapLine7 = {21,22,23,24,25,23,22,21};
    private Integer[] iniMapLine8 = {26,26,26,26,26,26,26,26};
    private Integer[][] initialMap = {iniMapLine1,iniMapLine2,iniMapEmptyLine,iniMapEmptyLine,
            iniMapEmptyLine,iniMapEmptyLine,iniMapLine7,iniMapLine8};

    private List<List<ChessPiece>> previousBoardArray;
    private List<List<ChessPiece>> currentBoardArray;

    public Game(Context context) {
        // Create paint for filling the area the game will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xff000000);

        Dark = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color_D = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        Dark.setColor(color_D);

        Light = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color_L = ContextCompat.getColor(context, R.color.colorPrimary);
        Light.setColor(color_L);

        Drawable boardDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_launcher_background);
        gameBoard = Bitmap.createBitmap(boardDrawable.getIntrinsicWidth(),
                boardDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        List<List<ChessPiece>> initialBoardArray = new ArrayList<>();
        int numPieces = initialPieceMap.size();

        List<ChessPiece> currentRow = new ArrayList<>();
        int column = 0;
        int row = 0;

        //
        // Load the pieces
        //
        for(int id = 0; id < numPieces; ++id){

            int drawableID = initialPieceMap.get(id).left;
            float x = initialPieceMap.get(id).right.left;
            float y = initialPieceMap.get(id).right.right;
            ChessPiece piece;

            if(Arrays.asList(0, 7, 24, 31).contains(id)){
                piece = new Rook(context, id, drawableID, x, y);
            }
            else if(Arrays.asList(1, 6, 25, 30).contains(id)){
                piece = new Knight(context, id, drawableID, x, y);
            }
            else if(Arrays.asList(2, 5, 26, 29).contains(id)){
                piece = new Bishop(context, id, drawableID, x, y);
            }
            else if(Arrays.asList(3, 27).contains(id)){
                piece = new Queen(context, id, drawableID, x, y);
            }
            else if(Arrays.asList(4, 28).contains(id)){
                piece = new King(context, id, drawableID, x, y);
            }
            else{
                if(BuildConfig.DEBUG && !(Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15,
                        16, 17, 18, 19, 20, 21, 22, 23).contains(id))){
                    throw new AssertionError("ID should belong to a Pawn");
                }
                piece = new Pawn(context, id, drawableID, x, y);
            }
            piece.setBoardPosition(row, column);
            pieces.add(piece);
            currentRow.add(piece);

            ++column;
            if(column == 8){
                initialBoardArray.add(new ArrayList<>(currentRow));
                currentRow.clear();
                if(row == 1){
                    row = 6;  // skip over the empty rows
                }
                else {
                    ++row;
                }
                column = 0;
            }
        }

        // Add four rows of empty spaces between the sets of black and white pieces
        for(int i = 2; i < 6; ++i){
            currentRow = Arrays.asList(null, null, null, null, null, null, null, null);
            initialBoardArray.add(i, currentRow);
        }

        setCurrentBoardArray(initialBoardArray);
    }

    public Game() {

    }


    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        gameSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the game
        marginX = (wid - gameSize) / 2;
        marginY = (hit - gameSize) / 2;

        // Draw the border of the game area
        canvas.drawRect(marginX - 4, marginY - 4,
                marginX + gameSize + 4, marginY + gameSize + 4, outlinePaint);

        scaleFactor = (float)gameSize / (float)3824;

        canvas.drawRect(marginX, marginY,marginX + gameSize, marginY + gameSize, outlinePaint);

        float lengthPerBlock = gameSize/8;
        for (int i =0; i<8;i++){
            for (int j=0; j<8; j++){
                if ((i+j)%2 ==1){
                    canvas.drawRect(marginX+lengthPerBlock*j, marginY+lengthPerBlock*i,
                            marginX + lengthPerBlock*(j+1), marginY+lengthPerBlock*(i+1), Dark);
                }
                else{
                    canvas.drawRect(marginX+lengthPerBlock*i, marginY+lengthPerBlock*j,
                            marginX + lengthPerBlock*(i+1), marginY+lengthPerBlock*(j+1), Light);
                }
            }
        }
        // Draw the board image

        canvas.save();


        for(ChessPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, gameSize, scaleFactor);
        }

        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.restore();

    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        //
        // Convert an x,y location to a relative location in the
        // game.
        //

        float relX = (event.getX() - marginX) / gameSize;
        float relY = (event.getY() - marginY) / gameSize;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(relX, relY);
//                Log.i("onTouchEvent", "ACTION_DOWN");
//                break;
//                return true;

            //treat cancel as if we received an "up" message
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return onReleased(view, relX, relY);
//                Log.i("onTouchEvent", "ACTION_UP");
//                break;

            case MotionEvent.ACTION_MOVE:
                Log.i("onTouchEvent",  "ACTION_MOVE: " + event.getX() + "," + event.getY());
                //If we are dragging, move the piece and force a redraw
                if(dragging != null) {
                    dragging.move(relX - lastRelX, relY - lastRelY);
                    lastRelX = relX;
                    lastRelY = relY;
                    view.invalidate();//redrawn
                    return true;
                }
                break;
        }


        return false;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the game - 0 to 1 over the game
     * @param y y location for the touch, relative to the game - 0 to 1 over the game
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        for(int p=pieces.size()-1; p>=0;  p--) {
            if(pieces.get(p).hit(x, y, gameSize, scaleFactor)) {
                // We hit a piece!

                dragging = pieces.get(p);
                lastRelX = x;
                lastRelY = y;

                lastRelXind = dragging.x;
                lastRelYind = dragging.y;

                pieces.remove(dragging);
                pieces.add(dragging);
                return true;
            }
        }

        return false;
    }

    /**
     * Handle a release of a touch message.
     * @param x x location for the touch release, relative to the game - 0 to 1 over the game
     * @param y y location for the touch release, relative to the game - 0 to 1 over the game
     * @return true if the touch is handled
     */
    @SuppressWarnings("unused")
    private boolean onReleased(View view, float x, float y) {

        if(dragging != null) {
            if(dragging.maybeSnap(lastRelXind, lastRelYind, currentBoardArray)) {
                // We have snapped into place
                view.invalidate();//redraw

                pieces.remove(dragging);
                pieces.add(0,dragging);

                updateBoardAfterMove(dragging);

                // The puzzle is done
                // Instantiate a dialog box builder
                //AlertDialog.Builder builder =
                //        new AlertDialog.Builder(view.getContext());

                // Create the dialog box and show it
                //AlertDialog alertDialog = builder.create();
                //alertDialog.show();
            }
            dragging = null;
            return true;
        }

        return false;
    }

    public void updateBoardAfterMove(ChessPiece draggingPiece) {
        int sourceRow = draggingPiece.rowIndex;
        int sourceCol = draggingPiece.columnIndex;
        int destRow = draggingPiece.movingToRowIndex;
        int destCol = draggingPiece.movingToColumnIndex;

        // Delete at old position
        currentBoardArray.get(sourceRow).set(sourceCol, null);
        // Set at new position
        currentBoardArray.get(destRow).set(destCol, draggingPiece);
        // Tell the piece where it is at now
        currentBoardArray.get(destRow).get(destCol).setBoardPosition(destRow, destCol);

    }

    /**
     * Save the game to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        float [] locations = new float[pieces.size() * 2];
        int [] ids = new int[pieces.size()];

        for(int i=0;  i<pieces.size(); i++) {
            ChessPiece piece = pieces.get(i);
            locations[i*2] = piece.getX();
            locations[i*2+1] = piece.getY();
            ids[i] = piece.getId();
        }
        bundle.putFloatArray(LOCATIONS, locations);
        bundle.putIntArray(IDS,  ids);

    }

    /**
     * Read the game from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        float [] locations = bundle.getFloatArray(LOCATIONS);
        int [] ids = bundle.getIntArray(IDS);


        assert ids != null;
        for(int i = 0; i<ids.length-1; i++) {

            // Find the corresponding piece
            // We don't have to test if the piece is at i already,
            // since the loop below will fall out without it moving anything
            for(int j=i+1;  j<ids.length;  j++) {
                if(ids[i] == pieces.get(j).getId()) {
                    // We found it
                    // Yah...
                    // Swap the pieces
                    ChessPiece t = pieces.get(i);
                    pieces.set(i, pieces.get(j));
                    pieces.set(j, t);
                }
            }
        }
        for(int i=0;  i<pieces.size(); i++) {
            ChessPiece piece = pieces.get(i);
            assert locations != null;
            piece.setX(locations[i*2]);
            piece.setY(locations[i*2+1]);
        }
    }

    public List<List<ChessPiece>> getCurrentBoardArray() {
        return currentBoardArray;
    }

    public void setCurrentBoardArray(List<List<ChessPiece>> currentBoardArray) {
        this.currentBoardArray = currentBoardArray;
    }

    public List<List<ChessPiece>> getPreviousBoardArray() {
        return previousBoardArray;
    }

    public void setPreviousBoardArray(List<List<ChessPiece>> previousBoardArray) {
        this.previousBoardArray = previousBoardArray;
    }
}
