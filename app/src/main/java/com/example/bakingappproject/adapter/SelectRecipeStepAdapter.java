package com.example.bakingappproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingappproject.R;
import com.example.bakingappproject.model.Recipe;

import java.util.ArrayList;

public class SelectRecipeStepAdapter extends RecyclerView.Adapter<SelectRecipeStepAdapter.SelectRecipeStepViewHolder>{
   public static final String TAG=SelectRecipeStepAdapter.class.getSimpleName();
   private Context context;
   private ArrayList<Recipe.StepsBean> mList;
   final private ListItemClickListener mOnClickListener;

    public SelectRecipeStepAdapter(Context context, ArrayList<Recipe.StepsBean> mList, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.mList = mList;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public SelectRecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycleritem,parent,false);
        return new SelectRecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectRecipeStepViewHolder holder, int position) {
        Recipe.StepsBean obj=mList.get(position);
        holder.textView.setText(obj.getShortDescription());


    }

    @Override
    public int getItemCount() {
        if (mList==null){

            return 0;

        }else {

            return mList.size();
        }
    }


    public interface ListItemClickListener {
        void onListItemClick(int clickedIndex);
    }
   public class SelectRecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView textView;


       public SelectRecipeStepViewHolder(@NonNull View itemView) {
           super(itemView);
           textView=itemView.findViewById(R.id.text);
           itemView.setOnClickListener(this);
       }

       @Override
       public void onClick(View view) {
           int clickedPosition=getAdapterPosition();
           mOnClickListener.onListItemClick(clickedPosition);

       }
   }




}
