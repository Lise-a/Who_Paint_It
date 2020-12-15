package Lise.whopaintit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ScoreListAdapter extends ArrayAdapter<Score> {
    private Context mContext;
    int mResource;

    public ScoreListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Score> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String username = getItem(position).getUser();
        int score = getItem(position).getScore();


        //Create the person object with the information
        Score person = new Score(username,score);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvUsername = (TextView)convertView.findViewById(R.id.ListViewUser);
        TextView tvScore = (TextView)convertView.findViewById(R.id.ListViewScore);

        tvUsername.setText(username);
        tvScore.setText(String.valueOf(score));
        return convertView;
        }

}
