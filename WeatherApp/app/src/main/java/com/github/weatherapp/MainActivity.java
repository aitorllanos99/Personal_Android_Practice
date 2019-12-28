package com.github.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    TextView resultShower = null;
    EditText cityName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void findWeatherByCity(View view){
        String weatherQuery = "http://api.openweathermap.org/data/2.5/weather?q=";
        String appiKey = "&APPID=c1fd90ee0f3543a5738c9e07fd41d2db";
        DownloadWeather downloadWeather = new DownloadWeather();
        String result = "";
        cityName = (EditText) findViewById(R.id.CityText);
        resultShower  = (TextView) findViewById(R.id.resultView);
        String query = weatherQuery + cityName.getText().toString() + appiKey;
        try {
            result = downloadWeather.execute(query).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
           e.printStackTrace();
        }

        keyboardHiddden();
    }

    private void keyboardHiddden(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(cityName.getWindowToken(),0); //To Hide the keyboard
    }

    class DownloadWeather extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result  = "";
            URL url  = null;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                int data = bufferedReader.read();

                while(data != -1){
                    char current = (char) data;
                    result += current;
                    data = bufferedReader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
                super.onPostExecute(result);
                String message = "";
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String weatherInfo = jsonObject.getString("weather");
                    JSONArray jsonArray = new JSONArray(weatherInfo);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonPart = jsonArray.getJSONObject(i);
                        String main,description = "";

                        main = jsonPart.getString("main");
                        description = jsonPart.getString("description");
                        if(main != "" && description != ""){
                            message += main + ":" + description + "\r\n";  //Format to print the message in the result view
                        }
                    }
                    if(message != ""){
                        resultShower.setText(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}
