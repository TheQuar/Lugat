package com.codearteam.lugat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewAdapter1.MyViewHolder> implements Filterable {

    private ArrayList<String> words;
    private ArrayList<String> searchWord;
    private String table_name;
    private Context mContext;


    public RecyclerViewAdapter1(Context context, String table_name, ArrayList<String> words) {
        this.mContext = context;
        this.words = words;
        this.table_name = table_name;
        this.searchWord = words;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.lesson_type_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.textView.setText(words.get(position));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InfoActivity.class);
                try {
                    intent.putExtra("title", words.get(position));
                    intent.putExtra("alltext", GetDataDB(words.get(position)));
                    intent.putExtra("tableName", table_name);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.word_title);
            linearLayout = itemView.findViewById(R.id.liner_layout_1);
        }

    }

    private String GetDataDB(String searchWord) throws SQLException {
        DatabaseHelper myDbHelper = new DatabaseHelper(mContext);
        String word = "";
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbHelper.openDataBase();

        Cursor myData = myDbHelper.RawQuery(table_name, searchWord);
        myData.moveToFirst();
        word = myData.getString(2);

        myData.close();
        myDbHelper.close();

        return word;
    }

    @Override
    public Filter getFilter() {
        return wordsFilter;
    }

    private Filter wordsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint.toString().isEmpty()) {
                results.values = searchWord;
            } else {
                ArrayList<String> filteredList = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : searchWord) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
                results.values = filteredList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            words = ((ArrayList<String>) results.values);
            notifyDataSetChanged();


        }
    };


}


