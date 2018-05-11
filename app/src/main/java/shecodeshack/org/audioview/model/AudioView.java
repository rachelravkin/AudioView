package shecodeshack.org.audioview.model;

import android.graphics.Bitmap;
import android.media.AudioRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * A class to preserve the connection between an image, a text and an audio.
 */
public class AudioView implements Serializable { //TODO change to Parcelable for performance
    private long id;
    private Bitmap image;
    private String text;
    private String audioPath;
    private Date created;

    public AudioView() {
        created = new Date();
    }

    public AudioView(Bitmap image, String text, String audioPath) {
        this();
        this.image = image;
        this.text = text;
        this.audioPath = audioPath;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Bitmap getImage() { return image; }
    public void setImage(Bitmap image) { this.image = image; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getAudio() { return audioPath; }
    public void setAudio(String audioPath) { this.audioPath = audioPath; }

    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }



}
