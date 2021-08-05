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


public class NumbersFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private  AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
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

        }};
    private MediaPlayer.OnCompletionListener mCompletionListener = mp -> releaseMediaPlayer();

    private  void releaseMediaPlayer()
    {
        if(mMediaPlayer != null)
        {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    public NumbersFragment() {
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
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        final ArrayList<Word> arrayList= new ArrayList<Word>();
        arrayList.add(new Word("ek","one", R.drawable.number_one,R.raw.bigbang));
        arrayList.add(new Word("du","Two",R.drawable.number_two,R.raw.number_two));
        arrayList.add(new Word("teen","Three",R.drawable.number_three,R.raw.number_three));
        arrayList.add(new Word("chaar","Four",R.drawable.number_four,R.raw.number_four));
        arrayList.add(new Word("paanch","Five",R.drawable.number_five,R.raw.number_five));
        arrayList.add(new Word("che","Six", R.drawable.number_six,R.raw.number_six));
        arrayList.add(new Word("saat","Seven", R.drawable.number_seven,R.raw.number_seven));
        arrayList.add(new Word("aath","Eight", R.drawable.number_eight,R.raw.number_eight));
        arrayList.add(new Word("nau","Nine", R.drawable.number_nine,R.raw.number_nine));
        arrayList.add(new Word("das","Ten",R.drawable.number_ten,R.raw.number_ten ));
        WordAdapter adapter = new WordAdapter(getActivity(),arrayList,R.color.category_numbers);
        ListView listview = (ListView) rootView.findViewById(R.id.list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = arrayList.get(position);
                releaseMediaPlayer();
                mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
                int res = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
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