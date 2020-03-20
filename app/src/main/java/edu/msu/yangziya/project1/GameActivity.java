package edu.msu.yangziya.project1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static edu.msu.yangziya.project1.Game.singleChoiceDialogeListener;

public class GameActivity extends AppCompatActivity implements
        ChoiceDialogFragment.SingleChoiceListener{
    private String namePlayer1 = "";
    private String namePlayer2 = "";
    private static final String PARAMETERS = "parameters";
    private GameView gameView = null;
    private int current = 1;

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

        gameView = (GameView)findViewById(R.id.gameView);

        ChoiceDialogFragment.SingleChoiceDialogeListener SingleChoice =
                new ChoiceDialogFragment.SingleChoiceDialogeListener() {
            @Override
            public void showDialoge() {
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
        singleChoiceDialogeListener = SingleChoice;


        Intent intent = getIntent();
        namePlayer1 = intent.getStringExtra("NamePlayer1");
        namePlayer2 = intent.getStringExtra("NamePlayer2");

        ((TextView)findViewById(R.id.player)).setText(namePlayer1 + " V.S. " + namePlayer2);

        if(bundle != null) {
            // We have saved state
            GameView view = (GameView)this.findViewById(R.id.gameView);
            view.loadInstanceState(bundle);
        }


//         /* Restore any state*/
//
//        if(savedInstanceState != null) {
//
//            gameView.getFromBundle(PARAMETERS, savedInstanceState);
//            switch( gameView.getTurn()) {
//                case 1:
//                    getPlayerText().setText("Black's Turn: " + namePlayer1 );
//                    break;
//                case 2:
//                    getPlayerText().setText("White's Turn: " + namePlayer2 );
//                    break;
//                default:
//                    break;
//            }
//        } else {
//            getPlayerText().setText( "Black's Turn: " + namePlayer1 );
//        }

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
            intent.putExtra("winner", namePlayer1);
            intent.putExtra("loser", namePlayer2);
        }else {
            intent.putExtra("winner", namePlayer2);
            intent.putExtra("loser", namePlayer1);
        }
        startActivity(intent);
        startActivity(intent);
    }

    public void onDone(View view) {
        current = 1;
    }

    public void onDoneP1(View view) {
        current = 2;
    }

    /**
     * Get the game view
     * @return GameeView reference
     */
    private GameView getGameView() {
        return (GameView) this.findViewById(R.id.gameView);
    }


    @Override
    public void onPositiveButtonClicked(String[] list, int position) {

    }

    @Override
    public void onNegativeButtonClicked() {

    }
}
