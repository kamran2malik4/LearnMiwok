package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordTranslationAdapter extends ArrayAdapter<Word> {

    private int m_backgroundColorResourceID;

    public WordTranslationAdapter(Context context, ArrayList<Word> objects, int backgroundColorResourceID) {
        super(context, 0, objects);
        m_backgroundColorResourceID = backgroundColorResourceID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentItem = getItem(position);

        TextView number = view.findViewById(R.id.number_text);
        number.setText(currentItem.getNumber());

        TextView translation = view.findViewById(R.id.translation_text);
        translation.setText(currentItem.getTranslation());

        int imageResourceID = currentItem.getImageResourceID();

        ImageView imageView = view.findViewById(R.id.image_representation);

        if(imageResourceID != 0){
            imageView.setImageResource(currentItem.getImageResourceID());
        }
        else{
            imageView.setVisibility(View.GONE);
        }

        LinearLayout wordsBackground = view.findViewById(R.id.words_list_background);
        wordsBackground.setBackgroundColor(m_backgroundColorResourceID);

        return view;
    }
}
