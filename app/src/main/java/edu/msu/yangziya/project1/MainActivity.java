package edu.msu.yangziya.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements ChoiceDialogFragment.SingleChoiceListener {
//implements ChoiceDialogFragment.SingleChoiceListener

    private Boolean hName1 = Boolean.FALSE;
    private Boolean hName2 = Boolean.FALSE;
    private String namePlayer1 = "";
    private String namePlayer2 = "";

    private String namePlayer1_1 = "";
    private String namePlayer2_2 = "";

    private TextView tvDisplayChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplayChoice = findViewById(R.id.tvDisplayChoice);

        getplayer1EditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                namePlayer1=s.toString();
                int length = s.toString().replace(" ", "").length();
                if(length == 0) {
                    getPlayButton().setEnabled(false);
                }else {
                    hName1 = Boolean.TRUE;
                    if(hName2){
                        getPlayButton().setEnabled(true);
                    }
                    else {
                        getPlayButton().setEnabled(false);
                    }

                }
            }
        });
        getplayer2EditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                namePlayer2=s.toString();
                int length = s.toString().replace(" ", "").length();
                if(length == 0) {
                    getPlayButton().setEnabled(false);
                }else {
                    hName2 = Boolean.TRUE;
                    if(hName1){
                        getPlayButton().setEnabled(true);
                    }
                    else{
                        getPlayButton().setEnabled(false);
                    }
                }
            }
        });
    }


    public void onStartGame(View view){
        Intent intent = new Intent(this, GameActivity.class);

        String[] myArrayOfStrings = {namePlayer1, namePlayer2};
        namePlayer1_1 = myArrayOfStrings[new Random().nextInt(myArrayOfStrings.length)];
        if(namePlayer1_1 == namePlayer1){
            namePlayer2_2 = namePlayer2;
        }
        else{
            namePlayer2_2 = namePlayer1;
        }
        intent.putExtra("NamePlayer1", namePlayer1_1);
        intent.putExtra("NamePlayer2", namePlayer2_2);
        startActivity(intent);
    }

    private Button getPlayButton() {
        return (Button)findViewById(R.id.play_button);
    }

    private EditText getplayer1EditText(){
        return (EditText)findViewById(R.id.player1);
    }

    private EditText getplayer2EditText(){
        return (EditText)findViewById(R.id.player2);
    }

    public void onShowRules(View view){
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        tvDisplayChoice.setText("Selected Item = " + list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {

    }
}
