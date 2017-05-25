package com.juanitarouse.pollme.model;
 import java.util.List;

 import io.realm.RealmObject;
 import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 5/25/2017.
 */

public class Question extends RealmObject {

    @PrimaryKey
    private String id;
    private String body;
    private Answer answers;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Answer getAnswers() {
        return answers;
    }

    public void setAnswers(Answer answers) {
        this.answers = answers;
    }
}
