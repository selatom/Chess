package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Knight extends Soldires{
    private static final String TAG = "Knight";

    public Knight(String name, Bitmap bm, int x, int y, String color, Board board) {
        super(name, bm, x, y, color, board, 300);
    }

    @Override
    // מקבל את לוח המשבצות ואת המשבצת שלחצו עליה מחזירה מערך של משבצות שהסוס יכול להתקדם אליהן
    public ArrayList<Board> checkMove(ArrayList<Board> board, Board current) {
        ArrayList<String>collectionName = new ArrayList<>();
        ArrayList<String>posiblleNames=new ArrayList<>();
        ArrayList<Board>canMove=new ArrayList<>();
        int width=current.getrow();
        int hieght=current.getcolumn();

        // סוס יכול להתקדם שניים קדימה אחורה או לצדדים ואז אחד לכיוון אחר שהוא לא הפוך
        posiblleNames.add((width-2)+""+(hieght-1)); // two - left, one - bottom
        posiblleNames.add((width+2)+""+(hieght+1)); // two - right, one - top
        posiblleNames.add((width-2)+""+(hieght+1)); // two - left, one - top
        posiblleNames.add((width+1)+""+(hieght+2)); // two - top, one - right
        posiblleNames.add((width-1)+""+(hieght+2)); // two - top, one - left
        posiblleNames.add((width+1)+""+(hieght-2)); // two - bottom, one - right
        posiblleNames.add((width-1)+""+(hieght-2)); // two - bottom, one - left
        posiblleNames.add((width+2)+""+(hieght-1)); // two - right, one - bottom

        // The following two conditions ensure that all the squares that are to be included in the returned list meet the conditions of the board and do not exceed its scope.
        for(String name : posiblleNames)
        {
            if(name.contains("-"))
            {
                collectionName.add(name);
            }

            else if((Integer.parseInt(name) / 10) > 8 || (Integer.parseInt(name) % 10) > 8)
            {
                collectionName.add(name);

            }
        }

        posiblleNames.removeAll(collectionName);

        //  בשביל לוודא שבאמת אפשר לזוז למשבצות המצויינות, נוודא שאין שם עוד שחקן בצבע זהה
        for(Board square : board)
        {
            for(String name : posiblleNames)
            {
                if(name.equals(square.getName()))
                {
                    if(!square.getColorOn().equals(this.getColor()))
                    {
                        canMove.add(square);
                        break;
                    }
                }
            }
        }
        return canMove;
    }

    public Knight copyPiece(){
        return new Knight(
                this.getName(),
                this.getBm(),
                this.getX(),
                this.getY(),
                this.getColor(),
                new Board(this.getBoard()));
    }
}
