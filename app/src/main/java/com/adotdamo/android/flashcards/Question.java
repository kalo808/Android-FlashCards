package com.adotdamo.android.flashcards;

/**
 * Created by karloandrada on 3/18/16.
 *
 */
public class Question {

    private String mQuestion;
    private String mAnswer;

    public Question(String Question,String Answer)
    {
        mQuestion = Question;
        mAnswer = Answer;
    }

    /*Question Getter. Returns Question String */
    public String getQuestion()
    {
        return mQuestion;
    }

    /*Answer Getter. Returns Answer String */
    public String getAnswer()
    {
        return mAnswer;
    }
}
