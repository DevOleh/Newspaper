package com.example.oleg.newspaper.DataBase;


import com.example.oleg.newspaper.Model.Item;

import java.util.List;

public interface IDataBase {

    void createArticle(Item article);
    List getFavoritesArticles();
}
