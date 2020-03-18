package edu.msu.yangziya.project1;

import android.content.Context;

import java.util.List;

import static java.lang.Math.abs;

public class Pawn extends ChessPiece {
    /**
     * True if this is the first time this pawn is being moved
     */
    boolean isFirstMove = true;

    public Pawn(Context context, int id, int drawableId, float x, float y, char color) {
        super(context, id, drawableId, x, y, color);
    }

    protected Boolean isValidMove (int TpX, int TpY, List<List<ChessPiece>> currentBoard){
        if(!super.isValidMove(TpX, TpY, currentBoard)){
            return false;
        }
        else if(abs(columnIndex-TpX) > 0){  // Moved to the side in either direction
            return false;
        }
        else if(color == 'b'){
            if(TpY < rowIndex){  // Wrong direction
                return false;
            }
            if(isFirstMove && TpY-rowIndex > 2){  // Can move two spaces on first move
                return false;
            }
            else if(!isFirstMove && TpY-rowIndex > 1) {  // Moved more than 1 space
                return false;
            }
        }
        else if(color == 'w'){  // Apply same rules in opposite direction for white pawns
            if(TpY > rowIndex){
                return false;
            }
            if(isFirstMove && rowIndex-TpY > 2){
                return false;
            }
            else if(!isFirstMove && rowIndex-TpY > 1) {
                return false;
            }
        }

        if(isFirstMove){
            isFirstMove = false;
        }

        return true;
    }
}
