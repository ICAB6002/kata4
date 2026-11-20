package software.ulpgc.katas.service.application.usecases;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import software.ulpgc.katas.service.application.domain.Movie;
import software.ulpgc.katas.service.infrastructure.adapters.InMemoryMovieRepository;
import software.ulpgc.katas.service.infrastructure.adapters.JsonMovieSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListMoviesUseCaseTest {
    private final ListMoviesUseCase useCase = new ListMoviesUseCase(new InMemoryMovieRepository(), new JsonMovieSerializer());
    private final Gson gson = new Gson();

    @Test
    void returns_all_movies_when_no_year_is_given() {
        assertEquals(5, moviesIn(useCase.execute(null)).length);
    }

    @Test
    void returns_only_movies_of_the_given_year() {
        Movie[] movies = moviesIn(useCase.execute("2010"));
        assertEquals(1, movies.length);
        assertEquals("Origen", movies[0].title());
    }

    @Test
    void returns_empty_list_when_no_movie_matches_the_year() {
        assertEquals("[]", useCase.execute("1900"));
    }

    @Test
    void throws_number_format_when_year_is_not_a_number() {
        assertThrows(NumberFormatException.class, () -> useCase.execute("abc"));
    }

    private Movie[] moviesIn(String json) {
        return gson.fromJson(json, Movie[].class);
    }
}