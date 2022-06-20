package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Rook extends Soldires{
    private static final String TAG = "Rook";

    public Rook(String name, Bitmap bm, int x, int y, String color, Board board) {
        super(name, bm, x, y, color, board, 600);
    }

    /**
     * @param board Square list of the board
     * @param current The specific square from which you want to move the soldier
     * @return A list of squares into which the soldier can be moved
     */
    @Override
    public ArrayList<Board> checkMove(ArrayList<Board> board, Board current) {
        ArrayList<String>collectionName = new ArrayList<>();
        ArrayList<String>posiblleNames = new ArrayList<>();
        ArrayList<Board>canMove = new ArrayList<>();
        int hieght = current.getcolumn();
        int width = current.getrow();

        // We will add to the array the squares that the Rook can go to. The Rook can move in a straight line in any direction
        // TOP ROW
        for(int i=hieght+1; i<9; i++)
        {
            // If a soldier of the same player blocks the soldier who wants to move
            if(GameView.getcolorFromName((width+""+(i)), board).equals(this.getColor()))
            {
                break;
            }
            posiblleNames.add(width+""+(i));

            // If there is a soldier of the opponent in the top row, the soldier will eat him and stop
            if(!GameView.getcolorFromName((width+""+(i)), board).equals("none"))
            {
                break;
            }
        }

        // BOTTOM ROW
        for(int i=hieght-1; i>0; i--)
        {
            // If a soldier of the same player blocks the soldier who wants to move
            if(GameView.getcolorFromName((width+""+(i)), board).equals(this.getColor()))
            {
                break;
            }
            posiblleNames.add(width+""+(i));

            // If there is a soldier of the opponent in the bottom row, the soldier will eat him and stop
            if(!GameView.getcolorFromName((width+""+(i)), board).equals("none"))
            {
                break;
            }
        }

        //RIGHT ROW
        for(int i=width+1; i<9; i++)
        {
            // If a soldier of the same player blocks the soldier who wants to move
            if(GameView.getcolorFromName((i+""+(hieght)), board).equals(this.getColor()))
            {
                break;
            }
            posiblleNames.add(i+""+(hieght));

            // If there is a soldier of the opponent in the right row, the soldier will eat him and stop
            if(!GameView.getcolorFromName((i+""+(hieght)), board).equals("none"))
            {
                break;
            }
        }

        //LEFT ROW
        for(int i=width-1; i>0; i--)
        {
            // If a soldier of the same player blocks the soldier who wants to move
            if(GameView.getcolorFromName((i+""+(hieght)), board).equals(this.getColor()))
            {
                break;
            }
            posiblleNames.add(i+""+(hieght));

            // If there is a soldier of the opponent in the right row, the soldier will eat him and stop
            if(!GameView.getcolorFromName((i+""+(hieght)), board).equals("none"))
            {
                break;
            }
        }

        // The following two conditions ensure that all the squares that are to be included in the returned list meet the conditions of the board and do not exceed its scope.
        for(String name : posiblleNames)
        {
            if(name.contains("-"))
            {
                collectionName.add(name);
            }

            if((Integer.parseInt(name) / 10) > 8 || (Integer.parseInt(name) % 10) > 8)
            {
                collectionName.add(name);

            }
        }

        posiblleNames.removeAll(collectionName);

        //  We will convert all the names of the possible squares to a square type object
        for(int i=0; i<board.size(); i++)
        {
            for(int j=0; j<posiblleNames.size(); j++)
            {
                if(posiblleNames.get(j).equals(board.get(i).getName()))
                {
                        canMove.add(board.get(i));
                        break;

                }
            }
        }

        return canMove;
    }

    /**
     * @return A soldier-type object with the same values as the current soldier
     */
    public Rook copyPiece(){
        return new Rook(
                this.getName(),
                this.getBm(),
                this.getX(),
                this.getY(),
                this.getColor(),
                new Board(this.getBoard()));
    }
}
