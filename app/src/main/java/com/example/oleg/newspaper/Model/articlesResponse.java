package com.example.oleg.newspaper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class articlesResponse {
    @SerializedName("articles")
    @Expose
    private List<Item> articles;

    public List<Item> getArticles(){
        return articles;
    }

}
