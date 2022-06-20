package com.example.chess;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Bishop extends Soldires{

    public Bishop(String name, Bitmap bm, int x, int y, String color, Board board) {
        super(name, bm, x, y, color, board, 300);
    }


    // מקבל את לוח המשבצות ואת המשבצת שלחצו עליה מחזירה מערך של משבצות שהסוס יכול להתקדם אליהן
    @Override
    public ArrayList<Board> checkMove(ArrayList<Board> board, Board current) {
        ArrayList<String>collectionName=new ArrayList<>();
        ArrayList<String>posiblleNames=new ArrayList<>();
        ArrayList<Board>canMove=new ArrayList<>();
        int hieght=current.getcolumn();
        int width=current.getrow();
        int temp=width+1;


        // The soldier can take as many steps as he wants diagonally
        // BOTTOM RIGHT
        for(int i=hieght+1; i<9; i++)
        {
            //  If a soldier of the same player blocks the soldier who wants to move
            if (GameView.getcolorFromName((temp + "" + i), board).equals(this.getColor()))
            {
                break;
            }

            // Make sure there is no deviation from the table width
            if (temp < 9)
            {
                posiblleNames.add(temp + "" + i);

                // If there is a soldier of the opponent in the bottom right, the soldier will eat him and stop
                if (!GameView.getcolorFromName((temp + "" + i), board).equals("none"))
                {
                    break;
                }

                temp++;
            }
            else
            {
                break;
            }
        }


        temp=width+1;
        // TOP RIGHT
        for(int i=hieght-1; i>0; i--)
        {
            //  If a soldier of the same player blocks the soldier who wants to move
            if(GameView.getcolorFromName((temp+""+i), board).equals(this.getColor()))
            {
                break;
            }

            // Make sure there is no deviation from the table width
            if(temp<9)
            {
                posiblleNames.add(temp+""+i);

                // If there is a soldier of the opponent in the top right, the soldier will eat him and stop
                if(!GameView.getcolorFromName((temp+""+i),board).equals("none"))
                {
                    break;
                }

                temp++;
            }
            else
            {
                break;
            }
        }


        temp=width-1;
        // BOTTOM LEFT
        for(int i=hieght+1; i<9; i++)
        {
            //  If a soldier of the same player blocks the soldier who wants to move
            if (GameView.getcolorFromName((temp + "" + i), board).equals(this.getColor()))
            {
                break;
            }

            // Make sure there is no deviation from the table width
            if (temp > 0)
            {
                posiblleNames.add(temp + "" + i);

                // If there is a soldier of the opponent in the bottom left, the soldier will eat him and stop
                if (!GameView.getcolorFromName((temp + "" + i), board).equals("none"))
                {
                    break;
                }

                temp--;
            }
            else {
                break;
            }
        }


        temp=width-1;
        // TOP LEFT
        for(int i=hieght-1; i>0; i--)
        {
            //  If a soldier of the same player blocks the soldier who wants to move
            if(GameView.getcolorFromName((temp+""+i), board).equals(this.getColor()))
            {
                break;
            }

            // Make sure there is no deviation from the table width
            if(temp>0)
            {
                posiblleNames.add(temp+""+i);

                // If there is a soldier of the opponent in the top left, the soldier will eat him and stop
                if(!GameView.getcolorFromName((temp+""+i), board).equals("none"))
                {
                    break;
                }

                temp--;
            }
            else
            {
                break;
            }
        }


        //  בשביל לוודא שבאמת אפשר לזוז למשבצות המצויינות, נוודא שאין שם עוד שחקן בצבע זהה
        for(int i=0; i<board.size(); i++)
        {
            for(int j=0; j<posiblleNames.size(); j++)
            {
                if(posiblleNames.get(j).equals(board.get(i).getName()))
                {
                    {
                        canMove.add(board.get(i));
                        break;

                    }
                }
            }
        }

        return canMove;
    }

    public Bishop copyPiece(){
        return new Bishop(
                this.getName(),
                this.getBm(),
                this.getX(),
                this.getY(),
                this.getColor(),
                new Board(this.getBoard()));
    }
}
