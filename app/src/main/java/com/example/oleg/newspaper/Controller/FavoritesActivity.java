package com.example.oleg.newspaper.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.oleg.newspaper.Adapters.PaginationAdapter;
import com.example.oleg.newspaper.DataBase.BaseHelper;
import com.example.oleg.newspaper.Model.Item;
import com.example.oleg.newspaper.R;

import java.util.List;


public class FavoritesActivity extends AppCompatActivity {

    PaginationAdapter adapter;
    private RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private List<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_favorite);
        adapter = new PaginationAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        onLoadList();
        adapter.addAll(list);
        recyclerView.setAdapter(adapter);
    }
    private void onLoadList(){
        BaseHelper baseHelper = new BaseHelper(this);
        list =  baseHelper.getFavoritesArticles();
    }
}
