package com.example.android.miwok;

public class Word {
    //These variables keep track of Words and their translation
    private String m_number;
    private String m_translation;
    private int m_imageResourceID = 0;
    private int m_audioResourceID = 0;

    //Constructor for initializing member variables
    public Word(String number, String translation, int audioResourceID){
        m_number = number;
        m_translation = translation;
        m_audioResourceID = audioResourceID;
    }

    //Constructor for initializing member variables
    public Word(String number, String translation, int imageResourceID, int audioResourceID){
        m_number = number;
        m_translation = translation;
        m_imageResourceID = imageResourceID;
        m_audioResourceID = audioResourceID;
    }

    //Getters
    public String getNumber(){ return m_number; }
    public String getTranslation(){ return m_translation; }
    public int getImageResourceID(){ return m_imageResourceID; }
    public int getAudioResourceID(){ return m_audioResourceID; }
}
