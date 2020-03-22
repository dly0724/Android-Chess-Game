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

    protected Boolean isValidMove (List<List<ChessPiece>> currentBoard, int currentPlayer){
        ChessPiece targetPiece = currentBoard.get(snapYIndex).get(snapXIndex);
        if(!super.isValidMove(currentBoard, currentPlayer)){
            return false;
        }
        // Check for "L" shape movement
        else if(!((abs(rowIndex-snapYIndex)==1 && abs(columnIndex-snapXIndex)==2) ||
                (abs(rowIndex-snapYIndex)==2 && abs(columnIndex-snapXIndex)==1))){
            setViolationCode(11);
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
            if (rowIndex-2>=0 && columnIndex-1>=0 && currentBoard.get(rowIndex-2).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex-2).get(columnIndex-1).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex-2>=0 && columnIndex+1<8 && currentBoard.get(rowIndex-2).get(columnIndex+1) != null){
                if (currentBoard.get(rowIndex-2).get(columnIndex+1).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+2<8 && columnIndex+1<8 && currentBoard.get(rowIndex+2).get(columnIndex+1) != null){
                int x = currentBoard.get(rowIndex+2).get(columnIndex+1).id;
                if (currentBoard.get(rowIndex+2).get(columnIndex+1).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+2<8 && columnIndex-1>=0 && currentBoard.get(rowIndex+2).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex+2).get(columnIndex-1).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex-1>=0 && columnIndex-2>=0 && currentBoard.get(rowIndex-1).get(columnIndex-2) != null){
                if (currentBoard.get(rowIndex-1).get(columnIndex-2).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex-1>=0 && columnIndex+2<8 && currentBoard.get(rowIndex-1).get(columnIndex+2) != null){
                if (currentBoard.get(rowIndex-1).get(columnIndex+2).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+1<8 && columnIndex+2<8 && currentBoard.get(rowIndex+1).get(columnIndex+2) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex+2).id==4){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+1<8 && columnIndex-2>=0 && currentBoard.get(rowIndex+1).get(columnIndex-2) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex-2).id==4){
                    kingCaptured=true;
                    return;}
            }
        }
        if (this.color=='b'){
            if (rowIndex-2>=0 && columnIndex-1>=0 && currentBoard.get(rowIndex-2).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex-2).get(columnIndex-1).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex-2>=0 && columnIndex+1<8 && currentBoard.get(rowIndex-2).get(columnIndex+1) != null){
                if (currentBoard.get(rowIndex-2).get(columnIndex+1).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+2<8 && columnIndex+1<8 && currentBoard.get(rowIndex+2).get(columnIndex+1) != null){
                int x = currentBoard.get(rowIndex+2).get(columnIndex+1).id;
                if (currentBoard.get(rowIndex+2).get(columnIndex+1).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+2<8 && columnIndex-1>=0 && currentBoard.get(rowIndex+2).get(columnIndex-1) != null){
                if (currentBoard.get(rowIndex+2).get(columnIndex-1).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex-1>=0 && columnIndex-2>=0 && currentBoard.get(rowIndex-1).get(columnIndex-2) != null){
                if (currentBoard.get(rowIndex-1).get(columnIndex-2).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex-1>=0 && columnIndex+2<8 && currentBoard.get(rowIndex-1).get(columnIndex+2) != null){
                if (currentBoard.get(rowIndex-1).get(columnIndex+2).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+1<8 && columnIndex+2<8 && currentBoard.get(rowIndex+1).get(columnIndex+2) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex+2).id==28){
                    kingCaptured=true;
                    return;}
            }
            if (rowIndex+1<8 && columnIndex-2>=0 && currentBoard.get(rowIndex+1).get(columnIndex-2) != null){
                if (currentBoard.get(rowIndex+1).get(columnIndex-2).id==28){
                    kingCaptured=true;
                    return;}
            }
        }
        kingCaptured=false;
        return;
    }
}
