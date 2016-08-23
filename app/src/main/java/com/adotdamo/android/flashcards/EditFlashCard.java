package com.adotdamo.android.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditFlashCard extends AppCompatActivity {

    private EditText mQuestionText;
    private EditText mAnswerText;
    private Button done;
    private String Question;
    private String Answer;
    private String Table;
    private DataBaseAdapter mDataBaseAdapter;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__flash__card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        /* Link UI */
        mQuestionText = (EditText) findViewById(R.id.editquestion_text);
        mAnswerText = (EditText) findViewById(R.id.editanswer_text);
        done = (Button) findViewById(R.id.done_edit_button);

        /* Get Data from previous Activity */
        Question = getIntent().getStringExtra("Question");
        Answer = getIntent().getStringExtra("Answer");
        Table = getIntent().getStringExtra("tablename");

        mQuestionText.setText(Question);
        mAnswerText.setText(Answer);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQuestion = mQuestionText.getText().toString();
                String newAnswer = mAnswerText.getText().toString();

                mDataBaseAdapter = new DataBaseAdapter("",EditFlashCard.this,true);
                mDataBaseAdapter.update(Table,Question,Answer,newQuestion,newAnswer);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.QuestionList");
                intent.putExtra("tablename",Table);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
