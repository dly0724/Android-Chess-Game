package edu.msu.yangziya.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        Intent intent = getIntent();

        String winner = intent.getStringExtra("WINNER");
        String loser = intent.getStringExtra("LOSER");

        ((TextView) findViewById(R.id.winnerTV)).setText(getString(R.string.Winner, winner));
        ((TextView) findViewById(R.id.loserTV)).setText(getString(R.string.Loser, loser));
    }

    public void onRestart(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
