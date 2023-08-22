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

public class NumbersFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_numbers, container, false);
        // Inflate the layout for this fragment
        m_audioManager = (AudioManager)this.getActivity().getSystemService(Context.AUDIO_SERVICE);

        int audioResult = m_audioManager.requestAudioFocus(m_audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);


        ListView list = view.findViewById(R.id.numbers_list);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("One", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("Two", "otiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("Three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("Four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("Five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("Six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("Nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("Ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        WordTranslationAdapter adapter = new WordTranslationAdapter(getActivity(), words, getResources().getColor(R.color.category_numbers));

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
