package com.github.downloadingimageweb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView downloadedImg =  (ImageView) findViewById(R.id.imageView);

    }


    public class ImageDownloader extends AsyncTask<String,Void, Bitmap>{ //BitMap cause we returns a Image
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection  = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void downloadImage(View view){
        ImageDownloader downloader = new ImageDownloader();
        Log.i("Interacting  image","Before Message");
        String urlDirection = "https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png";
        ImageView downloadedImg =  (ImageView) findViewById(R.id.imageView);
        Bitmap bmap = null;
        try {
            bmap =  downloader.execute(urlDirection).get();
            downloadedImg.setImageBitmap(bmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
