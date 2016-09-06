package com.example.vladimir.tic_tac_toe;

import android.content.Context;
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

        mCurrentPlayer=1;

        mInfo=(TextView) findViewById(R.id.textView);
        mRetryButton=(Button) findViewById(R.id.retry_button);


        //Set OnClick listener for all game buttons
        for (Button button:mGameButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleGameClick((Button)v);

                    //Check if game should end
                    if(mGameBoard.hasGameEnded()){
                        endGame(mGameBoard.getWinner());
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
                    button.setEnabled(true);
                }

                mGameBoard.resetBoard();

                //Show whose turn it is
                if(mCurrentPlayer==1){
                    mInfo.setText(R.string.green_turn);
                }
                else{
                    mInfo.setText(R.string.red_turn);
                }

                mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            }
        });
    }

    private void endGame(Integer winner) {
        for (Button button:mGameButtons) {
            button.setEnabled(false);
        }

        if(winner.equals(Constants.GREEN_PLAYER)){
            //Green player Won
            mInfo.setText(R.string.green_wins);
            mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.GreenPlayer));
        }
        else if(winner.equals(Constants.RED_PLAYER)){
            //Red player won
            mInfo.setText(R.string.red_wins);
            mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.RedPlayer));
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
        if(mCurrentPlayer.equals(Constants.GREEN_PLAYER)){
            view.setBackgroundResource(R.drawable.button_green);
            mInfo.setText(R.string.red_turn);
            mCurrentPlayer=Constants.RED_PLAYER;
        }
        else if(mCurrentPlayer.equals(Constants.RED_PLAYER)) {
            view.setBackgroundResource(R.drawable.button_red);
            mInfo.setText(R.string.green_turn);
            mCurrentPlayer=Constants.GREEN_PLAYER;
        }
    }
}
