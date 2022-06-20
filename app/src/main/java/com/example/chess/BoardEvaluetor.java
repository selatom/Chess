package com.example.chess;

import java.util.ArrayList;

public interface BoardEvaluetor {
    int evaluate(ArrayList<Board>board, int depth, ArrayList<Soldires>white, ArrayList<Soldires>black);
}
