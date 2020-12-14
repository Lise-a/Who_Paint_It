package Lise.whopaintit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class QuizzAct extends AppCompatActivity {
    public static String artist_title = "";
    public static String painting_title = "" ;
    public static String artist_display = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView painting = (ImageView)findViewById(R.id.paintingShow);
        importImageAsyncTask async = new importImageAsyncTask(painting);
        async.execute();
        setContentView(R.layout.activity_quizz);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int score = intent.getIntExtra("score", 0);
        int nblife = intent.getIntExtra("nblife", 3);
        int nbquestion = intent.getIntExtra("nbquestion",1);
        TextView question = (TextView)findViewById(R.id.questionText);
        ImageButton nextPage = (ImageButton)findViewById(R.id.nextPageBtn);
        TextView scoredisplay = (TextView)findViewById(R.id.scoreDisplay);
        TextView lifedisplay = (TextView)findViewById(R.id.nblifeDisplay);
        String questionSet = "Question " + nbquestion;
        String scoredisplaySet = "score : " + score;
        String lifedisplaySet = nblife + " : life(s)";
        question.setText(questionSet);
        scoredisplay.setText(scoredisplaySet);
        lifedisplay.setText(lifedisplaySet);
        nextPage.setVisibility(View.INVISIBLE);
        //affichage de la peinture assync task
        //recuperer le nom du peintre (artist_display ; artist_title, title,date)
        EditText artistAnswer = (EditText) findViewById(R.id.artistTextEdit);
        Button submit = (Button) findViewById(R.id.sendAnswerBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String propositionArtist = artistAnswer.getText().toString();
                Boolean bool = isright(artist_title, propositionArtist); //variable pas encore définie
                nextPage.setVisibility(View.VISIBLE);
                if (bool) {
                    //afficher le fait que la reponse est bonne toast ou textview ?
                    nextPage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentQuizz = new Intent(getApplicationContext(), QuizzAct.class);
                            intentQuizz.putExtra("username",username);
                            intentQuizz.putExtra("nblife",nblife);
                            intentQuizz.putExtra("score",score+10);
                            intentQuizz.putExtra("nbquestion",nbquestion+1);
                            startActivity(intentQuizz);
                        }
                    });
                }
                else{
                    //afficher le fait que la réponse est mauvaise toast ou textview
                    nextPage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (nblife==1){
                                Intent intentScore = new Intent(getApplicationContext(), ScoreAct.class);
                                intentScore.putExtra("username",username);
                                intentScore.putExtra("score",score);
                                // peut etre directement l'ajouter à la bd pour evitez un traitement apres
                                startActivity(intentScore);
                            }
                            Intent intentQuizz = new Intent(getApplicationContext(), QuizzAct.class);
                            intentQuizz.putExtra("username",username);
                            intentQuizz.putExtra("nblife",nblife-1);
                            intentQuizz.putExtra("score",score);
                            startActivity(intentQuizz);
                        }
                    });
                }
            }
        });
    }
    public Boolean isright(String right, String proposition){
        //pb si l'utilisateur ecrit juste le nom de famille et si le user pense que Delacroix s'écrit De la Croix est ce qu'on valide ?

        return true;
    }
}