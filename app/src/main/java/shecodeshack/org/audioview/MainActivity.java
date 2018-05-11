package shecodeshack.org.audioview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import shecodeshack.org.audioview.activity.CreateAudioViewActivity;
import shecodeshack.org.audioview.activity.ExistantActivity;

/**
 * Main Activity of the app.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO create view
    }

    /** Opens {@link CreateAudioViewActivity} view. */
    public void openCreateAudioViewActivity(View view) {
        Intent intent = new Intent(this, CreateAudioViewActivity.class);
        startActivity(intent);
    }

    /** method to go from the main activity to the Existant Activity*/
    public void goExistantActivity(View view) {
        Intent intent = new Intent(this, ExistantActivity.class);
        startActivity(intent);

    }





}
