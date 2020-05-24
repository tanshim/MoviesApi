package com.example.moviesapi.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.moviesapi.R;
import com.example.moviesapi.adapters.MovieAdapter;
import com.example.moviesapi.pojo.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerViewMovies;
    private MovieAdapter adapter;
    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeContainer = findViewById(R.id.swipeRefresh);
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        adapter = new MovieAdapter();
        adapter.setMovies(new ArrayList<Movie>());
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovies.setAdapter(adapter);
        recyclerViewMovies.setHasFixedSize(true);
        recyclerViewMovies.setItemViewCacheSize(20);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MovieViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setMovies(movies);
            }
        });
        viewModel.loadData();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                viewModel.loadData();
                swipeContainer.setRefreshing(false);
            }
        });
        adapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(int position) {
                Movie movie = adapter.getItem(position);
                createIntent(movie);
            }
        });
    }

    public void createIntent (Movie movie) {

        String title = movie.getTitle();
        String rating = "" + movie.getPopularity();
        String releaseDate = movie.getReleaseDate();
        String overview = movie.getOverview();
        String url = movie.getPosterPath();

        Intent intent = new Intent(getApplication(), ShowInfoActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("rating", rating);
        intent.putExtra("releaseDate", releaseDate);
        intent.putExtra("overview", overview);
        intent.putExtra("url", url);

        startActivity(intent);
    }
}
