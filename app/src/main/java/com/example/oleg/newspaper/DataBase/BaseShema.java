package com.example.oleg.newspaper.DataBase;

public class BaseShema {

    public static final class FavoritesArticles{
        public static final String TABLE_NAME = "favorites_articles";
    }

    public static final class ColsFavoritesArticles {
        public static final String UUID = "uuid";
        public static final String ARTICLE_NAME = "name";
        public static final String ARTICCLE_ADRES = "adress";
        public static final String IMG_ADRES = "adressImg";
    }
}