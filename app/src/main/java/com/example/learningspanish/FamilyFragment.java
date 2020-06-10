package com.example.learningspanish;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    /** Handles audio focus when playing a sound file */
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };


    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };


    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        // Create and setup the {@link AudioManager} to request audio focus


        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Father", "Padre", R.drawable.family_father, R.raw.family_father,"Hombre que ha tenido uno o más hijos o animal macho que ha tenido una o más crías."));
        words.add(new Word("Mother", "Madre", R.drawable.family_mother, R.raw.family_mother,"Mujer que ha tenido uno o más hijos, o animal hembra que ha tenido una o más crías."));
        words.add(new Word("Son", "Hijo", R.drawable.family_son, R.raw.family_hijo,"Persona o animal considerados con relación a su padre y a su madre o a uno de los dos."));
        words.add(new Word("Daughter", "Hija", R.drawable.family_daughter, R.raw.family_hija,"Persona o animal considerados con relación a su padre y a su madre o a uno de los dos."));
        words.add(new Word("older Brother", "Hermano Mayor", R.drawable.family_older_brother, R.raw.family_older_brother,"hermano Fórmula que se emplea para dirigirse a una persona con la que se tiene confianza y amistad.mayor En el ejército de algunos países, militar de categoría equivalente a la de comandante."));
        words.add(new Word("Younger Brother", "Hermano Más Joven", R.drawable.family_younger_brother, R.raw.family_younger_brother,"hermano Fórmula que se emplea para dirigirse a una persona con la que se tiene confianza y amistad mas joven Es un hombre joven que se encuentra en las primeras etapas de su existencia o crecimiento."));
        words.add(new Word("Older Sister", "Hermana Mayor", R.drawable.family_older_sister, R.raw.family_older_sister,"Fórmula que se emplea para dirigirse a una persona con la que se tiene confianza y amistad.mayor En el ejército de algunos países, militar de categoría equivalente a la de comandante."));
        words.add(new Word("Younger Sister", "Hermana Menor", R.drawable.family_younger_sister, R.raw.family_younger_sister,"Fórmula que se emplea para dirigirse a una persona con la que se tiene confianza y amistad. menor = Pequeño"));
        words.add(new Word("Grandmother ", "Abuela", R.drawable.family_grandmother, R.raw.family_grandmother,"El abuelo y la abuela de una persona."));
        words.add(new Word("Grandfather", "Abuelo", R.drawable.family_grandfather, R.raw.family_grandfather,"El abuelo y la abuela de una persona."));



        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();
                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                    Intent i = new Intent(getContext(),ItemDetails.class);;
                    i.putExtra(ItemDetails.EXTRA_DESCRIPTION_TEXT, word.getmDefaultDetails());
                    i.putExtra(ItemDetails.EXTRA_ENGLISH_TEXT, word.getDefaultTranslation());
                    i.putExtra(ItemDetails.EXTRA_SPANISH_TEXT,word.getSpanishTranslation());
                    i.putExtra(ItemDetails.EXTRA_RESOURCE_ID,word.getImageResourceId());
                    startActivity(i);
                }


            }
        });


        return rootView;

    }



    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}



