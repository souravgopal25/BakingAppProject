package com.example.bakingappproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.bakingappproject.DetailActivity;
import com.example.bakingappproject.DetailIngredient;
import com.example.bakingappproject.MainActivity;
import com.example.bakingappproject.R;

import com.example.bakingappproject.ViewRecipieStep;
import com.example.bakingappproject.adapter.SelectRecipeStepAdapter;
import com.example.bakingappproject.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;

import static com.example.bakingappproject.DetailActivity.mTwoPane;
import static com.example.bakingappproject.constant.Constant.INGREDIENT;
import static com.example.bakingappproject.constant.Constant.STEPS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectRecipeStep#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectRecipeStep extends Fragment implements SelectRecipeStepAdapter.ListItemClickListener{


    ArrayList<Recipe.IngredientsBean> listOfIngredients;
    ArrayList<Recipe.StepsBean> listofStep;

    TextView ingredient;

    CardView ingredientCard;
    public static int t=0;
    RecyclerView recyclerView;
    Context context;
    OnSelectRecipie mCallback;
    View rootView;
    String LISTOFINGREDIENT="listofingredient";
    String LISTOFSTEP="listofstep";

    public ArrayList<Recipe.IngredientsBean> getListOfIngredients() {
        return listOfIngredients;
    }

    public void setListOfIngredients(ArrayList<Recipe.IngredientsBean> listOfIngredients) {
        this.listOfIngredients = listOfIngredients;
    }

    public ArrayList<Recipe.StepsBean> getListofStep() {
        return listofStep;
    }

    public void setListofStep(ArrayList<Recipe.StepsBean> listofStep) {
        this.listofStep = listofStep;
        Log.e(SelectRecipeStep.class.getSimpleName(),"setListoF step :"+listofStep.size());
    }

    public SelectRecipeStep() {
        this.listofStep=new ArrayList<>();
        this.listOfIngredients=new ArrayList<>();
        // Required empty public constructor
    }
    public interface OnSelectRecipie{
        void onRecipieSelected(int position);
    }


    public static SelectRecipeStep newInstance(String param1, String param2) {
        SelectRecipeStep fragment = new SelectRecipeStep();
        Bundle args = new Bundle();
       /* args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            listOfIngredients=savedInstanceState.getParcelableArrayList(LISTOFINGREDIENT);
            listofStep=savedInstanceState.getParcelableArrayList(LISTOFSTEP);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_select_recipe_step, container, false);
        context=getContext();


            if(true){
                initaliation();
            }
            t+=1;
            Log.e(SelectRecipeStep.class.getSimpleName(),"VALUE OF T is"+t);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            Toast.makeText(context, "NO OF OBJECT"+listofStep.size(), Toast.LENGTH_SHORT).show();
            SelectRecipeStepAdapter selectRecipeStepAdapter=new SelectRecipeStepAdapter(context,listofStep, this::onListItemClick);
            recyclerView.setAdapter(selectRecipeStepAdapter);


            ingredientCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getContext(), DetailIngredient.class);
                    intent.putParcelableArrayListExtra(INGREDIENT,listOfIngredients);
                    startActivity(intent);
                }
            });





        return rootView;
    }
    public void initaliation(){
        ingredient=rootView.findViewById(R.id.ingredient);
        ingredientCard=rootView.findViewById(R.id.ingredientCard);
        recyclerView=rootView.findViewById(R.id.selectRecipie);

        ingredient.setText("INGREDIENT");


    }

    @Override
    public void onListItemClick(int clickedIndex) {
        if (mTwoPane!=true) {
            Intent intent = new Intent(getContext(), ViewRecipieStep.class);
            intent.putExtra(STEPS, listofStep.get(clickedIndex));
            mCallback.onRecipieSelected(clickedIndex);
            startActivity(intent);
        }else {
            pass(clickedIndex);

        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback= (OnSelectRecipie) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"MUST IMPLEMENT onSelectRecipie");
        }

    }
    public void pass(int pos) {
        if (mTwoPane) {
            FragmentManager fragmentManager = getFragmentManager();

            Toast.makeText(getContext(), "CLCIKED" + pos, Toast.LENGTH_SHORT).show();
            // PASS DATA
           /* selectRecipeStep.setListOfIngredients(listOfIngredients);
            selectRecipeStep.setListofStep(listofSteps);
            fragmentManager.beginTransaction().add(R.id.selectRecipieDetail,selectRecipeStep).commit();*/

            MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
            //PASS DATA
            mediaPlayerFragment.setObject(listofStep.get(pos));
            fragmentManager.beginTransaction().replace(R.id.playerFragment, mediaPlayerFragment).commit();
            RecipeStepInstruction recipeStepInstruction = new RecipeStepInstruction();
            // PASS DATA HERE
            recipeStepInstruction.setObject(listofStep.get(pos));
            fragmentManager.beginTransaction().replace(R.id.descriptionFragment, recipeStepInstruction).commit();

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LISTOFINGREDIENT,listOfIngredients);
        outState.putParcelableArrayList(LISTOFSTEP,listofStep);
    }
}