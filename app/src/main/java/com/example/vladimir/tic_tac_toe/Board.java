package com.example.vladimir.tic_tac_toe;

import java.util.ArrayList;

public class Board {
    private ArrayList<Integer> mBoardState;
    private Integer mWinner;
    private ArrayList<Integer> mWinningRow;

    public Board() {
        mBoardState = new ArrayList<>();
        for(int i=0;i<9;i++){
            mBoardState.add(Constants.EMPTY_CELL);
        }
        mWinningRow =  new ArrayList<>();
    }

    public ArrayList<Integer> getBoardState() {
        return mBoardState;
    }

    public void resetBoard() {
        this.mBoardState = new Board().getBoardState();
        this.mWinner = Constants.NO_WINNER;
        this.mWinningRow.clear();
    }

    public void changeBoardState (Integer player, int position){
        this.mBoardState.set(position,player);
    }

    public Integer getWinner() {
        return this.mWinner;
    }

    public ArrayList<Integer> getWinningRow() {
        return this.mWinningRow;
    }

    public boolean hasGameEnded() {
        mWinningRow.clear();
        //Check all possible win conditions
        if (!mBoardState.get(0).equals(Constants.EMPTY_CELL)) {
            if (mBoardState.get(0).equals(mBoardState.get(1)) && mBoardState.get(1).equals(mBoardState.get(2))) {
                mWinner = mBoardState.get(0);
                mWinningRow.add(0);
                mWinningRow.add(1);
                mWinningRow.add(2);
                return true;
            }
            else if(mBoardState.get(0).equals(mBoardState.get(3)) && mBoardState.get(3).equals(mBoardState.get(6))){
                mWinner=mBoardState.get(0);
                mWinningRow.add(0);
                mWinningRow.add(3);
                mWinningRow.add(6);
                return true;
            }
        }
        if(!mBoardState.get(4).equals(Constants.EMPTY_CELL)){
            if (mBoardState.get(0).equals(mBoardState.get(4)) && mBoardState.get(4).equals(mBoardState.get(8))){
                mWinner=mBoardState.get(4);
                mWinningRow.add(0);
                mWinningRow.add(4);
                mWinningRow.add(8);
                return true;
            }
            else if(mBoardState.get(2).equals(mBoardState.get(4)) && mBoardState.get(4).equals(mBoardState.get(6))){
                mWinner=mBoardState.get(4);
                mWinningRow.add(2);
                mWinningRow.add(4);
                mWinningRow.add(6);
                return true;
            }
            else if(mBoardState.get(3).equals(mBoardState.get(4)) && mBoardState.get(4).equals(mBoardState.get(5))){
                mWinner=mBoardState.get(4);
                mWinningRow.add(3);
                mWinningRow.add(4);
                mWinningRow.add(5);
                return true;
            }
            else if(mBoardState.get(1).equals(mBoardState.get(4)) && mBoardState.get(4).equals(mBoardState.get(7))){
                mWinner=mBoardState.get(4);
                mWinningRow.add(1);
                mWinningRow.add(4);
                mWinningRow.add(7);
                return true;
            }
        }
        if(!mBoardState.get(8).equals(Constants.EMPTY_CELL)){
            if(mBoardState.get(6).equals(mBoardState.get(7)) && mBoardState.get(7).equals(mBoardState.get(8))){
                mWinner=mBoardState.get(8);
                mWinningRow.add(6);
                mWinningRow.add(7);
                mWinningRow.add(8);
                return true;
            }
            else if(mBoardState.get(2).equals(mBoardState.get(5)) && mBoardState.get(5).equals(mBoardState.get(8))){
                mWinner=mBoardState.get(8);
                mWinningRow.add(2);
                mWinningRow.add(5);
                mWinningRow.add(8);
                return true;
            }
        }

        //No winner, check if there are empty spaces left
        for (int i:mBoardState) {
            if(i==Constants.EMPTY_CELL){
                return false;
            }
        }

        //No empty spaces, Game is draw
        mWinner=Constants.DRAW;
        return true;
    }
}
