package shecodeshack.org.audioview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import shecodeshack.org.audioview.activity.CreateAudioViewActivity;

import shecodeshack.org.audioview.constants.Constants;
import shecodeshack.org.audioview.model.AudioView;
import shecodeshack.org.audioview.model.AudioViewList;

import shecodeshack.org.audioview.activity.ExistantActivity;


/**
 * Main Activity of the app.
 */
public class MainActivity extends AppCompatActivity {
    private AudioViewList avList;


    public MainActivity() {
        avList = AudioViewList.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Re-renders view according to added AudioViews.
     */
    @Override
    protected void onResumeFragments() { //render view
        List<AudioView> list = avList.getAudioViewList();
        Toast.makeText(this, "Resumed fragment " + list.size(), Toast.LENGTH_LONG).show();

        if (list.size() > 0) {
            ImageView imageView = findViewById(R.id.imageView); //TODO change: gardcoded top image rendering
            imageView.setImageBitmap(list.get(list.size()-1).getImage());
        }
    }

    /** Opens {@link CreateAudioViewActivity} view. */
    public void openCreateAudioViewActivity(View view) {
        Intent intent = new Intent(this, CreateAudioViewActivity.class);
        startActivity(intent);
    }

    /** method to go from the main activity to the Existant Activity*/
    public void goExistantActivity(View view) {
        Intent intent = new Intent(this, ExistantActivity.class);
        boolean renderImg = false;
        if(AudioViewList.getInstance().getAudioViewList().size() > 0)
            renderImg = true;

        intent.putExtra(Constants.RENDER_IMG.name(), renderImg);
        startActivity(intent);

    }

}
