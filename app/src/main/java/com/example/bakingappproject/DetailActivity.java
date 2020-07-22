package com.example.bakingappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bakingappproject.model.Recipe;

import java.util.ArrayList;

import static com.example.bakingappproject.constant.Constant.INGREDIENT;
import static com.example.bakingappproject.constant.Constant.STEPS;

public class DetailActivity extends AppCompatActivity {
    ArrayList<Recipe.IngredientsBean> listOfIngredients;
    ArrayList<Recipe.StepsBean> listofSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        listOfIngredients=intent.getParcelableArrayListExtra(INGREDIENT);
        listofSteps=intent.getParcelableArrayListExtra(STEPS);


    }
}