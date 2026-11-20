package software.ulpgc.katas.service.application.ports;

import software.ulpgc.katas.service.application.domain.Movie;

import java.util.List;

public interface MovieSerializer {
    String serialize(Movie movie);
    String serialize(List<Movie> movies);
}