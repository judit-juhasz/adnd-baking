package name.juhasz.judit.udacity.delicious.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.adapter.IngredientAdapter;
import name.juhasz.judit.udacity.delicious.adapter.StepAdapter;
import name.juhasz.judit.udacity.delicious.model.Ingredient;
import name.juhasz.judit.udacity.delicious.model.Recipe;
import name.juhasz.judit.udacity.delicious.model.Step;

public class RecipeDetailFragment extends Fragment {

    public static final String RECIPE_DATA = "RECIPE_DATA";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View rootView =
                inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        final Bundle arguments = getArguments();
        final Recipe recipe = (Recipe) arguments.getParcelable(RECIPE_DATA);

        final RecyclerView ingredientsRecycleView = rootView.findViewById(R.id.rv_ingredients);
        final IngredientAdapter ingredientAdapter = new IngredientAdapter();
        final List<Ingredient> ingredients = recipe.getIngredients();
        ingredientAdapter.setIngredients(ingredients.toArray(new Ingredient[ingredients.size()]));
        ingredientsRecycleView.setAdapter(ingredientAdapter);

        final RecyclerView stepsRecycleView = rootView.findViewById(R.id.rv_steps);
        final StepAdapter stepAdapter = new StepAdapter(null);
        final List<Step> steps = recipe.getSteps();
        stepAdapter.setSteps(steps.toArray(new Step[steps.size()]));
        stepsRecycleView.setAdapter(stepAdapter);

        return rootView;
    }
}
