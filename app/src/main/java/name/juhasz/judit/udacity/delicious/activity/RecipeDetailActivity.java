package name.juhasz.judit.udacity.delicious.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.adapter.StepAdapter;
import name.juhasz.judit.udacity.delicious.fragment.RecipeDetailFragment;
import name.juhasz.judit.udacity.delicious.fragment.StepDetailFragment;
import name.juhasz.judit.udacity.delicious.model.Recipe;
import name.juhasz.judit.udacity.delicious.model.Step;
import name.juhasz.judit.udacity.delicious.widget.IngredientListWidgetProvider;

public class RecipeDetailActivity extends AppCompatActivity implements StepAdapter.OnClickListener {

    public static final String RECIPE_DATA = "RECIPE_DATA";

    private boolean mTwoPaneMode;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        final Intent intent = getIntent();

        if (null != intent && intent.hasExtra(RECIPE_DATA)&& savedInstanceState == null) {
            final Recipe recipe = intent.getParcelableExtra(RECIPE_DATA);

            setTitle(recipe.getName());

            final RecipeDetailFragment fragment = new RecipeDetailFragment();
            final Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.RECIPE_DATA, recipe);
            fragment.setArguments(arguments);

            final FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.container_recipe_details, fragment)
                    .commit();

            mTwoPaneMode = isTwoPaneMode();
            if (mTwoPaneMode) {
                final List<Step> steps = recipe.getSteps();
                final Step firstStep = steps.get(0);
                if (null != firstStep) {
                    setStepDetailFragment(firstStep);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = RecipeDetailActivity.this;
        final Intent intent = getIntent();
        final Recipe recipe = intent.getParcelableExtra(RECIPE_DATA);
        final int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_send_to_widget:
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getString(R.string.successful_send_to_widget), Snackbar.LENGTH_SHORT);

                snackbar.show();
                IngredientListWidgetProvider.updateAllWidgets(context, recipe);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStepListItemClick(final Step step) {
        if (mTwoPaneMode) {
            setStepDetailFragment(step);
        } else {
            final Intent intentToStartStepDetailActivity =
                    new Intent(this, StepDetailActivity.class);
            intentToStartStepDetailActivity.putExtra(StepDetailActivity.STEP_DATA, step);
            startActivity(intentToStartStepDetailActivity);
        }
    }

    private void setStepDetailFragment(final Step step) {
        final StepDetailFragment fragment = new StepDetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable(StepDetailFragment.STEP_DATA, step);
        fragment.setArguments(arguments);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container_step_details, fragment)
                .commit();
    }

    private boolean isTwoPaneMode() {
        return getResources().getBoolean(R.bool.tablet_device);
    }
}
