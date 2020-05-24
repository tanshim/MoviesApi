package com.example.moviesapi.adapters;

import com.example.moviesapi.R;
import com.example.moviesapi.pojo.Movie;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private OnMovieClickListener onMovieClickListener;

    public List<Movie> getMovies()
    {
        return movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.textViewTitle.setText(movie.getTitle());
        holder.textViewRating.setText(String.format("Popularity: %s", movie.getPopularity()));
        ImageView iv = holder.imageViewPicture;
        String url = "https://image.tmdb.org/t/p/w200" + movie.getPosterPath();
        Picasso.get().load(url).into(iv);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewRating;
        private ImageView imageViewPicture;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            imageViewPicture = itemView.findViewById(R.id.imageViewPicture);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMovieClickListener != null) {
                        onMovieClickListener.onMovieClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(int position);
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
          notifyDataSetChanged();
    }

    public void clear() {
        movies.clear();
         notifyDataSetChanged();
    }

}
