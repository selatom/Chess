package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Bishop extends Soldires{
    private static final String TAG = "Bishop";

    public Bishop(Bitmap bm, int x, int y, String color, Board board) {
        super("bishop", bm, x, y, color, board);
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

        // רץ יכול להתקדם כמה שהוא רוצה באלכסון
        int temp=width+1;
        // תחתון ימני
        for(int i=hieght+1; i<9; i++) {
            if (GameView.getcolorFromName((temp + "" + i), board).equals(this.getColor())) {
                break;// אם יש על המשבצת שחקן הוא חוסם את הדרך וצריך לצאת מהללולאה
            }
            if (temp < 9) {
                // מוודא שאין חריגה מרוחב השולחן
                posiblleNames.add(temp + "" + i);//מכניס את האלכסון התחתון ימני
                if (!GameView.getcolorFromName((temp + "" + i), board).equals("none")) { break; }// במידה והחייל מגיע למשבצת עם חייל שך השחקן השני, הוא יוכל לעלות על המשבצת אבל לא להמשיך
                temp++;
            } else { break; }
        }

        // עליון ימני
        temp=width+1;
        for(int i=hieght-1; i>0; i--){
            if(GameView.getcolorFromName((temp+""+i), board).equals(this.getColor())){
                break;// אם יש על המשבצת שחקן הוא חוסם את הדרך וצריך לצאת מהללולאה
            }
            if(temp<9){
                // מוודא שאין חריגה מרוחב השולחן
                posiblleNames.add(temp+""+i);// אלכסון עליון שמאלי
                if(!GameView.getcolorFromName((temp+""+i),board).equals("none")) { break; }// במידה והחייל מגיע למשבצת עם חייל שך השחקן השני, הוא יוכל לעלות על המשבצת אבל לא להמשיך
                temp++;
            }else { break; }
        }

        // תחתון שמאלי
        temp=width-1;
        for(int i=hieght+1; i<9; i++) {
            if (GameView.getcolorFromName((temp + "" + i), board).equals(this.getColor())) {
                break;// אם יש על המשבצת שחקן הוא חוסם את הדרך וצריך לצאת מהללולאה
            }
            if (temp > 0) {
                // מוודא שאין חריגה מרוחב השולחן
                posiblleNames.add(temp + "" + i);// אלכסון תחתון שמאלי
                if (!GameView.getcolorFromName((temp + "" + i), board).equals("none")) { break; }// במידה והחייל מגיע למשבצת עם חייל שך השחקן השני, הוא יוכל לעלות על המשבצת אבל לא להמשיך
                temp--;
            } else { break; }
        }
        // עליון שמאלי
        temp=width-1;
        for(int i=hieght-1; i>0; i--){
            if(GameView.getcolorFromName((temp+""+i), board).equals(this.getColor())){
                break;// אם יש על המשבצת שחקן הוא חוסם את הדרך וצריך לצאת מהללולאה
            }
            if(temp>0){
                // מוודא שאין חריגה מרוחב השולחן
                posiblleNames.add(temp+""+i);// אלכסון עליון ימני
                if(!GameView.getcolorFromName((temp+""+i), board).equals("none")) { break; }// במידה והחייל מגיע למשבצת עם חייל שך השחקן השני, הוא יוכל לעלות על המשבצת אבל לא להמשיך
                temp--;
            }else { break; }
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
