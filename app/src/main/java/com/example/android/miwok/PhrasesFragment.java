package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PhrasesFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phrases, container, false);
        m_audioManager = (AudioManager)this.getActivity().getSystemService(Context.AUDIO_SERVICE);

        int audioResult = m_audioManager.requestAudioFocus(m_audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);


        ListView list = view.findViewById(R.id.phrases_list);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is..", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        WordTranslationAdapter adapter = new WordTranslationAdapter(getActivity(), words, getResources().getColor(R.color.category_phrases));

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                if(m_player != null){
                    releaseMedia();
                }
                m_player = MediaPlayer.create(getActivity(), word.getAudioResourceID());
                m_player.start();
                m_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                        releaseMedia();
                    }
                });
            }
        });
        return view;
    }

    private void releaseMedia(){
        if(m_player != null){
            m_player.stop();
            m_player.release();
            m_player = null;
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        releaseMedia();
    }
}
