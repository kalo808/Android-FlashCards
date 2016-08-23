package com.adotdamo.android.flashcards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Study extends AppCompatActivity {

    DataBaseAdapter mDataBaseAdapter;
    ArrayList<Question> Questions = new ArrayList<>();
    private static final String KEY_INDEX = "index";
    int mIndex = 0;
    TextView mQuestiontext;
    TextView mAnswertext;
    TextView mIndexText;


    public void setQuestion()
    {
        mQuestiontext.setText(Questions.get(mIndex).getQuestion());
        mIndexText.setText("QUESTION: "+ (mIndex+1)+ "/" + Questions.size());
        mAnswertext.setText("");
    }

    public void showAnswer()
    {
        mAnswertext.setText(Questions.get(mIndex).getAnswer());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
        {
            mIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }



        setContentView(R.layout.activity_study);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

         mQuestiontext = (TextView) findViewById(R.id.question_text);
         mAnswertext = (TextView) findViewById(R.id.answer_text);
        Button mShowAnswer = (Button) findViewById(R.id.show_answer);
        Button mNext = (Button) findViewById(R.id.next_button);
        Button mPrev = (Button) findViewById(R.id.prev_button);
        TextView mdecktitle = (TextView) findViewById(R.id.study_deck_title);
        mIndexText = (TextView) findViewById(R.id.questionindex);


      String tablename =  getIntent().getStringExtra("tablename");
        mdecktitle.setText("DECK: " + tablename);


        mDataBaseAdapter = new DataBaseAdapter("",Study.this,false);

       Questions = mDataBaseAdapter.QuestionArray(tablename);

            setQuestion();


        if (mShowAnswer != null) {
            mShowAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAnswer();
                }
            });
        }


        if (mNext != null) {
            mNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mIndex < Questions.size()-1)
                    {
                        mIndex++;
                        setQuestion();
                    }
                }
            });
        }

        if (mPrev != null) {
            mPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mIndex > 0)
                    {

                        mIndex--;
                        setQuestion();
                    }
                }
            });
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,mIndex);
    }
}
