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

    protected Boolean isValidMove (List<List<ChessPiece>> currentBoard, int currentPlayer){
        ChessPiece targetPiece = currentBoard.get(snapYIndex).get(snapXIndex);

        if(!super.isValidMove(currentBoard, currentPlayer)){
            return false;
        }
        else if(abs(columnIndex-snapXIndex) > 1){  // Moved to the side in either direction
            return false;
        }
        else if (abs(columnIndex-snapXIndex) == 1&& targetPiece == null){
            return false;
        }
        else if(columnIndex-snapXIndex == 1 && targetPiece != null){ //move to left diagonal
            if(targetPiece.color == this.color){
                return false;
            }
        }
        else if(snapXIndex- columnIndex == 1 && targetPiece != null){  //move to right diagonal
            if(targetPiece.color == this.color){
                return false;
            }
        }
        else if(color == 'b'){
            if(snapYIndex < rowIndex){  // Wrong direction
                return false;
            }
            if (isFirstMove && snapYIndex-rowIndex == 2){
                if(currentBoard.get(rowIndex+1).get(columnIndex)!=null){
                    return  false;
                }
            }
            else if(isFirstMove && snapYIndex-rowIndex > 2){  // Can move two spaces on first move
                return false;
            }
            else if (!isFirstMove && snapYIndex-rowIndex == 1){
                if(currentBoard.get(snapYIndex).get(snapXIndex)!=null){
                    return  false;
                }
            }
            else if(!isFirstMove && snapYIndex-rowIndex > 1) {  // Moved more than 1 space
                return false;
            }
        }
        else if(color == 'w'){  // Apply same rules in opposite direction for white pawns
            if(snapYIndex > rowIndex){
                return false;
            }
            if (isFirstMove && rowIndex-snapYIndex == 2){
                if(currentBoard.get(snapYIndex+1).get(columnIndex)!=null){
                    return  false;
                }
            }
            else if(isFirstMove && rowIndex-snapYIndex > 2){
                return false;
            }
            else if (!isFirstMove && rowIndex-snapYIndex == 1){
                if(currentBoard.get(snapYIndex).get(snapXIndex)!=null){
                    return  false;
                }
            }
            else if(!isFirstMove && rowIndex-snapYIndex > 1) {
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

    protected void toKing(List<List<ChessPiece>> currentBoard){

        if (this.color == 'w'){
            if (rowIndex-1>=0 && columnIndex-1>=0 && currentBoard.get(rowIndex-1).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex-1).get(columnIndex-1).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex-1>=0 && columnIndex+1<8 && currentBoard.get(rowIndex-1).get(columnIndex+1) != null){
                if (currentBoard.get(rowIndex-1).get(columnIndex+1).id==4){
                    kingCaptured=true;
                    return;}
            }
        }
        if (this.color == 'b'){
            if (rowIndex+1<8 && columnIndex-1>=0 && currentBoard.get(rowIndex+1).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex-1).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+1<8 && columnIndex+1<8 && currentBoard.get(rowIndex+1).get(columnIndex+1) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex+1).id==28){
                    kingCaptured=true;
                    return;}
            }
        }
        kingCaptured=false;
        return;
    }
}
