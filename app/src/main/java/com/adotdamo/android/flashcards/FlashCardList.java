package com.adotdamo.android.flashcards;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FlashCardList extends AppCompatActivity {
    private ArrayList<String> questions = new ArrayList<String>();
    private DataBaseAdapter mdataBaseHelper;
    private ListView mListView;

    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mdataBaseHelper = new DataBaseAdapter("",FlashCardList.this,false);
        questions = mdataBaseHelper.getTableNames();
        questions.remove("android_metadata");
        questions.remove("sqlite_sequence");
        mdataBaseHelper.deletetable("");





        mListView = (ListView) findViewById(R.id.listView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FlashCardList.this,android.R.layout.simple_list_item_1,questions);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String study = getIntent().getStringExtra("type");
                String table = questions.get(position);
                if(study.equals("study") ) {
                    if(mdataBaseHelper.QuestionArray(table).size() == 0)
                    {
                        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(FlashCardList.this);
                        alertdialogbuilder.setTitle("ERROR")
                                .setMessage("Deck Must Have at least one question")
                                .show();

                    }else {
                        intent.setClassName("com.adotdamo.android.flashcards", "com.adotdamo.android.flashcards.Study");
                        intent.putExtra("tablename", table);
                        startActivity(intent);
                    }
                } else if(study.equals("test"))
                {
                    if(mdataBaseHelper.QuestionArray(table).size() == 0)
                    {
                        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(FlashCardList.this);
                            alertdialogbuilder.setTitle("ERROR")
                                    .setMessage("Deck Must Have at least one question")
                                    .show();

                    }else {
                        intent.setClassName("com.adotdamo.android.flashcards", "com.adotdamo.android.flashcards.Test");
                        intent.putExtra("tablename", table);
                        startActivity(intent);
                    }
                }else if (study.equals("edit"))
                {
                    intent.setClassName("com.adotdamo.android.flashcards", "com.adotdamo.android.flashcards.QuestionList");
                    intent.putExtra("tablename", table);
                    startActivity(intent);
                }


            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int mposition = position;
                final String table = questions.get(position);


                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(FlashCardList.this);



                alertdialogbuilder.setTitle("You are deleting "+table).setMessage("Are You Sure")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mdataBaseHelper.deletetable(table);
                                questions.remove(mposition);
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
                intent.setClassName("com.adotdamo.android.flashcards","com.adotdamo.android.flashcards.MainActivity");
                startActivity(intent);
                return true;
            case R.id.help:
                AlertDialog.Builder builder = new AlertDialog.Builder(FlashCardList.this);
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
