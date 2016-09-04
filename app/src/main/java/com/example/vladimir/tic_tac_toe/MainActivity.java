package com.example.vladimir.tic_tac_toe;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    int mCurrentPlayer;
    TextView mInfo;
    Button mRetryButton;
    Button[] mGameButtons;
    int[] mPlacings;
    int mWinner;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;

        initialize();
    }

    private void initialize() {
        //Initialize variables
        mPlacings=new int[9];
        for (int i:mPlacings) {
            i=0;
        }
        mGameButtons= new Button[9];
        mGameButtons[0]=(Button)findViewById(R.id.button);
        mGameButtons[1]=(Button)findViewById(R.id.button2);
        mGameButtons[2]=(Button)findViewById(R.id.button3);
        mGameButtons[3]=(Button)findViewById(R.id.button4);
        mGameButtons[4]=(Button)findViewById(R.id.button5);
        mGameButtons[5]=(Button)findViewById(R.id.button6);
        mGameButtons[6]=(Button)findViewById(R.id.button7);
        mGameButtons[7]=(Button)findViewById(R.id.button8);
        mGameButtons[8]=(Button)findViewById(R.id.button9);

        mCurrentPlayer=1;

        mInfo=(TextView) findViewById(R.id.textView);
        mRetryButton=(Button) findViewById(R.id.retry_button);


        //Set OnClick listener for all game buttons
        for (Button button:mGameButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleGameClick(v);

                    //Check if game should end
                    if(checkForWinner()){
                        endGame();
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
                for (int i=0;i<9;i++) {
                    mPlacings[i]=0;
                }

                //Show whose turn it is
                if(mCurrentPlayer==1){
                    mInfo.setText(R.string.green_turn);
                }
                else{
                    mInfo.setText(R.string.red_turn);
                }

                mWinner=0;
                mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            }
        });
    }

    private void endGame() {
        for (Button button:mGameButtons) {
            button.setEnabled(false);
        }

        if(mWinner==1){
            //Green player Won
            mInfo.setText(R.string.green_wins);
            mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.GreenPlayer));
        }
        else if(mWinner==-1){
            //Red player won
            mInfo.setText(R.string.red_wins);
            mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.RedPlayer));
        }
        else{
            //Draw
            mInfo.setText(R.string.draw);
        }
    }

    private void handleGameClick(View view){
        //Change the view depending on the player
        if(mCurrentPlayer==1){
            view.setBackgroundResource(R.drawable.button_green);
            mInfo.setText(R.string.red_turn);
        }
        else {
            view.setBackgroundResource(R.drawable.button_red);
            mInfo.setText(R.string.green_turn);
        }
        view.setEnabled(false);

        //Update placings and current player
        int index= Arrays.asList(mGameButtons).indexOf(view);
        mPlacings[index]=mCurrentPlayer;
        mCurrentPlayer=mCurrentPlayer*-1;
    }

    private boolean checkForWinner() {
        if (mPlacings[0] != 0) {
            if (mPlacings[0] == mPlacings[1] && mPlacings[1] == mPlacings[2]) {
                mWinner = mPlacings[0];
                return true;
            }
            else if(mPlacings[0]==mPlacings[3]&&mPlacings[3]==mPlacings[6]){
                mWinner=mPlacings[0];
                return true;
            }
        }
        if(mPlacings[4]!=0){
            if (mPlacings[0] == mPlacings[4]&&mPlacings[4]==mPlacings[8]){
                mWinner=mPlacings[4];
                return true;
            }
            else if(mPlacings[2]==mPlacings[4]&&mPlacings[4]==mPlacings[6]){
                mWinner=mPlacings[4];
                return true;
            }
            else if(mPlacings[3]==mPlacings[4]&& mPlacings[4]==mPlacings[5]){
                mWinner=mPlacings[4];
                return true;
            }
            else if(mPlacings[1]==mPlacings[4]&&mPlacings[4]==mPlacings[7]){
                mWinner=mPlacings[4];
                return true;
            }
        }
        if(mPlacings[8]!=0){
            if(mPlacings[6]==mPlacings[7]&&mPlacings[7]==mPlacings[8]){
                mWinner=mPlacings[8];
                return true;
            }
            else if(mPlacings[2]==mPlacings[5]&&mPlacings[5]==mPlacings[8]){
                mWinner=mPlacings[8];
                return true;
            }
        }

        for (int i:mPlacings) {
            if(i==0){
                return false;
            }
        }

        return true;
    }
}
