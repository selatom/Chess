package com.example.chess;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class Soldires {
    private String color;
    private Board board;
    private String name;
    private final int score;
    private final Bitmap bm;
    private int x, y;

    /**
     * @param name soldier name
     * @param bm soldier image
     * @param x Position on the X-axis
     * @param y Position on the Y-axis
     * @param color color of the player
     * @param board The soldier's square
     * @param score Score in board evaluation
     *
     * Creates a soldier-type object according to the parameters obtained
     */
    public Soldires(String name, Bitmap bm, int x, int y, String color, Board board, int score) {
        this.name = name;
        this.color=color;
        this.board=board;
        this.score=score;
        this.bm = bm;
        this.x = x;
        this.y = y;
    }

    /**
     * @param soldires soldier-type object
     *
     * Creates a soldier-type object with the same parameters as the soldier received as a parameter
     */
    public Soldires(Soldires soldires){
        this.color = soldires.getColor();
        this.board = soldires.getBoard();
        this.name = soldires.getName();
        this.bm = soldires.getBm();
        this.x = soldires.getX();
        this.y = soldires.getY();

        this.score = soldires.score;
    }

    public String getName() {
        return name;
    }

    public Bitmap getBm() {
        return bm;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(this.bm, this.x, this.y, null);
    }


    /**
     * @param board Array of board squares
     * @param current The square that was pressed
     * @return The squares that the soldier on the pressed square can move to
     */
    public ArrayList<Board>checkMove(ArrayList<Board>board, Board current){

        // The action is passed as an inheritance to each object of a specific type of soldier that changes the action accordingly
        return new ArrayList<>();
    }


    /**
     * @param boards Array of board squares
     * @param board A square to which you want to move the king
     * @return Will when the king moves to a square obtained as a parameter he will be threatened
     */
    // פעולה שמקבלת את לוח המשבצות ומשבצת ספיציפית ומחזירה האם יהיה שח כשאר המלך יזוז למשבצת
    public boolean isChess(ArrayList<Board>boards, Board board){
        ArrayList<Soldires>soldiresColorNow = new ArrayList<>();
        ArrayList<Board>checkSquare;
        int indexOfOld = 0;
        int inexOfNew = 0;
        King king = null;
        String color;

        // Copies the current player's soldiers list
        if(this.color.equals("black"))
        {
            // WHITE
            soldiresColorNow.addAll(GameView.arrWhiteSoldires);
        }
        else
        {
            // BLACK
            soldiresColorNow.addAll(GameView.arrBlackSoldires);
        }


        // Finds the king of the current player
        for(int i=0; i<soldiresColorNow.size(); i++)
        {
            if(soldiresColorNow.get(i).getName().contains("king"))
            {
                king=new King(soldiresColorNow.get(i).name,
                              soldiresColorNow.get(i).bm,
                              soldiresColorNow.get(i).x,
                              soldiresColorNow.get(i).y,
                              soldiresColorNow.get(i).color,
                              soldiresColorNow.get(i).board);

                // Takes the King off the soldiers list
                soldiresColorNow.remove(i);
                break;
            }
        }

        // Preserves the original square values
        for(int i=0 ;i<boards.size(); i++)
        {
            if(boards.get(i)==board)
            {
                inexOfNew=i;
            }

            if(boards.get(i).isEqual(this.board))
            {
                boards.get(i).setColorOn("none");
                indexOfOld=i;

            }
        }

        color=boards.get(inexOfNew).getColorOn();
        boards.get(inexOfNew).setColorOn(this.color);


        // Goes through the list of rival soldiers and checks if any of them can threaten the King in his new location
        for(Soldires s : soldiresColorNow)
        {
            checkSquare=s.checkMove(boards, s.getBoard());

            if(containBoard(checkSquare, board))
            {
                // We will return the board to its original state
                boards.get(inexOfNew).setColorOn(color);
                boards.get(indexOfOld).setColorOn(this.color);
                return true;

            }
        }

        // We will see if the opponent's king can threaten the king of the current player
        assert king != null;

        ArrayList<String>checkKing = new ArrayList<>();
        int hieght=king.getBoard().getcolumn();
        int width=king.getBoard().getrow();

        // Square of the opposing king
        checkKing.add(width+1 + "" + hieght);
        checkKing.add(width-1 + "" + hieght);
        checkKing.add(width +""+ (hieght+1));
        checkKing.add(width +""+ (hieght-1));
        checkKing.add(width+1+""+(hieght+1));
        checkKing.add(width-1+""+(hieght+1));
        checkKing.add(width+1+""+(hieght-1));
        checkKing.add(width-1+""+(hieght-1));


        // if the opponent's king can threaten the king of the current player
        if(checkKing.contains(board.getName()))
        {
            boards.get(inexOfNew).setColorOn("none");
            boards.get(indexOfOld).setColorOn(this.color);

            return  true;
        }

        // We will return the board to its original state
        boards.get(inexOfNew).setColorOn("none");
        boards.get(indexOfOld).setColorOn(this.color);
        return  false;
    }



    /**
     * @param boards Array of board squares
     * @param player Soldiers list of the current player
     * @return Is there a threat to the King in his current position
     */
    public boolean isChess(ArrayList<Board>boards, ArrayList<Soldires>player){
        ArrayList<Board>possibleMoves = new ArrayList<>();

        // Builds an array of squares that consists of all the squares that the current player can move into
        for(Soldires s : player)
        {
            possibleMoves.addAll(s.checkMove(boards, s.getBoard()));
        }

        // Is the opponent's king's square included in the list of squares that the current soldier can reach
        for(Board b : possibleMoves)
        {
            if(b.getName().equals(this.board.getName()))
            {
                return  true;
            }
        }

        return false;
    }


    /**
     * @param sizeOfMove The number of squares the opponent can move into
     * @return Returns whether there is a checkmate according to the number of squares the opposing soldier can move to
     */
    public boolean isMat(int sizeOfMove){
        return sizeOfMove == 0;
    }


    /**
     * @param boards Array of board squares
     * @param player Soldiers list of the current player
     * @param sizeOfMove The number of squares the opponent king can move into
     * @return Has the current player made his opponent checkmate
     */
    public boolean isMat(ArrayList<Board>boards, ArrayList<Soldires>player, int sizeOfMove){
        // CHECK
        if(isChess(boards, player))
        {
            // MATE
            return sizeOfMove == 0;
        }

        return false;
    }

    /**
     * @param soldires Soldier-type object
     * @return Is the current soldier equal to the soldier received as a parameter
     */
    public boolean equal(Soldires soldires){
        return this.name.equals(soldires.getName()) ;
    }

    // An action that copies the received soldier. The action is inherited by a specific type of soldier and changes accordingly
    public Soldires copyPiece(){
        return null;
    }


    /**
     * @param boards Array of squares
     * @param board Specific square
     * @return Is the square obtained as a parameter included in the list of squares
     */
    public boolean containBoard(ArrayList<Board>boards, Board board){
        for(Board b :boards)
        {
            if(b.isEqual(board))
                return true;
        }
        return false;
    }

}
