package com.example.chess;

import android.util.Log;

import java.util.ArrayList;

/**
 * MiniMax is an algorithm that calculates the best move the computer can make in a game.
 * He does this through two methods, min and max.
 * One of the players is Min and the other is Max.
 * MiniMax work on recursion. Min calls Max and Max calls Min each time with a different value to the game board depending on the move each of them makes.
 * In addition, with the help of {@StandardBoardEvaluator}, the minimax gives a score to the game board according to possible moves and soldiers in the game.
 * The goal of the min player is for the score to be the lowest and the goal for the max is to be the highest.
 * The operation goes through all the possible steps in the current round and receives as a parameter the number of
 * future steps it needs to go through as well and returns the best move to the computer depending on whether it is min or max.
 * To save running time, you can see here the use of the alpha beta sorting method.
 * The method will reduce the testing of moves that already from their initial testing phase,
 * can be seen that their value does not exceed the value of the best move recorded up to that moment.
 */
public class MiniMax implements MOveStrategy{
    private static final String TAG = "MiniMax";

    private final ArrayList<String>eatenSoldire;
    private final BoardEvaluetor boardEvaluator;
    private Soldires soldiresPlay = null;
    private ArrayList<Soldires>whiteFake;
    private ArrayList<Soldires>blackFake;
    private ArrayList<Board> boardFake;
    private ArrayList<Soldires>allFake;
    private final int  searchDepth;
    static  boolean  isMat = false;
    static boolean endGame = false;
    static boolean looseIf = false;


    /**
     * @param searchDepth The number of future moves the computer will think about
     */
    public MiniMax(int searchDepth) {
        this.boardEvaluator = new StandardBoardEvaluator();
        this.eatenSoldire = new ArrayList<>();
        this.searchDepth = searchDepth;

        makeFakeBoard();
    }

    @Override
    public Board execute(ArrayList<Board> board, int alpha, int beta) {
        ArrayList<String>legal = getLegalMovesA(currentPlayer());
        ArrayList<Board>copyBoard = new ArrayList<>();
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue =  Integer.MAX_VALUE;
        int currentValue;

        Board bestMove = null;

        // create a copy of the board
        for(Board b : GameView.arrBoard)
        {
            copyBoard.add(new Board(b));
        }

        for(String name : legal)
        {
            makeFakeBoard();
            eatenSoldire.clear();

            Board squre = Board.getBoardFromName(name.substring(0, 2), boardFake);
            GameView.soldierPressed = getSoldiresFromName(name.substring(2));

            fakeMove(squre);

            // White - min , Black - max
            currentValue = GameView.colorNow.equals("white") ?
                    min(boardFake, (this.searchDepth-1), alpha, beta) :
                    max(boardFake, (this.searchDepth - 1), alpha, beta);

            // If there is a check mate before we have gone through all the moves, the action will stop
            if(endGame)
            {
                GameView.soldierPressed = getSoldiresFromName(name.substring(2));
                return squre;
            }

            // If it's White's turn and the value returned from min method is the smallest so far, it means it's the best move for the white player so far.
            if(GameView.colorNow.equals("white") && currentValue >= highestSeenValue && (!looseIf))
            {
                soldiresPlay = getSoldiresFromName(name.substring(2)); // save the original soldier
                highestSeenValue=currentValue;
                bestMove=squre;

            }
            // If it's Black's turn and the value returned from max method is the biggest so far, it means it's the best move for the black player so far.
            else if(GameView.colorNow.equals("black") && currentValue <= lowestSeenValue && (!looseIf))
            {
                soldiresPlay = getSoldiresFromName(name.substring(2));
                lowestSeenValue=currentValue;
                bestMove=squre;
            }

            looseIf = false; // In each turn the parameter checks if your opponent has made you a checkmate. If so, he will skip this move
        }

        GameView.soldierPressed = soldiresPlay;
        GameView.arrBoard = copyBoard;
        GameView.colorNow  = "black";

        return bestMove;
    }

