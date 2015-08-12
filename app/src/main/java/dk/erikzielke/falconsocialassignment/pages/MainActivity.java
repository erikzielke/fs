package dk.erikzielke.falconsocialassignment.pages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dk.erikzielke.falconsocialassignment.ConfigurationService;
import dk.erikzielke.falconsocialassignment.MovieListItemViewHolder;
import dk.erikzielke.falconsocialassignment.R;
import dk.erikzielke.falconsocialassignment.TheMovieDbApp;
import dk.erikzielke.falconsocialassignment.api.TheMovieDbService;
import dk.erikzielke.falconsocialassignment.injection.DaggerTheMovieDbComponent;
import dk.erikzielke.falconsocialassignment.model.Configuration;
import dk.erikzielke.falconsocialassignment.model.Movie;
import dk.erikzielke.falconsocialassignment.model.MovieSearchResults;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.movie_search_result) RecyclerView movieSearchResult;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.progress) ProgressBar progressBar;

    @Inject TheMovieDbService movieDbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.menu_main);
        ((TheMovieDbApp)getApplication()).getTheMovieDbComponent().inject(this);


        final MovieListAdapter adapter = new MovieListAdapter();
        movieDbService.getConfiguration(getString(R.string.api_key), new Callback<Configuration>() {
            @Override
            public void success(Configuration configuration, Response response) {
                ConfigurationService.setConfiguration(configuration);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        final Callback<MovieSearchResults> movieSearchResultsCallback = getMovieSearchResultsCallback(adapter);
        movieDbService.popular(getString(R.string.api_key), movieSearchResultsCallback);
        movieSearchResult.setAdapter(adapter);



        Menu menu = toolbar.getMenu();
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView actionView = (SearchView) item.getActionView();
        actionView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(newText)) {
                    movieDbService.search(getString(R.string.api_key), newText, movieSearchResultsCallback);
                } else {
                    movieDbService.popular(getString(R.string.api_key), movieSearchResultsCallback);
                }
                return false;
            }
        });

        movieSearchResult.setLayoutManager(new LinearLayoutManager(this));
    }

    @NonNull
    private Callback<MovieSearchResults> getMovieSearchResultsCallback(final MovieListAdapter adapter) {
        return new Callback<MovieSearchResults>() {
            @Override
            public void success(MovieSearchResults movieSearchResults, Response response) {
                progressBar.setVisibility(View.GONE);
                adapter.setMovies(movieSearchResults.getResults());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(movieSearchResult, getString(R.string.could_not_fetch_movie_data), Snackbar.LENGTH_SHORT).show();
            }
        };
    }

    private class MovieListAdapter extends RecyclerView.Adapter<MovieListItemViewHolder> {
        private List<Movie> movies = Collections.emptyList();

        @Override
        public MovieListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_movie_list_item, parent, false);
            return new MovieListItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieListItemViewHolder holder, int position) {
            Movie movie = movies.get(position);
            holder.update(movie);
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

        public void setMovies(List<Movie> results) {
            movies = results;
        }
    }
}
