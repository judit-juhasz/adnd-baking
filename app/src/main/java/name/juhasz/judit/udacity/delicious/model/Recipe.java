package name.juhasz.judit.udacity.delicious.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable{

    @SerializedName("name")
    private String mName;

    @SerializedName("id")
    private String mId;

    @SerializedName("ingredients")
    private List<Ingredient> mIngredients = new ArrayList<>();

    @SerializedName("steps")
    private List<Step> mSteps = new ArrayList<>();

    @SerializedName("image")
    private String mRecipeImagePath;

    @SerializedName("servings")
    private int mServings;

    public Recipe(String recipeName,
                  String recipeId,
                  List<Ingredient> ingredients,
                  List<Step> steps,
                  String recipeImagePath,
                  Integer servings) {
        mName = recipeName;
        mId = recipeId;
        mIngredients = ingredients;
        mSteps = steps;
        mRecipeImagePath = recipeImagePath;
        mServings = servings;
    }

    public Recipe(String recipeName, Integer servings) {
        this.mName = recipeName;
        this.mServings = servings;
    }

    public String getName() { return mName; }

    public void setName(String recipeName) {
        this.mName = recipeName;
    }

    public String getId() { return mId; }

    public void setId(String recipeId) {
        this.mId = recipeId;
    }

    public List<Ingredient> getIngredients() { return mIngredients; }

    public void setIngredients(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    public List<Step> getSteps() { return mSteps; }

    public void setSteps(List<Step> steps) {
        this.mSteps = steps;
    }

    public String getRecipeImagePath() {
        return mRecipeImagePath;
    }

    public void setRecipeImagePath(String recipeImagePath) {
        this.mRecipeImagePath = recipeImagePath;
    }

    public Integer getServings() { return mServings; }

    public void setServings(Integer servings) {
        this.mServings = servings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mId);
        parcel.writeList(mIngredients);
        parcel.writeList(mSteps);
        parcel.writeInt(mServings);
        parcel.writeString(mRecipeImagePath);
    }

    public Recipe(Parcel in) {
        mName = in.readString();
        mId = in.readString();
        mIngredients = new ArrayList<Ingredient>();
        in.readList(mIngredients, Ingredient.class.getClassLoader());
        mSteps = new ArrayList<Step>();
        in.readList(mSteps, Step.class.getClassLoader());
        mServings = in.readInt();
        mRecipeImagePath = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}

