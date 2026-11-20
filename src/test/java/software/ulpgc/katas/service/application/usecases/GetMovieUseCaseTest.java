package software.ulpgc.katas.service.application.usecases;

import org.junit.jupiter.api.Test;
import software.ulpgc.katas.service.application.domain.MovieNotFoundException;
import software.ulpgc.katas.service.infrastructure.adapters.InMemoryMovieRepository;
import software.ulpgc.katas.service.infrastructure.adapters.JsonMovieSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetMovieUseCaseTest {
    private final GetMovieUseCase useCase = new GetMovieUseCase(new InMemoryMovieRepository(), new JsonMovieSerializer());

    @Test
    void returns_movie_as_json_when_id_exists() {
        assertEquals("{\"id\":3,\"title\":\"Interstellar\",\"director\":\"Christopher Nolan\",\"year\":2014,\"rating\":8.7}",
                useCase.execute("3"));
    }

    @Test
    void throws_not_found_when_id_does_not_exist() {
        MovieNotFoundException e = assertThrows(MovieNotFoundException.class, () -> useCase.execute("99"));
        assertEquals("La película con id 99 no está en el catálogo", e.getMessage());
    }

    @Test
    void throws_number_format_when_id_is_not_a_number() {
        assertThrows(NumberFormatException.class, () -> useCase.execute("abc"));
    }
}