package dk.erikzielke.falconsocialassignment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import dk.erikzielke.falconsocialassignment.model.Configuration;
import dk.erikzielke.falconsocialassignment.model.Movie;
import dk.erikzielke.falconsocialassignment.pages.MovieActivity;

public class MovieListItemViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.list_title) TextView title;
    @InjectView(R.id.list_release_year) TextView year;

    @InjectView(R.id.list_image) ImageView image;

    private Movie movie;
    private final SimpleDateFormat dateFormat;

    public MovieListItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
        dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    }

    public void update(Movie movie) {
        this.movie = movie;
        title.setText(movie.getTitle());
        if (movie.getReleaseDate() != null) {
            year.setText(dateFormat.format(movie.getReleaseDate()));
        }

        if (movie.getPosterPath() != null) {
            Configuration configuration = ConfigurationService.getConfiguration();
            if (configuration != null) {
                Picasso picasso = Picasso.with(itemView.getContext());
                if (BuildConfig.DEBUG) {
                    picasso.setLoggingEnabled(true);
                    picasso.setIndicatorsEnabled(true);
                }
                String url = configuration.getImages().getBaseUrl() + configuration.getImages().getPosterSizes().get(0) + movie.getPosterPath();
                picasso.load(url).into(image);
            }
        }
    }

    @OnClick(R.id.list_root)
    public void showDetails() {
        Context context = itemView.getContext();
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra(MovieActivity.MOVIE, this.movie);
        context.startActivity(intent);
    }
}
