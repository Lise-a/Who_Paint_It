package Lise.whopaintit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.InterfaceAddress;

public class ScoreAct extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int score = intent.getIntExtra("score", 0);
        String result = username + " : " + Integer.toString(score) + " points !!";
        TextView FINAL = (TextView)findViewById(R.id.finalScore);
        FINAL.setText(result);
        Button nextPage = (Button)findViewById(R.id.gotoRank);
        ScoreDataBase bd = new ScoreDataBase(this);
        Score S = new Score(username,score);
        bd.insertData(S);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRank = new Intent(getApplicationContext(), RankingAct.class);
                startActivity(intentRank);
            }
        });

    }
}