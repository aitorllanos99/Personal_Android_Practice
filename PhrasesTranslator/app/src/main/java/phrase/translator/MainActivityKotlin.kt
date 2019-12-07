package phrase.translator

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivityKotlin : AppCompatActivity(){

    fun ButtonTapped(view: View){
        val pressed = findViewById<Button>(view.id)
        val resourceId = resources.getIdentifier(view.resources.getResourceName(view.id), "raw", "phrase.translator")
        val player = MediaPlayer.create(this, resourceId)
        player.start()
    }
    override fun onCreate(savedInstance: Bundle? ) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
    }
}