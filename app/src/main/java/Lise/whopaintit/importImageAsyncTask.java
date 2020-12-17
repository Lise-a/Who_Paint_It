package Lise.whopaintit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class importImageAsyncTask extends AsyncTask<Void, Integer, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;

    public importImageAsyncTask(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);}

    protected void onPreExecute() {
        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
           // if (imageView != null) {
               // imageView.setImageResource(R.drawable.interro);}
        }}



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Bitmap doInBackground(Void ... voids) {
        String urlstring = "https://api.artic.edu/api/v1/artworks/search?q=impressionism&limit=1&page=";
        double numpageDouble = Math.random();
        numpageDouble = numpageDouble * 275;
        int numpage = (int) Math.round(numpageDouble);
        urlstring = urlstring + numpage;
        String urlId = "";
        String image_link = "";
        InputStream stream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        try {
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection1 = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection1.getInputStream());
                String s = readStream(in);
                JSONObject json = new JSONObject(s);
                Object data = json.get("data");
                String dataS = data.toString();
                JSONArray dataArray = new JSONArray(dataS);
                Object dataObj = dataArray.get(0);
                JSONObject datajson = new JSONObject(dataObj.toString());
                urlId = datajson.getString("api_link");
                Log.i("acceder au lien", "OK");


            } finally {
                urlConnection1.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(urlId);
            HttpURLConnection urlConnection2 = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection2.getInputStream());
                String sA = readStream(in);
                JSONObject json = new JSONObject(sA);
                Object data = json.get("data");
                String dataS = data.toString();
                JSONObject datajson = new JSONObject(dataS);
                Object artistObj = datajson.get("artist_title");
                QuizzAct.artist_title = artistObj.toString();
                Object artistDisplayObj = datajson.get("artist_display");
                QuizzAct.artist_display = artistDisplayObj.toString();
                Log.i("Lise", "artist : " + QuizzAct.artist_display);
                Object titleObj = datajson.get("title");
                QuizzAct.painting_title = titleObj.toString();
                Log.i("Lise","title : " + QuizzAct.painting_title);
                Object thumbnailObj = datajson.get("thumbnail");
                JSONObject thumbnail = new JSONObject(thumbnailObj.toString());
                Object image_linkObj = thumbnail.get("url");
                image_link = image_linkObj.toString();
                Log.i("Lise", "lien image : " + image_link + "/full/843,/0/default.jpg");
            } finally {
                urlConnection2.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            System.setProperty("http.agent", "Chrome");
            URL url = new URL(image_link + "/full/843,/0/default.jpg");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            Bitmap B = BitmapFactory.decodeStream(in);
            urlConnection.disconnect();
            return B;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

        private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }}
        Log.i("Lise","fin AsyncTask");

    }

}

