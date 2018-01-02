package name.juhasz.judit.udacity.delicious.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.fragment.StepDetailFragment;
import name.juhasz.judit.udacity.delicious.model.Step;

public class StepDetailActivity extends AppCompatActivity {

    public static final String STEP_DATA = "STEP_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        final Intent intent = getIntent();

        if (null != intent && intent.hasExtra(STEP_DATA)) {
            final Step step = intent.getParcelableExtra(STEP_DATA);

            final StepDetailFragment fragment = new StepDetailFragment();
            final Bundle arguments = new Bundle();
            arguments.putParcelable(StepDetailFragment.STEP_DATA, step);
            final int orientation = this.getResources().getConfiguration().orientation;
            if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
                hideSystemUi();
                arguments.putBoolean(StepDetailFragment.FULLSCREEN_VIDEO, true);
            }
            fragment.setArguments(arguments);

            final FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.container_step_details, fragment)
                    .commit();
        }
    }

    private void hideSystemUi() {
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }
}
