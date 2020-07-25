package com.example.bakingappproject.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingappproject.R;
import com.example.bakingappproject.model.Recipe;


public class RecipeStepInstruction extends Fragment {

    Recipe.StepsBean object;
    TextView title,description;

    public RecipeStepInstruction() {
        // Required empty public constructor
    }


    public Recipe.StepsBean getObject() {
        return object;
    }

    public void setObject(Recipe.StepsBean object) {
        this.object = object;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_instruction, container, false);

        title=rootView.findViewById(R.id.shortDescription);
        description=rootView.findViewById(R.id.longDescription);
        title.setText(object.getShortDescription());
        description.setText(object.getDescription());
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}