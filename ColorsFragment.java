package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ColorsFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                mMediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                mMediaPlayer.stop();
                releaseMediaPlayer();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
        }
    };
    private  void releaseMediaPlayer()
    {
        if(mMediaPlayer != null)
        {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };



    public ColorsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.word_list, container, false);
        final ArrayList<Word> arrayList= new ArrayList<Word>();

        arrayList.add(new Word("lal","red",R.drawable.color_red,R.raw.color_red));
        arrayList.add(new Word("kala","Black",R.drawable.color_black,R.raw.color_black));
        arrayList.add(new Word("piyar","Yellow",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        arrayList.add(new Word("grey","grey",R.drawable.color_gray,R.raw.color_gray));
        arrayList.add(new Word("bhura","brown",R.drawable.color_brown,R.raw.color_brown));
        arrayList.add(new Word("ujjar","white",R.drawable.color_white,R.raw.color_white));
        arrayList.add(new Word("hara","green",R.drawable.color_green,R.raw.color_green));
        WordAdapter adapter = new WordAdapter(getActivity(),arrayList, R.color.category_colors);
        ListView listview = (ListView) rootView.findViewById(R.id.list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = arrayList.get(position);
                releaseMediaPlayer();
                audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
                int res = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mMediaPlayer = MediaPlayer.create(getActivity(),word.getRid());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }
}