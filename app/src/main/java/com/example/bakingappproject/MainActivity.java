package com.example.bakingappproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingappproject.adapter.RecipieAdapter;
import com.example.bakingappproject.constant.Constant;
import com.example.bakingappproject.model.Recipe;
import com.example.bakingappproject.networking.ApiInterface;
import com.example.bakingappproject.networking.RetrofitRequest;
import com.example.bakingappproject.utils.NetworkCheck;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bakingappproject.constant.Constant.ID;
import static com.example.bakingappproject.constant.Constant.INGREDIENT;
import static com.example.bakingappproject.constant.Constant.RECIPIENAME;
import static com.example.bakingappproject.constant.Constant.STEPS;

public class MainActivity extends AppCompatActivity implements RecipieAdapter.ListItemClickListener {

    @BindView(R.id.recyclerMain)
    RecyclerView recyclerMain;
    private RecipieAdapter recipieAdapter;
    private List<Recipe> listofRecipe;
    private ApiInterface apiInterface;
    private String TAG=MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        IdlingRes.setIdleResourceTo(false);
        initialization();
        getRecipie();
        populateRecycler();
    }

    private void populateRecycler() {

    }

    private void getRecipie() {
        if (NetworkCheck.connected(this)!=false){
            apiInterface.getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    listofRecipe=response.body();
                    Log.e(TAG,"RESPONSE.body is of class "+response.body().getClass());
                    Log.e(TAG,"1 ST :"+listofRecipe.get(0).getName());
                    recipieAdapter.setRecipie(response.body());
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.e(TAG,t.getMessage());

                }
            });

        }


    }

    private void initialization() {
        /*listofRecipe=new ArrayList<>();*/
        apiInterface= RetrofitRequest.getRetrofitInstance().create(ApiInterface.class);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,calculateNoOfColumns(this));
        recyclerMain.setLayoutManager(gridLayoutManager);
        recyclerMain.setHasFixedSize(true);
        recipieAdapter=new RecipieAdapter(this,this::onListItemClick);
        recyclerMain.setAdapter(recipieAdapter);
    }
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    @Override
    public void onListItemClick(int clickedIndex) {
        Intent intent=new Intent(MainActivity.this,DetailActivity.class);
        List<Recipe.IngredientsBean> list1=listofRecipe.get(clickedIndex).getIngredients();
        List<Recipe.StepsBean> list2=listofRecipe.get(clickedIndex).getSteps();
        intent.putParcelableArrayListExtra(INGREDIENT, (ArrayList<? extends Parcelable>) list1);
        intent.putParcelableArrayListExtra(STEPS, (ArrayList<? extends Parcelable>) list2);
        intent.putExtra(ID,listofRecipe.get(clickedIndex).getId());
        intent.putExtra(RECIPIENAME,listofRecipe.get(clickedIndex).getName());

        startActivity(intent);





    }
}