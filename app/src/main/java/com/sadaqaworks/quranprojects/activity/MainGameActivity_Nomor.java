package com.sadaqaworks.quranprojects.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sadaqaworks.quranprojects.R;
import com.sadaqaworks.quranprojects.database.datasource.AyahWordDataSource;
import com.sadaqaworks.quranprojects.model.AyahWord;
import com.sadaqaworks.quranprojects.model.TriviaQuestion;
import com.sadaqaworks.quranprojects.util.settings.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainGameActivity_Nomor extends AppCompatActivity {
    Button buttonA, buttonB, buttonC, buttonD;
    TextView questionText, triviaQuizText, timeText, resultText, coinText;
    TriviaQuestion currentQuestion;
    List<TriviaQuestion> list;
    int qid = 0;
    int timeValue = 20;
    int coinValue = 0;
    CountDownTimer countDownTimer;
   // Typeface tb, sb;
    private ArrayList<AyahWord> ayahWordArrayList;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        // get nomor surat dan jumlah ayat dari activity sebelumnya
        long surahId = getIntent().getLongExtra("surah_id",0);
        long ayahNumber = getIntent().getLongExtra("ayah_number",0);
        ayahWordArrayList = getAyahWordsBySurah(surahId, ayahNumber);

        //Initializing variables
        questionText = (TextView) findViewById(R.id.triviaQuestion);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonD = (Button) findViewById(R.id.buttonD);
        triviaQuizText = (TextView) findViewById(R.id.triviaQuizText);
        timeText = (TextView) findViewById(R.id.timeText);
        resultText = (TextView) findViewById(R.id.resultText);
        coinText = (TextView) findViewById(R.id.coinText);

        //This will return us a list of data type TriviaQuestion
        list = new ArrayList<>();
        // TODO

        //Now we gonna shuffle the elements of the list so that we will get questions randomly
        Collections.shuffle(list);

        //currentQuestion will hold the que, 4 option and ans for particular id
        currentQuestion = list.get(qid);

        //countDownTimer
        countDownTimer = new CountDownTimer(22000, 1000) {
            public void onTick(long millisUntilFinished) {

                //here you can have your logic to set text to timeText
                timeText.setText(String.valueOf(timeValue) + "\"");

                //With each iteration decrement the time by 1 sec
                timeValue -= 1;

                //This means the user is out of time so onFinished will called after this iteration
                if (timeValue == -1) {

                    //Since user is out of time setText as time up
                    resultText.setText("Time Up");

                    //Since user is out of time he won't be able to click any buttons
                    //therefore we will disable all four options buttons using this method
                    disableButton();
                }
            }

            //Now user is out of time
            public void onFinish() {
                //We will navigate him to the time up activity using below method
                timeUp();
            }
        }.start();

        //This method will set the que and four options
        updateQueAndOptions();


    }

    void allQuestion() {
        ArrayList<TriviaQuestion> arraylist = new ArrayList<>();

        arraylist.add(new TriviaQuestion("114", "Ayat ke 1", "قُلْ", "مَلِكِ", "إِلَٰهِ", "مِن", "قُلْ"));
        arraylist.add(new TriviaQuestion("114", "Ayat ke 2", "مَلِكِ", "قُلْ", "إِلَٰهِ", "مِن", "مَلِكِ"));
        arraylist.add(new TriviaQuestion("114", "Ayat ke 3", "إِلَٰهِ", "مَلِكِ", "قُلْ", "مِن", "إِلَٰهِ"));
        arraylist.add(new TriviaQuestion("114", "Ayat ke 4", "مِن", "مَلِكِ", "إِلَٰهِ", "قُلْ", "مِن"));
        arraylist.add(new TriviaQuestion("114", "Ayat ke 5", "ٱلَّذِى", "مَلِكِ", "إِلَٰهِ", "مِن", "ٱلَّذِى"));
        arraylist.add(new TriviaQuestion("114", "Ayat ke 6", "مِنَ", "مَلِكِ", "إِلَٰهِ", "قُلْ", "مِنَ"));
    }


    public void updateQueAndOptions() {

        //This method will setText for que and options
        questionText.setText(currentQuestion.getQuestion());
        buttonA.setText(currentQuestion.getOptA());
        buttonB.setText(currentQuestion.getOptB());
        buttonC.setText(currentQuestion.getOptC());
        buttonD.setText(currentQuestion.getOptD());


        timeValue = 20;

        //Now since the user has ans correct just reset timer back for another que- by cancel and start
        countDownTimer.cancel();
        countDownTimer.start();

        //set the value of coin text
        coinText.setText(String.valueOf(coinValue));
        //Now since user has ans correct increment the coinvalue
        coinValue++;

    }

    //Onclick listener for first button
    public void buttonA(View view) {
        //compare the option with the ans if yes then make button color green
        if (currentQuestion.getOptA().equals(currentQuestion.getAnswer())) {
            buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            //Check if user has not exceeds the que limit
            if (qid < list.size() - 1) {

                //Now disable all the option button since user ans is correct so
                //user won't be able to press another option button after pressing one button
                disableButton();

                //Show the dialog that ans is correct
                correctDialog();
            }
            //If user has exceeds the que limit just navigate him to GameWon activity
            else {

                gameWon();

            }
        }
        //User ans is wrong then just navigate him to the PlayAgain activity
        else {

            gameLostPlayAgain();

        }
    }

    //Onclick listener for sec button
    public void buttonB(View view) {
        if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())) {
            buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            if (qid < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            gameLostPlayAgain();
        }
    }

    //Onclick listener for third button
    public void buttonC(View view) {
        if (currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
            buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            if (qid < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {

            gameLostPlayAgain();
        }
    }

    //Onclick listener for fourth button
    public void buttonD(View view) {
        if (currentQuestion.getOptD().equals(currentQuestion.getAnswer())) {
            buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            if (qid < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            gameLostPlayAgain();
        }
    }

    //This method will navigate from current activity to GameWon
    public void gameWon() {
        Intent intent = new Intent(this, GameWon.class);
        startActivity(intent);
        finish();
    }

    //This method is called when user ans is wrong
    //this method will navigate user to the activity PlayAgain
    public void gameLostPlayAgain() {
        Intent intent = new Intent(this, PlayAgain.class);
        startActivity(intent);
        finish();
    }

    //This method is called when time is up
    //this method will navigate user to the activity Time_Up
    public void timeUp() {
        Intent intent = new Intent(this, Time_Up.class);
        startActivity(intent);
        finish();
    }

    //If user press home button and come in the game from memory then this
    //method will continue the timer from the previous time it left
    @Override
    protected void onRestart() {
        super.onRestart();
        countDownTimer.start();
    }

    //When activity is destroyed then this will cancel the timer
    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    //This will pause the time
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    //On BackPressed
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //This dialog is show to the user after he ans correct
    public void correctDialog() {
        final Dialog dialogCorrect = new Dialog(MainGameActivity_Nomor.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_correct);
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();

        //Since the dialog is show to user just pause the timer in background
        onPause();


        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.correctText);
        Button buttonNext = (Button) dialogCorrect.findViewById(R.id.dialogNext);

        //Setting type faces
//        correctText.setTypeface(sb);
//        buttonNext.setTypeface(sb);

        //OnCLick listener to go next que
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This will dismiss the dialog
                dialogCorrect.dismiss();
                //it will increment the question number
                qid++;
                //get the que and 4 option and store in the currentQuestion
                currentQuestion = list.get(qid);
                //Now this method will set the new que and 4 options
                updateQueAndOptions();
                //reset the color of buttons back to white
                resetColor();
                //Enable button - remember we had disable them when user ans was correct in there particular button methods
                enableButton();
            }
        });
    }


    //This method will make button color white again since our one button color was turned green
    public void resetColor() {
        buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
    }

    //This method will disable all the option button
    public void disableButton() {
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
    }

    //This method will all enable the option buttons
    public void enableButton() {
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
    }


}
