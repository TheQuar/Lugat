package com.codearteam.lugat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LessonTypeActivity extends AppCompatActivity {

    private static ArrayList<String> words;
    private String table_name;
    private static RecyclerViewAdapter1 adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_type);

        Intent intent = getIntent();

        table_name = intent.getExtras().getString("tableName");
        try {
            words = GetDataDB(table_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter1(this, table_name, words);
        recyclerView.setAdapter(adapter);

        TextView title_fan = findViewById(R.id.title_fan);
        title_fan.setText(table_name.toUpperCase());

        searchView = findViewById(R.id.search_view);
        int searchViewTextId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
        EditText searchViewText = searchView.findViewById(searchViewTextId);
        searchViewText.setTextColor(Color.WHITE);
        searchViewText.setHintTextColor(Color.WHITE);
        searchViewText.setTextSize(18);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        ImageView bac_btn = findViewById(R.id.back_btn_1);
        bac_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back_intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_btn);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        this.finish();
        super.onBackPressed();
    }

    private ArrayList<String> GetDataDB(String table_name) throws SQLException {
        DatabaseHelper myDbHelper = new DatabaseHelper(getApplicationContext());
        ArrayList<String> listDataSuz = new ArrayList<>();
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbHelper.openDataBase();

        Cursor myData = myDbHelper.GetTableData(table_name);

        while (myData.moveToNext()) {
            listDataSuz.add(myData.getString(1));
        }

        myData.close();
        myDbHelper.close();

        return listDataSuz;
    }

}