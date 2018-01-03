package name.juhasz.judit.udacity.delicious.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import java.util.List;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.model.Ingredient;
import name.juhasz.judit.udacity.delicious.model.Recipe;

public class IngredientListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory,
        SharedPreferences.OnSharedPreferenceChangeListener {
    private Context mContext = null;
    private List<Ingredient> mIngredients = null;

    public IngredientListRemoteViewsFactory(@NonNull final Context context) {
        mContext = context;
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(mContext);
        fetchIngredientList(sharedPreferences);
    }

    @Override
    public void onCreate() {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public int getCount() {
        return (null == mIngredients) ? 0 : mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_ingredient);
        final Ingredient ingredient = mIngredients.get(position);
        final String ingredientNameText = ingredient.getIngredientName();
        final String ingredientText = mContext.getString(R.string.ingredient_format,
                String.valueOf(ingredient.getQuantity()),
                ingredient.getMeasure());
        rv.setTextViewText(R.id.tv_ingredient_name, ingredientNameText);
        rv.setTextViewText(R.id.tv_ingredient, ingredientText);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences,
                                          final String key) {
        if (key.equals("recipe_on_widget")) {
            fetchIngredientList(sharedPreferences);
        }
    }

    private void fetchIngredientList(final SharedPreferences sharedPreferences) {
        final String recipeJson = sharedPreferences.getString("recipe_on_widget",null);
        final Recipe recipe =
                (null == recipeJson) ? null : new Gson().fromJson(recipeJson, Recipe.class);
        if (null != recipe) {
            mIngredients = recipe.getIngredients();
            onDataSetChanged();
        }
    }
}
