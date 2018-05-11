package shecodeshack.org.audioview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import shecodeshack.org.audioview.activity.CreateAudioViewActivity;
import shecodeshack.org.audioview.constants.Constants;
import shecodeshack.org.audioview.model.AudioView;
import shecodeshack.org.audioview.model.ListWrapper;

/**
 * Main Activity of the app.
 */
public class MainActivity extends AppCompatActivity {
    private Intent mainIntent;
    private ListWrapper<AudioView> avList;


    public MainActivity() {
        avList = new ListWrapper<>();
        mainIntent = new Intent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainIntent.putExtra(Constants.AUDIO_VIEW_LIST.name(), avList);

        //TODO create view
    }

    /** Opens {@link CreateAudioViewActivity} view. */
    public void openCreateAudioViewActivity(View view) {
        //Intent intent = new Intent(this, CreateAudioViewActivity.class); TODO remove
        mainIntent.setClass(this, CreateAudioViewActivity.class);
        startActivity(mainIntent);
    }


    /**
     * Adds a valid AudioView object to list of AudioViews.
     * @return {@code true} if object valid, {@code false} otherwise.
     */
    public boolean addAudioView(AudioView audioView) {
        if (audioView != null && audioView.getImage() != null) {
            avList.getList().add(audioView);
            return true;
        }
        return false;
    }


}
