package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.miwok.R;
import com.example.android.miwok.Word;
import com.example.android.miwok.WordAdapter;

import java.util.ArrayList;


public class PhrasesFragment extends Fragment {

    private AudioManager mAudioManager;
    private MediaPlayer mMediaPlayer;
    private  AudioManager.OnAudioFocusChangeListener mOnFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
                mMediaPlayer.start();
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                mMediaPlayer.stop();
                releaseMediaPlayer();
            }

        }
    };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    private  void releaseMediaPlayer()
    {
        if(mMediaPlayer != null)
        {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public PhrasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        final ArrayList<Word> arrayList= new ArrayList<Word>();

        arrayList.add(new Word("apne ker shubh naam?","what's your name?",R.raw.phrase_my_name_is));
        arrayList.add(new Word("pranaam","Hello",R.raw.phrase_are_you_coming));
        arrayList.add(new Word("Hammar naam aichh ____.","My name is ____",R.raw.phrase_come_here));
        arrayList.add(new Word("Badd dukhi bhelau","I'm sorry",R.raw.phrase_how_are_you_feeling));
        arrayList.add(new Word("Atay keyo chhathi je angreji bhakha bajbak gyan rôkhait chhaith?","Is there someone here who speaks English?",R.raw.phrase_im_coming));
        arrayList.add(new Word("Kani madaid kôrait jau","Help",R.raw.phrase_im_feeling_good));
        arrayList.add(new Word("Nai bujhi sakal","I don't understand",R.raw.phrase_what_is_your_name));
        arrayList.add(new Word("Hamraa asgar choir diyau.","Leave me alone",R.raw.phrase_where_are_you_going));
        arrayList.add(new Word("Hammar bag haraa gel aichh","I lost my bag",R.raw.phrase_yes_im_coming));
        Log.v("A","enter1");

        WordAdapter adapter = new WordAdapter(getActivity(),arrayList,R.color.category_phrases);
        ListView listview = (ListView) rootView.findViewById(R.id.list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = arrayList.get(position);
                releaseMediaPlayer();
                mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

                int res = mAudioManager.requestAudioFocus(mOnFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getRid());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }
}