package Lise.whopaintit;

import java.util.List;

public class Score {
    private String username;
    private int score;
    public Score(String u,int s){
        this.username = u;
        this.score = s;
    }

    public String getUser(){
        return this.username;
    }
    public int getScore(){
        return this.score;
    }
    public void setUser(String s){
        this.username = s;
    }
    public void setScore(int i){
        this.score = i;
    }
    public static Score max(List<Score> list){
        Score init = list.get(0);
        int taille = list.size();
        for (int i=1;i<taille;i++){
            if (init.getScore()<list.get(i).getScore()){
                init = list.get(i);
            }
        }
        return init;
    }
}
