package com.example.moviesapi.screens;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moviesapi.api.ApiFactory;
import com.example.moviesapi.api.ApiService;
import com.example.moviesapi.data.AppDatabase;
import com.example.moviesapi.pojo.Movie;
import com.example.moviesapi.pojo.MoviesResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MovieViewModel extends AndroidViewModel {

    private static AppDatabase db;
    private LiveData<List<Movie>> movies;
    private CompositeDisposable compositeDisposable;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        movies = db.movieDao().getAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    @SuppressWarnings("unchecked")
    private void insertMovies(List<Movie> movies) {
        new InsertMovieTask().execute(movies);
    }

    private static class InsertMovieTask extends AsyncTask<List<Movie>, Void, Void> {

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Movie>... lists) {
            if (lists != null && lists.length > 0) {
                db.movieDao().insertMovies(lists[0]);
            }
            return null;
        }
    }

    private void deleteAllMovies() {
        new DeleteAllMoviesTask().execute();
    }

    private static class DeleteAllMoviesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            db.movieDao().deleteAllMovies();
            return null;
        }
    }

    void loadData() {
        ApiFactory factory = ApiFactory.getInstance();
        ApiService apiService = factory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesResponse>() {
                    @Override
                    public void accept(MoviesResponse moviesResponse) throws Exception {
                        deleteAllMovies();
                        insertMovies(moviesResponse.getMovies());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

}
