package com.codearteam.lugat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    String table_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();

        TextView title = findViewById(R.id.info_title);
        TextView allText = findViewById(R.id.info_text);
        final ImageView back_button = findViewById(R.id.info_activity_back_btn);

        title.setText(intent.getExtras().getString("title"));
        allText.setText(intent.getExtras().getString("alltext"));

        table_name = intent.getExtras().getString("tableName");


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_intent = new Intent(getApplicationContext(), LessonTypeActivity.class);
                back_intent.putExtra("tableName", table_name);
                startActivity(back_intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}