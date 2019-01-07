package com.sadaqaworks.quranprojects.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sadaqaworks.quranprojects.R;

public class Permainan extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permainan);

        button = (Button) findViewById(R.id.buttonKata);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KataAwal();
            }
        });

        button = (Button) findViewById(R.id.buttonNomor);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NomorAyat();
            }
        });

    }
    public void KataAwal(){
        Intent intent = new Intent(this, KataAwal.class);
        intent.putExtra("surah_id",getIntent().getLongExtra("surah_id",0));
        intent.putExtra("ayah_number",getIntent().getLongExtra("ayah_number",0));
        startActivity(intent);
    }
    public void NomorAyat(){
        Intent intent = new Intent(this, NomorAyat.class);
        intent.putExtra("surah_id",getIntent().getLongExtra("surah_id",0));
        intent.putExtra("ayah_number",getIntent().getLongExtra("ayah_number",0));

        startActivity(intent);
    }

}
