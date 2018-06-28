package com.example.oleg.newspaper.Controller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.example.oleg.newspaper.API.ApiClient;
import com.example.oleg.newspaper.API.ApiInterface;
import com.example.oleg.newspaper.Adapters.ListSourcesAdapter;
import com.example.oleg.newspaper.Model.Source;
import com.example.oleg.newspaper.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static String API_KEY = "ba01b527b73541e99aa4fd52736a1f70";
    private RecyclerView rvly_list_sources;
    private ListSourcesAdapter listSourcesAdapter;
    private ApiInterface apiService;
    private Source source = new Source();
    private List<Source.ListSource> listSources = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvly_list_sources = (RecyclerView) findViewById(R.id.rvly_list_sources);
        listSourcesAdapter = new ListSourcesAdapter(this, listSources);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvly_list_sources.setLayoutManager(linearLayoutManager);
        rvly_list_sources.setAdapter(listSourcesAdapter);

        loadDataSources();
    }

    private void loadDataSources(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        Call<Source> api = apiService.getListSource("en",API_KEY);
        api.enqueue(new Callback<Source>() {
            @Override
            public void onResponse(Call<Source> call, Response<Source> response) {
                if (response.isSuccessful()){
                    source = response.body();

                    if (source.getStatus().equals("ok")){
                        listSources = source.getSources();
                        listSourcesAdapter.setListSources(listSources);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Source> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }}
