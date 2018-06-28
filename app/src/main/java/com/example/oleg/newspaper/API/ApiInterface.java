package com.example.oleg.newspaper.API;

import com.example.oleg.newspaper.Model.Source;
import com.example.oleg.newspaper.Model.articlesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("everything")
    Call<articlesResponse> getItems(@Query("sources") String source,
                                    @Query("from") String fromDate,
                                    @Query("to") String toDate,
                                    @Query("sortBy") String sortOrder,
                                    @Query("page") int pageNumber,
                                    @Query("apiKey") String apiKey);
    @GET("sources")
    Call<Source> getListSource(@Query("language") String languange,
                               @Query("apiKey") String apiKey);

}
