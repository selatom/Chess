package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Knight extends Soldires{
    private static final String TAG = "Knight";

    public Knight(Bitmap bm, int x, int y, String color, Board board) {
        super("knight", bm, x, y, color, board);
    }

    @Override
    // מקבל את לוח המשבצות ואת המשבצת שלחצו עליה מחזירה מערך של משבצות שהסוס יכול להתקדם אליהן
    public ArrayList<Board> checkMove(ArrayList<Board> board, Board current) {
        ArrayList<Board>canMove=new ArrayList<>();

        int width, hieght;
        ArrayList<String>posiblleNames=new ArrayList<>();

        width=current.getrow();
        hieght=current.getcolumn();

        // סוס יכול להתקדם שניים קדימה אחורה או לצדדים ואז אחד לכיוון אחר שהוא לא הפוך
        posiblleNames.add((width+2)+""+(hieght+1));  posiblleNames.add((width-2)+""+(hieght+1));  posiblleNames.add((width+1)+""+(hieght+2));
        posiblleNames.add((width-1)+""+(hieght+2));  posiblleNames.add((width+1)+""+(hieght-2));  posiblleNames.add((width+-2)+""+(hieght-1));
        posiblleNames.add((width-1)+""+(hieght-2));  posiblleNames.add((width+2)+""+(hieght-1));

        for(int i=0; i<posiblleNames.size(); i++){
            // שני התנאים הבאים מוודאים שכל המשבצות שעתידות להכנס לרשימה המוחזרת, עונות על תנאי הלוח ולא חורגות ממסגרתו
            if(posiblleNames.get(i).contains("-")){
                posiblleNames.remove(i);
                i=i-1;
            }

            if((Integer.parseInt(posiblleNames.get(i))/10)>8 || (Integer.parseInt(posiblleNames.get(i))%10)>8){
                posiblleNames.remove(i);
                i=i-1;
            }
        }
        //  בשביל לוודא שבאמת אפשר לזוז למשבצות המצויינות, נוודא שאין שם עוד שחקן בצבע זהה
        for(int i=0; i<board.size(); i++){
            for(int j=0; j<posiblleNames.size(); j++){
                if(posiblleNames.get(j).equals(board.get(i).getName())){
                    if(!board.get(i).getColorOn().equals(this.getColor()) || board.get(i).getColorOn().equals("None")){
                        canMove.add(board.get(i));
                        break;
                    }
                }
            }
        }
        return canMove;
    }
}
