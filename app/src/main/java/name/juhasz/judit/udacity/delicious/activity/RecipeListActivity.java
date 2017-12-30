package name.juhasz.judit.udacity.delicious.activity;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.adapter.RecipeAdapter;
import name.juhasz.judit.udacity.delicious.model.Recipe;
import name.juhasz.judit.udacity.delicious.network.FetchRecipesTask;

public class RecipeListActivity extends AppCompatActivity implements
        RecipeAdapter.OnClickListener, FetchRecipesTask.Listener {

    private RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        RecyclerView recipesRecycleView = (RecyclerView) findViewById(R.id.rv_recipes);
        final RecipeAdapter.OnClickListener listener = this;

        mAdapter = new RecipeAdapter(listener);
        recipesRecycleView.setAdapter(mAdapter);

        int spanCount = 1;
        final int orientation = this.getResources().getConfiguration().orientation;
        if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
            spanCount = 3;
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recipesRecycleView.setLayoutManager(layoutManager);

        new FetchRecipesTask(this).execute();
    }

    @Override
    public void onFetchRecipesFinished(Recipe[] recipes) {
        mAdapter.setRecipesData(recipes);
    }

    @Override
    public void onRecipeListItemClick(Recipe recipe) {

    }
}
