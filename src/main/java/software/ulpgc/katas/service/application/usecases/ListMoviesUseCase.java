package software.ulpgc.katas.service.application.usecases;

import software.ulpgc.katas.service.application.UseCase;
import software.ulpgc.katas.service.application.domain.Movie;
import software.ulpgc.katas.service.application.ports.MovieRepository;
import software.ulpgc.katas.service.application.ports.MovieSerializer;

import java.util.List;

public class ListMoviesUseCase implements UseCase {
    private final MovieRepository repository;
    private final MovieSerializer serializer;

    public ListMoviesUseCase(MovieRepository repository, MovieSerializer serializer) {
        this.repository = repository;
        this.serializer = serializer;
    }

    @Override
    public String execute(String value) {
        return serializer.serialize(value == null ? repository.findAll() : moviesOf(Integer.parseInt(value)));
    }

    private List<Movie> moviesOf(int year) {
        return repository.findAll().stream()
                .filter(movie -> movie.year() == year)
                .toList();
    }
}