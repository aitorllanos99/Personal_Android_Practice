package phrase.translator;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public void ButtonTapped(View view){
        Button pressed = findViewById(view.getId());
        int resourceId  = getResources().getIdentifier(view.getResources().getResourceName(view.getId()), "raw","phrase.translator");
        MediaPlayer player = MediaPlayer.create(this,resourceId);
        player.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
