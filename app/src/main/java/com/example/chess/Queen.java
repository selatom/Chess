package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Queen extends Soldires{
    private static final String TAG = "Queen";

    public Queen(String name, Bitmap bm, int x, int y, String color, Board board) {
        super(name, bm, x, y, color, board, 1000);
    }

    /**
     * @param board Square list of the board
     * @param current The specific square from which you want to move the soldier
     * @return A list of squares into which the soldier can be moved
     */
    @Override
    public ArrayList<Board> checkMove(ArrayList<Board> board, Board current) {
        Bishop bishop=new Bishop("", null, 0, 0, null, null);
        Rook rook=new Rook("", null, 0, 0, null, null);

        ArrayList<String>collectionName=new ArrayList<>();
        ArrayList<String>posiblleNames=new ArrayList<>();
        ArrayList<Board>canMove=new ArrayList<>();

        // The Queen's possible steps are a combination of the steps of the Rook and the Bishop

        ArrayList<Board>check;
        check=bishop.checkMove(board, current);
        // BISHOP
        for(Board b : check)
        {
            posiblleNames.add(b.getName());
        }


        check=rook.checkMove(board, current);
        //ROOK
        for(Board b : check)
        {
            posiblleNames.add(b.getName());
        }

        // The following two conditions ensure that all the squares that are to be included in the returned list meet the conditions of the board and do not exceed its scope.
        for(int i=0; i<posiblleNames.size(); i++)
        {
            if(posiblleNames.get(i).contains("-"))
            {
                collectionName.add(posiblleNames.get(i));
            }

            if((Integer.parseInt(posiblleNames.get(i))/10)>8 || (Integer.parseInt(posiblleNames.get(i))%10)>8)
            {
                collectionName.add(posiblleNames.get(i));

            }
        }

        posiblleNames.removeAll(collectionName);

        //  בשביל לוודא שבאמת אפשר לזוז למשבצות המצויינות, נוודא שאין שם עוד שחקן בצבע זהה
        for(Board go : board)
        {
            for(String name : posiblleNames)
            {
                if(name.equals(go.getName()))
                {
                    if(!go.getColorOn().equals(this.getColor()))
                    {
                        canMove.add(go);
                        break;

                    }
                }
            }
        }

        return canMove;
    }

    public Queen copyPiece(){
        return new Queen(
                this.getName(),
                this.getBm(),
                this.getX(),
                this.getY(),
                this.getColor(),
                new Board(this.getBoard()));
    }
}
