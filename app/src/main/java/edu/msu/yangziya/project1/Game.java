package edu.msu.yangziya.project1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Game{
    /**
     * Percentage of the display width or height that
     * is occupied by the game.
     */
    final static float SCALE_IN_VIEW = 0.8f;

    /**
     * Paint for filling the area the game is in
     */
    private Paint fillPaint;
    private Paint Dark;
    private Paint Light;

    /**
     * Paint for outlining the area the game is in
     */
    private Paint outlinePaint;

    private float scaleFactor = 0.8f;

    /**
     * Collection of chess pieces
     */
    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    private int marginX;
    private int marginY;
    private int gameSize;

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private ChessPiece dragging = null;

    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    private float lastRelXind;
    private float lastRelYind;

    /**
     * True if an invalid move was committed
     */
    private Boolean invalidMove = false;

    /**
     * True if the current player makes a valid move with one of their pieces
     */
    private Boolean moveCompleted;

    /**
     * Toast error message
     */
    private String toast = "";

    /**
     * The name of the bundle keys to save the game
     */
    private final static String LOCATIONS = "Chess.locations";
    private final static String IDS = "Chess.ids";
    private final static String PIECECHANGES = "Chess.pieceChanges";
    private final static String CURRENTMAP = "Chess.currentMap";
    private ArrayList<Integer> PieceChangesUpdate = new ArrayList<Integer>();
    private ArrayList<Integer> currentMapHold = new ArrayList<Integer>();

    public float[] xPositions = {0.059f,0.189f,0.319f,0.439f,0.569f,0.689f,0.819f,0.939f};
    public float[] yPositions = {0.06f,0.185f,0.31f,0.435f,0.56f,0.685f,0.812f,0.94f};

    private Map<Integer, ArrayList<Object>> initialPieceMap = new HashMap<Integer, ArrayList<Object>>() {
        {
            // Black Pieces (Leftmost to Rightmost)
            put(0, new ArrayList<Object>(Arrays.asList(R.drawable.chess_rdt45, 0.059f, 0.06f, 'b')));  // Rook
            put(1, new ArrayList<Object>(Arrays.asList(R.drawable.chess_ndt45, 0.189f, 0.06f, 'b')));  // Knight
            put(2, new ArrayList<Object>(Arrays.asList(R.drawable.chess_bdt45, 0.319f, 0.06f, 'b')));  // Bishop
            put(3, new ArrayList<Object>(Arrays.asList(R.drawable.chess_qdt45, 0.439f, 0.06f, 'b')));  // Queen
            put(4, new ArrayList<Object>(Arrays.asList(R.drawable.chess_kdt45, 0.569f, 0.06f, 'b')));  // King
            put(5, new ArrayList<Object>(Arrays.asList(R.drawable.chess_bdt45, 0.689f, 0.06f, 'b')));  // Bishop
            put(6, new ArrayList<Object>(Arrays.asList(R.drawable.chess_ndt45, 0.819f, 0.06f, 'b')));  // Knight
            put(6, new ArrayList<Object>(Arrays.asList(R.drawable.chess_ndt45, 0.819f, 0.06f, 'b')));  // Knight
            put(7, new ArrayList<Object>(Arrays.asList(R.drawable.chess_rdt45, 0.939f, 0.06f, 'b')));  // Rook
            put(8, new ArrayList<Object>(Arrays.asList(R.drawable.chess_pdt45, 0.059f, 0.185f, 'b'))); // Pawns
            put(9, new ArrayList<Object>(Arrays.asList(R.drawable.chess_pdt45, 0.189f, 0.185f, 'b')));
            put(10, new ArrayList<Object>(Arrays.asList(R.drawable.chess_pdt45, 0.319f, 0.185f, 'b')));
            put(11, new ArrayList<Object>(Arrays.asList(R.drawable.chess_pdt45, 0.439f, 0.185f, 'b')));
            put(12, new ArrayList<Object>(Arrays.asList(R.drawable.chess_pdt45, 0.569f, 0.185f, 'b')));
            put(13, new ArrayList<Object>(Arrays.asList(R.drawable.chess_pdt45, 0.689f, 0.185f, 'b')));
            put(14, new ArrayList<Object>(Arrays.asList(R.drawable.chess_pdt45, 0.819f, 0.185f, 'b')));
            put(15, new ArrayList<Object>(Arrays.asList(R.drawable.chess_pdt45, 0.939f, 0.185f, 'b')));

            // White Pieces (Leftmost to Rightmost)
            put(16, new ArrayList<Object>(Arrays.asList(R.drawable.chess_plt45, 0.059f, 0.812f, 'w'))); // Pawns
            put(17, new ArrayList<Object>(Arrays.asList(R.drawable.chess_plt45, 0.189f, 0.812f, 'w')));
            put(18, new ArrayList<Object>(Arrays.asList(R.drawable.chess_plt45, 0.319f, 0.812f, 'w')));
            put(19, new ArrayList<Object>(Arrays.asList(R.drawable.chess_plt45, 0.439f, 0.812f, 'w')));
            put(20, new ArrayList<Object>(Arrays.asList(R.drawable.chess_plt45, 0.569f, 0.812f, 'w')));
            put(21, new ArrayList<Object>(Arrays.asList(R.drawable.chess_plt45, 0.689f, 0.812f, 'w')));
            put(22, new ArrayList<Object>(Arrays.asList(R.drawable.chess_plt45, 0.819f, 0.812f, 'w')));
            put(23, new ArrayList<Object>(Arrays.asList(R.drawable.chess_plt45, 0.939f, 0.812f, 'w')));
            put(24, new ArrayList<Object>(Arrays.asList(R.drawable.chess_rlt45, 0.059f, 0.94f, 'w')));  // Rook
            put(25, new ArrayList<Object>(Arrays.asList(R.drawable.chess_nlt45, 0.189f, 0.94f, 'w')));  // Knight
            put(26, new ArrayList<Object>(Arrays.asList(R.drawable.chess_blt45, 0.319f, 0.94f, 'w')));  // Bishop
            put(27, new ArrayList<Object>(Arrays.asList(R.drawable.chess_qlt45, 0.439f, 0.94f, 'w')));  // Queen
            put(28, new ArrayList<Object>(Arrays.asList(R.drawable.chess_klt45, 0.569f, 0.94f, 'w')));  // King
            put(29, new ArrayList<Object>(Arrays.asList(R.drawable.chess_blt45, 0.689f, 0.94f, 'w')));  // Bishop
            put(30, new ArrayList<Object>(Arrays.asList(R.drawable.chess_nlt45, 0.819f, 0.94f, 'w')));  // Knight
            put(31, new ArrayList<Object>(Arrays.asList(R.drawable.chess_rlt45, 0.939f, 0.94f, 'w')));  // Rook

        }
    };

    private List<List<ChessPiece>> currentBoardArray;
    private List<List<ChessPiece>> initialChessPiece;
    Context context;
    static ChoiceDialogFragment.SingleChoiceDialogListener singleChoiceDialogListener;
    private ChessPiece promotePiece = null;



    private int currentPlayer;
    public int toWin = 0;

    public Game(Context context) {
        // Create paint for filling the area the game will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xff000000);

        Dark = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color_D = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        Dark.setColor(color_D);

        Light = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color_L = ContextCompat.getColor(context, R.color.colorPrimary);
        Light.setColor(color_L);

        List<List<ChessPiece>> initialBoardArray = new ArrayList<>();
        int numPieces = initialPieceMap.size();

        List<ChessPiece> currentRow = new ArrayList<>();
        int column = 0;
        int row = 0;

        //
        // Load the pieces
        //
        for(int id = 0; id < numPieces; ++id){

            int drawableID = (Integer)initialPieceMap.get(id).get(0);
            float x = (Float)initialPieceMap.get(id).get(1);
            float y = (Float)initialPieceMap.get(id).get(2);
            char color = (Character)initialPieceMap.get(id).get(3);
            ChessPiece piece;

            if(Arrays.asList(0, 7, 24, 31).contains(id)){
                piece = new Rook(context, id, drawableID, x, y, color);
            }
            else if(Arrays.asList(1, 6, 25, 30).contains(id)){
                piece = new Knight(context, id, drawableID, x, y, color);
            }
            else if(Arrays.asList(2, 5, 26, 29).contains(id)){
                piece = new Bishop(context, id, drawableID, x, y, color);
            }
            else if(Arrays.asList(3, 27).contains(id)){
                piece = new Queen(context, id, drawableID, x, y, color);
            }
            else if(Arrays.asList(4, 28).contains(id)){
                piece = new King(context, id, drawableID, x, y, color);
            }
            else{
                if(BuildConfig.DEBUG && !(Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15,
                        16, 17, 18, 19, 20, 21, 22, 23).contains(id))){
                    throw new AssertionError("ID should belong to a Pawn");
                }
                piece = new Pawn(context, id, drawableID, x, y, color);
            }
            piece.setBoardPosition(row, column);
            pieces.add(piece);
            currentRow.add(piece);

            ++column;
            if(column == 8){
                initialBoardArray.add(new ArrayList<>(currentRow));
                currentRow.clear();
                if(row == 1){
                    row = 6;  // skip over the empty rows
                }
                else {
                    ++row;
                }
                column = 0;
            }
        }

        // Add four rows of empty spaces between the sets of black and white pieces
        for(int i = 2; i < 6; ++i){
            currentRow = Arrays.asList(null, null, null, null, null, null, null, null);
            initialBoardArray.add(i, currentRow);
        }

        setCurrentBoardArray(initialBoardArray);
        initialChessPiece = initialBoardArray;
    }

    public Game() {

    }


    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        gameSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the game
        marginX = (wid - gameSize) / 2;
        marginY = (hit - gameSize) / 2;

        // Draw the border of the game area
        canvas.drawRect(marginX - 4, marginY - 4,
                marginX + gameSize + 4, marginY + gameSize + 4, outlinePaint);

        scaleFactor = (float)gameSize / (float)3824;

        canvas.drawRect(marginX, marginY,marginX + gameSize, marginY + gameSize, outlinePaint);

        float lengthPerBlock = gameSize/8;
        for (int i =0; i<8;i++){
            for (int j=0; j<8; j++){
                if ((i+j)%2 ==1){
                    canvas.drawRect(marginX+lengthPerBlock*j, marginY+lengthPerBlock*i,
                            marginX + lengthPerBlock*(j+1), marginY+lengthPerBlock*(i+1), Dark);
                }
                else{
                    canvas.drawRect(marginX+lengthPerBlock*i, marginY+lengthPerBlock*j,
                            marginX + lengthPerBlock*(i+1), marginY+lengthPerBlock*(j+1), Light);
                }
            }
        }
        // Draw the board image

        canvas.save();


        for(ChessPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, gameSize, (float) (scaleFactor * 0.65));
        }

        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.restore();

    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        //
        // Convert an x,y location to a relative location in the
        // game.
        //

        float relX = (event.getX() - marginX) / gameSize;
        float relY = (event.getY() - marginY) / gameSize;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(relX, relY);
