package com.example.vladimir.tic_tac_toe;

import java.util.ArrayList;

public class AIPlayer {
    Board mBoard;
    ArrayList<Integer> mBoardState;

    public AIPlayer(Board mBoard) {
        this.mBoard = mBoard;
        this.mBoardState = mBoard.getBoardState();
    }

    public Integer move(){
        Integer[] result = minimax(9,Constants.AI);
        return result[1];
    }

    private Integer[] minimax(Integer depth, Integer player) {
        ArrayList<Integer> nextMoves = generateMoves();

        Integer currentScore;
        Integer bestScore = (player.equals(Constants.AI)) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Integer bestMove=null;


        if(nextMoves.isEmpty()||depth==0){
            // Gameover or depth reached, evaluate score
            bestScore = evaluate();
        }
        else {
            for (Integer move : nextMoves) {
                //make each move before evaluating it's score
                mBoard.changeBoardState(player, move);

                //evaluate score
                if (player.equals(Constants.AI)) {  // AI is maximizing player
                    currentScore = minimax(depth - 1, Constants.PLAYER)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestMove=move;
                    }
                } else {  // PLAYER is minimizing player
                    currentScore = minimax(depth - 1, Constants.AI)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestMove=move;
                    }
                }

                //Revert move
                mBoard.changeBoardState(Constants.EMPTY_CELL, move);
            }
        }

        return new Integer[] {bestScore,bestMove};
    }

    private ArrayList<Integer> generateMoves(){
        ArrayList<Integer> nextMoves = new ArrayList<>();

        //Game has ended, no moves
        if(mBoard.hasGameEnded()){
            return nextMoves;
        }

        //Search for empty cells
        ArrayList<Integer> currentBoardState = mBoard.getBoardState();
        for(int i=0;i<9;i++){
            if(currentBoardState.get(i).equals(Constants.EMPTY_CELL)){
                nextMoves.add(i);
            }
        }

        //Return array of all possible moves
        return nextMoves;
    }

    private Integer evaluate(){
        Integer score=0;
        //evaluate and add score for all rows,cols,diagonals
        score+=evaluateLine(0,1,2);
        score+=evaluateLine(3,4,5);
        score+=evaluateLine(6,7,8);
        score+=evaluateLine(0,3,6);
        score+=evaluateLine(1,4,7);
        score+=evaluateLine(2,5,8);
        score+=evaluateLine(0,4,8);
        score+=evaluateLine(2,4,6);

        return score;
    }

    private Integer evaluateLine(Integer p1, Integer p2, Integer p3){
        Integer score = 0;


        // First cell
        if (mBoardState.get(p1).equals(Constants.AI)) {
            score = 1;
        } else if (p1.equals(Constants.PLAYER)) {
            score = -1;
        }

        // Second cell
        if (mBoardState.get(p2).equals(Constants.AI)) {
            if (score == 1) {   // cell1 is AI's
                score = 10;
            } else if (score == -1) {  // cell1 is Player's

            } else {  // cell1 is empty
                score = 1;
            }
        }
        else if (mBoardState.get(p2).equals(Constants.PLAYER)) {
            if (score == -1) { // cell1 is Player's
                score = -10;
            } else if (score == 1) { // cell1 is AI's

            } else {  // cell1 is empty
                score = -1;
            }
        }

        // Third cell
        if (mBoardState.get(p3).equals(Constants.AI)) {
            if (score > 0) {  // cell1 and/or cell2 is AI's
                score *= 10;
            } else if (score < 0) {  // cell1 and/or cell2 is Player's
                return 0;
            } else {  // cell1 and cell2 are empty
                score = 1;
            }
        } else if (mBoardState.get(p3).equals(Constants.PLAYER)) {
            if (score < 0) {  // cell1 and/or cell2 is Player's
                score *= 10;
            } else if (score > 1) {  // cell1 and/or cell2 is AI's
                return 0;
            } else {  // cell1 and cell2 are empty
                score = -1;
            }
        }
        return score;
    }
}
