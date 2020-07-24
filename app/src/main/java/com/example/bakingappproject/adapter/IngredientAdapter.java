package com.example.bakingappproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingappproject.R;
import com.example.bakingappproject.model.Recipe;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{
    private static final String TAG=RecipieAdapter.class.getSimpleName();
    private Context context;

    private List<Recipe.IngredientsBean> mList;

    public IngredientAdapter(Context context, List<Recipe.IngredientsBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.ingredientlist,parent,false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Recipe.IngredientsBean object=mList.get(position);
        String quantity=Float.toString(object.getQuantity())+object.getMeasure();
        holder.qty.setText(quantity);
        holder.description.setText(object.getIngredient());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder{
        TextView qty,description;
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            qty=itemView.findViewById(R.id.tv_ingredient_dose);
            description=itemView.findViewById(R.id.tv_ingredient_name);

        }
    }
}
