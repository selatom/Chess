package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class King extends Soldires{

    public King(Bitmap bm, int x, int y, String color, Board board) {
        super("King", bm, x, y, color, board);
    }


    // מקבל את לוח המשבצות ואת המשבצת שלחצו עליה מחזירה מערך של משבצות שהסוס יכול להתקדם אליהן
    @Override
    public ArrayList<Board> checkMove(ArrayList<Board> board, Board current) {
        ArrayList<Board>canMove=new ArrayList<>();
        ArrayList<String>collectionName=new ArrayList<>();
        ArrayList<String>posiblleNames=new ArrayList<>();
        int width, hieght;

        width=current.getrow();
        hieght=current.getcolumn();

        // מלך יכול לזוז פעם אחת לאיזה כיוון שייבחר
        posiblleNames.add(width+1+""+hieght);  posiblleNames.add(width-1+""+hieght);  posiblleNames.add(width+""+(hieght+1));  posiblleNames.add(width+""+(hieght-1));
        posiblleNames.add(width+1+""+(hieght+1));  posiblleNames.add(width-1+""+(hieght+1));  posiblleNames.add(width+1+""+(hieght-1));  posiblleNames.add(width-1+""+(hieght-1));

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

        // מוודא שבאף המהלכים אין שח, אם כן, מוציא אותו מרשימת המהלכים האפשריים
        ArrayList<Board>collectionBoard=new ArrayList<>();
        for(int i=0; i<canMove.size(); i++){
            if(this.isChess(board, canMove.get(i))){
                collectionBoard.add(canMove.get(i));
            } }
        canMove.removeAll(collectionBoard);

        return canMove;
    }


}
