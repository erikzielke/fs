package dk.erikzielke.falconsocialassignment.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import dk.erikzielke.falconsocialassignment.BuildConfig;
import dk.erikzielke.falconsocialassignment.ConfigurationService;
import dk.erikzielke.falconsocialassignment.R;
import dk.erikzielke.falconsocialassignment.TheMovieDbApp;
import dk.erikzielke.falconsocialassignment.api.TheMovieDbService;
import dk.erikzielke.falconsocialassignment.model.Configuration;
import dk.erikzielke.falconsocialassignment.model.Movie;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

import static dk.erikzielke.falconsocialassignment.ListUtils.last;

public class MovieActivity extends AppCompatActivity {
    public static final String MOVIE = "movie";

    @InjectView(R.id.movie_title) TextView title;
    @InjectView(R.id.movie_genre) TextView genre;
    @InjectView(R.id.movie_description) TextView overview;
    @InjectView(R.id.movie_rating) TextView rating;
    @InjectView(R.id.movie_votes) TextView votes;
    @InjectView(R.id.backdrop) ImageView backdrop;

    @Inject TheMovieDbService movieDbService;
    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.inject(this);
        ((TheMovieDbApp)getApplication()).getTheMovieDbComponent().inject(this);

        movie = (Movie) getIntent().getSerializableExtra(MovieActivity.MOVIE);
        Configuration configuration = ConfigurationService.getConfiguration();
        if (configuration != null) {
            Picasso with = Picasso.with(MovieActivity.this);
            if (BuildConfig.DEBUG) {
                with.setLoggingEnabled(true);
            }
            String path = configuration.getImages().getBaseUrl() + last(configuration.getImages().getBackdropSizes()) + movie.getBackdropPath();
            with.load(path).into(backdrop);

        }
        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());

        movieDbService.getMovie(getString(R.string.api_key), movie.getId(), new Callback<Movie>() {
            @Override
            public void success(Movie movie, Response response) {
                MovieActivity.this.movie = movie;
                overview.setText(movie.getOverview());
                genre.setText(TextUtils.join(", ", movie.getGenres()));
                NumberFormat numberFormat = NumberFormat.getInstance();
                rating.setText(numberFormat.format(movie.getVoteAverage()));
                votes.setText(String.valueOf(movie.getVoteCount()));
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(overview, R.string.could_not_fecth_movie_data, Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.imdb)
    public void openOnImdb() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.imdbUrl, movie.getImdbId()))));

    }

}
