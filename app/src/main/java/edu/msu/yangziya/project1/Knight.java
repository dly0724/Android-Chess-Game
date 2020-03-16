package edu.msu.yangziya.project1;

import android.content.Context;

public class Knight extends ChessPiece {
    public Knight(Context context, int id, float x, float y) {
        super(context, id, x, y);
    }
    /**
     * If we are within SNAP_DISTANCE of the correct
     * answer, snap to the correct answer exactly.
     * @return TRUE OF false
     */
    public boolean maybeSnap(float backupX,float backupY) {
        for (int i =0; i<8;i++){
            for (int j=0; j<8; j++){
                if(Math.abs(x - Xs[i]) < SNAP_DISTANCE &&
                        Math.abs(y - Ys[j]) < SNAP_DISTANCE) {
                    if (CanMove()){
                        x = Xs[i];
                        y = Ys[j];
                        //privousBoa = currentMap
                        //update currentMap
                    }
                    else{
                        x=backupX;
                        y=backupY;
                    }
                    return true;
                }
            }
        }

        return false;
    }

    private Boolean CanMove(){
        return false;
    }
}
