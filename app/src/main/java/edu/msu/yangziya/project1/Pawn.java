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
        ChessPiece targetPiece = currentBoard.get(TpY).get(TpX);

        if(!super.isValidMove(TpX, TpY, currentBoard)){
            return false;
        }
        else if(abs(columnIndex-TpX) > 1){  // Moved to the side in either direction
            return false;
        }
        else if (abs(columnIndex-TpX) == 1&& targetPiece == null){
            return false;
        }
        else if(columnIndex-TpX == 1 && targetPiece != null){ //move to left diagonal
            if(targetPiece.color == this.color){
                return false;
            }
        }
        else if(TpX- columnIndex == 1 && targetPiece != null){  //move to right diagonal
            if(targetPiece.color == this.color){
                return false;
            }
        }
        else if(color == 'b'){
            if(TpY < rowIndex){  // Wrong direction
                return false;
            }
            if (isFirstMove && TpY-rowIndex == 2){
                if(currentBoard.get(rowIndex+1).get(columnIndex)!=null){
                    return  false;
                }
            }
            else if(isFirstMove && TpY-rowIndex > 2){  // Can move two spaces on first move
                return false;
            }
            else if (!isFirstMove && TpY-rowIndex == 1){
                if(currentBoard.get(TpY).get(TpX)!=null){
                    return  false;
                }
            }
            else if(!isFirstMove && TpY-rowIndex > 1) {  // Moved more than 1 space
                return false;
            }
        }
        else if(color == 'w'){  // Apply same rules in opposite direction for white pawns
            if(TpY > rowIndex){
                return false;
            }
            if (isFirstMove && rowIndex-TpY == 2){
                if(currentBoard.get(TpY+1).get(columnIndex)!=null){
                    return  false;
                }
            }
            else if(isFirstMove && rowIndex-TpY > 2){
                return false;
            }
            else if (!isFirstMove && rowIndex-TpY == 1){
                if(currentBoard.get(TpY).get(TpX)!=null){
                    return  false;
                }
            }
            else if(!isFirstMove && rowIndex-TpY > 1) {
                return false;
            }
        }

        if(isFirstMove){
            isFirstMove = false;
        }

        if(targetPiece != null){
            deletePieceInTarget = true;
            deletedPiece = targetPiece;
        }
        return true;
    }
}
