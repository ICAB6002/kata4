package software.ulpgc.katas.service.infrastructure.adapters;

import software.ulpgc.katas.service.application.domain.Movie;
import software.ulpgc.katas.service.application.ports.MovieRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryMovieRepository implements MovieRepository {
    private final List<Movie> movies = List.of(
            new Movie(1, "El padrino", "Francis Ford Coppola", 1972, 9.2),
            new Movie(2, "Pulp Fiction", "Quentin Tarantino", 1994, 8.9),
            new Movie(3, "Amélie", "Jean-Pierre Jeunet", 2001, 8.3),
            new Movie(4, "El señor de los anillos: la comunidad del anillo", "Peter Jackson", 2001, 8.9),
            new Movie(5, "Mar adentro", "Alejandro Amenábar", 2004, 7.7)
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