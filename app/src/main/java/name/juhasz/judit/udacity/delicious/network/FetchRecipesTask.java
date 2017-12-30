package name.juhasz.judit.udacity.delicious.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import name.juhasz.judit.udacity.delicious.model.Recipe;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchRecipesTask extends AsyncTask<Void, Void, Recipe[]> {
    private static final String LOG_TAG = FetchRecipesTask.class.getSimpleName();

    private Listener mListener;

    public interface Listener {
        void onFetchRecipesFinished(Recipe[] recipes);
    }

    public FetchRecipesTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected Recipe[] doInBackground(Void... params) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TheRecipeDbService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Class<TheRecipeDbService> theRecipeDbServiceDefinition = TheRecipeDbService.class;

        TheRecipeDbService recipeDbService = retrofit.create(theRecipeDbServiceDefinition);

        final Call<List<Recipe>> call = recipeDbService.getRecipes();
        if (null == call) {
            return null;
        }

        try {
            final Response<List<Recipe>> response = call.execute();
            if (response.isSuccessful()) {
                final List<Recipe> recipes = response.body();

                return recipes.toArray(new Recipe[0]);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "The Recipe DB service connection error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Recipe[] recipes) {
        notifyAboutTaskCompletion(recipes);
    }

    @Override
    protected void onCancelled() {
        notifyAboutTaskCompletion(null);
    }

    private void notifyAboutTaskCompletion(Recipe[] recipes) {
        if (null == mListener) {
            Log.w(LOG_TAG, "Nobody is listening for FetchRecipesTask.");
        } else {
            mListener.onFetchRecipesFinished(recipes);
        }
    }
}
