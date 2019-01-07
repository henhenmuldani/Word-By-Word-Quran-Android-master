package com.sadaqaworks.quranprojects.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sadaqaworks.quranprojects.R;
import com.sadaqaworks.quranprojects.database.datasource.AyahWordDataSource;
import com.sadaqaworks.quranprojects.model.AyahWord;
import com.sadaqaworks.quranprojects.model.TriviaQuestion;
import com.sadaqaworks.quranprojects.util.settings.Config;

import java.util.ArrayList;

public class NomorAyat extends AppCompatActivity implements View.OnClickListener {

    ArrayList<AyahWord> ayahWordArrayList;
    Button buttonA, buttonB, buttonC, buttonD;
    TextView  coinText, timeText, question;
    int waktu;
    int skor;
    int numberQuestion;

    ArrayList<TriviaQuestion> quizResource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nomor_ayat);
        long surahId = getIntent().getLongExtra("surah_id",0);
        long ayahNumber = getIntent().getLongExtra("ayah_number",0);

        ayahWordArrayList = getAyahWordsBySurah(surahId, ayahNumber);
        numberQuestion = 0;
        skor = 0;
        waktu = 10;

        initializeComponent();
        coinText.setText(String.valueOf(skor));
        timeText.setText(String.valueOf(waktu));
        question.setText(quizResource.get(numberQuestion).getQuestion());
        buttonA.setText(quizResource.get(numberQuestion).getOptA());
        buttonB.setText(quizResource.get(numberQuestion).getOptB());
        buttonC.setText(quizResource.get(numberQuestion).getOptC());
        buttonD.setText(quizResource.get(numberQuestion).getOptD());

        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);
    }

    private void initializeComponent() {
        coinText = (TextView) findViewById(R.id.coinText);
        timeText = (TextView) findViewById(R.id.timeText);
        question = (TextView) findViewById(R.id.triviaQuestion);
        buttonA = (Button)findViewById(R.id.buttonA);
        buttonB = (Button)findViewById(R.id.buttonB);
        buttonC = (Button)findViewById(R.id.buttonC);
        buttonD = (Button)findViewById(R.id.buttonD);

    }

    public ArrayList<AyahWord> getAyahWordsBySurah(long surah_id, long ayah_number) {
        ArrayList<AyahWord> ayahWordArrayList = new ArrayList<AyahWord>();
        AyahWordDataSource ayahWordDataSource = new AyahWordDataSource(this);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = sp.getString(Config.LANG, Config.defaultLang);
        switch (lang) {
            case Config.LANG_BN:
                ayahWordArrayList = ayahWordDataSource.getBanglaAyahWordsBySurah(surah_id, ayah_number);
                break;
            case Config.LANG_INDO:
                ayahWordArrayList = ayahWordDataSource.getIndonesianAyahWordsBySurah(surah_id, ayah_number);
                break;
            case Config.LANG_EN:
                ayahWordArrayList = ayahWordDataSource.getEnglishAyahWordsBySurah(surah_id, ayah_number);
                break;
        }
        return ayahWordArrayList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonA :
                if(buttonA.getText().toString().equals(quizResource.get(numberQuestion).getAnswer())){
                    skor++;
                    coinText.setText(String.valueOf(skor));
                }
            break;

            case R.id.buttonB :
                if(buttonB.getText().toString().equals(quizResource.get(numberQuestion).getAnswer())){
                    skor++;
                    coinText.setText(String.valueOf(skor));
                }
                break;

            case R.id.buttonC :
                if(buttonC.getText().toString().equals(quizResource.get(numberQuestion).getAnswer())){
                    skor++;
                    coinText.setText(String.valueOf(skor));
                }
                break;

            case R.id.buttonD :
                if(buttonD.getText().toString().equals(quizResource.get(numberQuestion).getAnswer())){
                    skor++;
                    coinText.setText(String.valueOf(skor));
                }
                break;
        }
    }
}
