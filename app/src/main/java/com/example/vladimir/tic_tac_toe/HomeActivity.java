package com.example.vladimir.tic_tac_toe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Button mAgainstAI = (Button) findViewById(R.id.againstAI);
        Button mTwoPlayer = (Button) findViewById(R.id.twoPlayers);

        mContext = this;

        mAgainstAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,AgainstAI.class);
                startActivity(intent);
            }
        });

        mTwoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TwoPlayers.class);
                startActivity(intent);
            }
        });
    }
}
