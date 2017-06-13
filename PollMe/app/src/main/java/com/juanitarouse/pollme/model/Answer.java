package com.juanitarouse.pollme.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 5/25/2017.
 */

public class Answer extends RealmObject {

    @PrimaryKey
    private String id;
    private int AnswerNumber;
    private String QuestionId;

    public String getBodyAnswer() {
        return bodyAnswer;
    }

    public void setBodyAnswer(String bodyAnswer) {
        this.bodyAnswer = bodyAnswer;
    }

    private String bodyAnswer;

    public int getAnswerNumber() {
        return AnswerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        AnswerNumber = answerNumber;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }
}
