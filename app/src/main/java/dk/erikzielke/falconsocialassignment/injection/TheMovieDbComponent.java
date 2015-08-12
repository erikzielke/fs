package dk.erikzielke.falconsocialassignment.injection;

import javax.inject.Singleton;

import dagger.Component;
import dk.erikzielke.falconsocialassignment.pages.MainActivity;
import dk.erikzielke.falconsocialassignment.pages.MovieActivity;

@Singleton
@Component(modules = TheMovieDbModule.class)
public interface TheMovieDbComponent {

    void inject(MainActivity mainActivity);

    void inject(MovieActivity movieActivity);
}
