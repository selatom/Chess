package com.example.chess;

import android.graphics.Bitmap;

public class Board {
    private Bitmap bm;
    private int x, y, width, height;
    private String colorOn;
    private String name;
    private Soldires soldires;
    private boolean marked;
    private String sqColor;

    public Board(Bitmap bm, int x, int y, int width, int height, String name, String sqColor) {
        this.bm = bm;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colorOn="none";
        this.name=name;
        this.marked=false;
        this.sqColor=sqColor;

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

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColorOn() {
        return colorOn;
    }

    public void setColorOn(String colorOn) {
        this.colorOn = colorOn;
    }

    public String getName() {
        return name;
    }

    public Soldires getSoldires() {
        return soldires;
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

    public String getSqColor() {
        return sqColor;
    }

    public void setSqColor(String sqColor) {
        this.sqColor = sqColor;
    }

    public int getcolumn(){
        return (Integer.parseInt(this.name)%10);
    }

    public int getrow(){
        return (Integer.parseInt(this.name)/10);
    }

    public boolean isEqual(Board board){
        if(board.getName().equals(this.name))
            return true;
        return false;
    }
}

