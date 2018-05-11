package shecodeshack.org.audioview.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.List;

import shecodeshack.org.audioview.R;
import shecodeshack.org.audioview.constants.Constants;
import shecodeshack.org.audioview.model.AudioView;
import shecodeshack.org.audioview.model.AudioViewList;

public class ExistantActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.existant_activity);

        boolean hasImgRender = getIntent().getBooleanExtra(Constants.RENDER_IMG.name(), false);
        if (hasImgRender) {
            ImageView imageView = findViewById(R.id.imageView);
            List<AudioView> list = AudioViewList.getInstance().getAudioViewList();
            Bitmap img = list.get(list.size()-1).getImage();
            imageView.setImageBitmap(img);
        }



    }





}
