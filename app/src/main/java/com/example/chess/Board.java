package com.example.chess;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Board {
    private final int x;
    private final int y;
    private int width;
    private final int height;
    private Soldires soldires;
    private boolean marked;
    private final String sqColor;
    private String colorOn;
    private String name;
    private Bitmap bm;


    public Board(Bitmap bm, int x, int y, int width, int height, String name, String sqColor) {
        this.sqColor = sqColor;
        this.colorOn = "none";
        this.height = height;
        this.marked = false;
        this.width = width;
        this.name = name;
        this.bm = bm;
        this.x = x;
        this.y = y;
    }

    public Board(Board board){
        this.bm = board.getBm();
        this.x = board.getX();
        this.y = board.getY();
        this.width = board.getWidth();
        this.height = board.getHeight();
        this.colorOn= board.colorOn;
        this.name= board.getName();
        this.marked= board.isMarked();
        this.sqColor= board.getSqColor();
        this.soldires=board.getSoldires();
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getSqColor() {
        return sqColor;
    }

    public int getcolumn(){
        return (Integer.parseInt(this.name)%10);
    }

    public int getrow(){
        return (Integer.parseInt(this.name)/10);
    }

    public String getColorOn() {
        return colorOn;
    }

    public String getName() { return name; }

    public Soldires getSoldires() {
        return soldires;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setColorOn(String colorOn) {
        this.colorOn = colorOn;
    }

    public void setSoldires(Soldires soldires) {
        this.soldires = soldires;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }






    /**
     * @param board Specific square
     * @return Is the obtained square as a parameter equal to the current square
     */
    public boolean isEqual(Board board){
        return board.getName().equals(this.name);
    }


    /**
     * @param name name of square
     * @param boards List of squares of the board
     * @return Returns the square from the array of squares that corresponds to the name obtained as a parameter
     */
    public static Board getBoardFromName(String name, ArrayList<Board>boards){
        for(int i=0; i<boards.size(); i++)
        {
            if(boards.get(i).getName().equals(name))
            {
                return boards.get(i);
            }
        }

        return null;
    }
}

