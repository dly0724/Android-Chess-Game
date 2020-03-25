package edu.msu.yangziya.project1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static edu.msu.yangziya.project1.Game.singleChoiceDialogListener;

public class GameActivity extends AppCompatActivity implements
        ChoiceDialogFragment.SingleChoiceListener {
    private String namePlayer1 = "";
    private String namePlayer2 = "";
    public static final String WINNER = "WINNER";
    public static final String LOSER = "LOSER";
    private static final String PARAMETERS = "parameters";
    private GameView gameView = null;
    private int current = 1;
    private SparseArray<String> playerMap = new SparseArray<>();
    private static int p;
    private static List<Integer> promotion = new ArrayList<Integer>();

    /**
     * Save the instance state into a bundle
     * @param bundle the bundle to save into
     */
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        getGameView().saveInstanceState(bundle);
    }


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_game_play);

        gameView = getGameView();

        ChoiceDialogFragment.SingleChoiceDialogListener SingleChoice =
                new ChoiceDialogFragment.SingleChoiceDialogListener() {
            @Override
            public void showDialog() {
                // close existing dialog fragments
                FragmentManager manager = getFragmentManager();
                Fragment frag = manager.findFragmentByTag("ChoiceDialogFragment");
                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }
                ChoiceDialogFragment choiceDialogFragment = new ChoiceDialogFragment();
                choiceDialogFragment.show(manager, "ChoiceDialogFragment");
            }
        };
        singleChoiceDialogListener = SingleChoice;

        Intent intent = getIntent();
        namePlayer1 = intent.getStringExtra("PLAYER_ONE");
        namePlayer2 = intent.getStringExtra("PLAYER_TWO");

        // Randomly decide which player plays as white (and therefore goes first)
        String firstPlayer = new Random().nextBoolean() ? namePlayer1 : namePlayer2;
        String secondPlayer = firstPlayer.equals(namePlayer1)  ? namePlayer2 : namePlayer1;

        // Map player number to name (player 1 plays as white)
        playerMap.append(1, firstPlayer);
        playerMap.append(2, secondPlayer);

        gameView.setCurrentPlayer(current,gameView);
        setTurnHeader();

        if(bundle != null) {
            // We have saved state
            gameView.loadInstanceState(bundle);
        }
    }

    private TextView getPlayerText() {
        return (TextView)findViewById(R.id.player);
    }

    public void onRestart(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onShowRules(View view){
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }

    public void onQuit(View view){
        Intent intent = new Intent(this, GameOverActivity.class);
        if (current == 1){
            intent.putExtra(WINNER, playerMap.get(2));
            intent.putExtra(LOSER, playerMap.get(1));
        }
        else {
            intent.putExtra(WINNER, playerMap.get(1));
            intent.putExtra(LOSER, playerMap.get(2));
        }
        startActivity(intent);
    }

    public void onDone(View view){
        if (p!=0){
            getGameView().promotionIndex=p;
            p=0;
        }
        current = current == 2 ? 1: 2;  // toggle the current player
        setTurnHeader();
        getGameView().setCurrentPlayer(current,gameView);

        if (getGameView().Win() != 0){
            Intent intent = new Intent(this, GameOverActivity.class);
            if(current == 1){
                intent.putExtra(WINNER, playerMap.get(1));
            }
            else if(current == 2){
                intent.putExtra(LOSER, playerMap.get(2));
            }
            startActivity(intent);
        }
    }

    public void setTurnHeader(){
        if(current == 1){
            getPlayerText().setText(getString(R.string.turn_header_suffix, playerMap.get(1), "White"));
        }
        else{
            getPlayerText().setText(getString(R.string.turn_header_suffix, playerMap.get(2), "Black"));
        }
    }

    /**
     * Get the game view
     * @return GameeView reference
     */
    private GameView getGameView() {
        return (GameView) this.findViewById(R.id.gameView);
    }

    @Override
    public void onPositiveButtonClicked(int position) {
        p = position;
    }



    @Override
    public void onNegativeButtonClicked() {

    }


    public void setHeader(String text){
        getPlayerText().setText(text);
    }

    public String getHeader(){
        return (String)getPlayerText().getText();
    }

    public void displayToast(String msg, int duration){
        Toast.makeText(this, msg, duration).show();

    }

}
