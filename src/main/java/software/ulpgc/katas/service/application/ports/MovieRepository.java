package software.ulpgc.katas.service.application.ports;

import software.ulpgc.katas.service.application.domain.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> findAll();
    Optional<Movie> findById(int id);
}