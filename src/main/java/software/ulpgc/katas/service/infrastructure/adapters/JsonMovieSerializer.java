package software.ulpgc.katas.service.infrastructure.adapters;

import com.google.gson.Gson;
import software.ulpgc.katas.service.application.domain.Movie;
import software.ulpgc.katas.service.application.ports.MovieSerializer;

import java.util.List;

public class JsonMovieSerializer implements MovieSerializer {
    private final Gson gson = new Gson();

    @Override
    public String serialize(Movie movie) {
        return gson.toJson(movie);
    }

    @Override
    public String serialize(List<Movie> movies) {
        return gson.toJson(movies);
    }
}