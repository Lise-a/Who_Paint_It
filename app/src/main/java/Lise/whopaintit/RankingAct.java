package Lise.whopaintit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RankingAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ListView listScore = (ListView)findViewById(R.id.scoresList);
        ScoreDataBase bd = new ScoreDataBase(this);
        List<Score> ScoreList = bd.readDatas();
        ArrayList<Score> ScoreArray = new ArrayList<>();
        int taille = ScoreList.size();
        int displayMax = taille;
        if (taille > 10 ) {displayMax = 11;}
        for (int i=0; i<displayMax; i++) {
            Score initial = Score.max(ScoreList);
            ScoreArray.add(initial);}
        Log.i("FIN DE BOUCLE","OK");
        ScoreListAdapter adapter = new ScoreListAdapter(this, R.layout.list_view_layout,ScoreArray);
        Log.i("CREATION ADAPTER","OK");
        listScore.setAdapter(adapter);

        Button homeBtn = (Button)findViewById(R.id.HOME);
        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(getApplicationContext(), WelcomeAct.class);
                startActivity(intentHome);
            }
        });
    }
}