package edu.msu.yangziya.project1;

import android.content.Context;

import java.util.List;

import static java.lang.Math.abs;

public class King extends ChessPiece {
    public King(Context context, int id, int drawableId, float x, float y, char color) {
        super(context, id, drawableId, x, y, color);
    }

    protected Boolean isValidMove (int TpX, int TpY, List<List<ChessPiece>> currentBoard){
        ChessPiece targetPiece = currentBoard.get(TpY).get(TpX);

        if(!super.isValidMove(TpX, TpY, currentBoard)){
            return false;
        }
        else if(!((abs(rowIndex-TpY)==1 && abs(columnIndex-TpX)==1) ||
                (abs(rowIndex-TpY)==0 && abs(columnIndex-TpX)==1) ||
                (abs(rowIndex-TpY)==1 && abs(columnIndex-TpX)==0))){
            return false;
        }
        else{
            deletePieceInTarget = true;
            deletedPiece = targetPiece;
            return true;
        }
    }
}