    /**
     *
     * @param board copy of the board
     * @param depth Number of future moves
     * @param alpha The best value recorded in the min operation
     * @param beta The best value recorded in the max operation
     * @return The value of the best move the white player (user) can make depending on the board position.
     *         The best value for the white player will be the value with the lowest score
     */
    public int min(final ArrayList<Board> board, final int depth, int alpha, int beta){
        Log.d(TAG, "min: "+depth);

        ArrayList<String> copyMaxEat = new ArrayList<>(eatenSoldire);
        ArrayList<Board>copyMaxBoard =  new ArrayList<>();
        ArrayList<Soldires>copyMaxAll = new ArrayList<>();

        GameView.colorNow="white";

        // Copies the updated board according to the move that the Max made
        for(Board b : boardFake)
        {
            copyMaxBoard.add(new Board(b));
        }

        // Copies the updated soldier according to the move that the Max made
        for(Soldires soldires : allFake)
        {
            copyMaxAll.add(soldires.copyPiece());
        }

        // If we have reached the end of the test of all possible future moves, or during the last one there was chess mate,
        // the value of the current move will be returned and the board will return to its previous state
        if(depth==0 || isEndGame())
        {
            updateValuesAfterMinMax(copyMaxBoard, copyMaxAll, copyMaxEat);
            return this.boardEvaluator.evaluate(board, depth, whiteFake, blackFake);
        }

        int lowestSeenValue=Integer.MAX_VALUE;
        ArrayList<String>legal=getLegalMovesA(currentPlayer());

        // Goes through all the possible moves of the Min player
        for(String name : legal)
        {
            // If the soldier of the current move is still in the game
            if(!eatenSoldire.contains(name.substring(2)))
            {
                Board squre = Board.getBoardFromName(name.substring(0, 2), boardFake);
                GameView.soldierPressed = getSoldiresFromName(name.substring(2));

                fakeMove(squre);
                // The action of the Min performs the move and calls for the action of the max with the updated board to see how the other player can react
                final int currentValue = max(board, (depth-1), alpha, beta);

                updateValuesAfterMinMax(copyMaxBoard, copyMaxAll, copyMaxEat);

                // If the current move is the best move the Min player can make
                if(currentValue <= lowestSeenValue)
                {
                    beta = currentValue;
                    lowestSeenValue = currentValue;

                }
            }

            // Sort Alpha Beta.
            // If the value of the current move is smaller than the value of the best move so far,
            // it means that the current move will become the best move (because in Min method the best move has the lowest value),
            // so there is no need to keep checking how the opponent will react to the current move.
            // Whichever move the opponent makes, the value of the board will still be the lowest.
            if(beta <= alpha)
            {
                break;
            }
        }

        return lowestSeenValue;
    }


    public int max(final ArrayList<Board> board, final int depth, int alpha, int beta){ 
        Log.d(TAG, "max: "+depth+" "+eatenSoldire.size());

        ArrayList<String> copyExcuteEat = new ArrayList<>(eatenSoldire);
        ArrayList<Board>copyExcuteBoard =  new ArrayList<>();
        ArrayList<Soldires>copyExcuteAll = new ArrayList<>();

        GameView.colorNow="black";

        // Copies the updated board according to the move that the Min made
        for(Board b : boardFake)
        {
            copyExcuteBoard.add(new Board(b));
        }

        // Copies the updated soldier according to the move that the Max made
        for(Soldires soldires : allFake)
        {
            copyExcuteAll.add(soldires.copyPiece());
        }

        // If we have reached the end of the test of all possible future moves, or during the last one there was chess mate,
        // the value of the current move will be returned and the board will return to its previous state
        if(depth==0 || isEndGame())
        {
            updateValuesAfterMinMax(copyExcuteBoard,  copyExcuteAll, copyExcuteEat);
            return this.boardEvaluator.evaluate(board, depth, whiteFake, blackFake);
        }



        int highestSeenValue=Integer.MIN_VALUE;
        ArrayList<String>legal=getLegalMovesA(currentPlayer());

        // Goes through all the possible moves of the Min player
        for(String name : legal)
        {
            // If the soldier of the current move is still in the game
            if(!eatenSoldire.contains(name.substring(2)))
            {
                Board squre = Board.getBoardFromName(name.substring(0, 2), boardFake);
                GameView.soldierPressed = getSoldiresFromName(name.substring(2));


                fakeMove(squre);
                // The action of the Max performs the move and calls for the action of the max with the updated board to see how the other player can react
                final int currentValue = min(board, (depth - 1), alpha, beta);

                updateValuesAfterMinMax(copyExcuteBoard, copyExcuteAll, copyExcuteEat);

                // If the current move is the best move the Max player can make
                if(currentValue >= highestSeenValue)
                {
                    alpha=currentValue;
                    highestSeenValue = currentValue;

                }

                // Sort Alpha Beta.
                // If the value of the current move is bigger than the value of the best move so far,
                // it means that the current move will become the best move (because in Max method the best move has the highest value),
                // so there is no need to keep checking how the opponent will react to the current move.
                // Whichever move the opponent makes, the value of the board will still be the highest
                if(beta<=alpha)
                {
                    break;
                }
            }
        }

        return highestSeenValue;
    }

