package com.example.bakingappproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingappproject.adapter.IngredientAdapter;
import com.example.bakingappproject.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bakingappproject.constant.Constant.INGREDIENT;

public class DetailIngredient extends AppCompatActivity {
    ArrayList<Recipe.IngredientsBean> mList;
    @BindView(R.id.ingredientRecycler)
    RecyclerView ingredientRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ingredient);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mList = intent.getParcelableArrayListExtra(INGREDIENT);
        ingredientRecycler.setLayoutManager(new LinearLayoutManager(this));
        ingredientRecycler.setHasFixedSize(true);
        IngredientAdapter ingredientAdapter=new IngredientAdapter(this,mList);
        ingredientRecycler.setAdapter(ingredientAdapter);



    }
}