package com.example.bakingappproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.example.bakingappproject.fragment.MediaPlayerFragment;
import com.example.bakingappproject.fragment.RecipeStepInstruction;
import com.example.bakingappproject.model.Recipe;

import static com.example.bakingappproject.constant.Constant.STEPS;

public class ViewRecipieStep extends AppCompatActivity {
    private static final String TAG=ViewRecipieStep.class.getSimpleName();
    MediaPlayerFragment mediaPlayerFragment;
    RecipeStepInstruction recipeStepInstruction;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipie_step);
        Intent intent=getIntent();
        Recipe.StepsBean object=intent.getParcelableExtra(STEPS);

        Log.e(TAG,"DETAIL OF OBJECT :"+object.getVideoURL()+"\n"+object.getThumbnailURL()+"\n"+object.getDescription());

        mediaPlayerFragment=new MediaPlayerFragment();
        mediaPlayerFragment.setObject(object);
        recipeStepInstruction=new RecipeStepInstruction();
        recipeStepInstruction.setObject(object);
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.playerFragment,mediaPlayerFragment).commit();
        fragmentManager.beginTransaction().add(R.id.descriptionFragment,recipeStepInstruction).commit();







    }
}