package com.example.chess;

import java.util.ArrayList;

public final class StandardBoardEvaluator implements BoardEvaluetor {
    private static final int CHESSMATE_BONUS=10000;
    private static final int CHECK_BONUS=50;
    private static final int DEPTH_BONUS=100;

    /**
     * @param board Square board
     * @param depth The current depth of the Mini Max
     * @param white The White player's Soldiers
     * @param black The Black player's Soldiers
     *
     * @return Estimate the number of points on the board for each of the players
     */
    @Override
    public int evaluate(ArrayList<Board> board, int depth, ArrayList<Soldires>white, ArrayList<Soldires>black) {

        return scorePlayer(board, white, depth, black) - scorePlayer(board, black, depth, white);
    }


    /**
     * @param boards Square board
     * @param player The current player's Soldiers
     * @param depth The current depth of the Mini Max
     * @param other The opponent player's Soldiers
     *
     * @return Estimation of the number of points on the board for the current player
     */
    private int scorePlayer(ArrayList<Board>boards, ArrayList<Soldires>player, int depth, ArrayList<Soldires>other){
        return pieceValue(player) +
               mobility(player, boards) +
               check(player, boards, other) +
               checkmate(boards, player, depth, other);
    }


    /**
     * @param pieces The current player's Soldiers
     * @return The score the current player has on his soldiers
     */
    private static int pieceValue(ArrayList<Soldires>pieces){
        int pieceValueScore = 0;

        for(Soldires soldires : pieces)
        {
            pieceValueScore += soldires.getScore();
        }

        return pieceValueScore;
    }

    /**
     * @param boards Squares board
     * @param player The current player's Soldiers
     * @param depth The current depth of the Mini Max
     * @param other The opponent player's Soldiers
     * @return Raises points if there is a checkmate
     */
    private int checkmate(ArrayList<Board>boards, ArrayList<Soldires>player, int depth, ArrayList<Soldires>other){
        Soldires king = null;

        // find the king
        for(Soldires s : other)
        {
            if(s.getName().contains("king"))
            {
                king=new King(s.getName(),
                              s.getBm(),
                              s.getX(),
                              s.getY(),
                              s.getColor(),
                              s.getBoard());
                break;
            }
        }

        // CHECK MATE
        if( king == null || king.isMat(boards, player, king.checkMove(boards, king.getBoard()).size()))
        {
            if(player.get(0).getName().contains("B")) { MiniMax.endGame = true; }
            if(player.get(0).getName().contains("W")) { MiniMax.looseIf = true; }

            return CHESSMATE_BONUS * depthBonus(depth);

       }
        else return 0;
    }


    /**
     * @param depth The current depth of the Mini Max
     * @return The depth bonus according to the parameter
     */
    private static int depthBonus(int depth){
        return depth == 0 ? 1 : depth * DEPTH_BONUS;
    }


    /**
     * @param player The current player's Soldiers
     * @param boards Squares board
     * @param other The opponent player's Soldiers
     * @return The amount of points that will go up if the current player made his opponent check
     */
    private static int check(ArrayList<Soldires>player, ArrayList<Board>boards, ArrayList<Soldires>other){
        Soldires king = null;

        // find the king
        for(Soldires s : other)
        {
            if(s.getName().contains("king")){
                king = new King(s.getName(),
                        s.getBm(),
                        s.getX(),
                        s.getY(),
                        s.getColor(),
                        s.getBoard());
                break;

            }
        }

        return king == null || king.isChess(boards, player) ? CHECK_BONUS : 0;
    }


    /**
     * @param player The current player's Soldiers
     * @param boards Squares board
     * @return Returns a score on the player's mobility level
     */
    private static int mobility(ArrayList<Soldires>player, ArrayList<Board>boards){
        return getLegalMoves(player, boards).size();
    }


    /**
     * @param pieces Soldiers of one of the players
     * @param boards Squares board
     * @return All the legal steps of the array of soldiers received as a parameter
     */
    public static ArrayList<Board>getLegalMoves(ArrayList<Soldires>pieces, ArrayList<Board>boards){
        ArrayList<Board>legal=new ArrayList<>();

        for( Soldires soldires : pieces)
        {
            legal.addAll(soldires.checkMove(boards, soldires.getBoard()));
        }

        return legal;
    }
}
