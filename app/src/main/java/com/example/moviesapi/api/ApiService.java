package com.example.moviesapi.api;

import com.example.moviesapi.pojo.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("popular?api_key=577dfef89f6833e3fb257f958485c20e&language=en-US&page=1")
    Observable<MoviesResponse> getMovies();
}
