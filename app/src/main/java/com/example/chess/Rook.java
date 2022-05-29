package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Rook extends Soldires{
    private static final String TAG = "Rook";

    public Rook(Bitmap bm, int x, int y, String color, Board board) {
        super("rook", bm, x, y, color, board);
    }


    // מקבל את לוח המשבצות ואת המשבצת שלחצו עליה מחזירה מערך של משבצות שהסוס יכול להתקדם אליהן
    @Override
    public ArrayList<Board> checkMove(ArrayList<Board> board, Board current) {
        ArrayList<Board>canMove=new ArrayList<>();
        ArrayList<String>collectionName=new ArrayList<>();
        int width, hieght;
        ArrayList<String>posiblleNames=new ArrayList<>();

        width=current.getrow();
        hieght=current.getcolumn();

        // צריח יכול ללכת כמה שהוא רוצה בתנאי שזה בקו ישר
        for(int i=hieght+1; i<9; i++){
            if(GameView.getcolorFromName((width+""+(i)), board).equals(this.getColor())){
                break;// אם יש על המשבצת שחקן מאותו הצבע הוא חוסם את הדרך וצריך לצאת מהללולאה
            }
            posiblleNames.add(width+""+(i));// מוסיף את כל המשבצות בשורה למעלה
            if(!GameView.getcolorFromName((width+""+(i)), board).equals("none")) { break; }// במידה והחייל מגיע למשבצת עם חייל שך השחקן השני, הוא יוכל לעלות על המשבצת אבל לא להמשיך
        }
        for(int i=hieght-1; i>0; i--){
            if(GameView.getcolorFromName((width+""+(i)), board).equals(this.getColor())){
                break;// אם יש על המשבצת שחקן מאותו הצבע הוא חוסם את הדרך וצריך לצאת מהללולאה
            }
            posiblleNames.add(width+""+(i));// משבצות בשורה למטה
            if(!GameView.getcolorFromName((width+""+(i)), board).equals("none")) { break; }// במידה והחייל מגיע למשבצת עם חייל שך השחקן השני, הוא יוכל לעלות על המשבצת אבל לא להמשיך
        }

        for(int i=width+1; i<9; i++){
            if(GameView.getcolorFromName((i+""+(hieght)), board).equals(this.getColor())){
                break;// אם יש על המשבצת שחקן מאותו הצבע הוא חוסם את הדרך וצריך לצאת מהללולאה
            }
            posiblleNames.add(i+""+(hieght));// משבצות בשורה ימינה
            if(!GameView.getcolorFromName((i+""+(hieght)), board).equals("none")) { break; }// במידה והחייל מגיע למשבצת עם חייל שך השחקן השני, הוא יוכל לעלות על המשבצת אבל לא להמשיך
        }
        for(int i=width-1; i>0; i--){
            if(GameView.getcolorFromName((i+""+(hieght)), board).equals(this.getColor())){
                break;// אם יש על המשבצת שחקן מאותו הצבע הוא חוסם את הדרך וצריך לצאת מהללולאה
            }
            posiblleNames.add(i+""+(hieght));// משבצות בשורה שמאלה
            if(!GameView.getcolorFromName((i+""+(hieght)), board).equals("none")) { break; }// במידה והחייל מגיע למשבצת עם חייל שך השחקן השני, הוא יוכל לעלות על המשבצת אבל לא להמשיך
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
