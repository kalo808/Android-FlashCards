package com.adotdamo.android.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class DeckTitle extends AppCompatActivity {

    private Button mCreatebutton;
    private EditText mDeckTitle;
    private TextView mErrorText;
    DataBaseAdapter mDataBaseHelper;
    Intent intent = new Intent();


    public boolean alreadyCreated(String word)
    {
        mDataBaseHelper = new DataBaseAdapter("",DeckTitle.this,false);
        ArrayList<String> deckList = mDataBaseHelper.getTableNames();
        deckList.remove("android_metadata");
        deckList.remove("sqlite_sequence");

        for(String name : deckList)
        {
            if(name.toLowerCase().equals(word.toLowerCase()))
            {
                return true;
            }
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        mDeckTitle = (EditText) findViewById(R.id.decktitle);
        mCreatebutton = (Button) findViewById(R.id.done_creation);
        mErrorText = (TextView) findViewById(R.id.error_text);


        mCreatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = mDeckTitle.getText().toString();

                if(word.equals("")) {
                   mErrorText.setText("ERROR: Must enter a Deck Name");
                }else if(alreadyCreated(word))
                {
                    mErrorText.setText("ERROR: Deck has already been Created");

                }
                else{
                    mDataBaseHelper = new DataBaseAdapter(word, DeckTitle.this, true);
                    intent.setClassName("com.adotdamo.android.flashcards", "com.adotdamo.android.flashcards.CreateQuestions");
                    intent.putExtra("tablename", word);
                    intent.putExtra("previousactivity", "titlecreation");
                    startActivity(intent);
                }

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.MainActivity");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
