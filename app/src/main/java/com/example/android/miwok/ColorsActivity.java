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

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer m_player = null;

    private AudioManager m_audioManager;

    private AudioManager.OnAudioFocusChangeListener m_audioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange){
                        case AudioManager.AUDIOFOCUS_LOSS:
                            Log.i("Audios", "Audios");
                            releaseMedia();
                            m_audioManager.abandonAudioFocus(m_audioFocusChangeListener);
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                            Log.i("Audios", "Audios");
                            if(m_player != null){
                                m_player.pause();
                            }
                        case AudioManager.AUDIOFOCUS_GAIN:
                            Log.i("Audios", "Audios");
                            if(m_player != null){
                                m_player.start();
                            }
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        m_audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int audioResult = m_audioManager.requestAudioFocus(m_audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        ListView list = findViewById(R.id.colors_list);

        ArrayList<Word>words = new ArrayList<>();
        words.add(new Word("Red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("Green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("Brown", "takakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("Gray", "tokoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("Black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("White", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("Dusty Yellow", "toppisu", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("Mustard Yellow", "ch'wittu", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordTranslationAdapter adapter = new WordTranslationAdapter(this, words, getResources().getColor(R.color.category_colors));

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