package edu.msu.yangziya.project1;

import android.content.Context;

import java.util.Arrays;
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
            if(targetPiece != null){
                deletePieceInTarget = true;
                deletedPiece = targetPiece;
            }
            return true;
        }
    }

    protected void toKing(List<List<ChessPiece>> currentBoard){

        if (this.color=='w'){
            // 4 diagonal directions
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
            if (rowIndex+1<8 && columnIndex+1<8 && currentBoard.get(rowIndex+1).get(columnIndex+1) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex+1).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+1<8 && columnIndex-1>=0 && currentBoard.get(rowIndex+1).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex-1).id==4){
                    kingCaptured=true;
                    return;}
            }
            //vertical and horizontal
            if (columnIndex-1>=0 && currentBoard.get(rowIndex).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex).get(columnIndex-1).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (columnIndex+1<8 && currentBoard.get(rowIndex).get(columnIndex+1) != null){
                if (currentBoard.get(rowIndex).get(columnIndex+1).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+1<8 && currentBoard.get(rowIndex+1).get(columnIndex) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex-1>0 && currentBoard.get(rowIndex-1).get(columnIndex) != null){
                if (currentBoard.get(rowIndex-1).get(columnIndex).id==4){
                    kingCaptured=true;
                    return;}
            }
        }
        if (this.color=='b'){
            // 4 diagonal directions
            if (rowIndex-1>=0 && columnIndex-1>=0 && currentBoard.get(rowIndex-1).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex-1).get(columnIndex-1).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex-1>=0 && columnIndex+1<8 && currentBoard.get(rowIndex-1).get(columnIndex+1) != null){
                if (currentBoard.get(rowIndex-1).get(columnIndex+1).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+1<8 && columnIndex+1<8 && currentBoard.get(rowIndex+1).get(columnIndex+1) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex+1).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+1<8 && columnIndex-1>=0 && currentBoard.get(rowIndex+1).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex-1).id==28){
                    kingCaptured=true;
                    return;}
            }
            //vertical and horizontal
            if (columnIndex-1>=0 && currentBoard.get(rowIndex).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex).get(columnIndex-1).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (columnIndex+1<8 && currentBoard.get(rowIndex).get(columnIndex+1) != null){
                if (currentBoard.get(rowIndex).get(columnIndex+1).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+1<8 && currentBoard.get(rowIndex+1).get(columnIndex) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex-1>0 && currentBoard.get(rowIndex-1).get(columnIndex) != null){
                if (currentBoard.get(rowIndex-1).get(columnIndex).id==28){
                    kingCaptured=true;
                    return;}
            }
        }
        kingCaptured=false;
        return;
    }
}
