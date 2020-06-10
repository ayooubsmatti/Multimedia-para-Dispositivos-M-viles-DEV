package com.example.learningspanish;

public class Word {

    private String mDefaultTranslation;


    private String mSpanishTranslation;


    private String mDefaultDetails;



    // audio resource ID
    private int mAudioResourceId;

    // Drawable resource ID
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED= -1;

    /*
     * Create a new AndroidFlavor object.
     *
     * @param vName is the name of the Android version (e.g. Gingerbread)
     * @param vNumber is the corresponding Android version number (e.g. 2.3-2.7)
     * @param image is drawable reference ID that corresponds to the Android version
     * */
    public Word(String DefaultTranslation, String SpanishTranslation,int imageResourceId, int AudioResourceId)
    {
        mDefaultTranslation = DefaultTranslation;
        mSpanishTranslation = SpanishTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId    = AudioResourceId;
    }
    public Word(String DefaultTranslation, String SpanishTranslation,int imageResourceId, int AudioResourceId,String defaultDetails)
    {
        mDefaultTranslation = DefaultTranslation;
        mSpanishTranslation = SpanishTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId    = AudioResourceId;
        mDefaultDetails = defaultDetails;
    }

    public Word(String DefaultTranslation, String SpanishTranslation,int AudioResourceId)
    {
        mDefaultTranslation = DefaultTranslation;
        mSpanishTranslation = SpanishTranslation;
        mAudioResourceId    = AudioResourceId;
    }

    /**
     * Get the name of the Android version
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Android version number
     */
    public String getSpanishTranslation() {
        return mSpanishTranslation;
    }

    /**
     * Get the image resource ID
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Get the audio resource ID
     */
    public int getAudioResourceId(){
        return mAudioResourceId;
    }

    public String getmDefaultDetails() {
        return mDefaultDetails;
    }

    public void setmDefaultDetails(String mDefaultDetails) {
        this.mDefaultDetails = mDefaultDetails;
    }


    public Boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
