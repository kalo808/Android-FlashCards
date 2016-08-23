package com.adotdamo.android.flashcards;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView mNewFlashCards;
    private TextView mStudy;
    private TextView mEdit;
    private TextView mTest;
    Intent intent = new Intent();
    private DataBaseAdapter mDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
       mAdView.loadAd(adRequest);





        mEdit = (TextView) findViewById(R.id.editflashcards);
        mNewFlashCards = (TextView) findViewById(R.id.makeflashcards);
        mStudy = (TextView) findViewById(R.id.studyflashcards);
        mDataBaseAdapter = new DataBaseAdapter("",MainActivity.this,false);
        mTest = (TextView) findViewById(R.id.flashcardtest);

        mEdit.setOnClickListener(this);
        mNewFlashCards.setOnClickListener(this);
        mStudy.setOnClickListener(this);
        mTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.editflashcards:
                intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.FlashCardList");
                intent.putExtra("type","edit");
                startActivity(intent);
                break;
            case R.id.makeflashcards:
                intent.setClassName("com.adotdamo.android.flashcards", "com.adotdamo.android.flashcards.DeckTitle");
                startActivity(intent);
                break;
            case R.id.studyflashcards:
                intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.FlashCardList");
                intent.putExtra("type","study");
                startActivity(intent);
                break;
            case R.id.flashcardtest:
                intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.FlashCardList");
                intent.putExtra("type","test");
                startActivity(intent);
        }
    }
}
