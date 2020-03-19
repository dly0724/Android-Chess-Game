package edu.msu.yangziya.project1;

import android.content.Context;

import java.util.List;

import static java.lang.Math.abs;

public class Bishop extends ChessPiece {
    public Bishop(Context context, int id, int drawableId, float x, float y, char color) {
        super(context, id, drawableId, x, y, color);
    }

    protected Boolean isValidMove (int TpX, int TpY, List<List<ChessPiece>> currentBoard){
        ChessPiece targetPiece = currentBoard.get(TpY).get(TpX);
        if(!super.isValidMove(TpX, TpY, currentBoard)){
            return false;
        }

        else if(abs(rowIndex-TpY) ==abs(columnIndex-TpX)) {
            for (int i= 1;i<abs(rowIndex-TpY);i++){
                if ((rowIndex-TpY)<0 && (columnIndex-TpX)<0 && currentBoard.get(rowIndex+i).get(columnIndex+i )!=null){  //right-bottom
                    return false;
                }else if ((rowIndex-TpY)>0 && (columnIndex-TpX)<0 && currentBoard.get(rowIndex-i).get(columnIndex+i )!=null) { //right-top
                    return false;
                }else if ((rowIndex-TpY)>0 && (columnIndex-TpX)>0 && currentBoard.get(rowIndex-i).get(columnIndex-i )!=null) { //left-top
                    return false;
                }else if ((rowIndex-TpY)<0 && (columnIndex-TpX)>0 && currentBoard.get(rowIndex+i).get(columnIndex-i )!=null) { //left-bottom
                    return false;
                }

            }
            deletePieceInTarget = true;
            deletedPiece = targetPiece;
            return true;
        }
        else{
            return false;
        }
    }
}
