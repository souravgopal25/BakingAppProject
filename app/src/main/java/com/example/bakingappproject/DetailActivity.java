package com.example.bakingappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bakingappproject.constant.Constant;
import com.example.bakingappproject.fragment.MediaPlayerFragment;
import com.example.bakingappproject.fragment.RecipeStepInstruction;
import com.example.bakingappproject.fragment.SelectRecipeStep;
import com.example.bakingappproject.model.Recipe;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.example.bakingappproject.constant.Constant.ID;
import static com.example.bakingappproject.constant.Constant.INGREDIENT;
import static com.example.bakingappproject.constant.Constant.RECIPIENAME;
import static com.example.bakingappproject.constant.Constant.STEPS;

public class DetailActivity extends AppCompatActivity implements SelectRecipeStep.OnSelectRecipie{
    ArrayList<Recipe.IngredientsBean> listOfIngredients;
    ArrayList<Recipe.StepsBean> listofSteps;
    SelectRecipeStep selectRecipeStep;
    FragmentManager fragmentManager;
    Bundle bundle;
    int  id;
    private SharedPreferences sharedPreferences;
    public static boolean mTwoPane;
    private LinearLayout layoutRecipeInfo;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        listOfIngredients=intent.getParcelableArrayListExtra(INGREDIENT);
        listofSteps=intent.getParcelableArrayListExtra(STEPS);
        id=intent.getIntExtra(ID,0);
        name=intent.getStringExtra(name);
        layoutRecipeInfo=findViewById(R.id.layout_recipie_info);
        bundle=new Bundle();
        bundle.putParcelableArrayList(INGREDIENT,listOfIngredients);
        bundle.putParcelableArrayList(STEPS,listofSteps);

        sharedPreferences=getSharedPreferences(BuildConfig.APPLICATION_ID,MODE_PRIVATE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.recipe_menu,menu);
        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        menu.findItem(R.id.mi_action_widget).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.mi_action_widget){
            boolean isRecipeInWidget = (sharedPreferences.getInt(Constant.PREFERENCES_ID, -1) == id);

            // If recipe already in widget, remove it
            if (isRecipeInWidget){
                sharedPreferences.edit()
                        .remove(Constant.PREFERENCES_ID)
                        .remove(Constant.PREFERENCES_WIDGET_TITLE)
                        .remove(Constant.PREFERENCES_WIDGET_CONTENT)
                        .apply();


                Snackbar.make(layoutRecipeInfo, "Recipe Removed From Widget", Snackbar.LENGTH_SHORT).show();
            }
            // if recipe not in widget, then add it
            else{
                sharedPreferences
                        .edit()
                        .putInt(Constant.PREFERENCES_ID, id)
                        .putString(Constant.PREFERENCES_WIDGET_TITLE, name)
                        .putString(Constant.PREFERENCES_WIDGET_CONTENT, ingredientsString())
                        .apply();


                Snackbar.make(layoutRecipeInfo, "Recipie Added to Widget", Snackbar.LENGTH_SHORT).show();
            }

            // Put changes on the Widget
            ComponentName provider = new ComponentName(this, BakingWidgetProvider.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] ids = appWidgetManager.getAppWidgetIds(provider);
            BakingWidgetProvider bakingWidgetProvider = new BakingWidgetProvider();
            bakingWidgetProvider.onUpdate(this, appWidgetManager, ids);
        }
        return super.onOptionsItemSelected(item);
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
    private String ingredientsString(){
        String result = "";
        for (Recipe.IngredientsBean ingredient :  listOfIngredients){
            String str=ingredient.getQuantity()+"  "+ingredient.getMeasure()+"  "+ingredient.getIngredient()+" \n";
            result+=str;
        }
        return result;
    }
}