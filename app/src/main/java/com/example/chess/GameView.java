package com.example.chess;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    public static int sizeOfMap =  125*Constants.SCREEN_WIDTH / 1080;
    private final  ArrayList<Board> arrCopyBoard = new ArrayList<>();
    static ArrayList<Soldires>arrWhiteSoldires = new ArrayList<>();
    static ArrayList<Soldires>arrBlackSoldires = new ArrayList<>();
    static ArrayList<Soldires>allSoldier = new ArrayList<>();
    static ArrayList<Board> arrBoard = new ArrayList<>();

    static boolean firstmove = true;
    static Soldires soldierPressed;
    static String colorNow="white";
    private boolean endMach=false;


    private Bitmap winnerMessage;
    private Bitmap Bbishop;
    private Bitmap Bknight;
    private Bitmap Wbishop;
    private Bitmap Wknight;
    private Bitmap Bqueen;
    private Bitmap Wqueen;
    private Bitmap Bpawn;
    private Bitmap Bking;
    private Bitmap Brook;
    private Bitmap Wpawn;
    private Bitmap Wking;
    private Bitmap Wrook;
    private final String BLACK = "black";
    private final String WHITE = "white";

    public GameView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);

        Bitmap bmBoardB = BitmapFactory.decodeResource (this.getResources(), R.drawable.blackboard);
        Bitmap bmBoardW = BitmapFactory.decodeResource (this.getResources(), R.drawable.whiteboard);
        winnerMessage = BitmapFactory.decodeResource (this.getResources(), R.drawable.win);

        winnerMessage = Bitmap.createScaledBitmap (winnerMessage, (5*sizeOfMap), (2*sizeOfMap), true);
        bmBoardB = Bitmap.createScaledBitmap (bmBoardB, sizeOfMap, sizeOfMap, true);
        bmBoardW = Bitmap.createScaledBitmap (bmBoardW, sizeOfMap, sizeOfMap, true);

        Bbishop = BitmapFactory.decodeResource (this.getResources(), R.drawable.bb);
        Bbishop=Bitmap.createScaledBitmap(Bbishop, sizeOfMap, sizeOfMap, true);

        Bknight = BitmapFactory.decodeResource (this.getResources(), R.drawable.bn);
        Bknight=Bitmap.createScaledBitmap(Bknight, sizeOfMap, sizeOfMap, true);

        Bpawn = BitmapFactory.decodeResource (this.getResources(), R.drawable.bp);
        Bpawn = Bitmap.createScaledBitmap(Bpawn, sizeOfMap, sizeOfMap, true);

        Bking = BitmapFactory.decodeResource (this.getResources(), R.drawable.bk);
        Bking = Bitmap.createScaledBitmap(Bking, sizeOfMap, sizeOfMap, true);

        Bqueen = BitmapFactory.decodeResource(this.getResources(), R.drawable.bq);
        Bqueen=Bitmap.createScaledBitmap(Bqueen, sizeOfMap, sizeOfMap, true);

        Brook = BitmapFactory.decodeResource (this.getResources(), R.drawable.br);
        Brook = Bitmap.createScaledBitmap(Brook, sizeOfMap, sizeOfMap, true);

        Wbishop = BitmapFactory.decodeResource (this.getResources(), R.drawable.wb);
        Wbishop=Bitmap.createScaledBitmap(Wbishop, sizeOfMap, sizeOfMap, true);

        Wknight = BitmapFactory.decodeResource  (this.getResources(), R.drawable.wn);
        Wknight =Bitmap.createScaledBitmap(Wknight, sizeOfMap, sizeOfMap, true);

        Wpawn = BitmapFactory.decodeResource (this.getResources(), R.drawable.wp);
        Wpawn = Bitmap.createScaledBitmap(Wpawn, sizeOfMap, sizeOfMap, true);

        Wking = BitmapFactory.decodeResource (this.getResources(), R.drawable.wk);
        Wking = Bitmap.createScaledBitmap(Wking, sizeOfMap, sizeOfMap, true);

        Wqueen = BitmapFactory.decodeResource(this.getResources(), R.drawable.wq);
        Wqueen=Bitmap.createScaledBitmap(Wqueen, sizeOfMap, sizeOfMap, true);

        Wrook = BitmapFactory.decodeResource (this.getResources(), R.drawable.wr);
        Wrook = Bitmap.createScaledBitmap(Wrook, sizeOfMap, sizeOfMap, true);

        String name;


        for(int i = 0; i< 8; i++)
        {
          for(int j = 0; j< 8; j++)
          {
             name=(j+1)+""+(i+1);

             if((i+j)%2==0)
             {
                 // BLACK SQUARE
                 arrBoard.add(new Board(bmBoardB, j*sizeOfMap+Constants.SCREEN_WIDTH/2-(8 /2)*sizeOfMap,
              i*sizeOfMap+170*Constants.SCREEN_HEIGHT/1920, sizeOfMap, sizeOfMap, name, BLACK));
             }
             else
             {
                 // WHITE SQUARE
                 arrBoard.add(new Board(bmBoardW, j*sizeOfMap+Constants.SCREEN_WIDTH/2-(8 /2)*sizeOfMap,
              i*sizeOfMap+170*Constants.SCREEN_HEIGHT/1920, sizeOfMap, sizeOfMap, name, WHITE));
             }
          }
        }

        // רשימה ששומרת על הערכים המקוריים של הלוח
        for(int i=0; i<arrBoard.size(); i++)
        {
            arrCopyBoard.add(new Board(arrBoard.get(i)));
        }

        resetBoard();
    }


    /**
     * @param boards List of all squares
     * @param name Name of a particular square on the board
     * @return The color of the soldier standing on the square that corresponds to the given name
     */
    static String getcolorFromName(String name, ArrayList<Board>boards){
        for(int i=0; i<boards.size(); i++)
        {
            if(boards.get(i).getName().equals(name))
            { return boards.get(i).getColorOn(); }
        }
        return "";
    }

    /**
     * @param canvas
     * Displays the data at the visual level
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(getResources().getColor(R.color.white));

        for(Board b : arrBoard)
        {
            canvas.drawBitmap(
                    b.getBm(),
                    b.getX(),
                    b.getY(),
                  null);
        }

        for(Soldires black : arrBlackSoldires)
        {
            black.draw(canvas);
        }
        for(Soldires white : arrWhiteSoldires)
        {
            white.draw(canvas);
        }

        if(endMach)
        {
            canvas.drawBitmap(winnerMessage,
                             (sizeOfMap*2),
                             (sizeOfMap*5),
                             null);
        }
        else if(ChooseKindOfMatch.multi && colorNow.equals(BLACK) )
        {

            //  Waiting for the current player to finish and then calling the computer
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                final MOveStrategy strategy = new MiniMax(3);
                final Board aiMove = strategy.execute(arrBoard, Integer.MIN_VALUE, Integer.MAX_VALUE);
                move(aiMove);

            }, 300);

        }

        for(Soldires s : allSoldier){
            Log.d(TAG, "draw: "+s.getName()+" "+s.getBoard().getName());
        }
    }

    /**
     * Build:
     *   array of white soldiers
     *   array of black soldiers
     */
    public void resetBoard(){
        endMach=false;

        Rook rook;    Bishop bishop;  King king;
        Queen queen;  Knight knight;  Pawn pawn;

        // BLACK
        rook=new Rook("Brook1", Brook, arrBoard.get(0).getX(), arrBoard.get(0).getY(), BLACK, arrBoard.get(0));  arrBlackSoldires.add(rook);
        knight=new Knight("Bknight1", Bknight, arrBoard.get(1).getX(), arrBoard.get(1).getY(), BLACK, arrBoard.get(1));  arrBlackSoldires.add(knight);
        bishop=new Bishop("Bbishop1", Bbishop, arrBoard.get(2).getX(), arrBoard.get(2).getY(), BLACK, arrBoard.get(2));  arrBlackSoldires.add(bishop);
        queen=new Queen("Bqueen", Bqueen, arrBoard.get(3).getX(), arrBoard.get(3).getY(), BLACK, arrBoard.get(3));  arrBlackSoldires.add(queen);
        king=new King("Bking", Bking, arrBoard.get(4).getX(), arrBoard.get(4).getY(), BLACK, arrBoard.get(4));  arrBlackSoldires.add(king);
        arrBoard.get(0).setColorOn(BLACK);  arrBoard.get(0).setSoldires(rook);
        arrBoard.get(1).setColorOn(BLACK);  arrBoard.get(1).setSoldires(knight);
        arrBoard.get(2).setColorOn(BLACK);  arrBoard.get(2).setSoldires(bishop);
        arrBoard.get(3).setColorOn(BLACK);  arrBoard.get(3).setSoldires(queen);
        arrBoard.get(4).setColorOn(BLACK);  arrBoard.get(4).setSoldires(king);

        bishop=new Bishop("Bbishop2", Bbishop, arrBoard.get(5).getX(), arrBoard.get(5).getY(), BLACK, arrBoard.get(5));  arrBlackSoldires.add(bishop);
        knight=new Knight("Bknight2", Bknight, arrBoard.get(6).getX(), arrBoard.get(6).getY(), BLACK, arrBoard.get(6));  arrBlackSoldires.add(knight);
        rook=new Rook("Brook2", Brook, arrBoard.get(7).getX(), arrBoard.get(7).getY(), BLACK, arrBoard.get(7));  arrBlackSoldires.add(rook);
        pawn=new Pawn("Bpawn1", Bpawn, arrBoard.get(8).getX(), arrBoard.get(8).getY(), BLACK, arrBoard.get(8));  arrBlackSoldires.add(pawn);
        arrBoard.get(5).setColorOn(BLACK);  arrBoard.get(5).setSoldires(bishop);
        arrBoard.get(6).setColorOn(BLACK);  arrBoard.get(6).setSoldires(knight);
        arrBoard.get(7).setColorOn(BLACK);  arrBoard.get(7).setSoldires(rook);
        arrBoard.get(8).setColorOn(BLACK);  arrBoard.get(8).setSoldires(pawn);

        for(int i=9; i<16; i++)
        {
            pawn=new Pawn("Bpawn"+(i-7), Bpawn,
                    arrBoard.get(i).getX(),
                    arrBoard.get(i).getY(),
                    BLACK, arrBoard.get(i));

            arrBoard.get(i).setColorOn(BLACK);
            arrBoard.get(i).setSoldires(pawn);
            arrBlackSoldires.add(pawn);
        }

        allSoldier.addAll(arrBlackSoldires);


        // WHITE
        rook=new Rook("Wrook1", Wrook, arrBoard.get(56).getX(), arrBoard.get(56).getY(), WHITE, arrBoard.get(56));  arrWhiteSoldires.add(rook);
        knight=new Knight("Wknight1", Wknight, arrBoard.get(57).getX(), arrBoard.get(57).getY(), WHITE, arrBoard.get(57));  arrWhiteSoldires.add(knight);
        bishop=new Bishop("Wbishop1", Wbishop, arrBoard.get(58).getX(), arrBoard.get(58).getY(), WHITE, arrBoard.get(58));  arrWhiteSoldires.add(bishop);
        queen=new Queen("Wqueen", Wqueen, arrBoard.get(59).getX(), arrBoard.get(59).getY(), WHITE, arrBoard.get(59));  arrWhiteSoldires.add(queen);
        king=new King("Wking", Wking, arrBoard.get(60).getX(), arrBoard.get(60).getY(), WHITE, arrBoard.get(60));  arrWhiteSoldires.add(king);
        arrBoard.get(56).setColorOn(WHITE);  arrBoard.get(56).setSoldires(rook);
        arrBoard.get(57).setColorOn(WHITE);  arrBoard.get(57).setSoldires(knight);
        arrBoard.get(58).setColorOn(WHITE);  arrBoard.get(58).setSoldires(bishop);
        arrBoard.get(59).setColorOn(WHITE);  arrBoard.get(59).setSoldires(queen);
        arrBoard.get(60).setColorOn(WHITE);  arrBoard.get(60).setSoldires(king);

        bishop=new Bishop("Wbishop2", Wbishop, arrBoard.get(61).getX(), arrBoard.get(61).getY(), WHITE, arrBoard.get(61));  arrWhiteSoldires.add(bishop);
        knight=new Knight("Wknight2", Wknight, arrBoard.get(62).getX(), arrBoard.get(62).getY(), WHITE, arrBoard.get(62));  arrWhiteSoldires.add(knight);
        rook=new Rook("Wrook2", Wrook, arrBoard.get(63).getX(), arrBoard.get(63).getY(), WHITE, arrBoard.get(63));  arrWhiteSoldires.add(rook);
        pawn=new Pawn("Wpawn1", Wpawn, arrBoard.get(48).getX(), arrBoard.get(48).getY(), WHITE, arrBoard.get(48));  arrWhiteSoldires.add(pawn);
        arrBoard.get(61).setColorOn(WHITE);  arrBoard.get(61).setSoldires(bishop);
        arrBoard.get(62).setColorOn(WHITE);  arrBoard.get(62).setSoldires(knight);
        arrBoard.get(63).setColorOn(WHITE);  arrBoard.get(63).setSoldires(rook);
        arrBoard.get(48).setColorOn("WHITE");  arrBoard.get(48).setSoldires(pawn);

        for(int i=49; i<56; i++)
        {
            pawn=new Pawn("Wpawn"+(i-47), Wpawn,
                    arrBoard.get(i).getX(),
                    arrBoard.get(i).getY(),
                    WHITE, arrBoard.get(i));

            arrBoard.get(i).setColorOn(WHITE);
            arrBoard.get(i).setSoldires(pawn);
            arrWhiteSoldires.add(pawn);
        }

        allSoldier.addAll(arrWhiteSoldires);

    }

    /**
     * @param canGo An array of squares that the soldier can walk into in the current round
     *
     * Updates the squares that the soldier can reach with a green circle
     */
    public void updateBoard(ArrayList<Board>canGo){

        Bitmap whitemarked= BitmapFactory.decodeResource(this.getResources(), R.drawable.whitemarked);
        Bitmap blackmarked= BitmapFactory.decodeResource(this.getResources(), R.drawable.blackmarked);

        whitemarked=Bitmap.createScaledBitmap(whitemarked, sizeOfMap, sizeOfMap, true);
        blackmarked=Bitmap.createScaledBitmap(blackmarked, sizeOfMap, sizeOfMap, true);

        // Marks the squares that can be accessed
        if(canGo != null)
        {
            for(Board square : arrBoard)
            {
                 for(Board move : canGo)
                 {
                    if(move.getName().equals(square.getName()))
                    {

                        //BLACK
                        if(square.getSqColor().equals(BLACK))
                            square.setBm(blackmarked);
                        //WHITE
                        else
                            square.setBm(whitemarked);
                    }
                }
            }
        }

        this.invalidate();
    }

    /**
     * @param square A square that the player chose for the soldier to go to
     * Updates the board according to the move
     */
    public void move(Board square)
    {
        Soldires king=null;

        for (Soldires soldires : allSoldier)
        {
            if(soldires.equal(soldierPressed))
            {
                soldierPressed=soldires;
                break;
            }
         }

        // Updates the values of the old square and the new square
        for(Board board : arrBoard)
        {
            if(board.isEqual(square))
            {
                square=board;
                // EAT OPPONENT
                if(square.getSoldires()!=null)
                {
                    eatOpponent(square.getSoldires());
                }

                // NEW SQUARE
                board.setColorOn(colorNow);
                board.setSoldires(soldierPressed);
            }

            // OLD SQUARE
            if(soldierPressed.getBoard().getName().equals(board.getName()))
            {
                board.setColorOn("none");
                board.setSoldires(null);
            }
        }



        // Updating the moving soldier
        for (Soldires soldires : allSoldier)
        {
            if(soldires.equal(soldierPressed))
            {
                soldires.setX(square.getX());
                soldires.setY(square.getY());
                soldires.setBoard(square);
                break;
            }
        }



        // Finds the Black King to see if he's threatened
        if(colorNow.equals(WHITE))
        {
            for(Soldires black : arrBlackSoldires)
            {
                if(black.getName().equals("Bking"))
                {
                    king=black;
                    break;

                }
            }
        }
        else
        {
            // Finds the White King to see if he's threatened
            for(Soldires white : arrWhiteSoldires)
            {
                if(white.getName().equals("Wking"))
                {
                    king=white;
                    break;
                }
            }
        }

        assert king != null;

        // END GAME
        if(king.isChess(arrBoard, king.getBoard()) &&
            (king.isMat(king.checkMove(arrBoard, king.getBoard()).size())))
        {
            endMach=true;
        }


        // Updates the colors of the squares
        for(Board board :arrBoard)
        {
            if(board.getSoldires()!=null)
            {
                board.setColorOn(board.getSoldires().getColor());
            }
        }


        if(colorNow.equals(BLACK) && firstmove)
        {
            firstmove=false;
        }

        colorNow = changeColor();
        this.invalidate();

    }

    /**
     * @return return the color of the next player
     */
    public String changeColor(){
        return colorNow.equals(WHITE) ? BLACK : WHITE;
    }

    /**
     * @param soldire A soldier that his opponent ate
     * the method removes the eaten soldier from the arrays
     */
    public void eatOpponent(Soldires soldire){

        // WHITE
        if(colorNow.equals(WHITE))
        {
            arrBlackSoldires.remove(soldire);
        }
        //BLACK
        else
        {
            arrWhiteSoldires.remove(soldire);
        }

        allSoldier.remove(soldire);
    }

    /**
     * @param opption An array of squares into which the soldier can move
     * @return The method goes through the list and removes from it options in which a threat will be posed to the king. Returns the updated list
     */
    public ArrayList<Board> checkNextMoveChess(ArrayList<Board>opption) {
        ArrayList<Board>collection=new ArrayList<>();
        ArrayList<Board>copyBoard=new ArrayList<>();

        Soldires king = null, oldSoldires = null;
        Board board = null;
        int indexSol = 0, indexRemove = 0;
        boolean eatSoldier;


        // Checks each of the situations in which the soldier moves to one of the squares in the array
        for(Board options : opption)
        {
            eatSoldier=false;

            // copy the board list
            copyBoard.clear();
            for(Board b : arrBoard)
            {
                copyBoard.add(new Board(b));
            }


            // EAT OPPONENT
            if(options.getSoldires()!=null)
            {
                eatSoldier=true;
                oldSoldires=options.getSoldires();

                // Keeps the index of the soldier we ate and removes it from the arrays temporarily
                if(colorNow.equals(WHITE))
                {
                    indexRemove = arrBlackSoldires.indexOf(options.getSoldires());
                    arrBlackSoldires.remove(options.getSoldires());
                }
                else
                {
                    indexRemove  =arrWhiteSoldires.indexOf(options.getSoldires());
                    arrWhiteSoldires.remove(options.getSoldires());
                }
            }


            // מעדכן את המשבצת החדשה
            for(Board square : copyBoard)
            {
                // NEW
                if(options.isEqual(square))
                {
                    square.setColorOn(colorNow);
                    square.setSoldires(soldierPressed);
                }
                // OLD
                if(soldierPressed.getBoard().isEqual(square))
                {
                    square.setColorOn("none");
                    square.setSoldires(null);
                }
            }


            // Updating the soldier
            for(int j=0; j<allSoldier.size(); j++)
            {
                if(soldierPressed==allSoldier.get(j))
                {
                    indexSol=j;
                    board=allSoldier.get(j).getBoard();
                    allSoldier.get(j).setBoard(options);
                }
            }

            // Find the king
            if(colorNow.equals("black"))
            {
                for(Soldires black : arrBlackSoldires)
                {
                    // BLACK
                    if(black.getName().contains("Bking"))
                    {
                        king=black;
                        break;

                    }
                }
            }
            else
            {
                for(Soldires white : arrWhiteSoldires)
                {
                    // WHITE
                    if(white.getName().contains("Wking"))
                    {
                        king=white;
                        break;

                    }
                }
            }

            assert king != null;

            if(king.isChess(copyBoard, king.getBoard())){
                collection.add(options);
            }

            // Restores the board to its original state
            if(eatSoldier)
            {
                // BLACK
                if(colorNow.equals("white"))
                {
                    arrBlackSoldires.add(indexRemove, oldSoldires);
                }
                //WHITE
                else
                {
                    arrWhiteSoldires.add(indexRemove, oldSoldires);
                }
            }

            allSoldier.get(indexSol).setBoard(board);
        }

        opption.removeAll(collection);
        return opption;

    }


    /**
     * @param event event of touching the screen
     * @return Manages the game according to the player's קהקמא
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ArrayList<Board>canRemove = new ArrayList<>();
        ArrayList<Board>canGo;
        float x,y;

        // A condition that checks whether it is a player against a player or a player against a computer. If it's a player against a computer, the condition will only come true if it's the white turn
        if (event.getAction() == MotionEvent.ACTION_DOWN && (!ChooseKindOfMatch.multi) || event.getAction() == MotionEvent.ACTION_DOWN && (ChooseKindOfMatch.multi) && colorNow.equals("white"))
        {
            x=event.getX();
            y=event.getY();

            for(int j=0; j<arrBoard.size(); j++)
            {
                if(arrBoard.get(j).isMarked())
                {
                    // Deletes marked squares from the board
                    canRemove.add(arrBoard.get(j));
                    arrBoard.get(j).setBm(arrCopyBoard.get(j).getBm());
                    arrBoard.get(j).setMarked(false);

                }
            }

            // Handles the condition of a touch event
            for(Board board : arrBoard)
            {
                // Locates the square that the player has selected
                if(x >= board.getX() && x < board.getX() + sizeOfMap && y >= board.getY() && y < board.getY() + sizeOfMap)
                {
                    // MOVE
                    if(canRemove.contains(board))
                    {
                        move(board);

                    }

                    // Make sure that a soldier who is pressed belongs to the current player
                    if(board.getColorOn().equals(colorNow))
                    {
                        soldierPressed=board.getSoldires();
                        canGo=checkNextMoveChess(board.getSoldires().checkMove(arrBoard, board));

                        // MARK SQUARE
                        for(int j=0; j<canGo.size(); j++)
                        {
                            canGo.get(j).setMarked(true);
                        }

                        updateBoard( canGo );
                        break;
                    }
                    // Deletes the marked squares
                    else
                    {
                        updateBoard(null );
                    }

                }
            }
        }

        // UPDATE SQUARE COLOR
        for(Board board :arrBoard)
        {
            if(board.getSoldires()!=null)
            {
                board.setColorOn(board.getSoldires().getColor());
            }
        }

        return true;
    }

}



//            // מוצא את המשבצת של המלך
//            for(int j=0; j<copyBoard.size(); j++){
//                if(copyBoard.get(j).isEqual(king.getBoard())){
//                    kingBoard=copyBoard.get(j);
//                    break;
//                } }
