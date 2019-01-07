package com.sadaqaworks.quranprojects.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sadaqaworks.quranprojects.R;

public class Home extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        button = (Button) findViewById(R.id.buttonPermainan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity("main");
            }
        });

        button = (Button) findViewById(R.id.buttonMushaf);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity("mushaf");
            }
        });
    }

   // public void Permainan(){
   //     Intent intent = new Intent(this, Permainan.class);
   //     startActivity(intent);
   // }

    public void MainActivity(String menu){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("menu",menu);
        startActivity(intent);

    }

}
