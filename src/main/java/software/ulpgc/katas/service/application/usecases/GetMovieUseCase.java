package software.ulpgc.katas.service.application.usecases;

import software.ulpgc.katas.service.application.UseCase;
import software.ulpgc.katas.service.application.domain.MovieNotFoundException;
import software.ulpgc.katas.service.application.ports.MovieRepository;
import software.ulpgc.katas.service.application.ports.MovieSerializer;

public class GetMovieUseCase implements UseCase {
    private final MovieRepository repository;
    private final MovieSerializer serializer;

    public GetMovieUseCase(MovieRepository repository, MovieSerializer serializer) {
        this.repository = repository;
        this.serializer = serializer;
    }

    @Override
    public String execute(String value) {
        int id = Integer.parseInt(value);
        return repository.findById(id)
                .map(serializer::serialize)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }
}