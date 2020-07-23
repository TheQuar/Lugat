package com.codearteam.lugat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private String[] mNames;
    private int[] mImageUrls;
    private Context mContext;

    public RecyclerViewAdapter(Context context, String[] mNames, int[] mImageUrls){
        this.mContext = context;
        this.mNames = mNames;
        this.mImageUrls = mImageUrls;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.layout_listitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.textView.setText(mNames[position]);
        holder.imageView.setImageResource(mImageUrls[position]);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LessonTypeActivity.class );

                String table_name= "";
                switch (position){
                    case 0: table_name = "adabiyot"; break;
                    case 1: table_name = "akt"; break;
                    case 2: table_name = "fizika"; break;
                    case 3: table_name = "geografiya"; break;
                    case 4: table_name = "iqtisodiyot"; break;
                    case 5: table_name = "matematika"; break;
                    case 6: table_name = "psixologiya"; break;
                    case 7: table_name = "tarix"; break;
                }

                intent.putExtra("tableName", table_name);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
       TextView textView;
       ImageView imageView;
       ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview1);
            imageView = itemView.findViewById(R.id.imageview1);
            constraintLayout = itemView.findViewById(R.id.lesson_id);
        }
    }




}
