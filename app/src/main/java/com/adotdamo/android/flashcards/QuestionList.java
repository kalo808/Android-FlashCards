package com.adotdamo.android.flashcards;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class QuestionList extends AppCompatActivity {

   private ListView mQuestionListView;
   private Button mCreate;
   private ArrayList<Question> FlashCards = new ArrayList<Question>();
   private ArrayList<String> Questions = new ArrayList<String>();
   private DataBaseAdapter mDataBaseAdapter;
   private String tablename;
    Intent intent = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tablename = getIntent().getStringExtra("tablename");

        mDataBaseAdapter = new DataBaseAdapter(tablename,QuestionList.this,false);

        FlashCards = mDataBaseAdapter.QuestionArray(tablename);

        for (int i = 0; i < FlashCards.size();i++)
            {
                Questions.add(FlashCards.get(i).getQuestion());
            }

        mQuestionListView = (ListView) findViewById(R.id.QuestionListView);

        final ArrayAdapter adapter = new ArrayAdapter(QuestionList.this,android.R.layout.simple_list_item_1,Questions ) ;

        mQuestionListView.setAdapter(adapter);

        mQuestionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.EditFlashCard");
                intent.putExtra("Answer",FlashCards.get(position).getAnswer());
                intent.putExtra("Question",FlashCards.get(position).getQuestion());
                intent.putExtra("tablename", tablename);
                startActivity(intent);

            }
        });

        mCreate = (Button) findViewById(R.id.createnewbutton);

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.CreateQuestions");
               // Intent i = new Intent(QuestionList.this, CreateQuestions.class);
                intent.putExtra("tablename", tablename);
                intent.putExtra("previousactivity", "editflashcards");
                startActivity(intent);
            }
        });

        mQuestionListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int mposition = position;

               final Question question = new Question(FlashCards.get(position).getQuestion(), FlashCards.get(position).getAnswer());

                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(QuestionList.this);

                alertdialogbuilder.setMessage("Are You Sure").setTitle("Deleting Question")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDataBaseAdapter.deleteRow(question);
                                Questions.remove(mposition);
                                adapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();



                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
               intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.FlashCardList");
                intent.putExtra("type","edit");
                startActivity(intent);
                return true;
            case R.id.help:
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionList.this);
                builder.setMessage("Long Click to Delete").show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
