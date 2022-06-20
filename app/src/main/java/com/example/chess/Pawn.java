package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Pawn extends Soldires{
    private static final String TAG = "Pawn";

    public Pawn(String name, Bitmap bm, int x, int y, String color, Board board) {
        super(name, bm, x, y, color, board, 20);

    }

    /**
     * @param board Square list of the board
     * @param current The specific square from which you want to move the soldier
     * @return A list of squares into which the soldier can be moved
     */
    @Override
    public ArrayList<Board> checkMove(ArrayList<Board> board, Board current) {
        ArrayList<String>collectionName=new ArrayList<>();
        ArrayList<String>posiblleNames=new ArrayList<>();
        ArrayList<Board>canMove=new ArrayList<>();
        int hieght=current.getcolumn();
        int width=current.getrow();

        // A Pawn can move one step forward except in the first line where he can move two steps forward. In addition, if there is a rival soldier diagonally to the Pawn he will be able to eat him.

        // We will add the possible squares in case of eating the opponent - one step diagonally
        posiblleNames.add((width+1)+""+(hieght-1));
        posiblleNames.add((width-1)+""+(hieght-1));
        posiblleNames.add((width+1)+""+(hieght+1));
        posiblleNames.add((width-1)+""+(hieght+1));

        // Checks whether the soldier really has the option to eat the opponent. If not he removes the steps diagonally from the list
        for(Board go : board)
        {
            for(String name : posiblleNames)
            {
                if(name.equals(go.getName()))
                {
                    if(go.getColorOn().equals(this.getColor()) || go.getColorOn().equals("none"))
                    {
                        collectionName.add(name);
                    }

                }
            }
        }

        posiblleNames.removeAll(collectionName);

        // We will add to the array the options we have found so far
        for(Board go : board)
        {
            for(String name : posiblleNames)
            {
                if(name.equals(go.getName()))
                {
                        canMove.add(go);
                        break;

                }
            }
        }

        posiblleNames.clear();

        // Find which player is playing in the current queue to know the direction of his steps
        if(this.getColor().equals("black"))
        {
            //BLACK
            posiblleNames.add(width+""+(hieght+1));

            if(GameView.firstmove)
            {
                posiblleNames.add((width)+""+(hieght+2));
            }
        }
        else
        {
            // WHITE
            posiblleNames.add(width+""+(hieght-1));

            if(GameView.firstmove)
            {
                posiblleNames.add(width+""+(hieght-2));
            }
        }

        collectionName=new ArrayList<>();

        // The following two conditions ensure that all the squares that are to be included in the returned list meet the conditions of the board and do not exceed its scope.
        for(int i=0; i<posiblleNames.size(); i++)
        {
            if(posiblleNames.get(i).contains("-"))
            {
                collectionName.add(posiblleNames.get(i));

            }
            else if((Integer.parseInt(posiblleNames.get(i))/10)>8 || (Integer.parseInt(posiblleNames.get(i))%10)>8)
            {
                collectionName.add(posiblleNames.get(i));
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
                    if(board.get(i).getColorOn().equals("none"))
                    {
                        canMove.add(board.get(i));
                        break;

                    }
                }
            }
        }

        return canMove;
    }

    public Pawn copyPiece(){
        return new Pawn(
                this.getName(),
                this.getBm(),
                this.getX(),
                this.getY(),
                this.getColor(),
                new Board(this.getBoard()));
    }
}
