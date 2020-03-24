package edu.msu.yangziya.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

public class GameView extends View {
    /**
     * The actual game
     */
    protected Game game;
    private Parameters params = new Parameters();


    /**
     * Paint object we will use to draw a line
     */
    private Paint linePaint;

    /**
     * The board bitmap.
     */
    private Bitmap boardBitmap = null;

    public int promotionIndex = 0;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        game = new Game(getContext());

        boardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xff008000);
        linePaint.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(game.getDragging() == null){
            GameActivity activity = (GameActivity)getContext();
            if(game.moveWasInvalid()){
                if(game.getToast() != null){
                    activity.displayToast(game.getToast(), Toast.LENGTH_SHORT);
                }
            }
            else if (game.getWin()!=0){
                if(game.getToast() != null){
                    activity.displayToast(game.getToast(), Toast.LENGTH_SHORT);
                }
            }
            else if(game.moveWasCompleted()){
                String completedMsg = "Move Completed\n[Select DONE]";
                if(!activity.getHeader().equals(completedMsg)){
                    activity.setHeader(completedMsg);
                }
            }
        }

        game.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Lint warning fix
        performClick();
        return game.onTouchEvent(this, event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     * Get the view state from a bundle
     * @param key key name to use in the bundle
     * @param bundle bundle to load from
     */
    public void getFromBundle(String key, Bundle bundle) {
        params = (Parameters)bundle.getSerializable(key);

        setRow(params.row);
        setCol(params.col);
        setRowPos(params.rowPos);
        setX(params.X);
        setY(params.Y);
    }

    public void saveInstanceState(Bundle bundle) {
        game.saveInstanceState(bundle);
    }

    public void loadInstanceState(Bundle bundle) {
        game.loadInstanceState(bundle);
    }

    private static class Parameters implements Serializable {
        public int[][] board = new int[8][8];
        public int[] rowPos = new int[]{4, 4, 4, 4};
        public int row = -1;
        public int col = -1;
        public float X = 0;
        public float Y = 0;
    }


    public void setRowPos(int[] rowPos) {
        params.rowPos = rowPos;
    }

    public void setRow(int row) {
        params.row = row;
    }

    public void setCol(int col) {
        params.col = col;
    }

    public void setCurrentPlayer(int currentPlayer) {
        if (promotionIndex!=0){
            game.promotion(promotionIndex);
        }
        promotionIndex = 0;
        game.setCurrentPlayer(currentPlayer);
    }

    public int Win(){
        return game.getWin();
    }

}
