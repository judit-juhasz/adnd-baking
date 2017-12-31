package name.juhasz.judit.udacity.delicious.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.model.Ingredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    private Ingredient[] mIngredients = null;

    @Override
    public IngredientViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();

        final int layoutIdForListItem = R.layout.item_ingredient;
        final LayoutInflater inflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        final View view =
                inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        final IngredientViewHolder viewHolder = new IngredientViewHolder(view);

        return viewHolder;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.mIngredients = ingredients;
    }

    @Override
    public void onBindViewHolder(final IngredientViewHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (null == mIngredients) {
            return 0;
        } else {
            return mIngredients.length;
        }
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredientTextView;

        public IngredientViewHolder(final View itemView) {
            super(itemView);

            ingredientTextView = (TextView) itemView.findViewById(R.id.tv_ingredient);
        }

        void bind(final int position) {
            final Ingredient ingredient = mIngredients[position];
            final Double quantity = ingredient.getQuantity();
            final String measure = ingredient.getMeasure();
            final String name = ingredient.getIngredientName();
            ingredientTextView.setText(quantity + " " + measure + " " + name);
        }
    }
}