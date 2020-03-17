package edu.msu.yangziya.project1;

import android.content.Context;

import java.util.List;

import static java.lang.Math.abs;

public class Knight extends ChessPiece {
    private int XCurrentPosition;
    private int YCurrentPosition;

    public Knight(Context context, int id, float x, float y) {
        super(context, id, x, y);
    }
    /**
     * If we are within SNAP_DISTANCE of the correct
     * answer, snap to the correct answer exactly.
     * @return TRUE OF false
     */
    public boolean maybeSnap(float backupX,float backupY,List<List<Integer>> currentBoard) {

        for (int i =0; i<8;i++){
            for (int j=0; j<8; j++){
                if(Math.abs(x - Xs[i]) < SNAP_DISTANCE &&
                        Math.abs(y - Ys[j]) < SNAP_DISTANCE) {
                    if (CanMove(i,j,currentBoard)){
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


    private Boolean CanMove(int TpX, int TpY, List<List<Integer>> currentBoard){
        boolean moveflag = validationCheck(TpX,TpY,currentBoard);

        if (!moveflag){
            /*Integer pieceID = getId();
            for (int i=0;i<8;i++){
                for(int j = 0; j<8;j++){
                    if (currentBoard.get(i).get(j)== pieceID){
                        XCurrentPosition = j;
                        YCurrentPosition = i;
                    }
                }
            }*/

            XCurrentPosition = 1;
            YCurrentPosition = 0;
            if ((abs(XCurrentPosition-TpX)==1 && abs(YCurrentPosition-TpY)==2)|| (abs(XCurrentPosition-TpX)==2 && abs(YCurrentPosition-TpY)==1)){
                return true;
            }
            else{
                return false;
            }
        }else{
            return false;
        }
    }

    private Boolean validationCheck (int TpX, int TpY, List<List<Integer>> currentBoard){
        switch (currentBoard.get(TpY).get(TpX)){
            case 0:
                return false;
        }
        return true;
    }
}
