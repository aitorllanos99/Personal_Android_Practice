package com.github.downloadingwebcontent

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val task = DownloadTask()
        var result = ""
       try {
            result = task.execute("https://google.com", "https://amazon.com")
       } catch (InterruptedException e){

       }catch (Exception e){

       }
        Log.i("POSIBLE RESULT: " , result.toString())
    }

    class DownloadTask : AsyncTask<String, Unit, String>() { //<ToDownload, method to execute, Return of the ToDownload>
        override  fun doInBackground(vararg params: String?): String {
            Log.i("URL try: ", params[0])
            return "Done"
        }
    }

}
