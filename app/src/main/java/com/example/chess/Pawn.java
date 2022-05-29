package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Pawn extends Soldires{
    private static final String TAG = "Pawn";

    public Pawn(Bitmap bm, int x, int y, String color, Board board) {
        super("pawn", bm, x, y, color, board);
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

        // חייל יכול ללכת רק צעד אחד קדימה אלה אם זה התור הראשון ואז זה שני צעדים קדימה או במקרה של אכילה, גם באלכסון
        posiblleNames.add((width+1)+""+(hieght-1));  posiblleNames.add((width-1)+""+(hieght-1));  posiblleNames.add((width+1)+""+(hieght+1));  posiblleNames.add((width-1)+""+(hieght+1));

        // לולאה שבודקת האם באמת יש לחייל אופציה לאכול חייל של השחקן השני
        for(int i=0; i<board.size(); i++){
            for(int j=0; j<posiblleNames.size(); j++){
                if(posiblleNames.get(j).equals(board.get(i).getName())){
                    if(board.get(i).getColorOn().equals(this.getColor()) || board.get(i).getColorOn().equals("none")) { collectionName.add(posiblleNames.get(j)); }

                } } }

        posiblleNames.removeAll(collectionName);

        // נוסיך למהלכים האפשריים את האופציות שחישבנו עד כה
        for(int i=0; i<board.size(); i++){
            for(int j=0; j<posiblleNames.size(); j++){
                if(posiblleNames.get(j).equals(board.get(i).getName())){
                        canMove.add(board.get(i));
                        break;

                    } } }
        posiblleNames.clear();

        if(this.getColor().equals("black")){
            // במידה והשחור משחק, בשביל לדעת לאיזה כיוון הוא זז צריך לדעת את הצבע שלו
            posiblleNames.add(width+""+(hieght+1));
            if(GameView.firstmove){posiblleNames.add((width)+""+(hieght+2));}
        }else{
            // במידה והלבן משחק, בשביל לדעת לאיזה כיוון הוא זז צריך לדעת את הצבע שלו
            posiblleNames.add(width+""+(hieght-1));
            if(GameView.firstmove){posiblleNames.add(width+""+(hieght-2));} }

        collectionName=new ArrayList<>();
        for(int i=0; i<posiblleNames.size(); i++){

            // שני התנאים הבאים מוודאים שכל המשבצות שעתידות להכנס לרשימה המוחזרת, עונות על תנאי הלוח ולא חורגות ממסגרתו
            if(posiblleNames.get(i).contains("-")){
                collectionName.add(posiblleNames.get(i));
            }

            if((Integer.parseInt(posiblleNames.get(i))/10)>8 || (Integer.parseInt(posiblleNames.get(i))%10)>8){
                collectionName.add(posiblleNames.get(i));
            } }
        posiblleNames.removeAll(collectionName);

        // במצב שהחייל הולך ישר, אסור שיהיה שם חייל של היריב
        for(int i=0; i<board.size(); i++){
            for(int j=0; j<posiblleNames.size(); j++){
                // החייל יוכל להתקדם ישר בתנאי שלא יהיה שם חייל שלו או של יריבו. נבדוק זאת בתנאי ואם זה עונה על התנאי, נוסיף אותו למהלכים אפשריים
                if(posiblleNames.get(j).equals(board.get(i).getName())){
                    if(board.get(i).getColorOn().equals("none")) {
                        canMove.add(board.get(i));
                        break;

                    } } } }
        // posiblleNames.removeAll(collectionName);

//        //  בשביל לוודא שבאמת אפשר לזוז למשבצות המצויינות, נוודא שאין שם עוד שחקן בצבע זהה
//        for(int i=0; i<board.size(); i++){
//            for(int j=0; j<posiblleNames.size(); j++){
//                if(posiblleNames.get(j).equals(board.get(i).getName())){
//                    if(!board.get(i).getColorOn().equals(this.getColor())){
//                        canMove.add(board.get(i));
//                        break;
//
//                    } } } }

        return canMove;
    }


}
