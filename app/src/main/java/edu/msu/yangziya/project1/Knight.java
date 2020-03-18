package edu.msu.yangziya.project1;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class Knight extends ChessPiece {


    public Knight(Context context, int id, int drawableId, float x, float y) {
        super(context, id, drawableId, x, y);
    }

    protected Boolean isValidMove (int TpX, int TpY, List<List<ChessPiece>> currentBoard){
        if(!super.isValidMove(TpX, TpY, currentBoard)){
            return false;
        }
        // Check for "L" shape movement
        else if(!((abs(rowIndex-TpX)==1 && abs(columnIndex-TpY)==2) ||
                (abs(rowIndex-TpX)==2 && abs(columnIndex-TpY)==1))){
            return false;
        }
        else{
            return true;
        }
    }
}
