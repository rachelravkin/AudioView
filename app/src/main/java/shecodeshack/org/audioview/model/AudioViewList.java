package shecodeshack.org.audioview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to hold all AudioView items.
 */
public class AudioViewList implements Serializable {
    private static AudioViewList instance;
    private List<AudioView> audioViewList;

    private AudioViewList(){
        audioViewList = new ArrayList<>();
    }

    public static AudioViewList getInstance() {
        synchronized (AudioViewList.class) {
            if (instance == null)
                instance = new AudioViewList();
        }
        return instance;

    }

    public List<AudioView> getAudioViewList() { return audioViewList; }
    public void setAudioViewList(List<AudioView> audioViewList) { this.audioViewList = audioViewList; }



}
