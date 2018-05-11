package shecodeshack.org.audioview.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import shecodeshack.org.audioview.MainActivity;
import shecodeshack.org.audioview.R;
import shecodeshack.org.audioview.constants.Constants;
import shecodeshack.org.audioview.model.AudioView;
import shecodeshack.org.audioview.model.ListWrapper;


/**
 * Activity to create new AudioView object.
 */
public class CreateAudioViewActivity extends AppCompatActivity {
    AudioView audioViewObject = new AudioView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_audio_view);
        findViewById(R.id.btn_save).setClickable(false);
    }

    /**
     * Saves AudioView object to list and returns to main activity.
     */
    public void onClickSave(View view){
        ListWrapper<AudioView> ls = (ListWrapper) getIntent().getSerializableExtra(Constants.AUDIO_VIEW_LIST.name());
        ls.getList().add(audioViewObject);
        finish();
    }

    /**
     * Allows to add image to AudioView object. <br>
     * Activated when imageView is clicked.
     *///imageView.onClick:
    public void onClickImage(View view) {
        String readExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if ( ! hasPermission(readExternalStoragePermission)) {
            promptRequestPermissions(readExternalStoragePermission);
        }
        else {
            //permission granted
            pickImage();
        }
    }


    /** Checks if sent permission is granted */
    boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * Prompts a request for given permissions. <br>
     * Prompt is asynchronous, a user response invokes the {@code onRequestPermissionsResult()} method
     */
    private void promptRequestPermissions(String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //TODO add explanation to user
            }
        }
        ActivityCompat.requestPermissions( this, permissions,
                Constants.READ_EXTERNAL_STORAGE_PERMISSION.ordinal());
    }


    /**
     * Invoked from user response to request. <br>
     * Checks if user permitted to access media files for image retrieval.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.READ_EXTERNAL_STORAGE_PERMISSION.ordinal()) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                pickImage();
        }

    }

    /** Picks Image from gallery */
    public void pickImage() {
        Intent pickImgIntent = new Intent();
        pickImgIntent.setType("image/*");
        pickImgIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pickImgIntent, "Select Image"), Constants.PICK_IMAGE_REQUEST.ordinal());
    }


    /**
     * Activated after user picks image from gallery.
     * Image picked from gallery is shown on screen.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.PICK_IMAGE_REQUEST.ordinal()) {
            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData()); //check doc
                BufferedInputStream buff = new BufferedInputStream(inputStream);
                Bitmap bmp = BitmapFactory.decodeStream(buff);

                audioViewObject.setImage(bmp);

                ImageView galleryImg = findViewById(R.id.img_gallery);
                galleryImg.setImageBitmap(bmp);

                //allow save
                findViewById(R.id.btn_save).setClickable(true);

            }catch(FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }



}
