package com.example.chess;

import com.example.chess.Board;

import java.util.ArrayList;

public interface MOveStrategy {

    Board execute(ArrayList<Board> board, int alpha, int beta);
}
