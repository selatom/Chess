package com.example.chess;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class Soldires {
    private static final String TAG = "Soldires";
    private String name;
    private Bitmap bm;
    private int x, y;
    private String color;
    private Board board;

    public Soldires(String name, Bitmap bm, int x, int y, String color, Board board) {
        this.name = name;
        this.bm = bm;
        this.x = x;
        this.y = y;
        this.color=color;
        this.board=board;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(this.bm, this.x, this.y, null);
    }

    // פעולה שמקבלת את לוח המשחק ומחזירה לוח שבו יש את המשבצות שהשחקן יכול לזוז אליהן
    public ArrayList<Board>checkMove(ArrayList<Board>board, Board current){
        ArrayList<Board>canMove=new ArrayList<>();

        for(int i=0; i<board.size(); i++){
            if(!board.get(i).getColorOn().equals(this.getColor()) || board.get(i).getColorOn().equals("None")){
                canMove.add(board.get(i));
            }
        }

        return canMove;
    }

    // פעולה שמקבלת את לוח המשבצות ומשבצת ספיציפית ומחזירה האם יהיה שח כשאר המלך יזוז למשבצת
    public boolean isChess(ArrayList<Board>boards, Board board){
        ArrayList<Soldires>soldiresColorNow=new ArrayList<>();
        ArrayList<Board>checkSquare;

        // בודק מה הצבע שמשחק ויעתיק לרשימה את רשימת החיילים מהצבע הנגדי
        if(this.color.equals("black")){
            // רשימת החיילים הלבנים שיש על הלוח
            soldiresColorNow.addAll(GameView.arrWhiteSoldires);
        }else{
            // רשימת החיילים השחורים על הלוח
            soldiresColorNow.addAll(GameView.arrBlackSoldires);
        }

        King king=null;
        for(int i=0; i<soldiresColorNow.size(); i++){
            // מוצא את המלך

            if(soldiresColorNow.get(i).getName().equals("King")){
                king=new King(soldiresColorNow.get(i).bm, soldiresColorNow.get(i).x, soldiresColorNow.get(i).y, soldiresColorNow.get(i).color, soldiresColorNow.get(i).board);
                soldiresColorNow.remove(i); // יש לבדוק באופן ידני אם המלך מאיים על המלך של השחקן השני עקב חשש ללופ אין-סופי
                break;

            } }

        int inexOfNew=0, indexOfOld=0;
        String color;
        for(int i=0 ;i<boards.size(); i++){
            // שומר את האינדקס של המשבצת שממנה זזים ואת האינדקס של המשבצת שאליה זזים.
            if(boards.get(i)==board){ inexOfNew=i; }

            if(boards.get(i)==this.board){
                boards.get(i).setColorOn("none");
                indexOfOld=i;

            } }

        color=boards.get(inexOfNew).getColorOn();
        boards.get(inexOfNew).setColorOn(this.color);

        for(int i=0; i<soldiresColorNow.size(); i++){
            //בודק בין כל החיילי היריב האם מישהו מהם יכול להגיע למשבצת שאחיה רוצים להזיז את המלך

            checkSquare=soldiresColorNow.get(i).checkMove(boards, soldiresColorNow.get(i).getBoard());

            if(checkSquare.contains(board)){
                // נחזיר את הלוח לקדמותו
                boards.get(inexOfNew).setColorOn(color);
                boards.get(indexOfOld).setColorOn(this.color);
                return true;

            } }

        // עכשיו נבדוק שהמלך של היריב לא מאיים בשח
        ArrayList<String>checkKing=new ArrayList<>();
        assert king != null;
        int width=king.getBoard().getrow();
        int hieght=king.getBoard().getcolumn();

        // משבצות שהמלך של היריב יכול לזוז אליהן
        checkKing.add(width+1+""+hieght);  checkKing.add(width-1+""+hieght);  checkKing.add(width+""+(hieght+1));  checkKing.add(width+""+(hieght-1));
        checkKing.add(width+1+""+(hieght+1));  checkKing.add(width-1+""+(hieght+1));  checkKing.add(width+1+""+(hieght-1));  checkKing.add(width-1+""+(hieght-1));

        if(checkKing.contains(board.getName())){
            // אם המלך של היריב יכול להגיע למשבצת המדוברת

            // נחזיר את הלוח לקדמותו
            boards.get(inexOfNew).setColorOn("none");
            boards.get(indexOfOld).setColorOn(this.color);

            return  true;
        }

        // נחזיר את הלוח לקדמותו
        boards.get(inexOfNew).setColorOn("none");
        boards.get(indexOfOld).setColorOn(this.color);
        return  false;
    }

    // פעולה מקבלת את המלך ובודקת אם יש לו לאן ללכת במצב של שח. אם לא, מחזירה אמת אם כן מחזירה שקר
    public boolean isMat(Soldires soldires){
        ArrayList<Board>options=soldires.checkMove(GameView.arrBoard, soldires.getBoard());
        if(options.size()>0){
            return false;
        }

        return true;
    }

}
