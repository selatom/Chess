package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class King extends Soldires{

    public King(String name, Bitmap bm, int x, int y, String color, Board board) {
        super(name, bm, x, y, color, board, 100000);
    }


    // מקבל את לוח המשבצות ואת המשבצת שלחצו עליה מחזירה מערך של משבצות שהסוס יכול להתקדם אליהן
    @Override
    public ArrayList<Board> checkMove(ArrayList<Board> board, Board current) {
        ArrayList<String>collectionName=new ArrayList<>();
        ArrayList<String>posiblleNames=new ArrayList<>();
        ArrayList<Board>canMove=new ArrayList<>();
        int width=current.getrow();
        int hieght=current.getcolumn();


        posiblleNames.add(width+1+""+(hieght+1)); // top right
        posiblleNames.add(width-1+""+(hieght+1)); // top left
        posiblleNames.add(width+1+""+(hieght-1)); // bottom right
        posiblleNames.add(width-1+""+(hieght-1)); // bottom left
        posiblleNames.add(width+""+(hieght+1)); // top
        posiblleNames.add(width+""+(hieght-1)); // bottom
        posiblleNames.add(width+1+""+hieght); // right
        posiblleNames.add(width-1+""+hieght); // left


        // The following two conditions ensure that all the squares that are to be included in the returned list meet the conditions of the board and do not exceed its scope.
        for(int i=0; i<posiblleNames.size(); i++)
        {
            if(posiblleNames.get(i).contains("-"))
            {
                collectionName.add(posiblleNames.get(i));
            }

            if((Integer.parseInt(posiblleNames.get(i)) / 10) > 8 || (Integer.parseInt(posiblleNames.get(i)) % 10) > 8)
            {
                collectionName.add(posiblleNames.get(i));
            }
        }
        posiblleNames.removeAll(collectionName);

        //  בשביל לוודא שבאמת אפשר לזוז למשבצות המצויינות, נוודא שאין שם עוד שחקן בצבע זהה
        for(int i=0; i<board.size(); i++)
        {
            for(int j=0; j<posiblleNames.size(); j++)
            {
                if(posiblleNames.get(j).equals(board.get(i).getName()))
                {
                    if(!board.get(i).getColorOn().equals(this.getColor()))
                    {
                        canMove.add(board.get(i));
                        break;
                    }
                }
            }
        }

        // Make sure that in all possible moves there is no threat to the king
        ArrayList<Board>collectionBoard=new ArrayList<>();
        for(Board go : canMove)
        {
            if(this.isChess(board, go))
            {
                collectionBoard.add(go);
            }
        }
        canMove.removeAll(collectionBoard);

        return canMove;
    }

    public King copyPiece(){
        return new King(
                this.getName(),
                this.getBm(),
                this.getX(),
                this.getY(),
                this.getColor(),
                new Board(this.getBoard()));
    }
}
