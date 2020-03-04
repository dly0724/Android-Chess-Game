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
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.io.Serializable;
import java.util.ArrayList;


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

    /**
     * The name of the bundle keys to save the puzzle
     */
    private final static String LOCATIONS = "Chess.locations";
    private final static String IDS = "Chess.ids";


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

        //
        // Load the Black pieces
        //

        // Rooks
        pieces.add(new ChessPiece(context, R.drawable.chess_rdt45,1 ,1 ));
        pieces.add(new ChessPiece(context, R.drawable.chess_rdt45,8,1 ));
        // Knights
        pieces.add(new ChessPiece(context, R.drawable.chess_ndt45,2 ,1 ));
        pieces.add(new ChessPiece(context, R.drawable.chess_ndt45, 7,1 ));
        // Bishops
        pieces.add(new ChessPiece(context, R.drawable.chess_bdt45, 3, 1));
        pieces.add(new ChessPiece(context, R.drawable.chess_bdt45, 6, 1));
        // Queen
        pieces.add(new ChessPiece(context, R.drawable.chess_qdt45,4,1 ));
        // King
        pieces.add(new ChessPiece(context, R.drawable.chess_kdt45,5 , 1));

        // Pawns
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45,1 ,2 ));  // Leftmost
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45,2 ,2 ));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 3,2 ));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 4,2 ));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45,5 , 2));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 6,2 ));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45,7 ,2 ));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45,8 ,2 )); // Rightmost


        //
        // Load the White pieces
        //

        // Rooks
        pieces.add(new ChessPiece(context, R.drawable.chess_rlt45, 1, 8));
        pieces.add(new ChessPiece(context, R.drawable.chess_rlt45, 8, 8));
        // Knights
        pieces.add(new ChessPiece(context, R.drawable.chess_nlt45, 2, 8));
        pieces.add(new ChessPiece(context, R.drawable.chess_nlt45, 7, 8));
        // Bishops
        pieces.add(new ChessPiece(context, R.drawable.chess_blt45, 3, 8));
        pieces.add(new ChessPiece(context, R.drawable.chess_blt45, 6, 8));
        // Queen
        pieces.add(new ChessPiece(context, R.drawable.chess_qlt45, 4, 8));
        // King
        pieces.add(new ChessPiece(context, R.drawable.chess_klt45, 5, 8));

        // Pawns
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 1, 7)); // Leftmost
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 2, 7));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 3, 7));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 4, 7));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 5, 7));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 6, 7));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 7, 7));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 8, 7));  // Rightmost
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


//        scaleFactor = (float)gameSize / (float)gameComplete.getWidth();
        scaleFactor = (float)gameSize / (float)3824;
//        // Draw the game checkerboard
//
//        canvas.drawRect(marginX / 8, marginY / 8, (marginX + gameSize) / 8,
//                (marginY + gameSize) / 8, paint1);


        // Draw the outline of the game area

        canvas.drawRect(marginX, marginY, marginX + gameSize,marginY + gameSize, fillPaint);

        float legnthPerBlck = gameSize/8;
        for (int i =0; i<8;i++){
            for (int j=0; j<8; j++){
                if ((i+j)%2 ==1){
                    canvas.drawRect(marginX+legnthPerBlck*j, marginY+legnthPerBlck*i,
                    marginX + legnthPerBlck*(j+1), marginY+legnthPerBlck*(i+1), Dark);
                }
                else{
                    canvas.drawRect(marginX+legnthPerBlck*i, marginY+legnthPerBlck*j,
                    marginX + legnthPerBlck*(i+1), marginY+legnthPerBlck*(j+1), Light);
                }
            }
        }
        canvas.save();
        // Draw the board image
        //canvas.drawBitmap(boardImage, 0, 0, fillPaint);
        for(ChessPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, gameSize, scaleFactor);
        }
        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        //canvas.drawBitmap(puzzleComplete, 0, 0, null);
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
        // puzzle.
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
                //Log.i("onTouchEvent",  "ACTION_MOVE: " + event.getX() + "," + event.getY());
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
     * @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
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
                pieces.remove(dragging);
                pieces.add(dragging);
                return true;
            }
        }

        return false;
    }

    /**
     * Handle a release of a touch message.
     * @param x x location for the touch release, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch release, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    @SuppressWarnings("unused")
    private boolean onReleased(View view, float x, float y) {

        if(dragging != null) {
            dragging = null;
            return true;
        }

        return false;
    }

    /**
     * Save the puzzle to a bundle
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
     * Read the puzzle from a bundle
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
}
