package com.example.vladimir.tic_tac_toe;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TwoPlayers extends AppCompatActivity {

    Integer mCurrentPlayer;
    TextView mInfo;
    Button mRetryButton;
    ArrayList<Button> mGameButtons;
    Board mGameBoard;
    Context mContext;
    Button mBackButton;

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

        mBackButton=(Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HomeActivity.class);
                startActivity(intent);
            }
        });

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

        mCurrentPlayer=Constants.X;

        mInfo=(TextView) findViewById(R.id.textView);
        mInfo.setText(R.string.X_turn);
        mRetryButton=(Button) findViewById(R.id.retry_button);


        //Set OnClick listener for all game buttons
        for (Button button:mGameButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleGameClick((Button)v);

                    //Check if game should end
                    if(mGameBoard.hasGameEnded()){
                        endGame(mGameBoard.getWinner(),mGameBoard.getWinningRow());
                    }
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

                mCurrentPlayer=Constants.X;
                mInfo.setText(R.string.X_turn);

                mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            }
        });
    }

    private void endGame(Integer winner, ArrayList<Integer> winningRow) {
        for (Button button:mGameButtons) {
            button.setEnabled(false);
        }

        if(winner.equals(Constants.X)){
            //X player Won
            mInfo.setText(R.string.X_wins);
            mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.GreenPlayer));
            for(int i=0;i<winningRow.size();i++){
                mGameButtons.get(winningRow.get(i)).setBackgroundResource(R.drawable.button_green);
            }
        }
        else if(winner.equals(Constants.O)){
            //O player won
            mInfo.setText(R.string.O_wins);
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

        //Change the view depending on the player
        if(mCurrentPlayer.equals(Constants.X)){
            view.setText(R.string.X);
            view.setTextColor(ContextCompat.getColor(mContext,R.color.GreenPlayer));
            mInfo.setText(R.string.O_turn);
            mCurrentPlayer=Constants.O;
        }
        else if(mCurrentPlayer.equals(Constants.O)) {
            view.setText(R.string.O);
            view.setTextColor(ContextCompat.getColor(mContext,R.color.RedPlayer));
            mInfo.setText(R.string.X_turn);
            mCurrentPlayer=Constants.X;
        }
    }
}