    /**
     * @return True if one of the players wins the game
     */
    private boolean isEndGame(){
        Soldires king=null;

        // Finds the king of the current player
        for(Soldires soldires : otherPlayer())
        {
            if(soldires.getName().contains("king"))
            {
                king=soldires;
                break;
            }
        }

        // If one of the players ate the opponent's king (Mate)
        if(king == null)
        {
            isMat=true;
            return true;

        }
        // Check if one of the players is going to eat the opponent's king in the next turn
        else
        {
            // CHESS
            if(isChess(boardFake, king.getBoard()))
            {
                // MATE
                return isMat(king);
            }
        }

        return false;
    }


    // פעולה מקבלת את המלך ובודקת אם יש לו לאן ללכת במצב של שח. אם לא, מחזירה אמת אם כן מחזירה שקר
    public boolean isMat(Soldires soldires){
        ArrayList<Board>options=soldires.checkMove(boardFake, soldires.getBoard());
        Log.d(TAG, "isMat: "+options.size());
        return options.size() == 0;
    }


    /**
     * Creates a fake board according to the state of the game
     */
    private void makeFakeBoard(){
        boardFake=new ArrayList<>();
        whiteFake=new ArrayList<>();
        blackFake=new ArrayList<>();
        allFake = new ArrayList<>();

        // SQUARE BOARD
        for( Board board : GameView.arrBoard)
        {
            this.boardFake.add(new Board(board));
        }

        // WHITE SOLDIERS
        for( Soldires soldires : GameView.arrWhiteSoldires)
        {
            this.whiteFake.add(soldires.copyPiece());
        }

        // // WHITE SOLDIERS
        for( Soldires soldires : GameView.arrBlackSoldires)
        {
            this.blackFake.add(soldires.copyPiece());
        }

        allFake.addAll(whiteFake);
        allFake.addAll(blackFake);
    }

    /**
     * @param square A square to which you want to move the current soldier
     *
     * The action simulates the effect of the move on the
     * board and changes the board copy according to the move
     */
    public void fakeMove(Board square){

        for(int i=0; i<boardFake.size(); i++)
        {
            // OLD SQUARE
            if(GameView.soldierPressed.getBoard().getName().equals(boardFake.get(i).getName()))
            {
                boardFake.get(i).setColorOn("none");
                boardFake.get(i).setSoldires(null);
            }
        }


        // EAT OPPONENT PIECE
        if(square.getSoldires()!=null)
        {
            eatOpponent(square.getSoldires());
        }


        // UPDATE SOLDIER DETAILS
        for(int i=0; i<allFake.size(); i++)
        {
            if(allFake.get(i).equal(GameView.soldierPressed))
            {
                allFake.get(i).setX(square.getX());
                allFake.get(i).setY(square.getY());
                allFake.get(i).setBoard(square);
            }
        }


        // NEW SQUARE
        for(int i=0; i<boardFake.size(); i++)
        {
            if(square.getName().equals(boardFake.get(i).getName()))
            {
                boardFake.get(i).setColorOn(GameView.colorNow);
                boardFake.get(i).setSoldires(GameView.soldierPressed);
            }
        }
    }


