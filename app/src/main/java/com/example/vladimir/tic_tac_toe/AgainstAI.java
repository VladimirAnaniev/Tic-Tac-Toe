package com.example.vladimir.tic_tac_toe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class AgainstAI extends Activity {
    Integer mCurrentPlayer;
    TextView mInfo;
    Button mRetryButton;
    Button mFirstOrSecond;
    ArrayList<Button> mGameButtons;
    Board mGameBoard;
    Context mContext;
    Integer mFirstPlayer;
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

        //Initialize back button and add OnClickListener
        mBackButton=(Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HomeActivity.class);
                startActivity(intent);
            }
        });

        //Add all game buttons in an ArrayList
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

        //Set OnClick listener for all game buttons
        for (Button button:mGameButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleGameClick((Button)v);
                }
            });
        }

        //The player is first by default
        mFirstPlayer = Constants.PLAYER;
        mCurrentPlayer = mFirstPlayer;


        //Create button for changing the starting player
        mFirstOrSecond = (Button) findViewById(R.id.first_second);
        mFirstOrSecond.setVisibility(View.VISIBLE);
        mFirstOrSecond.setText(R.string.play_second);
        mFirstOrSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFirstPlayer.equals(Constants.PLAYER)) {
                    mFirstPlayer=Constants.AI;
                    mFirstOrSecond.setText(R.string.play_first);
                }
                else {
                    mFirstPlayer=Constants.PLAYER;
                    mFirstOrSecond.setText(R.string.play_second);
                }

                //Reset the game after changing
                resetGame();
            }
        });

        //initialize the info text
        mInfo=(TextView) findViewById(R.id.textView);
        mInfo.setText(R.string.your_turn);

        //initialize the retry button and handle clicks
        mRetryButton=(Button) findViewById(R.id.retry_button);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void resetGame() {
        //Reset all game buttons
        for (Button button:mGameButtons) {
            button.setBackgroundResource(R.drawable.button_empty);
            button.setText("");
            button.setEnabled(true);
        }

        //Reset the board
        mGameBoard.resetBoard();

        mCurrentPlayer=mFirstPlayer;

        //Indicate who should start
        if(mFirstPlayer.equals(Constants.AI)){
            //AI starts, make a random move
            AIMakeMove(true);
        }
        else{
            mInfo.setText(R.string.your_turn);
        }
        mInfo.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
    }

    private void endGame(Integer winner, ArrayList<Integer> winningRow) {
        //Disable all game buttons
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
        for(Button btn:mGameButtons){
            btn.setEnabled(false);
        }

        //Display X or O
        if(mFirstPlayer.equals(Constants.PLAYER)){
            view.setText(R.string.X);
        }
        else{
            view.setText(R.string.O);
        }
        view.setTextColor(ContextCompat.getColor(mContext,R.color.GreenPlayer));

        //indicate whose turn it is
        mInfo.setText(R.string.AI_turn);
        mCurrentPlayer=Constants.AI;

        //Check if game should end
        if(mGameBoard.hasGameEnded()){
            endGame(mGameBoard.getWinner(),mGameBoard.getWinningRow());
        }
        else{
            //Let the AI make a move
            AIMakeMove(false);
        }
    }

    private void AIMakeMove(boolean isFirstMove){
        AIPlayer player= new AIPlayer(mGameBoard);
        Integer move;

        if(isFirstMove){
            //It's the first move of the game, make it random
            Random rand = new Random();
            move = rand.nextInt(9);
        }
        else{
            //Find the best play available
            move = player.move();
        }

        //Change the board state
        mGameBoard.changeBoardState(mCurrentPlayer, move);

        //Display X or O
        if(mFirstPlayer.equals(Constants.AI)){
            mGameButtons.get(move).setText(R.string.X);
        }
        else{
            mGameButtons.get(move).setText(R.string.O);
        }
        mGameButtons.get(move).setTextColor(ContextCompat.getColor(mContext,R.color.RedPlayer));

        //Check if game should continue
        if(mGameBoard.hasGameEnded()){
            //Game has ended, Indicate properly
            endGame(mGameBoard.getWinner(),mGameBoard.getWinningRow());
        }
        else{
            //Game continues, let the player make a move
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
