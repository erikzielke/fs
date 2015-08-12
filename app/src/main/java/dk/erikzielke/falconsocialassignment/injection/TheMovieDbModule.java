package dk.erikzielke.falconsocialassignment.injection;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dk.erikzielke.falconsocialassignment.BuildConfig;
import dk.erikzielke.falconsocialassignment.R;
import dk.erikzielke.falconsocialassignment.TheMovieDbApp;
import dk.erikzielke.falconsocialassignment.api.TheMovieDbService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module
public class TheMovieDbModule {

    private TheMovieDbApp theMovieDbApp;

    public TheMovieDbModule(TheMovieDbApp theMovieDbApp) {
        this.theMovieDbApp = theMovieDbApp;
    }

    @Provides Context providesContext() {
        return theMovieDbApp;
    }

    @Provides
    @Singleton
    public TheMovieDbService providesMovieDbService(Context context) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        GsonConverter converter = new GsonConverter(gson);

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(context.getString(R.string.base_url))
                .setConverter(converter)
                .build();

        if(BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        return restAdapter.create(TheMovieDbService.class);
    }
}
