package edu.msu.yangziya.project1;

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
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;


public class Game {
    private Bitmap basebitmap = null;
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

    /**
     * Completed game bitmap
     */
    private Bitmap gameComplete;

    private float scaleFactor = 0.8f;

    private Paint paint1;

    /**
     * The game board image
     */
    private Bitmap boardImage;

    /**
     * How much we scale the puzzle pieces
     */
    //private float scaleFactor;

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

        Dark = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color_D = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        Dark.setColor(color_D);

        Light = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color_L = ContextCompat.getColor(context, R.color.colorPrimary);
        Light.setColor(color_L);

        // Load the solved puzzle image
        gameComplete = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
//        Drawable drawable = ContextCompat(context, R.drawable.ic_launcher_background);
//        gameComplete = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Resources res = context.getResources();
        boardImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
//        board_image = BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background);

        basebitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chessboard);
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
//        canvas.drawBitmap(boardImage, 0, 0, fillPaint);
        for(ChessPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, gameSize, scaleFactor);
        }

    }
}
