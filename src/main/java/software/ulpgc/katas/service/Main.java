package software.ulpgc.katas.service;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import software.ulpgc.katas.service.application.ports.MovieRepository;
import software.ulpgc.katas.service.application.ports.MovieSerializer;
import software.ulpgc.katas.service.application.usecases.GetMovieUseCase;
import software.ulpgc.katas.service.application.usecases.ListMoviesUseCase;
import software.ulpgc.katas.service.infrastructure.adapters.InMemoryMovieRepository;
import software.ulpgc.katas.service.infrastructure.adapters.JsonMovieSerializer;
import software.ulpgc.katas.service.infrastructure.adapters.endpoints.MovieEndPoint;
import software.ulpgc.katas.service.infrastructure.adapters.endpoints.MoviesEndPoint;

public class Main {
    private static final int PORT = 8081;

    static void main() {
        Javalin.create(Main::configure).start(PORT);
    }

    private static void configure(JavalinConfig config) {
        MovieRepository repository = new InMemoryMovieRepository();
        MovieSerializer serializer = new JsonMovieSerializer();
        config.routes.get("/movies", context -> MoviesEndPoint.use(new ListMoviesUseCase(repository, serializer), context).execute());
        config.routes.get("/movies/{id}", context -> MovieEndPoint.use(new GetMovieUseCase(repository, serializer), context).execute());
    }
}