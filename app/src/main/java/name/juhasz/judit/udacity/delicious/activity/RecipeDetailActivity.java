package name.juhasz.judit.udacity.delicious.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.adapter.StepAdapter;
import name.juhasz.judit.udacity.delicious.fragment.RecipeDetailFragment;
import name.juhasz.judit.udacity.delicious.model.Recipe;
import name.juhasz.judit.udacity.delicious.model.Step;

public class RecipeDetailActivity extends AppCompatActivity implements StepAdapter.OnClickListener {

    public static final String RECIPE_DATA = "RECIPE_DATA";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        final Intent intent = getIntent();

        if (null != intent && intent.hasExtra(RECIPE_DATA)) {
            final Recipe recipe = intent.getParcelableExtra(RECIPE_DATA);

            final RecipeDetailFragment fragment = new RecipeDetailFragment();
            final Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.RECIPE_DATA, recipe);
            fragment.setArguments(arguments);

            final FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.container_recipe_details, fragment)
                    .commit();
        }
    }

    @Override
    public void onStepListItemClick(final Step step) {
        final Intent intentToStartStepDetailActivity =
                new Intent(this, StepDetailActivity.class);
        intentToStartStepDetailActivity.putExtra(StepDetailActivity.STEP_DATA, step);
        startActivity(intentToStartStepDetailActivity);
    }
}
