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
    public static final String FULLSCREEN_VIDEO = "FULLSCREEN_VIDEO";

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

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            final Context context = getContext();
            mVideoPlayer =
                    ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            visualizationSimpleExoPlayerView.setPlayer(mVideoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, getString(R.string.application_name_video_player)));

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            final Uri videoUri = Uri.parse(videoUrlString);
            MediaSource videoSource = new ExtractorMediaSource(videoUri,
                    dataSourceFactory, extractorsFactory, null, null);
            mVideoPlayer.prepare(videoSource);

            if (arguments.getBoolean(FULLSCREEN_VIDEO, false)) {
                visualizationSimpleExoPlayerView.post(new Runnable() {
                    @Override
                    public void run() {
                        final int fullscreenHeight = rootView.getLayoutParams().height;
                        visualizationSimpleExoPlayerView.getLayoutParams().height = fullscreenHeight;
                        visualizationSimpleExoPlayerView.requestLayout();
                    }
                });
            }
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