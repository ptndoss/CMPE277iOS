package com.example.imagesonair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.Exclude;

public class Upload {
    private String name;
    private String imageURL;

    private String dbKey;

    @Exclude
    public String getDbKey() {
        return dbKey;
    }

    @Exclude
    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    public Upload(){

    }
    public Upload(String name, String URL){
        if(name.trim().equalsIgnoreCase("")){
            name = "Image";
        }

        this.name = name;
        this.imageURL = URL;
    }

    public void setName(String name){
        this.name = name;

    }
    public void setImageURL(String URL){
        this.imageURL = URL;
    }

    public String getName(){
        return name;
    }
    public String getImageURL(){
        return imageURL;
    }
}
