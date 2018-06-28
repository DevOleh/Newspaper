package com.example.oleg.newspaper.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.oleg.newspaper.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class BaseHelper extends SQLiteOpenHelper
        implements IDataBase {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "favorites_articles";
    private SQLiteDatabase mSQL;

    public BaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String mSQL = "create table " + BaseShema.FavoritesArticles.TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                BaseShema.ColsFavoritesArticles.UUID + ", " +
                BaseShema.ColsFavoritesArticles.ARTICLE_NAME + ", " +
                BaseShema.ColsFavoritesArticles.ARTICCLE_ADRES +", "+
                BaseShema.ColsFavoritesArticles.IMG_ADRES +
                ")";
        sqLiteDatabase.execSQL(mSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void createArticle(Item article) {
        try {
            mSQL = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            //contentValues.put(BaseShema.ColsFavoritesArticles.UUID, String.valueOf(article.)
            contentValues.put(BaseShema.ColsFavoritesArticles.ARTICLE_NAME, article.getTitle());
            contentValues.put(BaseShema.ColsFavoritesArticles.ARTICCLE_ADRES, article.getUrl());
            contentValues.put(BaseShema.ColsFavoritesArticles.IMG_ADRES, article.getUrlToImage());

            mSQL.insert(BaseShema.FavoritesArticles.TABLE_NAME, null, contentValues);

        } finally {
            mSQL.close();
        }
    }

    @Override
    public List getFavoritesArticles() {
        ArrayList<Item> list = new ArrayList<>();

        mSQL = getReadableDatabase();
        String projection [] = {
                BaseShema.ColsFavoritesArticles.UUID,
                BaseShema.ColsFavoritesArticles.ARTICLE_NAME,
                BaseShema.ColsFavoritesArticles.ARTICCLE_ADRES,
                BaseShema.ColsFavoritesArticles.IMG_ADRES
        };

        Cursor cursor = mSQL.query(BaseShema.FavoritesArticles.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int targetUUID = cursor
                    .getColumnIndex(BaseShema.ColsFavoritesArticles.UUID);
            int targetName = cursor
                    .getColumnIndex(BaseShema.ColsFavoritesArticles.ARTICLE_NAME);
            int targetMacAdres = cursor
                    .getColumnIndex(BaseShema.ColsFavoritesArticles.ARTICCLE_ADRES);
            int targetMacAdresImg = cursor
                    .getColumnIndex(BaseShema.ColsFavoritesArticles.IMG_ADRES);

            while (cursor.moveToNext()) {
                int uuid = cursor.getInt(targetUUID);
                String name = cursor.getString(targetName);
                String url = cursor.getString(targetMacAdres);
                String urlImg = cursor.getString(targetMacAdresImg);

                Item article = new Item();
                article.setTitle(name);
                article.setUrl(url);
                article.setUrlToImage(urlImg);
                list.add(article);
            }
        } finally {
            cursor.close();
            mSQL.close();
        }

        return list;
    }
}
