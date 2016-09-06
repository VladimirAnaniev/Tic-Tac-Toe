package com.example.vladimir.tic_tac_toe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AgainstAI extends Activity {
    Integer mCurrentPlayer;
    TextView mInfo;
    Button mRetryButton;
    ArrayList<Button> mGameButtons;
    Board mGameBoard;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe);

        mContext=this;

        initialize();
    }

    private void initialize() {
        //Initialize variables
        mGameBoard=new Board();

        mGameButtons= new ArrayList<>();
        mGameButtons.add((Button)findViewById(R.id.button));
        mGameButtons.add((Button)findViewById(R.id.button2));
        mGameButtons.add((Button)findViewById(R.id.button3));
        mGameButtons.add((Button)findViewById(R.id.button4));
        mGameButtons.add((Button)findViewById(R.id.button5));
        mGameButtons.add((Button)findViewById(R.id.button6));
        mGameButtons.add((Button)findViewById(R.id.button7));
        mGameButtons.add((Button)findViewById(R.id.button8));
        mGameButtons.add((Button)findViewById(R.id.button9));

        mCurrentPlayer=Constants.PLAYER;

        mInfo=(TextView) findViewById(R.id.textView);
        mInfo.setText(R.string.your_turn);
        mRetryButton=(Button) findViewById(R.id.retry_button);


        //Set OnClick listener for all game buttons
        for (Button button:mGameButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleGameClick((Button)v);
                }
            });
        }

        //onClick for retry button
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reset game
                for (Button button:mGameButtons) {
                    button.setBackgroundResource(R.drawable.button_empty);
                    button.setText("");
                    button.setEnabled(true);
                }

                mGameBoard.resetBoard();

                mCurrentPlayer=Constants.PLAYER;
                mInfo.setText(R.string.X_turn);

                mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            }
        });
    }

    private void endGame(Integer winner, ArrayList<Integer> winningRow) {
        for (Button button:mGameButtons) {
            button.setEnabled(false);
        }

        if(winner.equals(Constants.PLAYER)){
            //PLAYER Won
            mInfo.setText(R.string.you_win);
            mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.GreenPlayer));
            for(int i=0;i<winningRow.size();i++){
                mGameButtons.get(winningRow.get(i)).setBackgroundResource(R.drawable.button_green);
            }
        }
        else if(winner.equals(Constants.AI)){
            //AI won
            mInfo.setText(R.string.you_lost);
            mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.RedPlayer));
            for(int i=0;i<winningRow.size();i++){
                mGameButtons.get(winningRow.get(i)).setBackgroundResource(R.drawable.button_red);
            }
        }
        else if(winner.equals(Constants.DRAW)){
            //Draw
            mInfo.setText(R.string.draw);
        }
    }

    private void handleGameClick(Button view){
        //Update placings and current player
        mGameBoard.changeBoardState(mCurrentPlayer, mGameButtons.indexOf(view));
        view.setEnabled(false);


        view.setText(R.string.X);
        view.setTextColor(ContextCompat.getColor(mContext,R.color.GreenPlayer));
        mInfo.setText(R.string.AI_turn);
        mCurrentPlayer=Constants.AI;
        for(Button btn:mGameButtons){
            btn.setEnabled(false);
        }

        //Check if game should end
        if(mGameBoard.hasGameEnded()){
            endGame(mGameBoard.getWinner(),mGameBoard.getWinningRow());
        }
        else{
            AIMakeMove();
        }
    }

    private void AIMakeMove(){
        AIPlayer player= new AIPlayer(mGameBoard);
        Integer move = player.move();
        mGameBoard.changeBoardState(mCurrentPlayer, move);
        mGameButtons.get(move).setText(R.string.O);
        mGameButtons.get(move).setTextColor(ContextCompat.getColor(mContext,R.color.RedPlayer));

        if(mGameBoard.hasGameEnded()){
            endGame(mGameBoard.getWinner(),mGameBoard.getWinningRow());
        }
        else{
            for(int i=0;i<9;i++){
                if(mGameBoard.getBoardState().get(i).equals(Constants.EMPTY_CELL)){
                    mGameButtons.get(i).setEnabled(true);
                }
            }
            mInfo.setText(R.string.your_turn);
            mCurrentPlayer=Constants.PLAYER;
        }
    }
}
