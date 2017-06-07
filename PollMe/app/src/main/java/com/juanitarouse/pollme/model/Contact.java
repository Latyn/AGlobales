package com.juanitarouse.pollme.model;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
/**
 * Created by pc on 7/6/2017.
 */

public class Contact extends RealmObject {


    @PrimaryKey
    private String id;
    private String Name;
    private String Phone;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
