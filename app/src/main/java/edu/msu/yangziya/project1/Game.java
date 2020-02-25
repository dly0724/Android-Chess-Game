package edu.msu.yangziya.project1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

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

    /**
     * Paint for outlining the area the game is in
     */
    private Paint outlinePaint;

    /**
     * Completed game bitmap
     */
    private Bitmap gameComplete;


    private Paint paint1;

    /**
     * The game board image
     */
    private Bitmap boardImage;

    /**
     * How much we scale the puzzle pieces
     */
    private float scaleFactor;

    /**
     * Collection of chess pieces
     */
    private ArrayList<ChessPiece> pieces = new ArrayList<>();


    public Game(Context context) {

        // Create paint for filling the area the game will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xff000000);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(0xff00ff00);

        // Load the solved puzzle image
        gameComplete = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
//        Drawable drawable = ContextCompat(context, R.drawable.ic_launcher_background);
//        gameComplete = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Resources res = context.getResources();
        boardImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
//        board_image = BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background);

        //
        // Load the Black pieces
        //

        // Rooks
        pieces.add(new ChessPiece(context, R.drawable.chess_rdt45, 0.059f, 0.1f));
        pieces.add(new ChessPiece(context, R.drawable.chess_rdt45, 0.939f, 0.1f));
        // Knights
        pieces.add(new ChessPiece(context, R.drawable.chess_ndt45, 0.179f, 0.1f));
        pieces.add(new ChessPiece(context, R.drawable.chess_ndt45, 0.819f, 0.1f));
        // Bishops
        pieces.add(new ChessPiece(context, R.drawable.chess_bdt45, 0.319f, 0.1f));
        pieces.add(new ChessPiece(context, R.drawable.chess_bdt45, 0.689f, 0.1f));
        // Queen
        pieces.add(new ChessPiece(context, R.drawable.chess_qdt45, 0.439f, 0.1f));
        // King
        pieces.add(new ChessPiece(context, R.drawable.chess_kdt45, 0.569f, 0.1f));

        // Pawns
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 0.059f, 0.238f));  // Leftmost
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 0.179f, 0.238f));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 0.319f, 0.238f));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 0.439f, 0.238f));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 0.569f, 0.238f));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 0.689f, 0.238f));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 0.819f, 0.238f));
        pieces.add(new ChessPiece(context, R.drawable.chess_pdt45, 0.939f, 0.238f)); // Rightmost


        //
        // Load the White pieces
        //

        // Rooks
        pieces.add(new ChessPiece(context, R.drawable.chess_rlt45, 0.059f, 0.9f));
        pieces.add(new ChessPiece(context, R.drawable.chess_rlt45, 0.939f, 0.9f));
        // Knights
        pieces.add(new ChessPiece(context, R.drawable.chess_nlt45, 0.179f, 0.9f));
        pieces.add(new ChessPiece(context, R.drawable.chess_nlt45, 0.819f, 0.9f));
        // Bishops
        pieces.add(new ChessPiece(context, R.drawable.chess_blt45, 0.319f, 0.9f));
        pieces.add(new ChessPiece(context, R.drawable.chess_blt45, 0.689f, 0.9f));
        // Queen
        pieces.add(new ChessPiece(context, R.drawable.chess_qlt45, 0.439f, 0.9f));
        // King
        pieces.add(new ChessPiece(context, R.drawable.chess_klt45, 0.569f, 0.9f));

        // Pawns
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 0.059f, 0.75f)); // Leftmost
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 0.179f, 0.75f));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 0.319f, 0.75f));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 0.439f, 0.75f));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 0.569f, 0.75f));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 0.689f, 0.75f));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 0.819f, 0.75f));
        pieces.add(new ChessPiece(context, R.drawable.chess_plt45, 0.939f, 0.75f));  // Rightmost
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

        canvas.drawRect(marginX, marginY, marginX + gameSize,
                marginY + gameSize, fillPaint);

        // Draw the board image
//        canvas.drawBitmap(boardImage, 0, 0, fillPaint);
        for(ChessPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, gameSize, scaleFactor);
        }

    }
}
