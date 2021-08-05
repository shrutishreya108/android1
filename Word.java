package com.example.android.miwok;

import android.util.Log;

public class Word {
    private String miwokWord;
    private String englishWord;
    private int img = FLAG;
    private static final int FLAG = -1;
    private int audio;
    public Word(String s1,String s2,int rid)
    {
        miwokWord = s1;
        englishWord = s2;
        audio = rid;
    }
    public Word(String s1,String s2,int imgResourceId,int rid)
    {
        Log.v("A","enter2");
        miwokWord = s1;
        englishWord = s2;
        img = imgResourceId;
        audio = rid;
    }
    public String getMiwokTranslation()
    {

        return miwokWord;
    }
    public String getDefaultTranslation()
    {
        Log.v("A",miwokWord);
        return englishWord;
    }
    public int getImg(){return  img;}
    public int getRid(){return  audio;}
    public boolean hasImage(){
        return img != FLAG;
    }
}
