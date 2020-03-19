package edu.msu.yangziya.project1;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class Knight extends ChessPiece {


    public Knight(Context context, int id, int drawableId, float x, float y, char color) {
        super(context, id, drawableId, x, y, color);
    }

    protected Boolean isValidMove (int TpX, int TpY, List<List<ChessPiece>> currentBoard){
        ChessPiece targetPiece = currentBoard.get(TpY).get(TpX);
        if(!super.isValidMove(TpX, TpY, currentBoard)){
            return false;
        }
        // Check for "L" shape movement
        else if(!((abs(rowIndex-TpY)==1 && abs(columnIndex-TpX)==2) ||
                (abs(rowIndex-TpY)==2 && abs(columnIndex-TpX)==1))){
            return false;
        }
        else{
            deletePieceInTarget = true;
            deletedPiece = targetPiece;
            return true;
        }
    }
}
