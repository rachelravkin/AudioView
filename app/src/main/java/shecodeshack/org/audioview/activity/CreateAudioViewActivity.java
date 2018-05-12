package shecodeshack.org.audioview.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import shecodeshack.org.audioview.R;
import shecodeshack.org.audioview.constants.Constants;
import shecodeshack.org.audioview.model.AudioView;
import shecodeshack.org.audioview.model.AudioViewList;


/**
 * Activity to create new AudioView object.
 */
public class CreateAudioViewActivity extends AppCompatActivity {
    AudioView audioViewObject = new AudioView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_audio_view);
        findViewById(R.id.btn_save).setVisibility(View.INVISIBLE);

        //init record:
        record = findViewById(R.id.btn_record);
        recordOff = findViewById(R.id.btn_record_off);
        record.setEnabled(true);
        recordOff.setEnabled(false);
    }

    /**
     * Saves AudioView object to list and returns to main activity.
     */
    public void onClickSave(View view){
        //save to singleton instance
        AudioViewList.getInstance().getAudioViewList().add(audioViewObject);
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
                findViewById(R.id.btn_save).setVisibility(View.VISIBLE);

            }catch(FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }





    /*
    Record Implementation
     */

    private FloatingActionButton record, recordOff;
    private MediaRecorder mediaRecorder;
    private String audioPath;



    public void startRecord(View view) {
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                &&hasPermission(Manifest.permission.RECORD_AUDIO) ) {
            audioPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                    + UUID.randomUUID().toString() + "_audio_record.3gp";
            setupMediaRecorder();
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            }catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            record.setEnabled(false);
            recordOff.setEnabled(true);
            Toast.makeText(this, "Recording...", Toast.LENGTH_LONG).show();
        }
        else
            requestPermissions();
    }


    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(audioPath);
    }

    /**
     * Stops recording from device.
     */
    public void stopRecord(View v) {
        mediaRecorder.stop();
        recordOff.setEnabled(false);
        record.setEnabled(true);
    }




    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        }, Constants.RECORD_AUDIO_PERMISSION.ordinal());
    }





}
