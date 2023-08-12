package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer m_player = null;

    private AudioManager m_audioManager;

    private AudioManager.OnAudioFocusChangeListener m_audioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange){
                        case AudioManager.AUDIOFOCUS_LOSS:
                            releaseMedia();
                            m_audioManager.abandonAudioFocus(m_audioFocusChangeListener);
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                            if(m_player != null){
                                m_player.pause();
                            }
                        case AudioManager.AUDIOFOCUS_GAIN:
                            if(m_player != null){
                                m_player.start();
                            }
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        m_audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int audioResult = m_audioManager.requestAudioFocus(m_audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);


        ListView list = findViewById(R.id.family_list);

        ArrayList<Word>words = new ArrayList<>();
        words.add(new Word("Father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("Mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("Son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("Daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("Older Brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("Younger Brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("Older Sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("Younger Sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("Grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("Grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordTranslationAdapter adapter = new WordTranslationAdapter(this, words, getResources().getColor(R.color.category_family));

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                if(m_player != null){
                    releaseMedia();
                }
                m_player = MediaPlayer.create(getApplicationContext(), word.getAudioResourceID());
                m_player.start();
                m_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                        releaseMedia();
                    }
                });
            }
        });
    }

    private void releaseMedia(){
        if(m_player != null){
            m_player.stop();
            m_player.release();
            m_player = null;
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        releaseMedia();
    }
}