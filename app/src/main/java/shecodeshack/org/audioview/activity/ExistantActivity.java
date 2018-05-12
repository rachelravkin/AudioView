package shecodeshack.org.audioview.activity;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import shecodeshack.org.audioview.R;
import shecodeshack.org.audioview.constants.Constants;
import shecodeshack.org.audioview.model.AudioView;
import shecodeshack.org.audioview.model.AudioViewList;

public class ExistantActivity extends AppCompatActivity {
    private ImageView selectedImage;
    private MediaPlayer mediaPlayer;
    private FloatingActionButton start, stop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.existant_activity);

        start = findViewById(R.id.btn_play);
        stop = findViewById(R.id.btn_play_off);
        start.setEnabled(true);
        stop.setEnabled(false);
        selectedImage = findViewById(R.id.imageView);

        boolean hasImgRender = getIntent().getBooleanExtra(Constants.RENDER_IMG.name(), false);
        if (hasImgRender) {

            List<AudioView> list = AudioViewList.getInstance().getAudioViewList();
            Bitmap img = list.get(list.size()-1).getImage();
            selectedImage.setImageBitmap(img);
        }
    }

    /**
     * Animates selected image
     */
    public void animateImage(View view) {
        int rand = new Random().nextInt(Techniques.values().length);
        Toast.makeText(this, "rand val = " + rand, Toast.LENGTH_SHORT).show();
        YoYo.with(Techniques.values()[rand])
                .duration(700)
                .repeat(1)
                .playOn(view);
    }

    public void startPlay(View v) {
        stop.setEnabled(true);
        start.setEnabled(false);
        String audioPath="";
        mediaPlayer = new MediaPlayer();
        try {
            List<AudioView> list = AudioViewList.getInstance().getAudioViewList();
            if(list.size() > 0)
                audioPath = list.get(list.size()-1).getAudio();
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
        }catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();

    }

    public void stopPlay(View v) {
        start.setEnabled(true);
        stop.setEnabled(false);

        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();

        }

    }


}
