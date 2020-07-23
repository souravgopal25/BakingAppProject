package com.example.bakingappproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.bakingappproject.fragment.MediaPlayerFragment;
import com.example.bakingappproject.fragment.RecipeStepInstruction;
import com.example.bakingappproject.fragment.SelectRecipeStep;
import com.example.bakingappproject.model.Recipe;

import java.util.ArrayList;

import static com.example.bakingappproject.constant.Constant.INGREDIENT;
import static com.example.bakingappproject.constant.Constant.STEPS;

public class DetailActivity extends AppCompatActivity implements SelectRecipeStep.OnSelectRecipie{
    ArrayList<Recipe.IngredientsBean> listOfIngredients;
    ArrayList<Recipe.StepsBean> listofSteps;
    SelectRecipeStep selectRecipeStep;
    FragmentManager fragmentManager;
    Bundle bundle;
    int  pos;
    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        listOfIngredients=intent.getParcelableArrayListExtra(INGREDIENT);
        listofSteps=intent.getParcelableArrayListExtra(STEPS);
        bundle=new Bundle();
        bundle.putParcelableArrayList(INGREDIENT,listOfIngredients);
        bundle.putParcelableArrayList(STEPS,listofSteps);
        if (findViewById(R.id.tabMode)!=null){
            mTwoPane=true;
            if (savedInstanceState==null){
                FragmentManager fragmentManager=getSupportFragmentManager();
                SelectRecipeStep selectRecipeStep=new SelectRecipeStep();

                selectRecipeStep.setListOfIngredients(listOfIngredients);
                selectRecipeStep.setListofStep(listofSteps);
                fragmentManager.beginTransaction().add(R.id.selectRecipieDetail,selectRecipeStep).commit();
                MediaPlayerFragment mediaPlayerFragment=new MediaPlayerFragment();

                mediaPlayerFragment.setObject(listofSteps.get(0));
                fragmentManager.beginTransaction().add(R.id.playerFragment,mediaPlayerFragment).commit();
                RecipeStepInstruction recipeStepInstruction=new RecipeStepInstruction();

                recipeStepInstruction.setObject(listofSteps.get(0));
                fragmentManager.beginTransaction().add(R.id.descriptionFragment,recipeStepInstruction).commit();

            }

        }else {
            mTwoPane=false;
        }


        initialization();



    }

    private void initialization() {
        if (mTwoPane==false) {


            selectRecipeStep = new SelectRecipeStep();
            selectRecipeStep.setListofStep(listofSteps);
            selectRecipeStep.setListOfIngredients(listOfIngredients);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.frame, selectRecipeStep).commit();
        }

    }

    @Override
    public void onRecipieSelected(int position) {
        Toast.makeText(this, "CLICKED ON POSITION "+position, Toast.LENGTH_SHORT).show();
        if (mTwoPane){
            FragmentManager fragmentManager=getSupportFragmentManager();
            SelectRecipeStep selectRecipeStep=new SelectRecipeStep();
            Toast.makeText(this, "CLCIKED"+position, Toast.LENGTH_SHORT).show();
            // PASS DATA
           /* selectRecipeStep.setListOfIngredients(listOfIngredients);
            selectRecipeStep.setListofStep(listofSteps);
            fragmentManager.beginTransaction().add(R.id.selectRecipieDetail,selectRecipeStep).commit();*/

            MediaPlayerFragment mediaPlayerFragment=new MediaPlayerFragment();
            //PASS DATA
            mediaPlayerFragment.setObject(listofSteps.get(position));
            fragmentManager.beginTransaction().replace(R.id.playerFragment,mediaPlayerFragment).commit();
            RecipeStepInstruction recipeStepInstruction=new RecipeStepInstruction();
            // PASS DATA HERE
            recipeStepInstruction.setObject(listofSteps.get(position));
            fragmentManager.beginTransaction().replace(R.id.descriptionFragment,recipeStepInstruction).commit();

        }
        else {
            Intent intent=new Intent(this, ViewRecipieStep.class);
            intent.putExtra(STEPS,listofSteps.get(position));
            startActivity(intent);

        }

    }
    public void pass(int pos){
        if (mTwoPane){
            FragmentManager fragmentManager=getSupportFragmentManager();
            SelectRecipeStep selectRecipeStep=new SelectRecipeStep();
            Toast.makeText(this, "CLCIKED"+pos, Toast.LENGTH_SHORT).show();
            // PASS DATA
           /* selectRecipeStep.setListOfIngredients(listOfIngredients);
            selectRecipeStep.setListofStep(listofSteps);
            fragmentManager.beginTransaction().add(R.id.selectRecipieDetail,selectRecipeStep).commit();*/

            MediaPlayerFragment mediaPlayerFragment=new MediaPlayerFragment();
            //PASS DATA
            mediaPlayerFragment.setObject(listofSteps.get(pos));
            fragmentManager.beginTransaction().replace(R.id.playerFragment,mediaPlayerFragment).commit();
            RecipeStepInstruction recipeStepInstruction=new RecipeStepInstruction();
            // PASS DATA HERE
            recipeStepInstruction.setObject(listofSteps.get(pos));
            fragmentManager.beginTransaction().replace(R.id.descriptionFragment,recipeStepInstruction).commit();

        }
        else {
            Intent intent=new Intent(this, ViewRecipieStep.class);
            intent.putExtra(STEPS,listofSteps.get(pos));
            startActivity(intent);

        }

    }
}