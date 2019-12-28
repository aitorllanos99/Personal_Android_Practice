package com.github.guessthecelebritygame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int  NUMBER_OF_OPTIONS = 4;
    ArrayList<String> celebURLs = new ArrayList<String>();
    ArrayList<String> celebNamess = new ArrayList<String>();
    int celebrityToShow = 0;
    int bottonCorrect = 0; //Mostly to know the location of the right answer
    String[] answers = new String[NUMBER_OF_OPTIONS]; //We could change this if we want more options

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadContent();
        createNewQuestion();

    }
    private void loadContent(){
        DownloadTask task = new DownloadTask();
        String result = "";
        try {
            result = task.execute("http://www.posh24.se/kandisar").get();
            String[] splitResult = result.split(" <div class=\"sidebarContaniner\">");
            Pattern pattern = Pattern.compile("<img src=\"(.*?)\""); //First time using the pattern and the matcher, like this we´ll take the url of the image and the name of the celebrity
            Matcher matcher = pattern.matcher(splitResult[0]);
            while (matcher.find()) {
                celebURLs.add(matcher.group(1)); //Adding the urls of the images
              for(String url: celebURLs)
                  System.out.println(url);
            }


            pattern = Pattern.compile("alt=\"(.*?)\""); //First time using the pattern and the matcher, like this we´ll the name of the celebrity
            matcher = pattern.matcher(splitResult[0]);
            while (matcher.find()) {
                celebNamess.add(matcher.group(1));
                for(String name: celebNamess)
                    System.out.println(name);
            }

        } catch (ExecutionException e) {
            Log.e("Execution Exception", "OnCreate, trace:");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("Interrupted Exception", "OnCreate, trace:");
            e.printStackTrace();
        }
    }
    private void createNewQuestion(){
        celebrityToShow = getRandom(celebURLs.size()); //0-celebUrls-1, pick the image

        //Load the image

        ImageDownloader imtask = new ImageDownloader();
        Bitmap imageCelebrity = null;
        try {
            imageCelebrity = imtask.execute(celebURLs.get(celebrityToShow)).get();
        } catch (ExecutionException e) {
            Log.e("Execution Exception", "OnCreate, trace:");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("Interrupted Exception", "OnCreate, trace:");
            e.printStackTrace();
        }

        if(imageCelebrity == null){
            Log.e("Image Show Error",  "The image has not been loaded");
        }

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageBitmap(imageCelebrity);
        setAnswers();
        setButtonText();
    }

    private void setButtonText(){
        Button button0 = (Button) findViewById(R.id.Option1);
        Button button1 = (Button) findViewById(R.id.Option2);
        Button button2 = (Button) findViewById(R.id.Option3);
        Button button3 = (Button) findViewById(R.id.Option4);

        button0.setText(answers[0]);
        button1.setText(answers[1]);
        button2.setText(answers[2]);
        button3.setText(answers[3]);
    }


    private int getRandom(int top){
        Random r = new Random();
        return r.nextInt(top);

    }

    private void setAnswers(){
        bottonCorrect = getRandom(NUMBER_OF_OPTIONS);
        for(int i=0;i<NUMBER_OF_OPTIONS;i++){
            if(i == bottonCorrect){
                answers[i] = celebNamess.get(celebrityToShow);
            }else{
                int randomAnswer = getRandom(celebNamess.size());
                while(randomAnswer == bottonCorrect){
                    randomAnswer = getRandom(celebNamess.size());
                }
                answers[i] = celebNamess.get(randomAnswer);
            }
        }
    }

    public void checkCorrectAnswerClicked(View view){
      if(view.getTag().toString().equals(Integer.toString(bottonCorrect))){
          Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG).show();
      }else{
          Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_LONG).show();
      }
        createNewQuestion(); //You can replace this ive choosen to keep playing even thought u lost, u can change this parte inside of the if if u want to show a lost activity or something
    }
    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url = null;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                int data = br.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = br.read();
                }
            } catch (Exception e) {
                Log.e("Exception general", "doInBackGround, DownloadTask. Trace:");
                e.printStackTrace();
            }

            return result;
        }
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            String result = "";
            URL url = null;
            HttpURLConnection httpURLConnection = null;
            Bitmap bitmap = null;
            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream is = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                Log.e("Exception general", "doInBackGround, ImageDownloader. Trace:");
                e.printStackTrace();
            }

            return bitmap;

        }
    }
}
