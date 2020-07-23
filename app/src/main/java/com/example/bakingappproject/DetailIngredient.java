package com.example.bakingappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bakingappproject.model.Recipe;

import java.util.ArrayList;

import static com.example.bakingappproject.constant.Constant.INGREDIENT;

public class DetailIngredient extends AppCompatActivity {
    ArrayList<Recipe.IngredientsBean> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ingredient);
        Intent intent=getIntent();
        mList=intent.getParcelableArrayListExtra(INGREDIENT);
        Toast.makeText(this, "SIZE:"+mList.size(), Toast.LENGTH_SHORT).show();

    }
}