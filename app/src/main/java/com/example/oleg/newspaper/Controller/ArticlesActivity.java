package com.example.oleg.newspaper.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.oleg.newspaper.Filters.ArticleFilter;
import com.example.oleg.newspaper.R;
import com.example.oleg.newspaper.Model.articlesResponse;
import com.example.oleg.newspaper.Model.Item;
import com.example.oleg.newspaper.Fragments.SettingsFragment;
import com.example.oleg.newspaper.Adapters.PaginationScrollListener;
import com.example.oleg.newspaper.Adapters.PaginationAdapter;
import com.example.oleg.newspaper.API.ApiInterface;
import com.example.oleg.newspaper.API.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesActivity extends AppCompatActivity implements SettingsFragment.SettingsFragmentCallBack{
    public static String API_KEY = MainActivity.API_KEY;
    private static final String TAG = ArticlesActivity.class.getSimpleName();

    PaginationAdapter adapter;
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Toolbar toolbar;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private List<Item> results;
    private ApiInterface apiService;
    private String source = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        adapter = new PaginationAdapter(this);
        source = getIntent().getStringExtra("source");
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        apiService = ApiClient.getClient().create(ApiInterface.class);

        loadFirstPage();
    }


    private void loadFirstPage() {

        callNewsApi(currentPage).enqueue(new Callback<articlesResponse>() {
            @Override
            public void onResponse(Call<articlesResponse> call, Response<articlesResponse> response) {
                results = fetchResults(response);
                progressBar.setVisibility(View.GONE);
                Log.i("api response","results body contains "+ results);
                Log.d("response.body()", String.valueOf(results));
                adapter.addAll(results);

                if (adapter.getItemCount() < 1000)
                    adapter.addLoadingFooter();

                else {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<articlesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadNextPage() {

        callNewsApi(currentPage).enqueue(new Callback<articlesResponse>() {
            @Override
            public void onResponse(Call<articlesResponse> call, Response<articlesResponse> response) {
                adapter.removeLoadingFooter();
                isLoading = false;
                results = fetchResults(response);
                adapter.addAll(results);

                if (adapter.getItemCount() < 1050){
                    Log.i("api response","item count "+ adapter.getItemCount());
                    adapter.addLoadingFooter(); }
                else {
                    Log.i("api response","item count is "+ adapter.getItemCount());
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<articlesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Call<articlesResponse> callNewsApi(int currentPage) {

        return apiService.getItems(source,
                ArticleFilter.getArticleFilterInstance().getFromDate(),
                ArticleFilter.getArticleFilterInstance().getToDate(),
                ArticleFilter.getArticleFilterInstance().getSortOrder(),
                currentPage,API_KEY);
    }

    private List<Item> fetchResults(Response<articlesResponse> response) {
        articlesResponse articlesResponse = response.body();

        if (articlesResponse != null) {
            return articlesResponse.getArticles();
        }
        return results;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_filter) {
            FragmentManager fragMan = getSupportFragmentManager();
            SettingsFragment fragment = new SettingsFragment();
            fragment.show(fragMan, "fragment_settings");
            return true;
        }if (item.getItemId() == R.id.action_favorites){
            Intent intent = new Intent(this, FavoritesActivity.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void event() {
        this.startActivity(new Intent(this, ArticlesActivity.class)
                .putExtra("source", source));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ArticleFilter.getArticleFilterInstance().setFromDate(null);
        ArticleFilter.getArticleFilterInstance().setToDate(null);
        ArticleFilter.getArticleFilterInstance().setSortOrder(null);
    }
}