    /**
     * @param soldire A soldier whose opponent ate
     * Updates the board according to the eating action
     */
    public void eatOpponent(Soldires soldire){
        eatenSoldire.add(soldire.getName());

        // EAT FROM BLACK
        if(GameView.colorNow.equals("white"))
        {
            for(Soldires s : blackFake)
            {
                if(s.equal(soldire))
                {
                    blackFake.remove(s);
                    break;

                }
            }
        }
        // EAT FROM WHITE
        else
        {
            for(Soldires s : whiteFake)
            {
                if(s.equal(soldire)){
                    whiteFake.remove(s);
                    break;

                }
            }
        }

        // Removes from soldiers list
        for(Soldires s : allFake)
        {
            if(s.equal(soldire))
            {
                allFake.remove(s);
                break;
            }
        }
    }

    /**
     * @param soldires An array of soldiers from one of the players
     * @return An array of all the squares to which the soldiers can move and the name of the soldier who can move to the square
     */
    private ArrayList<String>getLegalMovesA(ArrayList<Soldires>soldires){
        ArrayList<String>lega = new ArrayList<>();

        // Goes through all the soldiers
        for(Soldires s : soldires)
        {
            // Goes through all the possible squares of the current soldier
            for(int j=0; j < (s.checkMove(boardFake, s.getBoard())).size(); j++)
            {
                lega.add((s.checkMove(boardFake, s.getBoard())).get(j).getName() + s.getName());
            }
        }

        return lega;
    }


    /**
     * @return List of soldiers of the current player
     */
    private ArrayList<Soldires>currentPlayer(){
        // WHITE
        if(GameView.colorNow.equals("white"))
            return whiteFake;
        // BLACK
        else
            return blackFake;
    }


    /**
     * @return List of soldiers of the current player's opponent
     */
    private ArrayList<Soldires>otherPlayer()
    {
        // CURRENT WHITE
        if(GameView.colorNow.equals("white"))
            return blackFake;
        // CURRENT BLACK
        else
            return whiteFake;
    }


    /**
     * @param name Name of a soldier
     * @return A soldier-type object whose name is the name given to the method as a parameter
     */
    private Soldires getSoldiresFromName(String name){

        // Goes through all the soldiers
        for(Soldires soldires : allFake)
        {
            if(soldires.getName().equals(name))
                return soldires;
        }

        return null;
    }


    /**
     * @param boards The square board in its condition before the fake move
     * @param all An array of soldiers on the board before the fake move
     * @param eaten An array of the eaten soldiers
     *
     * Returns the game data to the state it was in before the fake move
     */
    public void updateValuesAfterMinMax(ArrayList<Board>boards, ArrayList<Soldires>all, ArrayList<String>eaten){
        this.eatenSoldire.clear();
        this.boardFake.clear();
        this.whiteFake.clear();
        this.blackFake.clear();
        this.allFake.clear();

        this.eatenSoldire.addAll(eaten);

        // UPDATE SQUARES
        for(Board board : boards)
        {
            this.boardFake.add(new Board(board));
        }

        // UPDATE SOLDIERS
        for(Soldires soldires : all)
        {
            this.allFake.add(soldires.copyPiece());
        }

        // UPDATE WHITE & BLACK
        for(Soldires soldires : all)
        {
            if(soldires.getName().contains("W"))
            {
                whiteFake.add(soldires);
            }
            else
            {
                blackFake.add(soldires);
            }
        }
    }


    /**
     * @param boards array of the square board
     * @param board A square on which there is a king who wants to check if he is threatened
     * @return Whether the king is threatened or not
     */
    public boolean isChess(ArrayList<Board>boards, Board board){
        ArrayList<Board>possibleMoves = new ArrayList<>();
        ArrayList<Soldires>player;
        player = otherPlayer();

        // Saves the opponent's possible moves
        for(Soldires s : player)
        {
            possibleMoves.addAll(s.checkMove(boards, s.getBoard()));
        }

        // Checks if one of the opponent's possible squares is equal to the king's
        for(Board b : possibleMoves)
        {
            if(b.getName().equals(board.getName()))
            {
                return  true;

            }
        }

        return false;
    }

}
