package com.example.bakingappproject.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakingappproject.R;
import com.example.bakingappproject.model.Recipe;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MediaPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediaPlayerFragment extends Fragment {
    PlayerView playerView;
    private SimpleExoPlayer player;
    Recipe.StepsBean object;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private boolean playWhenReady = true;
    String url;
    ProgressBar progressBar;
    TextView textView;

    public Recipe.StepsBean getObject() {
        return object;
    }

    public void setObject(Recipe.StepsBean object) {
        this.object = object;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
            Toast.makeText(getContext(), "ONSTART", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
            Toast.makeText(getContext(), "ONSTOP", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
            Toast.makeText(getContext(), "ONRESUME", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
            Toast.makeText(getContext(), "ONPAUSE", Toast.LENGTH_SHORT).show();
        }
    }


    public MediaPlayerFragment() {
        // Required empty public constructor
    }


    public static MediaPlayerFragment newInstance(String param1, String param2) {
        MediaPlayerFragment fragment = new MediaPlayerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_media_player,container,false);
        playerView=rootView.findViewById(R.id.videoView);
        progressBar=rootView.findViewById(R.id.progressBar);
        textView=rootView.findViewById(R.id.noMedia);
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.INVISIBLE);



        return rootView;

    }
    private void initializePlayer() {

        player = new SimpleExoPlayer.Builder(getContext()).build();
        playerView.setPlayer(player);
        url=object.getVideoURL();
        if (!url.equals("")) {

            Uri uri = Uri.parse(url);

            Toast.makeText(getContext(), "url :" + url, Toast.LENGTH_SHORT).show();
            MediaSource mediaSource = buildMediaSource(uri);

            player.setPlayWhenReady(playWhenReady);

            player.seekTo(currentWindow, playbackPosition);


            player.prepare(mediaSource, false, false);
        }else {
            textView.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.INVISIBLE);
        }
        if (url==null){
            Toast.makeText(getContext(), "No url Provided", Toast.LENGTH_SHORT).show();
        }else {
            progressBar.setVisibility(View.INVISIBLE);

        }



    }
    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), "exoplayer");


      return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);


    }
    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }

    }
}