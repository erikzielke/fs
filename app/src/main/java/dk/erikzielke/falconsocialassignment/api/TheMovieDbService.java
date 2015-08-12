package dk.erikzielke.falconsocialassignment.api;

import dk.erikzielke.falconsocialassignment.model.Configuration;
import dk.erikzielke.falconsocialassignment.model.Movie;
import dk.erikzielke.falconsocialassignment.model.MovieSearchResults;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface TheMovieDbService {
    @GET("/configuration")
    void getConfiguration(@Query("api_key") String apiKey, Callback<Configuration> configurationCallback);

    @GET("/search/movie")
    void search(@Query("api_key") String apiKey, @Query("query") String query, Callback<MovieSearchResults> movieSearchResultCallback);

    @GET("/movie/popular")
    void popular(@Query("api_key") String apiKey, Callback<MovieSearchResults> movieSearchResultsCallback);

    @GET("/movie/{id}")
    void getMovie(@Query("api_key") String apiKey, @Path("id") long id,  Callback<Movie> movieCallback);
}
