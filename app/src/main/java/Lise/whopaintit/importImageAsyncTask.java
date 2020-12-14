package Lise.whopaintit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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



    @Override
    protected Bitmap doInBackground(Void ... voids) {
        String urlstring = "https://api.artic.edu/api/v1/artworks/search?q=impressionism&limit=1&page=";
        double numpageDouble = Math.random();
        numpageDouble = numpageDouble*275;
        int numpage = (int)Math.round(numpageDouble);
        urlstring = urlstring + numpage;
        String urlId = "";
        String image_link = "";
        try {
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                Log.i("page_impressionism", s);
                JSONObject json = new JSONObject(s);
                Object data = json.get("data");
                JSONObject datajson = new JSONObject(data.toString());
                Object urlIdObj = datajson.get("api_link");
                urlId = urlIdObj.toString();

            }  finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            url = new URL(urlId);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String sA = readStream(in);
                Log.i("page_artist", sA);
                JSONObject json = new JSONObject(sA);
                Object data = json.get("data");
                JSONObject datajson = new JSONObject(data.toString());
                Object artistObj = datajson.get("artist_title");
                QuizzAct.artist_title = artistObj.toString();
                Object artistDisplayObj = datajson.get("artist_display");
                QuizzAct.artist_display = artistDisplayObj.toString();
                Object titleObj = datajson.get("title");
                QuizzAct.painting_title = titleObj.toString();
                Object thumbnailObj = datajson.get("thumbnail");
                JSONObject thumbnail = new JSONObject(thumbnailObj.toString());
                Object image_linkObj = thumbnail.get("url");
                image_link = image_linkObj.toString();
            }  finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            url = new URL(image_link);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String sI = readStream(in);
                Log.i("image", sI);
                JSONObject json = new JSONObject(sI);
                return BitmapFactory.decodeStream(in);

            }  finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
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
            }}}

}

