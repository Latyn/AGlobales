package com.juanitarouse.pollme.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by pc on 14/6/2017.
 */

public class ToSend extends RealmObject {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PrimaryKey
    private String id;

    public String getQuestionToSend() {
        return QuestionToSend;
    }

    public void setQuestionToSend(String questionToSend) {
        QuestionToSend = questionToSend;
    }

    private String QuestionToSend;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;

}
