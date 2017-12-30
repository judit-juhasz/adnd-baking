package name.juhasz.judit.udacity.delicious.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private final static String LOG_TAG = RecipeAdapter.class.getSimpleName();

    public interface OnClickListener {
        void onRecipeListItemClick(Recipe recipe);
    }

    private Recipe[] mRecipes = null;

    public void setRecipesData(Recipe[] recipes) {
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }

    private RecipeAdapter.OnClickListener mListener;

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        final int layoutIdForListItem = R.layout.item_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = mRecipes[position];
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) {
            return 0;
        } else {
            return mRecipes.length;
        }
    }

    public RecipeAdapter(RecipeAdapter.OnClickListener listener) {
        mRecipes = null;
        mListener = listener;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mRecipeName;
        private TextView mServings;


        public RecipeViewHolder(View itemView) {
            super(itemView);

            mRecipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            mServings = (TextView) itemView.findViewById(R.id.tv_servings);
            itemView.setOnClickListener(this);
        }

        void bind(Recipe recipe) {
            String nameOfRecipe = recipe.getName();
            mRecipeName.setText(nameOfRecipe);
            Integer servings = recipe.getServings();
            mServings.setText(servings.toString());
        }

        @Override
        public void onClick(View view) {
            if(null != mRecipes) {
                final int adapterPosition = getAdapterPosition();
                final Recipe recipe = mRecipes[adapterPosition];
                mListener.onRecipeListItemClick(recipe);
            } else {
                Log.wtf(LOG_TAG, "OnClick handler call with empty recipe list.");
            }

        }
    }
}
