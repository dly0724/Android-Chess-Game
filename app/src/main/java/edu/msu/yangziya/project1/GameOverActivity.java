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
        String namePlayer1 = intent.getStringExtra("winner");
        String namePlayer2 = intent.getStringExtra("loser");
        ((TextView) findViewById(R.id.winnerTV)).setText("Winner: " + namePlayer1);
        ((TextView) findViewById(R.id.loserTV)).setText("Loser: " + namePlayer2);
    }

    public void onRestart(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
