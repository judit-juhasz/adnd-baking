package name.juhasz.judit.udacity.delicious.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.model.Recipe;

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
        final TextView recipeNameTextView = (TextView) rootView.findViewById(R.id.tv_recipe_name);
        recipeNameTextView.setText(recipe.getName());
        return rootView;
    }
}
