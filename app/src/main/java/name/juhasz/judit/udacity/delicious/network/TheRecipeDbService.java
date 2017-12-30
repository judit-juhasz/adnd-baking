package name.juhasz.judit.udacity.delicious.network;

import java.util.List;

import name.juhasz.judit.udacity.delicious.model.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TheRecipeDbService {
    public static final String URL = "http://go.udacity.com";

    @GET("android-baking-app-json")
    Call<List<Recipe>> getRecipes();
}
