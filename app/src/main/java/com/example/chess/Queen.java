package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Queen extends Soldires{
    private static final String TAG = "Queen";

    public Queen(Bitmap bm, int x, int y, String color, Board board) {
        super("queen", bm, x, y, color, board);
    }


    // מקבל את לוח המשבצות ואת המשבצת שלחצו עליה מחזירה מערך של משבצות שהסוס יכול להתקדם אליהן
    @Override
    public ArrayList<Board> checkMove(ArrayList<Board> board, Board current) {
        ArrayList<Board>canMove=new ArrayList<>();
        ArrayList<String>collectionName=new ArrayList<>();
        ArrayList<String>posiblleNames=new ArrayList<>();

        // מלכה יכולה להתקדם בכל קו ישר ובכל אלכסון
        /** מלכה יכולה להתקדם כמו בישופ לכן ניצור עצם בישופ והיתן למלכה את כל הצעדים האפשריים שלו
         */
        ArrayList<Board>check;

        Bishop bishop=new Bishop(null, 0, 0, null, null);
        check=bishop.checkMove(board, current);
        for(int i=0; i<check.size(); i++){
            posiblleNames.add(check.get(i).getName());
        }

        /** מלכה יכולה להתקדם כמו רוק לכן ניצור עצם רוק והיתן למלכה את כל הצעדים האפשריים שלו
         */
        Rook rook=new Rook(null, 0, 0, null, null);
        check=rook.checkMove(board, current);
        for(int i=0; i<check.size(); i++){
            posiblleNames.add(check.get(i).getName());
        }

        for(int i=0; i<posiblleNames.size(); i++){

            // שני התנאים הבאים מוודאים שכל המשבצות שעתידות להכנס לרשימה המוחזרת, עונות על תנאי הלוח ולא חורגות ממסגרתו
            if(posiblleNames.get(i).contains("-")){
                collectionName.add(posiblleNames.get(i));
            }

            if((Integer.parseInt(posiblleNames.get(i))/10)>8 || (Integer.parseInt(posiblleNames.get(i))%10)>8){
                collectionName.add(posiblleNames.get(i));

            } }

        posiblleNames.removeAll(collectionName);

        //  בשביל לוודא שבאמת אפשר לזוז למשבצות המצויינות, נוודא שאין שם עוד שחקן בצבע זהה
        for(int i=0; i<board.size(); i++){
            for(int j=0; j<posiblleNames.size(); j++){
                if(posiblleNames.get(j).equals(board.get(i).getName())){
                    if(!board.get(i).getColorOn().equals(this.getColor())){
                        canMove.add(board.get(i));
                        break;

                    } } } }

        return canMove;
    }
}
