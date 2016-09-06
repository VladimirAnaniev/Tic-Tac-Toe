package com.example.vladimir.tic_tac_toe;

import java.util.ArrayList;

public class Board {
    private ArrayList<Integer> mBoardState;
    private Integer mWinner;

    public Board() {
        mBoardState = new ArrayList<>();
        for(int i=0;i<9;i++){
            mBoardState.add(0);
        }
    }

    public ArrayList<Integer> getBoardState() {
        return mBoardState;
    }

    public void resetBoard() {
        this.mBoardState = new Board().getBoardState();
        this.mWinner = Constants.NO_WINNER;
    }

    public void changeBoardState (Integer player, int position){
        this.mBoardState.set(position,player);
    }

    public Integer getWinner() {
        return this.mWinner;
    }

    public boolean hasGameEnded() {
        //Check all possible win conditions
        if (mBoardState.get(0) != 0) {
            if (mBoardState.get(0).equals(mBoardState.get(1)) && mBoardState.get(1).equals(mBoardState.get(2))) {
                mWinner = mBoardState.get(0);
                return true;
            }
            else if(mBoardState.get(0).equals(mBoardState.get(3)) && mBoardState.get(3).equals(mBoardState.get(6))){
                mWinner=mBoardState.get(0);
                return true;
            }
        }
        if(mBoardState.get(4) !=0){
            if (mBoardState.get(0).equals(mBoardState.get(4)) && mBoardState.get(4).equals(mBoardState.get(8))){
                mWinner=mBoardState.get(4);
                return true;
            }
            else if(mBoardState.get(2).equals(mBoardState.get(4)) && mBoardState.get(4).equals(mBoardState.get(6))){
                mWinner=mBoardState.get(4);
                return true;
            }
            else if(mBoardState.get(3).equals(mBoardState.get(4)) && mBoardState.get(4).equals(mBoardState.get(5))){
                mWinner=mBoardState.get(4);
                return true;
            }
            else if(mBoardState.get(1).equals(mBoardState.get(4)) && mBoardState.get(4).equals(mBoardState.get(7))){
                mWinner=mBoardState.get(4);
                return true;
            }
        }
        if(mBoardState.get(8) !=0){
            if(mBoardState.get(6).equals(mBoardState.get(7)) && mBoardState.get(7).equals(mBoardState.get(8))){
                mWinner=mBoardState.get(8);
                return true;
            }
            else if(mBoardState.get(2).equals(mBoardState.get(5)) && mBoardState.get(5).equals(mBoardState.get(8))){
                mWinner=mBoardState.get(8);
                return true;
            }
        }

        //No winner, check if there are empty spaces left
        for (int i:mBoardState) {
            if(i==0){
                return false;
            }
        }

        //No empty spaces, Game is draw
        mWinner=Constants.DRAW;
        return true;
    }
}
