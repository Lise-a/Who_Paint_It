package Lise.whopaintit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String user = getPreferences(MODE_PRIVATE).getString("username", "");
        Button start = (Button)findViewById(R.id.startBtn);
        Button scores = (Button)findViewById(R.id.scoresBtn);
        EditText usernameEditText = (EditText)findViewById(R.id.usernameEdit);
        usernameEditText.setText(user);
        scores.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentScore = new Intent(getApplicationContext(), ScoreAct.class);
                startActivity(intentScore);
            }
        });
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                Intent intentQuizz = new Intent(getApplicationContext(), QuizzAct.class);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", username);
                editor.apply();
                intentQuizz.putExtra("username",username);
                intentQuizz.putExtra("nblife",3);
                intentQuizz.putExtra("score",0);
                intentQuizz.putExtra("nbquestion",1);
                startActivity(intentQuizz);
            }
        });
    }
}