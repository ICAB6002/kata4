package software.ulpgc.katas.service.infrastructure.adapters;

import software.ulpgc.katas.service.application.domain.Movie;
import software.ulpgc.katas.service.application.ports.MovieRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryMovieRepository implements MovieRepository {
    private final List<Movie> movies = List.of(
            new Movie(1, "El viaje de Chihiro", "Hayao Miyazaki", 2001, 8.6),
            new Movie(2, "Parásitos", "Bong Joon-ho", 2019, 8.5),
            new Movie(3, "Interstellar", "Christopher Nolan", 2014, 8.7),
            new Movie(4, "El laberinto del fauno", "Guillermo del Toro", 2006, 8.2),
            new Movie(5, "Origen", "Christopher Nolan", 2010, 8.8)
    );

    @Override
    public List<Movie> findAll() {
        return movies;
    }

    @Override
    public Optional<Movie> findById(int id) {
        return movies.stream()
                .filter(movie -> movie.id() == id)
                .findFirst();
    }
}