package edu.msu.yangziya.project1;

import android.content.Context;

import java.util.List;

import static java.lang.Math.abs;

public class Rook extends ChessPiece {
    public Rook(Context context, int id, int drawableId, float x, float y, char color) {
        super(context, id, drawableId, x, y, color);
    }

    protected Boolean isValidMove (List<List<ChessPiece>> currentBoard, int currentPlayer){
        ChessPiece targetPiece = currentBoard.get(snapYIndex).get(snapXIndex);
        if(!super.isValidMove(currentBoard, currentPlayer)){
            return false;
        }
        // Check for vertical movement
        else if(abs(rowIndex-snapYIndex)!=0 && abs(columnIndex-snapXIndex)==0) {
            for (int i= 1;i<abs(rowIndex-snapYIndex);i++){
                if (((rowIndex-snapYIndex)<0)&&(currentBoard.get(rowIndex+i).get(columnIndex))!=null){
                    setViolationCode(13);
                    return false;
                }
                else if(((rowIndex-snapYIndex)>0)&&(currentBoard.get(rowIndex-i).get(columnIndex))!=null){
                    setViolationCode(13);
                    return false;
                }
            }
            if(targetPiece != null){
                deletePieceInTarget = true;
                deletedPiece = targetPiece;
            }
            return true;
        }
        // Check for horizontal movement
        else if(abs(rowIndex-snapYIndex)==0 && abs(columnIndex-snapXIndex)!=0){
            for (int i= 1;i<abs(columnIndex-snapXIndex);i++){
                if (((columnIndex-snapXIndex)<0)&&(currentBoard.get(rowIndex).get(columnIndex+i))!=null){
                    return false;
                }
                else if(((columnIndex-snapXIndex)>0)&&(currentBoard.get(rowIndex).get(columnIndex-i))!=null){
                    return false;
                }
            }
            if(targetPiece != null){
                deletePieceInTarget = true;
                deletedPiece = targetPiece;
            }
            return true;
        }
        else{
            return false;
        }
    }

    protected void toKing(List<List<ChessPiece>> currentBoard){
        if (this.color=='w'){
            boolean up = true;
            boolean down = true;
            boolean right = true;
            boolean left = true;

            for (int i=1; i<8;i++){
                if (up && rowIndex-i>=0 && currentBoard.get(rowIndex-i).get(columnIndex) != null){
                    if (currentBoard.get(rowIndex-i).get(columnIndex).id == 4){
                        kingCaptured=true;
                        return;
                    }
                    else{
                        up = false;
                    }
                }
                if (right && columnIndex+i<8 && currentBoard.get(rowIndex).get(columnIndex+i) != null){
                    if (currentBoard.get(rowIndex).get(columnIndex+i).id == 4){
                        kingCaptured=true;
                        return;
                    }
                    else{
                        right = false;
                    }
                }
                if (down && rowIndex+i<8 && currentBoard.get(rowIndex+i).get(columnIndex) != null){
                    if (currentBoard.get(rowIndex+i).get(columnIndex).id == 4){
                        kingCaptured=true;
                        return;
                    }
                    else{
                        down = false;
                    }
                }
                if (left && columnIndex-i>=0 && currentBoard.get(rowIndex).get(columnIndex-i) != null){
                    if (currentBoard.get(rowIndex).get(columnIndex-i).id == 4){
                        kingCaptured=true;
                        return;
                    }
                    else{
                        left = false;
                    }
                }
            }
        }
        if (this.color=='b'){
            boolean up = true;
            boolean down = true;
            boolean right = true;
            boolean left = true;

            for (int i=1; i<8;i++){
                if (up && rowIndex-i>=0 && currentBoard.get(rowIndex-i).get(columnIndex) != null){
                    if (currentBoard.get(rowIndex-i).get(columnIndex).id == 28){
                        kingCaptured=true;
                        return;
                    }
                    else{
                        up = false;
                    }
                }
                if (right && columnIndex+i<8 && currentBoard.get(rowIndex).get(columnIndex+i) != null){
                    if (currentBoard.get(rowIndex).get(columnIndex+i).id == 28){
                        kingCaptured=true;
                        return;
                    }
                    else{
                        right = false;
                    }
                }
                if (down && rowIndex+i<8 && currentBoard.get(rowIndex+i).get(columnIndex) != null){
                    if (currentBoard.get(rowIndex+i).get(columnIndex).id == 28){
                        kingCaptured=true;
                        return;
                    }
                    else{
                        down = false;
                    }
                }
                if (left && columnIndex-i>=0 && currentBoard.get(rowIndex).get(columnIndex-i) != null){
                    if (currentBoard.get(rowIndex).get(columnIndex-i).id == 28){
                        kingCaptured=true;
                        return;
                    }
                    else{
                        left = false;
                    }
                }
            }
        }
    }
}
