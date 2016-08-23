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

public class CreateQuestions extends AppCompatActivity {

    EditText mQuestionEditText;
    EditText mAnswerEditText;
    Button mAddQuestionButton;
    String mtitle;

    DataBaseAdapter mDataBaseHelper;

    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        mAddQuestionButton = (Button) findViewById(R.id.add_question_button);
        mQuestionEditText = (EditText) findViewById(R.id.question_edittext);
        mAnswerEditText = (EditText) findViewById(R.id.answer_edittext);
        TextView mDeckTitle = (TextView) findViewById(R.id.flash_card_title);

        mtitle = getIntent().getStringExtra("tablename");

        mDeckTitle.setText(mtitle);

        mAddQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mquestion = mQuestionEditText.getText().toString();
                String manswer = mAnswerEditText.getText().toString();
                        mDataBaseHelper = new DataBaseAdapter(mtitle, CreateQuestions.this,false);
                        long id = mDataBaseHelper.insertData(mtitle, mquestion, manswer);
                        mDataBaseHelper.getTableNames();

                mQuestionEditText.setText("");
                mAnswerEditText.setText("");
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String activity = getIntent().getStringExtra("previousactivity");
                if(activity.equals("editflashcards")) {
                    intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.QuestionList");
                    intent.putExtra("tablename",mtitle);
                    startActivity(intent);

                }else
                {
                    intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.MainActivity");
                    startActivity(intent);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
