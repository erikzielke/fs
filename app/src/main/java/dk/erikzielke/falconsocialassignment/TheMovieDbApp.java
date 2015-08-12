package dk.erikzielke.falconsocialassignment;

import android.app.Application;

import dk.erikzielke.falconsocialassignment.injection.DaggerTheMovieDbComponent;
import dk.erikzielke.falconsocialassignment.injection.TheMovieDbComponent;
import dk.erikzielke.falconsocialassignment.injection.TheMovieDbModule;

public class TheMovieDbApp extends Application {

    private TheMovieDbComponent theMovieDbComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        theMovieDbComponent = DaggerTheMovieDbComponent.builder()
                .theMovieDbModule(new TheMovieDbModule(this))
                .build();
    }

    public TheMovieDbComponent getTheMovieDbComponent() {
        return theMovieDbComponent;
    }
}
