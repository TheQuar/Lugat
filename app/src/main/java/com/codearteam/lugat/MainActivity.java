package com.codearteam.lugat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.AppBarLayout;

import java.io.File;


public class MainActivity extends AppCompatActivity   {

    private static final String[] mNames = {
            "Adabiyot", "AKT", "Fizika", "Geografiya", "Iqtisodiyot", "Matematika", "Psixologiya", "Tarix"
    };

    private static  final int[] mImageUrls = {
            R.drawable.mn_adabiyot, R.drawable.mn_akt, R.drawable.mn_fizika, R.drawable.mn_geografiya,
            R.drawable.mn_iqtisod, R.drawable.mn_matematika, R.drawable.mn_psixologiya, R.drawable.mn_tarix
    };

    private ImageView main_down_up_btn;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();

        File file = new File("/data/data/" + getApplicationContext().getPackageName() + "/databases/base");
        if(file.exists())
            Log.i("FORIT", "ok");
        else
            Log.i("FORIT", "no");

        ImageView about_button = findViewById(R.id.about_button);
        main_down_up_btn = findViewById(R.id.main_down_up_button);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar1);

        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about_button_intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(about_button_intent);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + i == 0) {
                    isShow = true;
                    main_down_up_btn.setImageResource(R.drawable.down_button);
                } else if (isShow) {
                    isShow = false;
                    main_down_up_btn.setImageResource(R.drawable.up_button);
                }

            }
        });

    }


    private void initRecyclerView(){
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }



}