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


public class FamilyMembersFragment extends Fragment {
    private AudioManager audioManager;
    private MediaPlayer mMediaPlayer;
    private AudioManager.OnAudioFocusChangeListener mOnAudioChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);

            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                mMediaPlayer.stop();
                releaseMediaPlayer();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                mMediaPlayer.start();
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
    private MediaPlayer.OnCompletionListener mCompletionListener = mp -> releaseMediaPlayer();

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public FamilyMembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.word_list, container, false);
        final ArrayList<Word> arrayList= new ArrayList<>();

        arrayList.add(new Word("maa","mother",R.drawable.family_mother,R.raw.family_mother));
        arrayList.add(new Word("pappa","Father",R.drawable.family_father,R.raw.family_father));
        arrayList.add(new Word("bhai","brother",R.drawable.family_older_brother,R.raw.family_older_brother));
        arrayList.add(new Word("bahin","sister",R.drawable.family_older_sister,R.raw.family_younger_sister));
        arrayList.add(new Word("baba","grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));
        arrayList.add(new Word("nana","maternal grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));
        arrayList.add(new Word("dadi","grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
        arrayList.add(new Word("nani","maternal grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
        arrayList.add(new Word("chacha","uncle",R.drawable.family_father,R.raw.family_father));
        arrayList.add(new Word("chachi","aunt",R.drawable.family_mother,R.raw.family_mother));
        arrayList.add(new Word("mausi","aunt",R.drawable.family_mother,R.raw.family_mother));
        arrayList.add(new Word("mausa","uncle",R.drawable.family_father,R.raw.family_father));
        arrayList.add(new Word("mama","maternal uncle",R.drawable.family_older_brother,R.raw.family_older_brother));
        arrayList.add(new Word("mami","maternal aunt",R.drawable.family_older_sister,R.raw.family_older_sister));
        WordAdapter adapter = new WordAdapter(getActivity(),arrayList, R.color.category_family);
        ListView listview = (ListView) rootView.findViewById(R.id.list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = arrayList.get(position);
                audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
                int res = audioManager.requestAudioFocus(mOnAudioChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    releaseMediaPlayer();
                    mMediaPlayer = MediaPlayer.create(getActivity(),word.getRid());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }
}