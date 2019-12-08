package thread.init;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new CountDownTimer(10000,1000){
        //  public void onTick(long millis){
        //      //time till the end of the counter
        //  }
        //public void onFinish(){
        //  counter finished
        //  }
        // };
        final Handler handler = new Handler();
        Runnable run  = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(run);
    }
}