//                Log.i("onTouchEvent", "ACTION_DOWN");
//                break;
//                return true;

            //treat cancel as if we received an "up" message
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return onReleased(view, relX, relY);
//                Log.i("onTouchEvent", "ACTION_UP");
//                break;

            case MotionEvent.ACTION_MOVE:
                Log.i("onTouchEvent",  "ACTION_MOVE: " + event.getX() + "," + event.getY());
                //If we are dragging, move the piece and force a redraw
                if(dragging != null) {
                    if(!moveCompleted){
                        dragging.move(relX - lastRelX, relY - lastRelY);
                        lastRelX = relX;
                        lastRelY = relY;
                        view.invalidate();//redrawn
                        return true;
                    }
                    else{
                        invalidMove = true;
                        if(currentPlayer == 1 && dragging.color == 'b' ||
                                currentPlayer == 2 && dragging.color == 'w'){
                            dragging.setViolationCode(7);
                        }
                        else{
                            dragging.setViolationCode(6);
                        }
                        setToast(decodeViolationMessage(dragging.getViolationCode()));
                    }
                }
                break;
        }

        return false;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the game - 0 to 1 over the game
     * @param y y location for the touch, relative to the game - 0 to 1 over the game
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        for(int p=pieces.size()-1; p>=0;  p--) {
            if(pieces.get(p).hit(x, y, gameSize, scaleFactor)) {
                // We hit a piece!

                dragging = pieces.get(p);
                lastRelX = x;
                lastRelY = y;

                lastRelXind = dragging.x;
                lastRelYind = dragging.y;

                pieces.remove(dragging);
                pieces.add(dragging);
                return true;
            }
        }

        return false;
    }

    /**
     * Handle a release of a touch message.
     * @param x x location for the touch release, relative to the game - 0 to 1 over the game
     * @param y y location for the touch release, relative to the game - 0 to 1 over the game
     * @return true if the touch is handled
     */
    @SuppressWarnings("unused")
    private boolean onReleased(View view, float x, float y) {

        if(dragging != null) {
            if(x > 1 || x < 0 || y > 1 || y < 0){  // Off the board
                view.invalidate();//redraw
                invalidMove = true;
                if(!moveCompleted){
                    if(currentPlayer == 1 && dragging.color == 'b' ||  // Can't move opponent's piece
                            currentPlayer == 2 && dragging.color == 'w'){
                        dragging.setViolationCode(5);
                    }
                    else{
                        dragging.setViolationCode(3);
                    }
                }
                dragging.updateBackup(lastRelXind, lastRelYind);
            }
            else if(dragging.maybeSnap()) {
                // We have snapped into place
                view.invalidate();//redraw

                if(!moveCompleted) {
                    if(dragging.isValidMove(currentBoardArray, currentPlayer)){
                        invalidMove = false;
                        dragging.updatePosition();
                        moveCompleted = true;
                        pieces.remove(dragging);
                        pieces.add(0, dragging);

                        updateBoardAfterMove(dragging);

                        int win = isWin();
                        if(win != 0){
                            if(win == 1){              //white wins this turn
                                //winner variable equals to player white's name,
                                toWin = 1;
                                setToast("White wins, Please click Done button to return the Game Over Page.");
                            }
                            else if(win == 2){         //black wins this turn
                                toWin = 2;
                                setToast("Black wins, Please click Done button to return the Game Over Page.");
                            }
                            //go to win page,
                        }
                    }
                    else{
                        invalidMove = true;
                        dragging.updateBackup(lastRelXind, lastRelYind);
                    }
                }
            }
            Integer code = dragging.getViolationCode();
            if(code != null){
                setToast(decodeViolationMessage(code));
            }
            dragging = null;
            return true;
        }

        return false;
    }

    public void updateBoardAfterMove(ChessPiece draggingPiece) {
        int sourceRow = draggingPiece.rowIndex;
        int sourceCol = draggingPiece.columnIndex;
        int destRow = draggingPiece.movingToRowIndex;
        int destCol = draggingPiece.movingToColumnIndex;
        boolean deleteOpponentPiece = draggingPiece.deletePieceInTarget;

        // show choose dialog
        if (destRow == 0 && draggingPiece.getId()>15 && draggingPiece.getId()<24){
            promotePiece = draggingPiece;
            singleChoiceDialogListener.showDialog();
        }

        if (destRow == 7 && draggingPiece.getId()>7 && draggingPiece.getId()<16) {
            promotePiece = draggingPiece;
            singleChoiceDialogListener.showDialog();
        }

        //If there is an opponent piece in the new position
        if (deleteOpponentPiece){
            ChessPiece deletePiece = draggingPiece.deletedPiece;
            setDeletePieceID(deletePiece.getId());
            pieces.remove(deletePiece);
            currentBoardArray.get(destRow).set(destCol, null);

        }

        // Delete at old position
        currentBoardArray.get(sourceRow).set(sourceCol, null);
        // Set at new position
        currentBoardArray.get(destRow).set(destCol, draggingPiece);
        // Tell the piece where it is at now
        currentBoardArray.get(destRow).get(destCol).setBoardPosition(destRow, destCol);

    }

    public int isWin(){
        int currentPieceColor=0;
        for (int i=0;i<8;i++){
            for (int j =0; j<8;j++){
                ChessPiece currentPiece = currentBoardArray.get(i).get(j);
                //check all opponent players's piece, if any piece can capture the king, game end.
                if (currentPiece != null && currentPiece.color=='w'){
                    currentPieceColor = 1;
                }
                else if(currentPiece != null && currentPiece.color=='b'){
                    currentPieceColor = 2;
                }
                if (currentPiece != null && currentPlayer != currentPieceColor){
                //if (currentPiece != null){

                    currentPiece.toKing(currentBoardArray);
                    Boolean kingCaptured = currentPiece.kingCaptured;
                    if (kingCaptured){
                        if (currentPiece.color == 'w'){
                            return 1;
                        }
                        else{
                            return 2;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Save the game to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        float [] locations = new float[pieces.size() * 2];
        int [] ids = new int[pieces.size()];

        currentMapHold.clear();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if (currentBoardArray.get(i).get(j)!= null){
                    currentMapHold.add(currentBoardArray.get(i).get(j).getId());
                }
                else{
                    currentMapHold.add(null);
                }
            }
        }

        for(int i=0;  i<pieces.size(); i++) {
            ChessPiece piece = pieces.get(i);
            locations[i*2] = piece.getX();
            locations[i*2+1] = piece.getY();
            ids[i] = piece.getId();
        }

        bundle.putFloatArray(LOCATIONS, locations);
        bundle.putIntArray(IDS,  ids);
        bundle.putIntegerArrayList(PIECECHANGES,PieceChangesUpdate);
        bundle.putIntegerArrayList(CURRENTMAP,currentMapHold);
    }

    /**
     * Read the game from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        float [] locations = bundle.getFloatArray(LOCATIONS);
        int [] ids = bundle.getIntArray(IDS);

        if (bundle.getIntegerArrayList(PIECECHANGES).size() != 0){
            PieceChangesUpdate = bundle.getIntegerArrayList(PIECECHANGES);
            for (int i=0;i<pieces.size();i++){
                if (PieceChangesUpdate.contains(pieces.get(i).getId())){
                    pieces.set(i,null);
                }
            }

        }
        currentMapHold = bundle.getIntegerArrayList(CURRENTMAP);
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if (currentMapHold.get(i*8+j)!=null){
                    currentBoardArray.get(i).set(j,pieces.get(currentMapHold.get(i*8+j)));
                }
                else{
                    currentBoardArray.get(i).set(j,null);
                }
            }
        }

        while (pieces.remove(null)){}

        assert ids != null;
        for(int i = 0; i<ids.length-1; i++) {

            // Find the corresponding piece
            // We don't have to test if the piece is at i already,
            // since the loop below will fall out without it moving anything
            for(int j=i+1;  j<ids.length;  j++) {
                if(ids[i] == pieces.get(j).getId()) {
                    // We found it
                    // Yah...
                    // Swap the pieces
                    ChessPiece t = pieces.get(i);
                    pieces.set(i, pieces.get(j));
                    pieces.set(j, t);
                }
            }
        }
        for(int i=0;  i<pieces.size(); i++) {
            ChessPiece piece = pieces.get(i);
            assert locations != null;
            piece.setX(locations[i*2]);
            piece.setY(locations[i*2+1]);
        }
    }

    public void promotion(int promotingIndex){
        ChessPiece cp;
        int replacedPiece;
        if (promotingIndex==1){                 //Queen
            if (currentPlayer == 1){
                replacedPiece = initialChessPiece.get(7).get(3).drawableID;
            }
            else{
                replacedPiece = initialChessPiece.get(0).get(3).drawableID;
            }
            cp = new Queen(promotePiece.con,promotePiece.id,replacedPiece,promotePiece.x,promotePiece.y,promotePiece.color);
        }else if (promotingIndex==2){           //Rook
            if (currentPlayer == 1){
                replacedPiece = initialChessPiece.get(7).get(0).drawableID;
            }
            else{
                replacedPiece = initialChessPiece.get(0).get(0).drawableID;
            }
            cp = new Rook(promotePiece.con,promotePiece.id,replacedPiece,promotePiece.x,promotePiece.y,promotePiece.color);
        }else if (promotingIndex==3){           //Knight
            if (currentPlayer == 1){
                replacedPiece = initialChessPiece.get(7).get(1).drawableID;
            }
            else{
                replacedPiece = initialChessPiece.get(0).get(1).drawableID;
            }
            cp = new Knight(promotePiece.con,promotePiece.id,replacedPiece,promotePiece.x,promotePiece.y,promotePiece.color);
        }else{       //Bishop
            if (currentPlayer == 1){
                replacedPiece = initialChessPiece.get(7).get(2).drawableID;
            }
            else{
                replacedPiece = initialChessPiece.get(0).get(2).drawableID;
            }
            cp = new Bishop(promotePiece.con,promotePiece.id,replacedPiece,promotePiece.x,promotePiece.y,promotePiece.color);
        }



        for (int i=0;i<8;i++){
            if (i==0 || i==7){
                for(int j=0;j<8;j++){
                    if (promotePiece!= null && currentBoardArray.get(i).get(j).getId() == promotePiece.getId()){

                        cp.rowIndex = promotePiece.rowIndex;
                        cp.columnIndex = promotePiece.columnIndex;
                        cp.movingToColumnIndex = promotePiece.movingToRowIndex;
                        cp.movingToColumnIndex = promotePiece.movingToColumnIndex;
                        cp.deletePieceInTarget = promotePiece.deletePieceInTarget;
                        cp.deletedPiece = promotePiece.deletedPiece;
                        cp.snapXIndex = promotePiece.snapXIndex;
                        cp.snapYIndex = promotePiece.snapYIndex;
                        cp.x = promotePiece.x;
                        cp.y = promotePiece.y;

                        //cp.updatePosition();
                        pieces.set(0,cp);

                        int sourceRow = promotePiece.rowIndex;
                        int sourceCol = promotePiece.columnIndex;
                        int destRow = promotePiece.movingToRowIndex;
                        int destCol = promotePiece.movingToColumnIndex;
                        boolean deleteOpponentPiece = promotePiece.deletePieceInTarget;

                        // Delete at old position
                        currentBoardArray.get(sourceRow).set(sourceCol, null);
                        // Set at new position
                        currentBoardArray.get(destRow).set(destCol, cp);
                        // Tell the piece where it is at now
                        currentBoardArray.get(destRow).get(destCol).setBoardPosition(destRow, destCol);
                        promotePiece = null;

                    }
                }
            }
        }

    }

    public void setCurrentBoardArray(List<List<ChessPiece>> currentBoardArray) {
        this.currentBoardArray = currentBoardArray;
    }

    private void setDeletePieceID(int ID){
        PieceChangesUpdate.add(ID);
    }

    public void setCurrentPlayer(int player){

        this.currentPlayer = player;
        this.moveCompleted = false;  // New player, reset movement status
    }

    public int getWin(){
        return toWin;
    }

    public Boolean moveWasCompleted(){
        return this.moveCompleted;
    }

    public Boolean moveWasInvalid(){
        return this.invalidMove;
    }

    public ChessPiece getDragging(){
        return this.dragging;
    }

    public String decodeViolationMessage(Integer code){
        if(code == 1){
            return "Can't Move Opponent's Piece";
        }
        else if(code == 2){
            return "Space Occupied By Ally Piece";
        }
        else if(code == 3){
            return "Board Is This Way";
        }
        else if(code == 4){
            return "Must Move A Piece To A New Spot";
        }
        else if(code == 5){
            return "That's Not Your Piece (Plus That's Not Even On The Board)";
        }
        else if(code == 6){
            return "You've Had Your Turn";
        }
        else if(code == 7){
            return "Your Opponent Can Move That Piece Once You Hit 'DONE'";
        }
        else if(code == 8){
            return "Invalid Pawn Move";
        }
        else if(code == 9){
            return "Invalid Bishop Move";
        }
        else if(code == 10){
            return "Invalid King Move";
        }
        else if(code == 11){
            return "Invalid Knight Move";
        }
        else if(code == 12){
            return "Invalid Queen Move";
        }
        else if(code == 13){
            return "Invalid Rook Move";
        }
        else{
            return null;
        }
    }

    public String getToast(){
        return this.toast;
    }

    public void setToast(String msg){
        this.toast = msg;
    }
}
