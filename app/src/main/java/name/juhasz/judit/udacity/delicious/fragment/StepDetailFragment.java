package name.juhasz.judit.udacity.delicious.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.model.Step;

public class StepDetailFragment extends Fragment {

    public static final String STEP_DATA = "STEP_DATA";

    private SimpleExoPlayer mVideoPlayer = null;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View rootView =
                inflater.inflate(R.layout.fragment_step_detail, container, false);

        final Bundle arguments = getArguments();
        final Step step = (Step) arguments.getParcelable(STEP_DATA);

        final SimpleExoPlayerView visualizationSimpleExoPlayerView =
                (SimpleExoPlayerView) rootView.findViewById(R.id.sepv_step_visualization);
        final String videoUrlString = step.getVideoUrl();
        if (null != videoUrlString && !videoUrlString.isEmpty()) {
            // 1. Create a default TrackSelector
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            // 2. Create the player
            final Context context = getContext();
            mVideoPlayer =
                    ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            // Bind the player to the view.
            visualizationSimpleExoPlayerView.setPlayer(mVideoPlayer);

            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "ExoPlayer"));
            // Produces Extractor instances for parsing the media data.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            // This is the MediaSource representing the media to be played.
            final Uri videoUri = Uri.parse(videoUrlString);
            MediaSource videoSource = new ExtractorMediaSource(videoUri,
                    dataSourceFactory, extractorsFactory, null, null);
            // Prepare the player with the source.
            mVideoPlayer.prepare(videoSource);
        } else {
            visualizationSimpleExoPlayerView.setVisibility(View.GONE);
        }

        final TextView description = (TextView) rootView.findViewById(R.id.tv_step_description);
        description.setText(step.getDescription());

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mVideoPlayer) {
            mVideoPlayer.release();
        }
    }
}