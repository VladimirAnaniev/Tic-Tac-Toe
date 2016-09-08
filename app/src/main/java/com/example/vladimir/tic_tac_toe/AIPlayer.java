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

    private Integer evaluate() {
        //+10 points for win, -10 for lose,0 for draw
        if (mBoard.hasGameEnded()) {
            if (mBoard.getWinner().equals(Constants.AI)) {
                return 10;
            } else if (mBoard.getWinner().equals(Constants.PLAYER)) {
                return -10;
            }
            else {
                return 0;
            }
        }
        return null;
    }
}
