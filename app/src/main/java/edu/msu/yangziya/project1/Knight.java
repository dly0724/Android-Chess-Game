package edu.msu.yangziya.project1;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class Knight extends ChessPiece {
    private int XCurrentPosition;
    private int YCurrentPosition;

    public Knight(Context context, int id, int drawableId, float x, float y) {
        super(context, id, drawableId, x, y);
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
                    if (CanMove(i, j, currentBoard)){
                        x = xPositions[i];
                        y = yPositions[j];
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


    private Boolean CanMove(int TpX, int TpY, List<List<ChessPiece>> currentBoard){

        boolean moveflag = IsValid(TpX,TpY,currentBoard);
        if (moveflag){
            for (int i=0;i<8;i++){
                for(int j = 0; j<8;j++){
                    ChessPiece targetPiece = currentBoard.get(i).get(j);
                    if (targetPiece!= null){
                        if (currentBoard.get(i).get(j).id== this.id){
                            XCurrentPosition = j;
                            YCurrentPosition = i;
                        }
                    }
                }
            }


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

    private Boolean IsValid (int TpX, int TpY, List<List<ChessPiece>> currentBoard){
        List<Integer> blackPieces = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
        List<Integer> whitePieces = Arrays.asList(16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
        ChessPiece targetPiece = currentBoard.get(TpY).get(TpX);
        if (targetPiece ==null){
            return true;
        }else if(blackPieces.contains(this.id) && blackPieces.contains(targetPiece.id)){
            return false;
        } else if(whitePieces.contains(this.id) && whitePieces.contains(targetPiece.id)){
            return false;
        }
        //int test1=  test.get(TpX);
        //if(currentBoard.get(TpY).get(TpX).getId()!=4){

        //}
        return true;
    }
}
