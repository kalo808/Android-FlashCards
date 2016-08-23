package com.adotdamo.android.flashcards;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;


public class Test extends AppCompatActivity {
    private ArrayList<Question> Flashcards = new ArrayList<>();
    private static final String KEY_INDEX = "index";
    private static final String TIME_INDEX = "timeindex";
    private static final String ENTERED_TIME = "enteredtime";
    private static final String CORRECT_INDEX = "correctindex";
    private int mIndex = 0;
    private int mCorrect = 0;
    private TextView mQuestionText;
    private TextView mcorrecttext;
    private TextView mqnumber;
    private EditText mAnswerText;
    private CountDownTimer mtimer;
    private TextView mCorrectorWrong;
    TextView mtimertext;
    EditText mTime;
    int timeleft;
    Boolean mEnterTime = false;


    public void setQuestion()
    {
        mQuestionText.setText(Flashcards.get(mIndex).getQuestion());
        mAnswerText.setText("");
        mqnumber.setText("Question " + (mIndex + 1));
        mcorrecttext.setText(mCorrect + "/" + mIndex);
    }

    public void checkAnswer(String answer)
    {
        if(answer.toLowerCase().equals(Flashcards.get(mIndex).getAnswer().toLowerCase()))
        {
            mCorrectorWrong.setText("Correct");
            mCorrectorWrong.setTextColor(getResources().getColor(R.color.Right));
            mCorrect++;
        }else
        {
            mCorrectorWrong.setText("Wrong");
            mCorrectorWrong.setTextColor(getResources().getColor(R.color.Wrong));
        }
    }

    public void finishDialog()
    {
        mtimer.cancel();
        AlertDialog.Builder finishalert = new AlertDialog.Builder(Test.this);
        finishalert.setCancelable(false);
        finishalert.setTitle("You got " +mCorrect+ " correct out of " +mIndex+ " questions").setMessage("TRY AGAIN")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.MainActivity");
                        startActivity(intent);
                    }
                }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mIndex = 0;
                mCorrect = 0;
                setQuestion();
                mEnterTime = false;
                timerDialog();

            }
        }).show();



    }

    public void timerDialog()
    {

        LayoutInflater inflater = LayoutInflater.from(this);
        View timeview = inflater.inflate(R.layout.user_input, null);
        mTime = (EditText) timeview.findViewById(R.id.time_input);
        final AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setCancelable(false);

        alertdialogbuilder.setTitle("How many Seconds?").setMessage("Time must be under 1000 Seconds").setView(timeview).setNeutralButton("OK", null);

        final AlertDialog dialog = alertdialogbuilder.create();

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int seconds;
                if (mTime.getText().toString().equals("")) {
                    seconds = 1000;
                } else {
                    seconds = Integer.parseInt(mTime.getText().toString());
                }
                if (seconds < 1000) {
                    timer(seconds);
                    mEnterTime = true;
                    dialog.dismiss();
                }

            }
        });

    }

    public void timer(int seconds)
    {
        seconds = seconds * 1000;
        mtimer = new CountDownTimer(seconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mtimertext.setText(millisUntilFinished/1000 + "s");

                timeleft = (int) millisUntilFinished/1000;
            }
            @Override
            public void onFinish() {
                mtimertext.setText("0s");
                finishDialog();
            }
        };
        mtimer.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mtimertext = (TextView) findViewById(R.id.timertext);
        mQuestionText = (TextView) findViewById(R.id.test_question_text);
        mCorrectorWrong = (TextView) findViewById(R.id.correct_or_wrong);
        Button submit = (Button) findViewById(R.id.test_enter);
        mAnswerText = (EditText) findViewById(R.id.test_answertext);
        mqnumber = (TextView) findViewById(R.id.question_number_text);
        mcorrecttext = (TextView) findViewById(R.id.correcttext);

        if(savedInstanceState != null)
        {
            mIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mEnterTime = savedInstanceState.getBoolean(ENTERED_TIME);
            mCorrect = savedInstanceState.getInt(CORRECT_INDEX);
            if(mEnterTime == true) {
                timeleft = savedInstanceState.getInt(TIME_INDEX, 0);
                timer(timeleft);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);




        if(savedInstanceState == null || mEnterTime == false ) {
            timerDialog();
        }

        String tablename = getIntent().getStringExtra("tablename");
        DataBaseAdapter mDataBaseAdapter = new DataBaseAdapter("", Test.this, false);
        Flashcards = mDataBaseAdapter.QuestionArray(tablename);
        if(mIndex == 0)
        {
            setQuestion();
        }

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String answer = mAnswerText.getText().toString();
                    if (mIndex < Flashcards.size() - 1) {
                        checkAnswer(answer);
                        mIndex++;
                        setQuestion();
                    } else if (mIndex == Flashcards.size() - 1) {
                        checkAnswer(answer);
                        mIndex++;
                        mQuestionText.setText("");
                        mAnswerText.setText("");
                        mcorrecttext.setText(mCorrect + "/" + mIndex);
                        finishDialog();

                    }
                }
            });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
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
        savedInstanceState.putInt(TIME_INDEX,timeleft);
        savedInstanceState.putBoolean(ENTERED_TIME, mEnterTime);
        savedInstanceState.putInt(CORRECT_INDEX, mCorrect);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mtimer.cancel();
    }
}
